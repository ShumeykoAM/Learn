package ru.bloodliviykot.footboll;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;
import android.os.PowerManager.WakeLock;

import java.util.Random;

import common.geometry.*;

public class GameRender
  implements Renderer,           //Реализуем интерфейс рендера
             SensorEventListener //Реализуем интерфейс обработки сенсоров
{
  Random rand;
  public GameRender(GLSurfaceView _glView, Activity _activity, WakeLock _wakeLock)
  {
    glView   = _glView;
    activity = _activity;
    wakeLock = _wakeLock;
    rand = new Random();
  }
  private GLSurfaceView glView;
  private Activity activity;
  WakeLock wakeLock;
  
  private static final int VERTEX_TYPE_SIZE = 4; //sizeof(float)
  private static final int INDEX_TYPE_SIZE  = 2; //sizeof(short)
  private static final int VERTEX_SIZE = 44; //размер одной вершины в байтах
  private FloatBuffer vertices; //Нативный буфер вершин
  private ShortBuffer indices;  //Нативный буфер индексов вершин
  private int textureIds[] = new int[2]; //Массив id_шников текстур
  
  private int Width;
  private int Height;
  private int radius;
  int size_view;
  int rotation;
  
  //Координаты лунок на рисунке текстуры (256.256)
  float points[][] = { {55 ,53 }, {42,157}, {35 ,198}, {116,73}, {116,246},
                       {139,138}, {181,95}, {171,236}, {194,36}, {238,152}
                     };
  CVector2D loonks[] = new CVector2D[10];
  private void init()
  {
    Width  = glView.getWidth();
    Height = glView.getHeight();
    
    size_view = Width < Height ? Width : Height;
    radius = size_view / 2;
    
    //Можно узнать был ли повернут экран (в манифесте) от его поворота по умолчанию
    rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
    
    float muler = size_view / 256.0f;
    for(int i=0; i<10; i++)
    {
      loonks[i] = new CVector2D( (points[i][0]-128)*muler, ((256-points[i][1])-128)*muler );
    }
    
    //Список вершин           координаты 2D(или 3D) цвет(или цвет материала) координаты текстур     нормали
    float arrVertices[] = {/*0*/ -radius, -radius,    1.0f, 1.0f, 1.0f, 1.0f,    0.0f, 1.0f,      0.0f ,0.0f, 1.0f,     
                           /*1*/ -radius,  radius,    1.0f, 1.0f, 1.0f, 1.0f,    0.0f, 0.0f,      0.0f ,0.0f, 1.0f,
                           /*2*/  radius,  radius,    1.0f, 1.0f, 1.0f, 1.0f,    1.0f, 0.0f,      0.0f ,0.0f, 1.0f,
                           /*3*/  radius, -radius,    1.0f, 1.0f, 1.0f, 1.0f,    1.0f, 1.0f,      0.0f ,0.0f, 1.0f,
                           
                           /*0*/ -radius/50, -radius/50,    1.0f, 1.0f, 1.0f, 1.0f,    0.0f, 1.0f,      0.0f ,0.0f, 1.0f,     
                           /*1*/ -radius/50,  radius/50,    1.0f, 1.0f, 1.0f, 1.0f,    0.0f, 0.0f,      0.0f ,0.0f, 1.0f,
                           /*2*/  radius/50,  radius/50,    1.0f, 1.0f, 1.0f, 1.0f,    1.0f, 0.0f,      0.0f ,0.0f, 1.0f,
                           /*3*/  radius/50, -radius/50,    1.0f, 1.0f, 1.0f, 1.0f,    1.0f, 1.0f,      0.0f ,0.0f, 1.0f
                          };
    
    //Создаем нативный буфер вершин
    //Резервируем нужное количество байт (количество вершин * размер типа вершин)
    ByteBuffer byteBuffer1 = ByteBuffer.allocateDirect(arrVertices.length * VERTEX_TYPE_SIZE);
    byteBuffer1.order(ByteOrder.nativeOrder()); //Тип
    vertices = byteBuffer1.asFloatBuffer();     //Интерпретировать как
    vertices.put( arrVertices );                //Запихиваем данные
    vertices.flip();                            //Тоже надо для установки
    
    //Индексы
    //Список индексов
    short arrIndices[] = { 0, 1, 2,
                           0, 2, 3,
                           
                           4, 5, 6,
                           4, 6, 7,
                         };
    //Создаем нативный буфер индексов
    //Резервируем нужное количество байт (количество индексов * размер типа индексов)
    ByteBuffer byteBuffer2 = ByteBuffer.allocateDirect(arrIndices.length * INDEX_TYPE_SIZE );
    byteBuffer2.order(ByteOrder.nativeOrder()); //Тип
    indices = byteBuffer2.asShortBuffer();      //Интерпретировать как
    indices.put( arrIndices );                  //Запихиваем данные
    indices.flip();                             //Тоже надо для установки
  }

  @Override
  public void onSurfaceCreated(GL10 gl, EGLConfig config)
  {
    init();
    //Задаем область просмотра в нашем фрейм буфере (область на экране где будет отрисовываться изображение)
    //gl.glViewport( (Width-size_view)/2, (Height-size_view)/2, size_view, size_view );
    gl.glViewport( 0, 0, Width, Height );
    
    //Устанавливаем матрицу проекции (паралельную-ортогональную или перспективную)
    gl.glMatrixMode(GL10.GL_PROJECTION); //Далее все матричные методы будут работать с матрицей проекции
    gl.glLoadIdentity();                 //Сбрасываем текущую активную матрицу, делаем ее единичной
    //gl.glOrthof(-size_view, size_view, -size_view, size_view, 10, -10);  //Здесть идут координаты куба отсечения (просмотра)
    gl.glOrthof(-Width/2, Width/2, -Height/2, Height/2, 10, -10);  //Здесть идут координаты куба отсечения (просмотра)
    
    gl.glClearColor(0.4f, 0.5f, 0.7f, 0.15f); //Устанавливаем состояние цвета фона
    
    //Включить нормализацию нормалей при масштабировании
    gl.glEnable(GL10.GL_NORMALIZE);
    
    gl.glEnable(GL10.GL_BLEND); //Включить альфа смешивание
    gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA); //Способ смешивания
    
    gl.glEnable(GL10.GL_LIGHTING); //Включаем режим использования света
    //Глобальный источник подсветки, есть только один такой
    float[] ambientColor = { 0.0f, 0.0f, 0.0f, 1.0f };
    //float[] ambientColor = { 1.0f, 1.0f, 1.0f, 1.0f };
    gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, ambientColor, 0); //Задаем глобальный источник подсветки
    //Включаем первый источник света
    gl.glEnable(GL10.GL_LIGHT0); 
    float[] ambient_1  = { 1.0f, 1.0f, 1.0f, 1.0f }; // Массивы float_ов
    float[] diffuse_1  = { 1.0f, 1.0f, 1.0f, 1.0f }; //   в которых перечислены
    float[] specular_1 = { 1.0f, 1.0f, 1.0f, 1.0f }; //   красный, синий, зеленый, и альфа
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT , ambient_1 , 0);
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE , diffuse_1 , 0);
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specular_1, 0);
    float[] position = {0.0f, 0.0f, -5, 0}; //x, y, z - точка, 0 - говорит о том что свет направлен из точки в центр
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, position, 0);
    
    //Разрешить openGL использовать цвет вершин в качестве фонового и рассеянного цветов материала
    //  это необязательно (хотя удобно), можно указывать свойства материала в методе glMaterialfv(ниже)
    gl.glEnable(GL10.GL_COLOR_MATERIAL); //Включаем работу с материалами, фоновый и рассеянный цвета брать из точек
    
    float[] specular = { 1.0f, 1.0f, 1.0f, 1.0f }; // красный, синий, зеленый, и альфа
    gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specular, 0); // Зеркальный цвет материала
    

    //Указываем openGL что работаем с вершинами которые имеют позиции
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY); // правда других вершин не бывает (по ходу метод не обязателен)
    //Указываем openGL что у вершин есть цвет
    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
    //Указываем OpenGL что у вершин есть текстурные координаты
    gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    //Указываем OpenGL что у вершин есть нормали
    gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

    //Указываем нативный буфер вершин
    vertices.position(0);  //Смещаемся на начало описания коордтинат первой вершины
    gl.glVertexPointer( 2,             // Количество координат в вершинах (2 или 3)  
                        GL10.GL_FLOAT, // Тип данных хранимых в нативном буфере
                        VERTEX_SIZE,   // Шаг (в байтах) между вершинами, если кроме координат еще что то есть
                        vertices       // Сам буфер, который стоит на нужной позиции ( position )
                       );
    
    //Указываем нативный буфер цветов вершин
    vertices.position(2); //Смещаемся на начало описания цвета первой вершины
    gl.glColorPointer( 4,             // Количество float_ов описывающих цвет  
                       GL10.GL_FLOAT, // Тип данных хранимых в нативном буфере
                       VERTEX_SIZE,   // Шаг (в байтах) между вершинами, если кроме координат еще что то есть
                       vertices       // Сам буфер, который стоит на нужной позиции ( position )
                      );
    //Указываем нативный буфер координат текстуры
    vertices.position(6); //Смещаемся на первую текстурную координату
    gl.glTexCoordPointer(2,             // Количество float_ов описывающих текстурные координаты
                         GL10.GL_FLOAT, // Тип данных хранимых в нативном буфере
                         VERTEX_SIZE,   // Шаг (в байтах) между вершинами, если кроме координат еще что то есть
                         vertices       // Сам буфер, который стоит на нужной позиции ( position )
                        );
    //Указываем нативный буфер нормалей
    vertices.position(8); //Смещаемся на первую текстурную координату
    gl.glNormalPointer(GL10.GL_FLOAT, // Тип данных хранимых в нативном буфере
                       VERTEX_SIZE,   // Шаг (в байтах) между вершинами, если кроме координат еще что то есть
                       vertices       // Сам буфер, который стоит на нужной позиции ( position )
                      );

    
    //Смещаемся на начало описания индексов
    indices.position(0); //Указывать буфер индексов будем непосредственно при рисовании
    
    
    //Загружаем текстуру
    gl.glEnable(GL10.GL_TEXTURE_2D); //Включаем режим привязывания текстур к треугольникам
    
    AssetManager assets = activity.getAssets(); //Для работы с папкой assets в проекте 
    InputStream is = null;
    try
    {
      is = assets.open("field.png"); //Создает файловый поток файла из папки assets в проекте 
    }
    catch (IOException e)
    {  }
    Bitmap bitmap = BitmapFactory.decodeStream(is); //Создаем битмап из файлового потока
    
    //Создаем объекты текстур
    gl.glGenTextures(2,          // Количество объектов текстур
                     textureIds, // Массив для id_шников объектов текстур
                     0           // Индекс в массиве id_шников с которого начинаем запись (в данном случае с нулевого)
                    );
    //Говорим openGl что последующие методы будут работать с указанной текстурой
    gl.glBindTexture(GL10.GL_TEXTURE_2D, //Тип текстуры
                     textureIds[0]       //id объекта текстуры
                    );
    //Прикрепляем к текущему объекту текстуры графические данные
    GLUtils.texImage2D(GL10.GL_TEXTURE_2D, // Тип текстуры
                       0,                  // Уровень детализации
                       bitmap,             // Изображение (должно быть квадратным с размером стороны = число в степени 2)
                       0                   // Всегда 0
                      );
    //Два метода ниже задают фильтрацию текущего объекта текстуры (типа сглаживание ...)
    //Задаем способ фильтрации когда размер рисуемого изображения больше размера изображения взятого у текстуры (магнификация) 
    gl.glTexParameterf(GL10.GL_TEXTURE_2D,         // Тип текстуры
                       GL10.GL_TEXTURE_MAG_FILTER, // Для какого случая задаем фильт (в данном случае для магнификации)
                       GL10.GL_NEAREST             // Способ фильтрации
                      );
    //Задаем способ фильтрации когда размер рисуемого изображения меньше размера изображения взятого у текстуры (минификация)
    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
    
    //Отвязываем текущий объект текстуры от контекста openGL
    gl.glBindTexture(GL10.GL_TEXTURE_2D, // Тип текстуры
                     0                   // Вместо id_шника объекта текстуры 0 
                    );
    bitmap.recycle(); //Освобождаем память занятую битмапом, можно его повторно использовать
    
    is = null;
    try
    {
      is = assets.open("boll.png"); //Создает файловый поток файла из папки assets в проекте 
    }
    catch (IOException e)
    {  }
    bitmap = BitmapFactory.decodeStream(is); //Создаем битмап из файлового потока
    
    //Говорим openGl что последующие методы будут работать с указанной текстурой
    gl.glBindTexture(GL10.GL_TEXTURE_2D, //Тип текстуры
                     textureIds[1]       //id объекта текстуры
                    );
    //Прикрепляем к текущему объекту текстуры графические данные
    GLUtils.texImage2D(GL10.GL_TEXTURE_2D, // Тип текстуры
                       0,                  // Уровень детализации
                       bitmap,             // Изображение (должно быть квадратным с размером стороны = число в степени 2)
                       0                   // Всегда 0
                      );
    //Два метода ниже задают фильтрацию текущего объекта текстуры (типа сглаживание ...)
    //Задаем способ фильтрации когда размер рисуемого изображения больше размера изображения взятого у текстуры (магнификация) 
    gl.glTexParameterf(GL10.GL_TEXTURE_2D,         // Тип текстуры
                       GL10.GL_TEXTURE_MAG_FILTER, // Для какого случая задаем фильт (в данном случае для магнификации)
                       GL10.GL_NEAREST             // Способ фильтрации
                      );
    //Задаем способ фильтрации когда размер рисуемого изображения меньше размера изображения взятого у текстуры (минификация)
    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
    
    //Отвязываем текущий объект текстуры от контекста openGL
    gl.glBindTexture(GL10.GL_TEXTURE_2D, // Тип текстуры
                     0                   // Вместо id_шника объекта текстуры 0 
                    );
    bitmap.recycle(); //Освобождаем память занятую битмапом, можно его повторно использовать

  }

  @Override
  public void onSurfaceChanged(GL10 gl, int width, int height)
  {
    
  }

  private float acceleration_x = 0.0f, acceleration_y = 0.0f;
  private float speed_x = 0.0f, speed_y = 0.0f;
  private float x = 0.0f, y = 0.0f;
  float TimeOld = -1;
  @Override
  public void onDrawFrame(GL10 gl)
  {
    Physics(gl);
    
    gl.glClear(GL10.GL_COLOR_BUFFER_BIT);  //Очищаем буфер цвета

    gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIds[0]);
    indices.position(0);
    //Меняем матрицу мировых преобразований
    gl.glMatrixMode(GL10.GL_MODELVIEW); //Работаем с матрицей модели (с мировыми координатами)
    gl.glLoadIdentity();                //Сбрасываем матрицу
    //Рисуем элементы с помощью индексов
    gl.glDrawElements(GL10.GL_TRIANGLES,      // Тип рисуемого элемента
                      6,                      //Количество индексов
                      GL10.GL_UNSIGNED_SHORT, //Типы индексов
                      indices                 //Нативный буфер индексов
                     );
    gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIds[1]);    
    indices.position(6);
    //Меняем матрицу мировых преобразований
    gl.glMatrixMode(GL10.GL_MODELVIEW); //Работаем с матрицей модели (с мировыми координатами)
    gl.glLoadIdentity();                //Сбрасываем матрицу
    gl.glTranslatef(x, y, 0);           //Перемещаем координаты на указанные значения x, y z
//    gl.glRotatef(angel, rx, ry, rz);    //Вращяем вокруг вектора (rx,ry,rz) на угол angel
//    gl.glScalef(sx, sy, sz);            //Масштабируем в по оси x в sx раз по оси y и z в sy и sz раз    
    
    //Рисуем элементы с помощью индексов
    gl.glDrawElements(GL10.GL_TRIANGLES,      // Тип рисуемого элемента
                      6,                      //Количество индексов
                      GL10.GL_UNSIGNED_SHORT, //Типы индексов
                      indices                 //Нативный буфер индексов
                     );    
  }

  int cur_lunka = -1;  
  //Физака объектов
  void PhisCollider(GL10 gl)
  {
    for(int i=0; i<9; i++)
    {
      CVector2D v = new CVector2D(new CVector2D(x,y), loonks[i] );
      if(v.module() < radius / 30)
      {
        if(cur_lunka != i)
          gl.glClearColor(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1);
        cur_lunka = i;
        break;
      }
      else if(cur_lunka == i)
        cur_lunka = -1;
    }    
  }
  public void Physics(GL10 gl)
  {
    float Time = System.nanoTime();
    if(TimeOld == -1)
      TimeOld = Time;
    float mul = (Time - TimeOld)/630000000;
    TimeOld = Time;
    
    speed_x += acceleration_x*mul;
    speed_y += acceleration_y*mul;
    
    x += speed_x;
    y += speed_y;
    
    float r_vector = (float)Math.sqrt(x*x + y*y);
    
    if(r_vector >= radius - radius / 50)
    {
      float k = (float)Math.sqrt(x*x + y*y);
      x = x / k * (radius - radius / 49);
      y = y / k * (radius - radius / 49);
    
      CVector2D speed = new CVector2D(speed_x, speed_y);
      CVector2D orto = new CVector2D(x, y);
      orto.ortogonal(); //Вектор параллельный касательной
      speed.projectionV(orto);
      
      speed_x = speed.vx;
      speed_y = speed.vy;      
    }
    speed_x /= 1.01f;
    speed_y /= 1.01f;
    PhisCollider(gl);
  }

  @Override
  public void onSensorChanged(SensorEvent event)
  {        
    if( rotation == 0 /*или 2*/)
    {
      acceleration_y = -event.values[1];
      acceleration_x = -event.values[0];
    }
    else
    {
      acceleration_y = -event.values[0];
      acceleration_x =  event.values[1];      
    }
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy)
  {
    // TODO Auto-generated method stub
    
  }
  
  
  
}
































