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
  //������ ������
  // 3 ���������, 4 �������� �����(����� ���������), 2 ���������� �������, 3 ���������� �������
  public static class Vertex
  {
    public float x, y, z;     // ���������� � ������������
    public float r, g, b, a;  // ���� ������ ��� ������� � ���������� ���� ��������� �������
    public float xt, yt;      // ���������� ��������
    public float xn, yn, zn;  // ������� �������

    public static final int VERTEX_TYPE_SIZE = 4;                // sizeof(float)
    public static final int VERTEX_SIZE = VERTEX_TYPE_SIZE * 12; // ������ ����� ������� � ������

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
    public short index; //������ �������

    public static final int INDEX_TYPE_SIZE = 2;                 // sizeof(short)
    public static final int INDEX_SIZE = INDEX_TYPE_SIZE;        // ������ ������ �������
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
      tokens = _obj_file.split(Pattern.quote(File.separator)); //������� ��� ����� �� ����
      tokens = tokens[tokens.length - 1].split("\\.");       //������� ������ ��� ��� ����������

      AssetManager assets = _activity.getAssets(); //��� ������ � ������ assets � �������
      FileInputStream is = (FileInputStream)assets.open(_obj_file);     //������� �������� ����� ����� �� ����� assets � �������
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
  //�������� �������� ������ ������ � �������� � ������ ��������
  public static void ClearALL()
  {
    d3d_objects.clear();
    Vertices = null;
    Indices = null;
  }
  //������� �������� ������ ������ � �������� �� ������� ��������
  public static void PrepareAll(GL10 gl)
  {
    //��������� ����� ���������� ����� � ��������
    short count_all_arrvertex = 0;
    short count_all_arrindex = 0;
    for(D3D_objects obj: d3d_objects)
    {
      count_all_arrvertex += obj.ArrVertices.length;
      count_all_arrindex += obj.ArrIndices.length;
    }
    if(count_all_arrvertex == 0 || count_all_arrindex == 0)
      return;
    //������� �������� ����� ������
    ByteBuffer byteBuffer1 = ByteBuffer.allocateDirect(count_all_arrvertex * Vertex.VERTEX_SIZE);
    byteBuffer1.order(ByteOrder.nativeOrder()); //���
    Vertices = byteBuffer1.asFloatBuffer();     //���������������� ���
    //������� �������� ����� ��������
    ByteBuffer byteBuffer2 = ByteBuffer.allocateDirect(count_all_arrindex * Index.INDEX_SIZE);
    byteBuffer2.order(ByteOrder.nativeOrder()); //���
    Indices = byteBuffer2.asShortBuffer();      //���������������� ���
    //�������� ������
    short offset_vertex_data = 0; //�������� �� ����� ������ ������� � ����� ������ ������
    short offset_index_data = 0;  //�������� �� ����� �������� � ����� ������ ��������
    for(D3D_objects obj: d3d_objects)
    {
      for(Vertex v: obj.ArrVertices) //���������� ������ ������
      {
        Vertices.put(v.x);  Vertices.put(v.y);  Vertices.put(v.z);
        Vertices.put(v.r);  Vertices.put(v.g);  Vertices.put(v.b); Vertices.put(v.a);
        Vertices.put(v.xt); Vertices.put(v.yt);
        Vertices.put(v.xn); Vertices.put(v.yn); Vertices.put(v.zn);
      }

      for(Index i: obj.ArrIndices)  //���������� ������ ��������
      {
        Indices.put((short)(i.index + offset_vertex_data));
      }

      obj.offset_index = offset_index_data;           //�������� �������� � ���������� ������ �� ������� ������
      obj.count_index = (short)obj.ArrIndices.length; //�������� ���������� �������� ������ �������� �������

      offset_vertex_data += obj.ArrVertices.length;
      offset_index_data += obj.count_index;
    }
    Vertices.flip(); //���� ���� ��� ���������
    Indices.flip();

    //��������� � ����������� �������
    //��������� openGL ��� �������� � ��������� ������� ����� �������
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY); // ������ ������ ������ �� ������ (�� ���� ����� �� ����������)
    //��������� openGL ��� � ������ ���� ���� (��� ���� ���������)
    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
    //��������� OpenGL ��� � ������ ���� ���������� ����������
    gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    //��������� OpenGL ��� � ������ ���� �������
    gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

    //��� ������ glxxxxxxxPointer (����� ������ 4��) ���������� ������ � ����������� (��� ���������� ������ ������ ������ ���� �� ����)
    //��������� �������� ����� ������
    Vertices.position(0);  //��������� �� ������ �������� ���������� ������ �������
    gl.glVertexPointer( 3,                  // ���������� ��������� � �������� (2 ��� 3)
                        GL10.GL_FLOAT,      // ��� ������ �������� � �������� ������
                        Vertex.VERTEX_SIZE, // ��� (� ������) ����� ���������, ���� ����� ��������� ��� ��� �� ����
                        Vertices            // ��� �����, ������� ����� �� ������ ������� ( position )
                      );
    //��������� �������� ����� ������ ������
    Vertices.position(3);   //��������� �� ������ �������� ����� ������ �������
    gl.glColorPointer( 4,                   // ���������� float_�� ����������� ����
                       GL10.GL_FLOAT,       // ��� ������ �������� � �������� ������
                       Vertex.VERTEX_SIZE,  // ��� (� ������) ����� ���������, ���� ����� ��������� ��� ��� �� ����
                       Vertices             // ��� �����, ������� ����� �� ������ ������� ( position )
                     );
    //��������� �������� ����� ��������� ��������
    Vertices.position(7); //��������� �� ������ ���������� ����������
    gl.glTexCoordPointer( 2,                  // ���������� float_�� ����������� ���������� ����������
                          GL10.GL_FLOAT,      // ��� ������ �������� � �������� ������
                          Vertex.VERTEX_SIZE, // ��� (� ������) ����� ���������, ���� ����� ��������� ��� ��� �� ����
                          Vertices            // ��� �����, ������� ����� �� ������ ������� ( position )
                        );
    //��������� �������� ����� ��������
    Vertices.position(9); //��������� �� ������ ���������� ����������
    gl.glNormalPointer( GL10.GL_FLOAT,      // ��� ������ �������� � �������� ������
                        Vertex.VERTEX_SIZE, // ��� (� ������) ����� ���������, ���� ����� ��������� ��� ��� �� ����
                        Vertices            // ��� �����, ������� ����� �� ������ ������� ( position )
                      );

  }
  private boolean NewModelMatrix = true; //������� �������� ������� �������
  private void PrepareModelMatrix(GL10 gl)
  {
    //������ ������� ������� ��������������
    gl.glMatrixMode(GL10.GL_MODELVIEW);  //�������� � �������� ������ (� �������� ������������)
    gl.glLoadIdentity();                 //���������� �������

    GLU.gluLookAt(gl,
    0, 0, 8,
    0, 0, -5,
    0, 1, 0);

    NewModelMatrix = false;
  }
  //������ ������
  public void Draw(GL10 gl)
  {
    Indices.position(offset_index);
    if(NewModelMatrix)
      PrepareModelMatrix(gl);
    //������������� ��������

    //������������� �������� (���������� ��������)

    //������ �������� � ������� ��������
    gl.glDrawElements(GL10.GL_TRIANGLES, // ��� ��������� ��������
        count_index,                     //���������� ��������
        GL10.GL_UNSIGNED_SHORT,          //���� ��������
        Indices                          //�������� ����� ��������
    );
    NewModelMatrix = true;
  }
  public void Scale(GL10 gl, float _xs, float _ys, float _zs)
  {
    if(NewModelMatrix)
      PrepareModelMatrix(gl);
    gl.glScalef(_xs, _ys, _zs); //������������ � �� ��� x � sx ��� �� ��� y � z � sy � sz ���
  }
  public void Rotate(GL10 gl, float _angle, float _xr, float _yr, float _zr)
  {
    if(NewModelMatrix)
      PrepareModelMatrix(gl);
    gl.glRotatef(_angle, _xr, _yr, _zr);    //������� ������ ������� (rx,ry,rz) �� ���� angel
  }
  public void Translate(GL10 gl, float _x, float _y, float _z)
  {
    if(NewModelMatrix)
      PrepareModelMatrix(gl);
    gl.glTranslatef(_x, _y, _z);           //���������� ���������� �� ��������� �������� x, y z
  }

  //�������� �����
  private static ArrayList<D3D_objects> d3d_objects = new ArrayList<D3D_objects>();

  static private FloatBuffer Vertices; //�������� ����� ������
  static private ShortBuffer Indices;  //�������� ����� �������� ������
  short offset_index; //�������� � �������� ������ �������� �� ������ ������ ����������� ������
  short count_index;  //���������� �������� ����������� ������

  private Vertex ArrVertices[]; // �������
  private Index ArrIndices[];   // �������

  //������� ����� ����������
  private static class Mtl
  {
    public String name;              // ��������
    float ambient[] = new float[4];  // ���������� ����
    float diffuse[] = new float[4];  // ��������� ����
    float specular[] = new float[4]; // ���� ����������� ���������
    float shininess;                 // ����������� ����������� ��������� (�� 0 �� 1000)
    String texFileName;              // �������� ����� ��������
  }
  //���������� ���� ����������
  private List<Mtl> Parsemtl(Activity _activity, String _mtlfile)
  {
    List<Mtl> mtls = null;
    try
    {
      AssetManager assets = _activity.getAssets(); //��� ������ � ������ assets � �������
      InputStream is = assets.open(_mtlfile);     //������� �������� ����� ����� �� ����� assets � �������
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      String line = null;
      mtls = new ArrayList<Mtl>();
      Mtl mtl = null;
      while( (line = reader.readLine()) != null )
      {
        if(line.startsWith("newmtl ")) //�������� ���������
        {
          mtl = new Mtl();
          mtls.add(mtl);
          String[] tokens = line.split("[ ]+");
          mtl.name = new String(tokens[1]);
        }
        if(line.startsWith("Ka ")) // ���� ����������� ���������
        {
          String[] tokens = line.split("[ ]+");
          mtl.ambient[0] = Float.parseFloat(tokens[1]);
          mtl.ambient[1] = Float.parseFloat(tokens[2]);
          mtl.ambient[2] = Float.parseFloat(tokens[3]);
        }
        if(line.startsWith("Kd ")) // ��������� ����
        {
          String[] tokens = line.split("[ ]+");
          mtl.diffuse[0] = Float.parseFloat(tokens[1]);
          mtl.diffuse[1] = Float.parseFloat(tokens[2]);
          mtl.diffuse[2] = Float.parseFloat(tokens[3]);
        }
        if(line.startsWith("Ks ")) // ���� ����������� ���������
        {
          String[] tokens = line.split("[ ]+");
          mtl.specular[0] = Float.parseFloat(tokens[1]);
          mtl.specular[1] = Float.parseFloat(tokens[2]);
          mtl.specular[2] = Float.parseFloat(tokens[3]);
        }
        if(line.startsWith("Ns ")) // ����������� ����������� ��������� (�� 0 �� 1000)
        {
          String[] tokens = line.split("[ ]+");
          mtl.shininess = Float.parseFloat(tokens[1]);
        }
        if(line.startsWith("d ") || line.startsWith("Tr ")) // ������������
        {
          String[] tokens = line.split("[ ]+");
          mtl.ambient[3] = mtl.diffuse[3] = mtl.specular[3] = Float.parseFloat(tokens[1]);
        }
        if(line.startsWith("map_Kd ")) // ���� ��������
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


























