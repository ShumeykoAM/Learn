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
    gl.glClearColor(0.3f, 0.5f, 0.9f, 0.15f); //Устанавливаем состояние цвета фона

    gl.glFrontFace(GL10.GL_CCW);    // Лицевая сторона задается обходом против часовой стрелки
    gl.glEnable(GL10.GL_CULL_FACE); // Включить использование обхода вершин

    //Включить нормализацию нормалей при масштабировании
    gl.glEnable(GL10.GL_NORMALIZE);

    //gl.glEnable(GL10.GL_BLEND); //Включить альфа смешивание
    //gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA); //Способ смешивания

    gl.glEnable(GL10.GL_LIGHTING); //Включаем режим использования света
    //Глобальный источник подсветки, есть только один такой
    float[] ambientColor = { 0.0f, 0.0f, 0.0f, 1.0f };
    //float[] ambientColor = { 1.0f, 1.0f, 1.0f, 1.0f };
    gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, ambientColor, 0); //Задаем глобальный источник подсветки

    //Включаем первый источник света (всего 8 не считая глобальный)
    gl.glEnable(GL10.GL_LIGHT0);
    float[] ambient_1  = { 0.4f, 0.4f, 0.4f, 1.0f }; // Массивы float_ов
    float[] diffuse_1  = { 0.4f, 0.4f, 0.4f, 1.0f }; //   в которых перечислены
    float[] specular_1 = { 0.4f, 0.4f, 0.4f, 1.0f }; //   красный, синий, зеленый, и альфа
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT , ambient_1 , 0);
    float[] position = {5.0f, 0.0f, 8, 0}; //x, y, z - точка, 0 - говорит о том что свет направлен из точки в центр
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, position, 0);

    //Разрешить openGL использовать цвет вершин в качестве фонового и рассеянного цветов материала
    gl.glEnable(GL10.GL_COLOR_MATERIAL); //Включаем работу с материалами, фоновый и рассеянный цвета брать из точек
    //Задаем зеркальный цвет материала
    float[] specular = { 0.0f, 0.0f, 1.0f, 1.0f }; // красный, синий, зеленый, и альфа
    gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_SPECULAR, specular, 0); // Зеркальный цвет материала
    //Коэффициент зеркального отражения
    gl.glMaterialf(GL10.GL_FRONT, GL10.GL_SHININESS, 7);

    gl.glEnable(GL10.GL_DEPTH_TEST); //Включать тест глубины z буфера

  }

  @Override
  public void onSurfaceChanged(GL10 gl, int width, int height)
  {
    //Задаем облать вывода
    gl.glViewport( 0, 0, width, height );

    //Устанавливаем матрицу проекции (паралельную-ортогональную или перспективную)
    gl.glMatrixMode(GL10.GL_PROJECTION); //Далее все матричные методы будут работать с матрицей проекции
    gl.glLoadIdentity();                 //Сбрасываем текущую активную матрицу, делаем ее единичной
    //Ортогональная проекция
    //gl.glOrthof(-width/2, width/2, -height/2, height/2, 10, -10);  //Здесть идут координаты куба отсечения (просмотра)
    //Перспективная проекция
    GLU.gluPerspective(gl, 67, (float)width/(float)height, 0.01f, 300.0f);

    D3D_objects.PrepareAll(gl);
  }

  float angle = 0.0f;
  @Override
  public void onDrawFrame(GL10 gl)
  {
    //Очищаем буфер цвета и буфер глубины
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




































