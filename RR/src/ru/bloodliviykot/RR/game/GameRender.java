package ru.bloodliviykot.RR.game;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.view.MotionEvent;
import android.view.View;
import common.geometry.CVector3D;
import ru.bloodliviykot.RR.MyActivity;
import ru.bloodliviykot.RR.game.objects.Car;
import ru.bloodliviykot.RR.game.objects.Trace;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.view.View.OnTouchListener;

public class GameRender
  implements GLSurfaceView.Renderer,
             SensorEventListener,
             OnTouchListener
{
  Car car0 = new Car(new Color(0.0f, 0.0f, 1.0f));
  Trace trace = new Trace();

  @Override
  public void onSurfaceCreated(GL10 gl, EGLConfig config)
  {
    gl.glClearColor(0.3f, 0.5f, 0.9f, 0.15f); //������������� ��������� ����� ����

    gl.glFrontFace(GL10.GL_CCW);    // ������� ������� �������� ������� ������ ������� �������
    gl.glEnable(GL10.GL_CULL_FACE); // �������� ������������� ������ ������

    //�������� ������������ �������� ��� ���������������
    gl.glEnable(GL10.GL_NORMALIZE);

    gl.glEnable(GL10.GL_BLEND); //�������� ����� ����������
    gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA); //������ ����������

    gl.glEnable(GL10.GL_LIGHTING); //�������� ����� ������������� �����
    //���������� �������� ���������, ���� ������ ���� �����
    float[] ambientColor = { 0.01f, 0.01f, 0.01f, 1.0f };
    //float[] ambientColor = { 1.0f, 1.0f, 1.0f, 1.0f };
    gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, ambientColor, 0); //������ ���������� �������� ���������

    //�������� ������ �������� ����� (����� 8 �� ������ ����������)
    gl.glEnable(GL10.GL_LIGHT0);
    float[] ambient_0  = { 0.0f, 0.0f, 0.0f, 1.0f }; // ������� float_��
    float[] diffuse_0  = { 0.0f, 0.0f, 0.0f, 1.0f }; //   � ������� �����������
    float[] specular_0 = { 1.0f, 1.0f, 1.0f, 1.0f }; //   �������, �����, �������, � �����
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT , ambient_0 , 0);
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE , diffuse_0 , 0);
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR , specular_0 , 0);
    float[] position1 = {0.0f, 0.0f, 8, 0}; //x, y, z - �����, 0 - ������� � ��� ��� ���� ��������� �� ����� � �����
    gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, position1, 0);

    //�������� ������ �������� ����� (����� 8 �� ������ ����������)
    gl.glEnable(GL10.GL_LIGHT1);
    float[] ambient_1  = { 0.5f, 0.5f, 0.5f, 1.0f }; // ������� float_��
    float[] diffuse_1  = { 0.5f, 0.5f, 0.5f, 1.0f }; //   � ������� �����������
    float[] specular_1 = { 0.0f, 0.0f, 0.0f, 1.0f }; //   �������, �����, �������, � �����
    gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT , ambient_1 , 0);
    gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE , diffuse_1 , 0);
    gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPECULAR , specular_1 , 0);
    float[] position2 = {5.0f, 3.0f, 8, 0}; //x, y, z - �����, 0 - ������� � ��� ��� ���� ��������� �� ����� � �����
    gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, position2, 0);

    //��������� openGL ������������ ���� ������ � �������� �������� � ����������� ������ ���������
    gl.glEnable(GL10.GL_COLOR_MATERIAL); //�������� ������ � �����������, ������� � ���������� ����� ����� �� �����

    //������ ���������� ���� ���������
    float[] specular = { 1.0f, 1.0f, 1.0f, 1.0f }; // �������, �����, �������, � �����
    gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specular, 0); // ���������� ���� ���������
    //����������� ����������� ���������
    gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 50);

    gl.glEnable(GL10.GL_DEPTH_TEST); //�������� ���� ������� z ������

    //
    D3D_objects.PrepareAll(gl, MyActivity.activity);
    //��������� ����������� ��������� ������
    D3D_objects.camera.eye = new CVector3D(0.0f, 2.0f, 6.0f);
    D3D_objects.camera.pointLook = new CVector3D(0.0f, 1.0f, 0.0f);
    D3D_objects.camera.vertical = new CVector3D(0.0f, 1.0f, 0.0f);
  }

  @Override
  public void onSurfaceChanged(GL10 gl, int width, int height)
  {
    height_scr = height;
    //������ ������ ������
    gl.glViewport( 0, 0, width, height );

    //������������� ������� �������� (�����������-������������� ��� �������������)
    gl.glMatrixMode(GL10.GL_PROJECTION); //����� ��� ��������� ������ ����� �������� � �������� ��������
    gl.glLoadIdentity();                 //���������� ������� �������� �������, ������ �� ���������
    //������������� ��������
    //gl.glOrthof(-width/2, width/2, -height/2, height/2, 10, -10);  //������ ���� ���������� ���� ��������� (���������)
    //������������� ��������
    GLU.gluPerspective(gl, 46/*67*/, (float)width/(float)height, 0.01f, 300.0f);

  }

  float ang = 0.0f;

  float TimeOld = -1;
  float Time;
  float DeltaTime;

  CVector3D pos_trace = new CVector3D(0.0f, -0.41f, 0.0f);
  @Override
  public void onDrawFrame(GL10 gl)
  {
    //������� ����� ����� � ����� �������
    gl.glClear(GL10.GL_DEPTH_BUFFER_BIT | GL10.GL_COLOR_BUFFER_BIT );

    Time = System.nanoTime();
    if(TimeOld == -1)
      TimeOld = Time;
    DeltaTime = (Time - TimeOld)/630000000;
    TimeOld = Time;

    car0.anfW = ang/(car0.Speed/10);
    if(car0.anfW>45)
      car0.anfW=45;
    else if(car0.anfW<-45)
      car0.anfW = -45;

    car0.physics(DeltaTime);

    CVector3D posCam = car0.Position.getCopy();
    posCam.addVector(car0.VecSpeed.mulScalar(-7));
    //�������� ������ ����� ���������� �������� �� ����� ��������� ������ ���� ��������
    posCam.vy = 2.2f;
    D3D_objects.camera.eye = posCam;
    D3D_objects.camera.pointLook = car0.Position;
    //D3D_objects.camera.vertical = new CVector3D(0.0f, 1.0f, 0.0f);


    car0.Draw(gl);

    try
    {
      trace.Translate(gl, pos_trace);
      trace.Scale(gl, new CVector3D(10.0f, 1.0f, 10.0f));
    } catch(D3D_objects.D3D_obj_Exception e)
    {
      e.printStackTrace();
    }
    trace.Draw(gl, null);

  }
  float height_scr = 0;
  float touch = 0;


  // g sensor
  @Override
  public void onSensorChanged(SensorEvent event)
  {
    ang = -event.values[1] * 10;
    //acceleration_x = -event.values[0];
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy)
  {

  }

  //��������
  @Override
  public boolean onTouch(View v, MotionEvent event)
  {
    synchronized(this)
    {
      int action = event.getAction();
      float y = event.getY();
      switch(event.getAction())
      {
        case MotionEvent.ACTION_DOWN:
        case MotionEvent.ACTION_MOVE:
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
          touch = 100 / height_scr * (height_scr - event.getY());
          car0.Speed = touch;
          break;
      }
      if(action != MotionEvent.ACTION_DOWN)
      {
        touch=0;
      }
    }
    return true;
  }


}


























