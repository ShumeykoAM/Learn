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
  implements Renderer,           //��������� ��������� �������
             SensorEventListener //��������� ��������� ��������� ��������
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
  private static final int VERTEX_SIZE = 44; //������ ����� ������� � ������
  private FloatBuffer vertices; //�������� ����� ������
  private ShortBuffer indices;  //�������� ����� �������� ������
  private int textureIds[] = new int[2]; //������ id_������ �������
  
  private int Width;
  private int Height;
  private int radius;
  int size_view;
  int rotation;
  
  //���������� ����� �� ������� �������� (256.256)
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
    
    //����� ������ ��� �� �������� ����� (� ���������) �� ��� �������� �� ���������
    rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
    
    float muler = size_view / 256.0f;
    for(int i=0; i<10; i++)
    {
      loonks[i] = new CVector2D( (points[i][0]-128)*muler, ((256-points[i][1])-128)*muler );
    }
    
    //������ ������           ���������� 2D(��� 3D) ����(��� ���� ���������) ���������� �������     �������
    float arrVertices[] = {/*0*/ -radius, -radius,    1.0f, 1.0f, 1.0f, 1.0f,    0.0f, 1.0f,      0.0f ,0.0f, 1.0f,     
                           /*1*/ -radius,  radius,    1.0f, 1.0f, 1.0f, 1.0f,    0.0f, 0.0f,      0.0f ,0.0f, 1.0f,
                           /*2*/  radius,  radius,    1.0f, 1.0f, 1.0f, 1.0f,    1.0f, 0.0f,      0.0f ,0.0f, 1.0f,
                           /*3*/  radius, -radius,    1.0f, 1.0f, 1.0f, 1.0f,    1.0f, 1.0f,      0.0f ,0.0f, 1.0f,
                           
                           /*0*/ -radius/50, -radius/50,    1.0f, 1.0f, 1.0f, 1.0f,    0.0f, 1.0f,      0.0f ,0.0f, 1.0f,     
                           /*1*/ -radius/50,  radius/50,    1.0f, 1.0f, 1.0f, 1.0f,    0.0f, 0.0f,      0.0f ,0.0f, 1.0f,
                           /*2*/  radius/50,  radius/50,    1.0f, 1.0f, 1.0f, 1.0f,    1.0f, 0.0f,      0.0f ,0.0f, 1.0f,
                           /*3*/  radius/50, -radius/50,    1.0f, 1.0f, 1.0f, 1.0f,    1.0f, 1.0f,      0.0f ,0.0f, 1.0f
                          };
    
    //������� �������� ����� ������
    //����������� ������ ���������� ���� (���������� ������ * ������ ���� ������)
    ByteBuffer byteBuffer1 = ByteBuffer.allocateDirect(arrVertices.length * VERTEX_TYPE_SIZE);
    byteBuffer1.order(ByteOrder.nativeOrder()); //���
    vertices = byteBuffer1.asFloatBuffer();     //���������������� ���
    vertices.put( arrVertices );                //���������� ������
    vertices.flip();                            //���� ���� ��� ���������
    
    //�������
    //������ ��������
    short arrIndices[] = { 0, 1, 2,
                           0, 2, 3,
                           
                           4, 5, 6,
                           4, 6, 7,
                         };
    //������� �������� ����� ��������
    //����������� ������ ���������� ���� (���������� �������� * ������ ���� ��������)
    ByteBuffer byteBuffer2 = ByteBuffer.allocateDirect(arrIndices.length * INDEX_TYPE_SIZE );
    byteBuffer2.order(ByteOrder.nativeOrder()); //���
    indices = byteBuffer2.asShortBuffer();      //���������������� ���
    indices.put( arrIndices );                  //���������� ������
    indices.flip();                             //���� ���� ��� ���������
  }

  @Override
  public void onSurfaceCreated(GL10 gl, EGLConfig config)
  {
    init();
    //������ ������� ��������� � ����� ����� ������ (������� �� ������ ��� ����� �������������� �����������)
    //gl.glViewport( (Width-size_view)/2, (Height-size_view)/2, size_view, size_view );
    gl.glViewport( 0, 0, Width, Height );
    
    //������������� ������� �������� (�����������-������������� ��� �������������)
    gl.glMatrixMode(GL10.GL_PROJECTION); //����� ��� ��������� ������ ����� �������� � �������� ��������
    gl.glLoadIdentity();                 //���������� ������� �������� �������, ������ �� ���������
    //gl.glOrthof(-size_view, size_view, -size_view, size_view, 10, -10);  //������ ���� ���������� ���� ��������� (���������)
    gl.glOrthof(-Width/2, Width/2, -Height/2, Height/2, 10, -10);  //������ ���� ���������� ���� ��������� (���������)
    
    gl.glClearColor(0.4f, 0.5f, 0.7f, 0.15f); //������������� ��������� ����� ����
    
    //�������� ������������ �������� ��� ���������������
    gl.glEnable(GL10.GL_NORMALIZE);
    
    gl.glEnable(GL10.GL_BLEND); //�������� ����� ����������
    gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA); //������ ����������
    
    gl.glEnable(GL10.GL_LIGHTING); //�������� ����� ������������� �����
    //���������� �������� ���������, ���� ������ ���� �����
    float[] ambientColor = { 0.0f, 0.0f, 0.0f, 1.0f };
    //float[] ambientColor = { 1.0f, 1.0f, 1.0f, 1.0f };
    gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, ambientColor, 0); //������ ���������� �������� ���������
    //�������� ������ �������� �����
    gl.glEnable(GL10.GL_LIGHT0); 
    float[] ambient_1  = { 1.0f, 1.0f, 1.0f, 1.0f }; // ������� float_��
    float[] diffuse_1  = { 1.0f, 1.0f, 1.0f, 1.0f }; //   � ������� �����������
    float[] specular_1 = { 1.0f, 1.0f, 1.0f, 1.0f }; //   �������, �����, �������, � �����
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT , ambient_1 , 0);
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE , diffuse_1 , 0);
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specular_1, 0);
    float[] position = {0.0f, 0.0f, -5, 0}; //x, y, z - �����, 0 - ������� � ��� ��� ���� ��������� �� ����� � �����
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, position, 0);
    
    //��������� openGL ������������ ���� ������ � �������� �������� � ����������� ������ ���������
    //  ��� ������������� (���� ������), ����� ��������� �������� ��������� � ������ glMaterialfv(����)
    gl.glEnable(GL10.GL_COLOR_MATERIAL); //�������� ������ � �����������, ������� � ���������� ����� ����� �� �����
    
    float[] specular = { 1.0f, 1.0f, 1.0f, 1.0f }; // �������, �����, �������, � �����
    gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specular, 0); // ���������� ���� ���������
    

    //��������� openGL ��� �������� � ��������� ������� ����� �������
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY); // ������ ������ ������ �� ������ (�� ���� ����� �� ����������)
    //��������� openGL ��� � ������ ���� ����
    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
    //��������� OpenGL ��� � ������ ���� ���������� ����������
    gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    //��������� OpenGL ��� � ������ ���� �������
    gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

    //��������� �������� ����� ������
    vertices.position(0);  //��������� �� ������ �������� ���������� ������ �������
    gl.glVertexPointer( 2,             // ���������� ��������� � �������� (2 ��� 3)  
                        GL10.GL_FLOAT, // ��� ������ �������� � �������� ������
                        VERTEX_SIZE,   // ��� (� ������) ����� ���������, ���� ����� ��������� ��� ��� �� ����
                        vertices       // ��� �����, ������� ����� �� ������ ������� ( position )
                       );
    
    //��������� �������� ����� ������ ������
    vertices.position(2); //��������� �� ������ �������� ����� ������ �������
    gl.glColorPointer( 4,             // ���������� float_�� ����������� ����  
                       GL10.GL_FLOAT, // ��� ������ �������� � �������� ������
                       VERTEX_SIZE,   // ��� (� ������) ����� ���������, ���� ����� ��������� ��� ��� �� ����
                       vertices       // ��� �����, ������� ����� �� ������ ������� ( position )
                      );
    //��������� �������� ����� ��������� ��������
    vertices.position(6); //��������� �� ������ ���������� ����������
    gl.glTexCoordPointer(2,             // ���������� float_�� ����������� ���������� ����������
                         GL10.GL_FLOAT, // ��� ������ �������� � �������� ������
                         VERTEX_SIZE,   // ��� (� ������) ����� ���������, ���� ����� ��������� ��� ��� �� ����
                         vertices       // ��� �����, ������� ����� �� ������ ������� ( position )
                        );
    //��������� �������� ����� ��������
    vertices.position(8); //��������� �� ������ ���������� ����������
    gl.glNormalPointer(GL10.GL_FLOAT, // ��� ������ �������� � �������� ������
                       VERTEX_SIZE,   // ��� (� ������) ����� ���������, ���� ����� ��������� ��� ��� �� ����
                       vertices       // ��� �����, ������� ����� �� ������ ������� ( position )
                      );

    
    //��������� �� ������ �������� ��������
    indices.position(0); //��������� ����� �������� ����� ��������������� ��� ���������
    
    
    //��������� ��������
    gl.glEnable(GL10.GL_TEXTURE_2D); //�������� ����� ������������ ������� � �������������
    
    AssetManager assets = activity.getAssets(); //��� ������ � ������ assets � ������� 
    InputStream is = null;
    try
    {
      is = assets.open("field.png"); //������� �������� ����� ����� �� ����� assets � ������� 
    }
    catch (IOException e)
    {  }
    Bitmap bitmap = BitmapFactory.decodeStream(is); //������� ������ �� ��������� ������
    
    //������� ������� �������
    gl.glGenTextures(2,          // ���������� �������� �������
                     textureIds, // ������ ��� id_������ �������� �������
                     0           // ������ � ������� id_������ � �������� �������� ������ (� ������ ������ � ��������)
                    );
    //������� openGl ��� ����������� ������ ����� �������� � ��������� ���������
    gl.glBindTexture(GL10.GL_TEXTURE_2D, //��� ��������
                     textureIds[0]       //id ������� ��������
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
    
    is = null;
    try
    {
      is = assets.open("boll.png"); //������� �������� ����� ����� �� ����� assets � ������� 
    }
    catch (IOException e)
    {  }
    bitmap = BitmapFactory.decodeStream(is); //������� ������ �� ��������� ������
    
    //������� openGl ��� ����������� ������ ����� �������� � ��������� ���������
    gl.glBindTexture(GL10.GL_TEXTURE_2D, //��� ��������
                     textureIds[1]       //id ������� ��������
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
    
    gl.glClear(GL10.GL_COLOR_BUFFER_BIT);  //������� ����� �����

    gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIds[0]);
    indices.position(0);
    //������ ������� ������� ��������������
    gl.glMatrixMode(GL10.GL_MODELVIEW); //�������� � �������� ������ (� �������� ������������)
    gl.glLoadIdentity();                //���������� �������
    //������ �������� � ������� ��������
    gl.glDrawElements(GL10.GL_TRIANGLES,      // ��� ��������� ��������
                      6,                      //���������� ��������
                      GL10.GL_UNSIGNED_SHORT, //���� ��������
                      indices                 //�������� ����� ��������
                     );
    gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIds[1]);    
    indices.position(6);
    //������ ������� ������� ��������������
    gl.glMatrixMode(GL10.GL_MODELVIEW); //�������� � �������� ������ (� �������� ������������)
    gl.glLoadIdentity();                //���������� �������
    gl.glTranslatef(x, y, 0);           //���������� ���������� �� ��������� �������� x, y z
//    gl.glRotatef(angel, rx, ry, rz);    //������� ������ ������� (rx,ry,rz) �� ���� angel
//    gl.glScalef(sx, sy, sz);            //������������ � �� ��� x � sx ��� �� ��� y � z � sy � sz ���    
    
    //������ �������� � ������� ��������
    gl.glDrawElements(GL10.GL_TRIANGLES,      // ��� ��������� ��������
                      6,                      //���������� ��������
                      GL10.GL_UNSIGNED_SHORT, //���� ��������
                      indices                 //�������� ����� ��������
                     );    
  }

  int cur_lunka = -1;  
  //������ ��������
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
      orto.ortogonal(); //������ ������������ �����������
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
    if( rotation == 0 /*��� 2*/)
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
































