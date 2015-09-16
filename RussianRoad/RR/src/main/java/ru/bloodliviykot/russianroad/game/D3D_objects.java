package ru.bloodliviykot.russianroad.game;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.opengl.GLU;
import common.convertor.D3DModelFileReader;
import common.convertor.Obj;

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

  public D3D_objects(int _count_vertex, int _count_index)
  {
    d3d_objects.add(this);
    ArrVertices = new Vertex[_count_vertex];
    ArrIndices = new Index[_count_index];
  }
  public D3D_objects(String _obj_file, Activity _activity)
  {
    d3d_objects.add(this);
    try
    {
      String[] tokens;
      tokens = _obj_file.split(Pattern.quote(File.separator)); //Отделим имя файла от пути
      tokens = tokens[tokens.length - 1].split("\\.");       //Оставим только имя без расширения

      AssetManager assets = _activity.getAssets(); //Для работы с папкой assets в проекте
      FileInputStream is = (FileInputStream)assets.open(_obj_file);     //Создает файловый поток файла из папки assets в проекте
      D3DModelFileReader d = new D3DModelFileReader();
      Obj obj = d.Read(is, tokens[0]);
      is.close();

      /*
      ArrVertices = new Vertex[tempVertices.size()];
      ArrIndices = new Index[tempIndices.size()];
      for(Vertex v : tempVertices)
        putVertex(v);
      for(Index I : tempIndices)
        putIndex(I);
      */

    }
    catch(Exception e){}
  }
  public D3D_objects(Vertex _Vertices[], Index _Indices[])
  {
    d3d_objects.add(this);
    int v_len = _Vertices.length;
    ArrVertices = new Vertex[v_len];
    for(int i=0; i<v_len; i++)
      ArrVertices[i] = _Vertices[i].GetCopy();

    int i_len = _Indices.length;
    ArrIndices = new Index[-i_len];
    for(int i=0; i<i_len; i++)
      ArrIndices[i] = _Indices[i].GetCopy();
  }
  private int arrV_index = 0;
  public void putVertex(Vertex _v)
  {
    ArrVertices[arrV_index ] = _v.GetCopy();
    arrV_index++;
  }
  private int arrI_index = 0;
  public void putIndex(Index _i)
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
  //Создать нативные буферы вершин и индексов из массива объектов
  public static void PrepareAll(GL10 gl)
  {
    //Посчитаем общее количество верши и индексов
    short count_all_arrvertex = 0;
    short count_all_arrindex = 0;
    for(D3D_objects obj: d3d_objects)
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
    for(D3D_objects obj: d3d_objects)
    {
      for(Vertex v: obj.ArrVertices) //Запихиваем данные вершин
      {
        Vertices.put(v.x);  Vertices.put(v.y);  Vertices.put(v.z);
        Vertices.put(v.r);  Vertices.put(v.g);  Vertices.put(v.b); Vertices.put(v.a);
        Vertices.put(v.xt); Vertices.put(v.yt);
        Vertices.put(v.xn); Vertices.put(v.yn); Vertices.put(v.zn);
      }

      for(Index i: obj.ArrIndices)  //Запихиваем данные индексов
      {
        Indices.put((short)(i.index + offset_vertex_data));
      }

      obj.offset_index = offset_index_data;           //Сохраним смещение в индексоном буфере на текущий объект
      obj.count_index = (short)obj.ArrIndices.length; //Сохраним количество индексов вершин текущего объекта

      offset_vertex_data += obj.ArrVertices.length;
      offset_index_data += obj.count_index;
    }
    Vertices.flip(); //Тоже надо для установки
    Indices.flip();

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

  }
  private boolean NewModelMatrix = true; //Нужноли готовить мировую матрицу
  private void PrepareModelMatrix(GL10 gl)
  {
    //Меняем матрицу мировых преобразований
    gl.glMatrixMode(GL10.GL_MODELVIEW);  //Работаем с матрицей модели (с мировыми координатами)
    gl.glLoadIdentity();                 //Сбрасываем матрицу

    GLU.gluLookAt(gl,
    0, 0, 8,
    0, 0, -5,
    0, 1, 0);

    NewModelMatrix = false;
  }
  //Рисуем объект
  public void Draw(GL10 gl)
  {
    Indices.position(offset_index);
    if(NewModelMatrix)
      PrepareModelMatrix(gl);
    //Устанавливаем текстуру

    //Устанавливаем материал (зеркальные свойства)

    //Рисуем элементы с помощью индексов
    gl.glDrawElements(GL10.GL_TRIANGLES, // Тип рисуемого элемента
        count_index,                     //Количество индексов
        GL10.GL_UNSIGNED_SHORT,          //Типы индексов
        Indices                          //Нативный буфер индексов
    );
    NewModelMatrix = true;
  }
  public void Scale(GL10 gl, float _xs, float _ys, float _zs)
  {
    if(NewModelMatrix)
      PrepareModelMatrix(gl);
    gl.glScalef(_xs, _ys, _zs); //Масштабируем в по оси x в sx раз по оси y и z в sy и sz раз
  }
  public void Rotate(GL10 gl, float _angle, float _xr, float _yr, float _zr)
  {
    if(NewModelMatrix)
      PrepareModelMatrix(gl);
    gl.glRotatef(_angle, _xr, _yr, _zr);    //Вращяем вокруг вектора (rx,ry,rz) на угол angel
  }
  public void Translate(GL10 gl, float _x, float _y, float _z)
  {
    if(NewModelMatrix)
      PrepareModelMatrix(gl);
    gl.glTranslatef(_x, _y, _z);           //Перемещаем координаты на указанные значения x, y z
  }

  //Закрытые члены
  private static ArrayList<D3D_objects> d3d_objects = new ArrayList<D3D_objects>();

  static private FloatBuffer Vertices; //Нативный буфер вершин
  static private ShortBuffer Indices;  //Нативный буфер индексов вершин
  short offset_index; //Смещение в нативном буфере индексов на первый индекс описывающий объект
  short count_index;  //Количество индексов описывающих объект

  private Vertex ArrVertices[]; // Вершины
  private Index ArrIndices[];   // Индексы

  //Объекты файла материалов
  private static class Mtl
  {
    public String name;              // Название
    float ambient[] = new float[4];  // Окружающий цвет
    float diffuse[] = new float[4];  // Диффузный цвет
    float specular[] = new float[4]; // Цвет зеркального отражения
    float shininess;                 // Коэффициент зеркального отражения (от 0 до 1000)
    String texFileName;              // Название файла текстуры
  }
  //Распарсить файл материалов
  private List<Mtl> Parsemtl(Activity _activity, String _mtlfile)
  {
    List<Mtl> mtls = null;
    try
    {
      AssetManager assets = _activity.getAssets(); //Для работы с папкой assets в проекте
      InputStream is = assets.open(_mtlfile);     //Создает файловый поток файла из папки assets в проекте
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      String line = null;
      mtls = new ArrayList<Mtl>();
      Mtl mtl = null;
      while( (line = reader.readLine()) != null )
      {
        if(line.startsWith("newmtl ")) //Название материала
        {
          mtl = new Mtl();
          mtls.add(mtl);
          String[] tokens = line.split("[ ]+");
          mtl.name = new String(tokens[1]);
        }
        if(line.startsWith("Ka ")) // Цвет окружающего освещения
        {
          String[] tokens = line.split("[ ]+");
          mtl.ambient[0] = Float.parseFloat(tokens[1]);
          mtl.ambient[1] = Float.parseFloat(tokens[2]);
          mtl.ambient[2] = Float.parseFloat(tokens[3]);
        }
        if(line.startsWith("Kd ")) // Диффузный цвет
        {
          String[] tokens = line.split("[ ]+");
          mtl.diffuse[0] = Float.parseFloat(tokens[1]);
          mtl.diffuse[1] = Float.parseFloat(tokens[2]);
          mtl.diffuse[2] = Float.parseFloat(tokens[3]);
        }
        if(line.startsWith("Ks ")) // Цвет зеркального отражения
        {
          String[] tokens = line.split("[ ]+");
          mtl.specular[0] = Float.parseFloat(tokens[1]);
          mtl.specular[1] = Float.parseFloat(tokens[2]);
          mtl.specular[2] = Float.parseFloat(tokens[3]);
        }
        if(line.startsWith("Ns ")) // Коэффициент зеркального отражения (от 0 до 1000)
        {
          String[] tokens = line.split("[ ]+");
          mtl.shininess = Float.parseFloat(tokens[1]);
        }
        if(line.startsWith("d ") || line.startsWith("Tr ")) // Прозрачность
        {
          String[] tokens = line.split("[ ]+");
          mtl.ambient[3] = mtl.diffuse[3] = mtl.specular[3] = Float.parseFloat(tokens[1]);
        }
        if(line.startsWith("map_Kd ")) // Файл текстуры
        {
          String[] tokens = line.split("[ ]+");
          mtl.texFileName = tokens[1];
        }
      }
      is.close();
    }
    catch (IOException e)
    {  }
    return mtls;
  }





}


























