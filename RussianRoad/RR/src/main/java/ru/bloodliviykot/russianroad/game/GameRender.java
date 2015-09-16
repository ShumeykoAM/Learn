package ru.bloodliviykot.russianroad.game;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import ru.bloodliviykot.russianroad.MainActivity;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GameRender
  implements GLSurfaceView.Renderer
{
  myObj mo = new myObj();
  myObj2 m02 = new myObj2();
  Sphere sp = new Sphere();

  @Override
  public void onSurfaceCreated(GL10 gl, EGLConfig config)
  {
    gl.glClearColor(0.3f, 0.5f, 0.9f, 0.15f); //������������� ��������� ����� ����

    gl.glFrontFace(GL10.GL_CCW);    // ������� ������� �������� ������� ������ ������� �������
    gl.glEnable(GL10.GL_CULL_FACE); // �������� ������������� ������ ������

    //�������� ������������ �������� ��� ���������������
    gl.glEnable(GL10.GL_NORMALIZE);

    //gl.glEnable(GL10.GL_BLEND); //�������� ����� ����������
    //gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA); //������ ����������

    gl.glEnable(GL10.GL_LIGHTING); //�������� ����� ������������� �����
    //���������� �������� ���������, ���� ������ ���� �����
    float[] ambientColor = { 0.0f, 0.0f, 0.0f, 1.0f };
    //float[] ambientColor = { 1.0f, 1.0f, 1.0f, 1.0f };
    gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, ambientColor, 0); //������ ���������� �������� ���������

    //�������� ������ �������� ����� (����� 8 �� ������ ����������)
    gl.glEnable(GL10.GL_LIGHT0);
    float[] ambient_1  = { 0.4f, 0.4f, 0.4f, 1.0f }; // ������� float_��
    float[] diffuse_1  = { 0.4f, 0.4f, 0.4f, 1.0f }; //   � ������� �����������
    float[] specular_1 = { 0.4f, 0.4f, 0.4f, 1.0f }; //   �������, �����, �������, � �����
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT , ambient_1 , 0);
    float[] position = {5.0f, 0.0f, 8, 0}; //x, y, z - �����, 0 - ������� � ��� ��� ���� ��������� �� ����� � �����
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, position, 0);

    //��������� openGL ������������ ���� ������ � �������� �������� � ����������� ������ ���������
    gl.glEnable(GL10.GL_COLOR_MATERIAL); //�������� ������ � �����������, ������� � ���������� ����� ����� �� �����
    //������ ���������� ���� ���������
    float[] specular = { 0.0f, 0.0f, 1.0f, 1.0f }; // �������, �����, �������, � �����
    gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_SPECULAR, specular, 0); // ���������� ���� ���������
    //����������� ����������� ���������
    gl.glMaterialf(GL10.GL_FRONT, GL10.GL_SHININESS, 7);

    gl.glEnable(GL10.GL_DEPTH_TEST); //�������� ���� ������� z ������

  }

  @Override
  public void onSurfaceChanged(GL10 gl, int width, int height)
  {
    //������ ������ ������
    gl.glViewport( 0, 0, width, height );

    //������������� ������� �������� (�����������-������������� ��� �������������)
    gl.glMatrixMode(GL10.GL_PROJECTION); //����� ��� ��������� ������ ����� �������� � �������� ��������
    gl.glLoadIdentity();                 //���������� ������� �������� �������, ������ �� ���������
    //������������� ��������
    //gl.glOrthof(-width/2, width/2, -height/2, height/2, 10, -10);  //������ ���� ���������� ���� ��������� (���������)
    //������������� ��������
    GLU.gluPerspective(gl, 67, (float)width/(float)height, 0.01f, 300.0f);

    D3D_objects.PrepareAll(gl);
  }

  float angle = 0.0f;
  @Override
  public void onDrawFrame(GL10 gl)
  {
    //������� ����� ����� � ����� �������
    gl.glClear(GL10.GL_DEPTH_BUFFER_BIT | GL10.GL_COLOR_BUFFER_BIT );

    //mo.Translate(gl, 0, 0, -3);
    mo.Rotate(gl, -angle, 0.0f, 1.0f, 0.0f);
    mo.Draw(gl);

    //sp.Rotate(gl, -angle, 1.0f, 0.0f, 0.0f);;
    //sp.Draw(gl);

    angle += 0.1f;
    //m02.Rotate(gl, angle, 1, 0, 0);
    //m02.Draw(gl);

  }

}

class myObj
  extends D3D_objects
{
  myObj()
  {
    super(8, 12);
    putVertex(new Vertex(-1,  1, -1,   0.0f, 0.0f, 1.0f, 1.0f,    0.0f, 1.0f,      0.0f, 0.0f, -1.0f));
    putVertex(new Vertex( 1,  1, -1,   0.0f, 0.0f, 1.0f, 1.0f,    0.0f, 1.0f,      0.0f, 0.0f, -1.0f));
    putVertex(new Vertex( 1, -1, -1,   0.0f, 0.0f, 1.0f, 1.0f,    0.0f, 1.0f,      0.0f, 0.0f, -1.0f));
    putVertex(new Vertex(-1, -1, -1,   0.0f, 0.0f, 1.0f, 1.0f,    0.0f, 1.0f,      0.0f, 0.0f, -1.0f));

    putVertex(new Vertex(-1,  1,  1,   0.0f, 0.0f, 1.0f, 1.0f,    0.0f, 1.0f,      0.0f, 0.0f,  1.0f));
    putVertex(new Vertex( 1,  1,  1,   0.0f, 0.0f, 1.0f, 1.0f,    0.0f, 1.0f,      0.0f, 0.0f,  1.0f));
    putVertex(new Vertex( 1, -1,  1,   0.0f, 0.0f, 1.0f, 1.0f,    0.0f, 1.0f,      0.0f, 0.0f,  1.0f));
    putVertex(new Vertex(-1, -1,  1,   0.0f, 0.0f, 1.0f, 1.0f,    0.0f, 1.0f,      0.0f, 0.0f,  1.0f));



    putIndex(new Index((short)0));
    putIndex(new Index((short)1));
    putIndex(new Index((short)2));
    putIndex(new Index((short)0));
    putIndex(new Index((short)2));
    putIndex(new Index((short)3));

    putIndex(new Index((short)4));
    putIndex(new Index((short)7));
    putIndex(new Index((short)5));
    putIndex(new Index((short)5));
    putIndex(new Index((short)7));
    putIndex(new Index((short)6));

  }
}
class myObj2
    extends D3D_objects
{
  myObj2()
  {
    super(3, 3);
    putVertex(new Vertex(-5,  0,  0,   1.0f, 0.0f, 0.0f, 0.4f,    0.0f, 1.0f,      0.0f, 0.0f, 1.0f));
    putVertex(new Vertex( 3,  3,  0,   1.0f, 0.0f, 0.0f, 0.4f,    0.0f, 0.0f,      0.0f ,0.0f, 1.0f));
    putVertex(new Vertex( 4, -3,  0,   1.0f, 0.0f, 0.0f, 0.4f,    1.0f, 0.0f,      0.0f ,0.0f, 1.0f));

    putIndex(new Index((short)0));
    putIndex(new Index((short)2));
    putIndex(new Index((short)1));
  }
}


class Sphere
  extends D3D_objects
{
  Sphere()
  {
    //super("sphere.obj", MainActivity.activity );
    super("Tetra.obj", MainActivity.activity );
  }
}




































