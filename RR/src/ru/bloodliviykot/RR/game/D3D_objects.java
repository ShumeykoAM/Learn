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
  //�������� �����
  private static FloatBuffer Vertices; //�������� ����� ������
  private static ShortBuffer Indices;  //�������� ����� �������� ������
  private static ArrayList<D3D_objects> d3d_objects; //��� ��������� �������
  private static List<Pair<Integer/*ID ��������*/, String /*TextFileName*/>> TextureIDs; //������ ��� ID, ���� ��������
  private static int TexIds[]; //������ id_������ ������� ��� ���������

  private List<MaterialEx> materials; //������ ���������� ������ �������
  private short offset_index[]; //�������� � �������� ������ �������� �� ������ ������ ����������� ������
  private short count_index[];  //���������� �������� ����������� ������
  private Vertex ArrVertices[]; // �������
  private Index ArrIndices[];   // �������
  private int TextureID;        // ID �������� ������� ���������� ������� ������

  private static final int MainStep = 6; //������� ��� ������� ����� ������� ������ (����������� ���������)
  private int step; //��� �� ������� ��������� ��������� ��������� (��������� ��������� ���������������� ��������������)

  //����������� �����������
  static
  {
    camera = new Camera();
    d3d_objects = new ArrayList<D3D_objects>();
    TextureIDs = new ArrayList<Pair<Integer, String>>();
  }

  //����������� ������� �� ����� 3df
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
      tokens = _obj_file.split(Pattern.quote(File.separator)); //������� ��� ����� �� ����
      tokens = tokens[tokens.length - 1].split("\\.");       //������� ������ ��� ��� ����������

      //������ ����
      char ch[] = new char[3];
      ch[0] = input.readChar(); ch[1] = input.readChar(); ch[2] = input.readChar(); // ������ ������
      String signature = new String(ch);
      if(signature.compareTo("3DM") != 0) //��������� ���������
        throw new Exception();

      ArrVertices = new Vertex[input.readInt()];  //����� ���������� ������
      ArrIndices = new Index[input.readInt()];    //����� ���������� ��������

      int count_blocks = input.readInt(); //���������� ������ ��������-�������-�������
      //������� ���������� �� ����������
      offset_index = new short[count_blocks];
      count_index = new short[count_blocks];
      offset_index [0] = 0;

      for(int i=0; i<count_blocks; i++)
      {
        //��������� ��������� ��������
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
        //�������� �������� � ������ ����������
        materials.add(material);
        //��������� ��������� ���� ������
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
        //��������� �������
        int count_indices = input.readInt();
        int co_i = count_indices;
        while(co_i-- != 0)
        {
          Index index = new Index((short)input.readInt());
          putIndex(index);
        }
        //�������� ���������� �������� � �������� ��������
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
  //�������� �������� ������ ������ � �������� � ������ ��������
  public static void ClearALL()
  {
    d3d_objects.clear();
    Vertices = null;
    Indices = null;
  }

  private static boolean first = true;
  //������� �������� ������ ������ � �������� �� ������� ��������
  public static void PrepareAll(GL10 gl, Activity _activity)
  {
    if(first)
    {
      first = false;
      //��������� ����� ���������� ����� � ��������
      short count_all_arrvertex = 0;
      short count_all_arrindex = 0;
      for(D3D_objects obj : d3d_objects)
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
      for(D3D_objects obj : d3d_objects)
      {
        for(Vertex v : obj.ArrVertices) //���������� ������ ������
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

        for(Index i : obj.ArrIndices)  //���������� ������ ��������
        {
          Indices.put((short)(i.index + offset_vertex_data));
        }

        for(int i = 0; i < obj.offset_index.length; i++)
          obj.offset_index[i] += offset_index_data;    //�������� �������� � ���������� ������ �� ������� ������

        offset_vertex_data += obj.ArrVertices.length;
        offset_index_data += (short)obj.ArrIndices.length;
      }
      Vertices.flip(); //���� ���� ��� ���������
      Indices.flip();
    }

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

    //�������� ��������
    PrepareTextures(gl, _activity);
  }

  private static void PrepareTextures(GL10 gl, Activity _activity)
  {
    int countTextures = TextureIDs.size();
    if(countTextures == 0)
      return;
    //��������� ��������
    gl.glEnable(GL10.GL_TEXTURE_2D); //�������� ����� ������������ ������� � �������������
    AssetManager assets = _activity.getAssets(); //��� ������ � ������ assets � �������
    InputStream is = null;

    TexIds = new int[countTextures];
    //������� ������� �������
    gl.glGenTextures(TexIds.length, // ���������� �������� �������
                     TexIds,        // ������ ��� id_������ �������� �������
                     0              // ������ � ������� id_������ � �������� �������� ������ (� ������ ������ � ��������)
                    );
    for(Pair<Integer, String> Tex : TextureIDs)
    {
      try
      {
        is = assets.open(Tex.second); //������� �������� ����� ����� �� ����� assets � �������
      }
      catch (IOException e)
      {  }
      Bitmap bitmap = BitmapFactory.decodeStream(is); //������� ������ �� ��������� ������

      //������� openGl ��� ����������� ������ ����� �������� � ��������� ���������
      gl.glBindTexture(GL10.GL_TEXTURE_2D, //��� ��������
                       TexIds[Tex.first]       //id ������� ��������
                      );
      //����������� � �������� ������� �������� ����������� ������
      GLUtils.texImage2D(GL10.GL_TEXTURE_2D, // ��� ��������
                         0,                  // ������� �����������
                         bitmap,             // ����������� (������ ���� ���������� � �������� ������� = ����� � ������� 2)
                         0                   // ������ 0
                        );
      //��� ������ ���� ������ ���������� �������� ������� �������� (���� ����������� ...)
      //������ ������ ���������� ����� ������ ��������� ����������� ������ ������� ����������� ������� � �������� (������������)
      gl.glTexParameterf(GL10.GL_TEXTURE_2D,         // ��� ��������
                         GL10.GL_TEXTURE_MAG_FILTER, // ��� ������ ������ ������ ����� (� ������ ������ ��� ������������)
                         GL10.GL_NEAREST             // ������ ����������
                        );
      //������ ������ ���������� ����� ������ ��������� ����������� ������ ������� ����������� ������� � �������� (�����������)
      gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);

      //���������� ������� ������ �������� �� ��������� openGL
      gl.glBindTexture(GL10.GL_TEXTURE_2D, // ��� ��������
                       0                   // ������ id_����� ������� �������� 0
                      );
      bitmap.recycle(); //����������� ������ ������� ��������, ����� ��� �������� ������������
    }
  }

  private void PrepareModelMatrix(GL10 gl)
  {
    gl.glMatrixMode(GL10.GL_MODELVIEW);  //�������� � �������� ������ (� �������� ������������)
    gl.glLoadIdentity();                 //���������� �������
    //������������� ������
    GLU.gluLookAt(gl,
        camera.eye.vx, camera.eye.vy, camera.eye.vz,
        camera.pointLook.vx, camera.pointLook.vy, camera.pointLook.vz,
        camera.vertical.vx, camera.vertical.vy, camera.vertical.vz
        );
    step = 1; //��������� ������� �� ������� ����� ������ ����� ��������������
  }
  //������� ���������������� �������������� (����� ��������� � ������������ �������, ����� ����������)
  //��������� ��� �������������� ����� � ������������ ������� (� ��������):

  //������� ����� �������� ������������ ���������� ������
  //�������� ������ ���������� ������
  //�����������
  //�������� ������ ������ ������
  //���������������

  //(1)������� ����� �������� ������������ ���������� ������
  public void TranslateAfterRotateFar(GL10 gl, CVector3D translate) throws D3D_obj_Exception
  {
    if(step == MainStep)
      PrepareModelMatrix(gl);
    else if(step > 1)
      throw new D3D_obj_Exception("�������� ������������������ ���������������� ��������� ��������������");
    gl.glTranslatef(translate.vx, translate.vy, translate.vz);
    //step = 1; // � ��� ��� ����� 1
  }
  //(2)�������� ������������ ���������� ������ (����� ��������)
  public void RotateFar(GL10 gl, float angle, CVector3D vector) throws D3D_obj_Exception
  {
    if(step == MainStep)
      PrepareModelMatrix(gl);
    else if(step > 2)
      throw new D3D_obj_Exception("�������� ������������������ ���������������� ��������� ��������������");
    gl.glRotatef(angle, vector.vx, vector.vy, vector.vz);
    step = 2;
  }
  //(3)����������� �� ������
  public void Translate(GL10 gl, CVector3D translate) throws D3D_obj_Exception
  {
    if(step == MainStep)
      PrepareModelMatrix(gl);
    else if(step > 3)
      throw new D3D_obj_Exception("�������� ������������������ ���������������� ��������� ��������������");
    gl.glTranslatef(translate.vx, translate.vy, translate.vz);
    step = 3;
  }
  //(4)������� ������������ ������ ������
  public void Rotate(GL10 gl, float angle, CVector3D vector) throws D3D_obj_Exception
  {
    if(step == MainStep)
      PrepareModelMatrix(gl);
    else if(step > 4)
      throw new D3D_obj_Exception("�������� ������������������ ���������������� ��������� ��������������");
    gl.glRotatef(angle, vector.vx, vector.vy, vector.vz);
    step = 4;
  }
  //(5)���������������
  public void Scale(GL10 gl, CVector3D scale) throws D3D_obj_Exception
  {
    if(step == MainStep)
      PrepareModelMatrix(gl);
    /*  � ��� ���� ������ 5 �� ��� ����� ���� ������ 6
    else if(step > 5)
      throw new D3D_obj_Exception("�������� ������������������ ���������������� ��������� ��������������");
    */
    gl.glScalef(scale.vx, scale.vy, scale.vz);
    step = 5;
  }

  boolean needReplaseColor = false;
  MaterialEx material;
  private int OldTexID = -2;
  //������ ������
  public void Draw(GL10 gl, I_MaterialReplace im)
  {
    if(step == MainStep)
      PrepareModelMatrix(gl);
    step = MainStep;

    //������������� ��������
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

      //������������� ���������� ��������
      gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, material.color_specular.rgba, 0);
      //����������� ����������� ���������
      gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, material.k_specular);

      //��������� �������� (���������� � ���������) (���� �����) (!!!!�� ����������� ������ ����� �������� �� ���������)
      if(im != null)                   //������� ��� � ��������� color_ambient � color_diffuse ����������
        if(needReplaseColor = im.I_replace_ambient_and_diffuse_color(Index_material, material.color_ambient))
        {
          gl.glDisable(GL10.GL_COLOR_MATERIAL); //��������� ����� ���� ��������� �� ������
          //������ ���� ��������� (������� ��� � ��������� color_ambient � color_diffuse ����������)
          gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, material.color_ambient.rgba, 0);
          gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, material.color_ambient.rgba, 0);
        }

      //������ �������� � ������� ��������
      gl.glDrawElements(GL10.GL_TRIANGLES, // ��� ��������� ��������
          count_index[Index_material],     // ���������� ��������
          GL10.GL_UNSIGNED_SHORT,          // ���� ��������
          Indices                          // �������� ����� ��������
      );

      if(needReplaseColor) //����� �������� ����� ���� ��������� �� ������
        gl.glEnable(GL10.GL_COLOR_MATERIAL);
    }
  }


  //������ ���������
  public static class Camera
  {
    public CVector3D eye = null;       //��������� ������ � ������������
    public CVector3D pointLook = null; //����� � ������� ������� ������
    public CVector3D vertical = null;  //��������� ������
  }

  //����������
  public static class D3D_obj_Exception
    extends Exception
  {
    public String problem;
    public D3D_obj_Exception(String _problem)
    {
      problem = _problem;
    }
  }

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

  //����������
  public interface I_MaterialReplace
  {
    public boolean I_replace_ambient_and_diffuse_color(int IndexMaterial, Color color);
  }

}


























