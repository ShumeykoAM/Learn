package ru.bloodliviykot.RR.game;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.util.Pair;
import common.geometry.CVector3D;
import org.w3c.dom.Text;

import javax.microedition.khronos.opengles.GL10;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public abstract class D3D_objects
{
  public static Camera camera;
  //Закрытые члены
  private static FloatBuffer Vertices; //Нативный буфер вершин
  private static ShortBuffer Indices;  //Нативный буфер индексов вершин
  private static ArrayList<D3D_objects> d3d_objects; //Все созданные объекты
  private static List<Pair<Integer/*ID текстуры*/, String /*TextFileName*/>> TextureIDs; //Список пар ID, файл текстуры
  private static int TexIds[]; //Массив id_шников текстур для отрисовки

  private List<MaterialEx> materials; //Список материалов частей объекта
  private short offset_index[]; //Смещение в нативном буфере индексов на первый индекс описывающий объект
  private short count_index[];  //Количество индексов описывающих объект
  private Vertex ArrVertices[]; // Вершины
  private Index ArrIndices[];   // Индексы
  private int TextureID;        // ID текстуры которую использует текущий объект

  private static final int MainStep = 6; //Главный шаг который нужно сделать первым (выполняется автоматом)
  private int step; //Шаг на котором находится состояние отрисовки (состояние матричных пространственных преобразований)

  //Статический конструктор
  static
  {
    camera = new Camera();
    d3d_objects = new ArrayList<D3D_objects>();
    TextureIDs = new ArrayList<Pair<Integer, String>>();
  }

  //Конструктор объекта из файла 3df
  public D3D_objects(String _obj_file, Activity _activity, String _TextureFILE/*=null*/)
  {
    d3d_objects.add(this);
    TextureID = TextureAddInArray(_TextureFILE);
    try
    {
      init();
      InputStream fis = _activity.getAssets().open(_obj_file);
      DataInputStream input = new DataInputStream(fis);

      String[] tokens;
      tokens = _obj_file.split(Pattern.quote(File.separator)); //Отделим имя файла от пути
      tokens = tokens[tokens.length - 1].split("\\.");       //Оставим только имя без расширения

      //Читаем файл
      char ch[] = new char[3];
      ch[0] = input.readChar(); ch[1] = input.readChar(); ch[2] = input.readChar(); // Читаем символ
      String signature = new String(ch);
      if(signature.compareTo("3DM") != 0) //Проверяем заголовок
        throw new Exception();

      ArrVertices = new Vertex[input.readInt()];  //Общее количество вершин
      ArrIndices = new Index[input.readInt()];    //Общее количество индексов

      int count_blocks = input.readInt(); //Количество блоков материал-вершины-индексы
      //Индексы группируем по материалам
      offset_index = new short[count_blocks];
      count_index = new short[count_blocks];
      offset_index [0] = 0;

      for(int i=0; i<count_blocks; i++)
      {
        //Считываем очередной материал
        MaterialEx material = new MaterialEx();
        material.color_ambient.rgba[0] = input.readFloat();
        material.color_ambient.rgba[1] = input.readFloat();
        material.color_ambient.rgba[2] = input.readFloat();
        material.color_ambient.rgba[3] = input.readFloat();
        //
        material.color_diffuse.rgba[0] = input.readFloat();
        material.color_diffuse.rgba[1] = input.readFloat();
        material.color_diffuse.rgba[2] = input.readFloat();
        material.color_diffuse.rgba[3] = input.readFloat();
        //
        material.color_specular.rgba[0] = input.readFloat();
        material.color_specular.rgba[1] = input.readFloat();
        material.color_specular.rgba[2] = input.readFloat();
        material.color_specular.rgba[3] = input.readFloat();
        //
        material.k_specular = input.readFloat();
        //Сохраним материал в список материалов
        materials.add(material);
        //Считываем очередной блок вершин
        int count_vertices = input.readInt();
        while(count_vertices-- != 0)
        {
          Vertex v = new Vertex(
              input.readFloat(),input.readFloat(),input.readFloat(),
              material.color_ambient.rgba[0], material.color_ambient.rgba[1],
              material.color_ambient.rgba[2], material.color_ambient.rgba[3],
              input.readFloat(),input.readFloat(),
              input.readFloat(),input.readFloat(),input.readFloat()
              );
          putVertex(v);
        }
        //Считываем индексы
        int count_indices = input.readInt();
        int co_i = count_indices;
        while(co_i-- != 0)
        {
          Index index = new Index((short)input.readInt());
          putIndex(index);
        }
        //Сохраним количества индексов и смещения индексов
        count_index[i] = (short)count_indices;
        if(i>0)
          offset_index [i] = (short)(offset_index[i-1] + count_index[i-1]);
      }
    }
    catch(Exception e)
    {
      String s = e.toString();
      s = "";
    }
  }
  private void init()
  {
    materials = new ArrayList<MaterialEx>();
    step = MainStep;
  }
  private int TextureAddInArray(String _TextureFILE)
  {
    if(_TextureFILE == null)
      return -1;
    int ID = 0;
    for(Pair<Integer, String> Tex : TextureIDs)
      if(Tex.second == _TextureFILE)
      {
        ID = Tex.first;
        return ID;
      }
    TextureIDs.add(new Pair<Integer, String>(ID,_TextureFILE));
    return ID;
  }

  private int arrV_index = 0;
  private void putVertex(Vertex _v)
  {
    ArrVertices[arrV_index ] = _v.GetCopy();
    arrV_index++;
  }
  private int arrI_index = 0;
  private void putIndex(Index _i)
  {
    ArrIndices[arrI_index] = _i.GetCopy();
    arrI_index++;
  }
  //Сбросить нативные буферы вершин и индексов и массив объектов
  public static void ClearALL()
  {
    d3d_objects.clear();
    Vertices = null;
    Indices = null;
  }

  private static boolean first = true;
  //Создать нативные буферы вершин и индексов из массива объектов
  public static void PrepareAll(GL10 gl, Activity _activity)
  {
    if(first)
    {
      first = false;
      //Посчитаем общее количество верши и индексов
      short count_all_arrvertex = 0;
      short count_all_arrindex = 0;
      for(D3D_objects obj : d3d_objects)
      {
        count_all_arrvertex += obj.ArrVertices.length;
        count_all_arrindex += obj.ArrIndices.length;
      }
      if(count_all_arrvertex == 0 || count_all_arrindex == 0)
        return;
      //Создаем нативный буфер вершин
      ByteBuffer byteBuffer1 = ByteBuffer.allocateDirect(count_all_arrvertex * Vertex.VERTEX_SIZE);
      byteBuffer1.order(ByteOrder.nativeOrder()); //Тип
      Vertices = byteBuffer1.asFloatBuffer();     //Интерпретировать как
      //Создаем нативный буфер индексов
      ByteBuffer byteBuffer2 = ByteBuffer.allocateDirect(count_all_arrindex * Index.INDEX_SIZE);
      byteBuffer2.order(ByteOrder.nativeOrder()); //Тип
      Indices = byteBuffer2.asShortBuffer();      //Интерпретировать как
      //Заполним буфера
      short offset_vertex_data = 0; //Смещение на байты вершин объекта в общем буфере вершин
      short offset_index_data = 0;  //Смещение на байты индексов в общем буфере индексов
      for(D3D_objects obj : d3d_objects)
      {
        for(Vertex v : obj.ArrVertices) //Запихиваем данные вершин
        {
          Vertices.put(v.x);
          Vertices.put(v.y);
          Vertices.put(v.z);
          Vertices.put(v.r);
          Vertices.put(v.g);
          Vertices.put(v.b);
          Vertices.put(v.a);
          Vertices.put(v.xt);
          Vertices.put(v.yt);
          Vertices.put(v.xn);
          Vertices.put(v.yn);
          Vertices.put(v.zn);
        }

        for(Index i : obj.ArrIndices)  //Запихиваем данные индексов
        {
          Indices.put((short)(i.index + offset_vertex_data));
        }

        for(int i = 0; i < obj.offset_index.length; i++)
          obj.offset_index[i] += offset_index_data;    //Сохраним смещение в индексоном буфере на текущий объект

        offset_vertex_data += obj.ArrVertices.length;
        offset_index_data += (short)obj.ArrIndices.length;
      }
      Vertices.flip(); //Тоже надо для установки
      Indices.flip();
    }

    //Скопируем в видеопамять вершины
    //Указываем openGL что работаем с вершинами которые имеют позиции
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY); // правда других вершин не бывает (по ходу метод не обязателен)
    //Указываем openGL что у вершин есть цвет (или цвет материала)
    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
    //Указываем OpenGL что у вершин есть текстурные координаты
    gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    //Указываем OpenGL что у вершин есть нормали
    gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

    //Все методы glxxxxxxxPointer (нашел только 4ре) перемещают данные в видеопамять (для индексного буфера такого метода нету по ходу)
    //Указываем нативный буфер вершин
    Vertices.position(0);  //Смещаемся на начало описания коордтинат первой вершины
    gl.glVertexPointer( 3,                  // Количество координат в вершинах (2 или 3)
                        GL10.GL_FLOAT,      // Тип данных хранимых в нативном буфере
                        Vertex.VERTEX_SIZE, // Шаг (в байтах) между вершинами, если кроме координат еще что то есть
                        Vertices            // Сам буфер, который стоит на нужной позиции ( position )
                      );
    //Указываем нативный буфер цветов вершин
    Vertices.position(3);   //Смещаемся на начало описания цвета первой вершины
    gl.glColorPointer( 4,                   // Количество float_ов описывающих цвет
                       GL10.GL_FLOAT,       // Тип данных хранимых в нативном буфере
                       Vertex.VERTEX_SIZE,  // Шаг (в байтах) между вершинами, если кроме координат еще что то есть
                       Vertices             // Сам буфер, который стоит на нужной позиции ( position )
                     );
    //Указываем нативный буфер координат текстуры
    Vertices.position(7); //Смещаемся на первую текстурную координату
    gl.glTexCoordPointer( 2,                  // Количество float_ов описывающих текстурные координаты
                          GL10.GL_FLOAT,      // Тип данных хранимых в нативном буфере
                          Vertex.VERTEX_SIZE, // Шаг (в байтах) между вершинами, если кроме координат еще что то есть
                          Vertices            // Сам буфер, который стоит на нужной позиции ( position )
                        );
    //Указываем нативный буфер нормалей
    Vertices.position(9); //Смещаемся на первую текстурную координату
    gl.glNormalPointer( GL10.GL_FLOAT,      // Тип данных хранимых в нативном буфере
                        Vertex.VERTEX_SIZE, // Шаг (в байтах) между вершинами, если кроме координат еще что то есть
                        Vertices            // Сам буфер, который стоит на нужной позиции ( position )
                      );

    //Загрузим текстуры
    PrepareTextures(gl, _activity);
  }

  private static void PrepareTextures(GL10 gl, Activity _activity)
  {
    int countTextures = TextureIDs.size();
    if(countTextures == 0)
      return;
    //Загружаем текстуры
    gl.glEnable(GL10.GL_TEXTURE_2D); //Включаем режим привязывания текстур к треугольникам
    AssetManager assets = _activity.getAssets(); //Для работы с папкой assets в проекте
    InputStream is = null;

    TexIds = new int[countTextures];
    //Создаем объекты текстур
    gl.glGenTextures(TexIds.length, // Количество объектов текстур
                     TexIds,        // Массив для id_шников объектов текстур
                     0              // Индекс в массиве id_шников с которого начинаем запись (в данном случае с нулевого)
                    );
    for(Pair<Integer, String> Tex : TextureIDs)
    {
      try
      {
        is = assets.open(Tex.second); //Создает файловый поток файла из папки assets в проекте
      }
      catch (IOException e)
      {  }
      Bitmap bitmap = BitmapFactory.decodeStream(is); //Создаем битмап из файлового потока

      //Говорим openGl что последующие методы будут работать с указанной текстурой
      gl.glBindTexture(GL10.GL_TEXTURE_2D, //Тип текстуры
                       TexIds[Tex.first]       //id объекта текстуры
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
  }

  private void PrepareModelMatrix(GL10 gl)
  {
    gl.glMatrixMode(GL10.GL_MODELVIEW);  //Работаем с матрицей модели (с мировыми координатами)
    gl.glLoadIdentity();                 //Сбрасываем матрицу
    //Устанавливаем камеру
    GLU.gluLookAt(gl,
        camera.eye.vx, camera.eye.vy, camera.eye.vz,
        camera.pointLook.vx, camera.pointLook.vy, camera.pointLook.vz,
        camera.vertical.vx, camera.vertical.vy, camera.vertical.vz
        );
    step = 1; //Установим уровень на котором можно делать любые преобразования
  }
  //Функции пространственных преобразований (нужно выполнять в определенном порядке, иначе исключение)
  //Применять все преобразования нужно в определенном порядке (в обратном):

  //Перенос после поворота относительно удаленного центра
  //Вращение вокруг удаленного центра
  //Перемещение
  //Вращение вокруг своего центра
  //Масштабирование

  //(1)Перенос после поворота относительно удаленного центра
  public void TranslateAfterRotateFar(GL10 gl, CVector3D translate) throws D3D_obj_Exception
  {
    if(step == MainStep)
      PrepareModelMatrix(gl);
    else if(step > 1)
      throw new D3D_obj_Exception("Нарушена последовательность пространственных матричных преобразований");
    gl.glTranslatef(translate.vx, translate.vy, translate.vz);
    //step = 1; // и так уже равно 1
  }
  //(2)Вращение относительно удаленного центра (после переноса)
  public void RotateFar(GL10 gl, float angle, CVector3D vector) throws D3D_obj_Exception
  {
    if(step == MainStep)
      PrepareModelMatrix(gl);
    else if(step > 2)
      throw new D3D_obj_Exception("Нарушена последовательность пространственных матричных преобразований");
    gl.glRotatef(angle, vector.vx, vector.vy, vector.vz);
    step = 2;
  }
  //(3)Перемещение из центра
  public void Translate(GL10 gl, CVector3D translate) throws D3D_obj_Exception
  {
    if(step == MainStep)
      PrepareModelMatrix(gl);
    else if(step > 3)
      throw new D3D_obj_Exception("Нарушена последовательность пространственных матричных преобразований");
    gl.glTranslatef(translate.vx, translate.vy, translate.vz);
    step = 3;
  }
  //(4)Поворот относительно своего центра
  public void Rotate(GL10 gl, float angle, CVector3D vector) throws D3D_obj_Exception
  {
    if(step == MainStep)
      PrepareModelMatrix(gl);
    else if(step > 4)
      throw new D3D_obj_Exception("Нарушена последовательность пространственных матричных преобразований");
    gl.glRotatef(angle, vector.vx, vector.vy, vector.vz);
    step = 4;
  }
  //(5)Масштабирование
  public void Scale(GL10 gl, CVector3D scale) throws D3D_obj_Exception
  {
    if(step == MainStep)
      PrepareModelMatrix(gl);
    /*  и так если больше 5 то это может быть только 6
    else if(step > 5)
      throw new D3D_obj_Exception("Нарушена последовательность пространственных матричных преобразований");
    */
    gl.glScalef(scale.vx, scale.vy, scale.vz);
    step = 5;
  }

  boolean needReplaseColor = false;
  MaterialEx material;
  private int OldTexID = -2;
  //Рисуем объект
  public void Draw(GL10 gl, I_MaterialReplace im)
  {
    if(step == MainStep)
      PrepareModelMatrix(gl);
    step = MainStep;

    //Устанавливаем текстуру
    //if(TextureID != OldTexID)
    //{
    if(TextureID == -1)
    {
      gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
      OldTexID = -1;
    }
    else
      gl.glBindTexture(GL10.GL_TEXTURE_2D, TexIds[TextureID]);
    //}

    for(int Index_material = 0; Index_material < offset_index.length; Index_material++)
    {
      Indices.position(offset_index[Index_material]);
      material = materials.get(Index_material);

      //Устанавливаем зеркальный материал
      gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, material.color_specular.rgba, 0);
      //Коэффициент зеркального отражения
      gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, material.k_specular);

      //Подменяем материал (окружающий и диффузный) (если нужно) (!!!!на виртуальной машине может работать не правильно)
      if(im != null)                   //Считаем что в материале color_ambient и color_diffuse одинаковые
        if(needReplaseColor = im.I_replace_ambient_and_diffuse_color(Index_material, material.color_ambient))
        {
          gl.glDisable(GL10.GL_COLOR_MATERIAL); //Отключаем брать цвет материала из вершин
          //Задаем цвет материала (считаем что в материале color_ambient и color_diffuse одинаковые)
          gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, material.color_ambient.rgba, 0);
          gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, material.color_ambient.rgba, 0);
        }

      //Рисуем элементы с помощью индексов
      gl.glDrawElements(GL10.GL_TRIANGLES, // Тип рисуемого элемента
          count_index[Index_material],     // Количество индексов
          GL10.GL_UNSIGNED_SHORT,          // Типы индексов
          Indices                          // Нативный буфер индексов
      );

      if(needReplaseColor) //Снова включаем брать цвет материала из вершин
        gl.glEnable(GL10.GL_COLOR_MATERIAL);
    }
  }


  //Камера просмотра
  public static class Camera
  {
    public CVector3D eye = null;       //Положение камеры в пространстве
    public CVector3D pointLook = null; //Точка в которую смотрит камера
    public CVector3D vertical = null;  //Вертикаль камеры
  }

  //Исключения
  public static class D3D_obj_Exception
    extends Exception
  {
    public String problem;
    public D3D_obj_Exception(String _problem)
    {
      problem = _problem;
    }
  }

  //Формат вершин
  // 3 координат, 4 элемента цвета(цвета материала), 2 координаты текстур, 3 координаты нормали
  public static class Vertex
  {
    public float x, y, z;     // Координаты в пространстве
    public float r, g, b, a;  // Цвет вершин или фоновый и рассеянный цвет материала вершины
    public float xt, yt;      // Координаты текстуры
    public float xn, yn, zn;  // Нормаль вершины

    public static final int VERTEX_TYPE_SIZE = 4;                // sizeof(float)
    public static final int VERTEX_SIZE = VERTEX_TYPE_SIZE * 12; // размер одной вершины в байтах

    public Vertex(float _x, float _y, float _z,
                  float _r,float _g,float _b,float _a,
                  float _xt,float _yt,
                  float _xn,float _yn,float _zn)
    {
      x = _x; y = _y; z = _z;
      r = _r; g = _g; b = _b; a = _a;
      xt = _xt; yt = _yt;
      xn = _xn; yn = _yn; zn = _zn;
    }
    public Vertex(Vertex _v)
    {
      x = _v.x; y = _v.y; z = _v.z;
      r = _v.r; g = _v.g; b = _v.b; a = _v.a;
      xt = _v.xt; yt = _v.yt;
      xn = _v.xn; yn = _v.yn; zn = _v.zn;
    }

    public Vertex GetCopy()
    {
      return new Vertex(this);
    }
    public boolean IsEq(Vertex _v)
    {
      return x==_v.x && y==_v.y && z==_v.z &&
          r==_v.r && g==_v.g && b==_v.b && a==_v.a &&
          xt==_v.xt && yt==_v.yt &&
          xn==_v.xn && yn==_v.yn && zn==_v.zn;
    }

  }
  public static class Index
  {
    public short index; //Индекс вершины

    public static final int INDEX_TYPE_SIZE = 2;                 // sizeof(short)
    public static final int INDEX_SIZE = INDEX_TYPE_SIZE;        // размер одного индекса
    public Index(short _index)
    {
      index = _index;
    }
    public Index(Index _I)
    {
      index = _I.index;
    }
    public Index GetCopy()
    {
      return new Index(this);
    }
  }

  //Интерфейсы
  public interface I_MaterialReplace
  {
    public boolean I_replace_ambient_and_diffuse_color(int IndexMaterial, Color color);
  }

}


























