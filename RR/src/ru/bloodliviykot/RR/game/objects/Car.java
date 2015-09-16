package ru.bloodliviykot.RR.game.objects;


import common.geometry.CMatrix22;
import common.geometry.CVector2D;
import common.geometry.CVector3D;
import ru.bloodliviykot.RR.MyActivity;
import ru.bloodliviykot.RR.game.Color;
import ru.bloodliviykot.RR.game.D3D_objects;

import javax.microedition.khronos.opengles.GL10;

public class Car
  implements D3D_objects.I_MaterialReplace
{
  //Классы 3D объектов из которых состоит машина
  private static class Cover //Кузов
    extends D3D_objects
  {
    public Cover()
    {
      super("objects/car/WolfCar.3df", MyActivity.activity, null);
    }
  }
  private static class Wheel //Колесо
    extends D3D_objects
  {
    public Wheel()
    {
      super("objects/car/Wheel.3df", MyActivity.activity, null);
    }
  }


  //
  public static final CVector3D wFront = new CVector3D(0.8916f, 0.0f, -1.7738f); //Координаты правой передней оси
  public static final CVector3D wBack = new CVector3D(0.8928f, 0.0f, 1.5043f);   //Координаты правой задней оси
  public static final CVector3D wSupport = new CVector3D(0.084666f, 0.0f, 0.0f); //Торец диска (точка соприкачн с осью)
  //
  private static Cover cover; // Колесо
  private static Wheel wheel; // Кузов
  private Color color_cover;  // Цвет кузова
  //Смещения для колес
  private CVector3D wheel_fr = new CVector3D(wFront.vx + wSupport.vx, 0, wFront.vz);
  private CVector3D wheel_fl = new CVector3D(-wFront.vx - wSupport.vx, 0, wFront.vz);
  private CVector3D wheel_br = new CVector3D(wBack.vx + wSupport.vx, 0, wBack.vz);
  private CVector3D wheel_bl = new CVector3D(-wBack.vx - wSupport.vx, 0, wBack.vz);
  //Ось вращения и ось поворота колес
  private CVector3D wheel_rotate = new CVector3D(1.0f, 0.0f, 0.0f);
  private CVector3D wheel_axis = new CVector3D(0.0f, 1.0f, 0.0f);

  //Параметры всего автомобиля
  //Крайние точки автомобиля
  public static final CVector3D pointFront = new CVector3D(0.0f, 0.0f, -2.204899f); //Перед
  public static final CVector3D pointBack = new CVector3D(0.0f, 0.0f, 2.170667f);   //зад

  //Константы (1 метр равет 1единице в пространственных координатах
  public static final float length = pointBack.vz - pointFront.vz; //Длина автомобиля
  public static final float LBase = wBack.vz - wFront.vz;  // Расстояние пежлу передним и задним колесом
  public CVector3D Position = new CVector3D(0.0f, 0.0f, 0.0f);    // Положение в пространстве

  public float Speed = 21.1f; //Скорость 0,1 метра в секунду
  float SpeedAngle = 0.0f; //Угол вектора скорости (против часовой стрелки) относительно оси -z, в плоскости (x,z);
  public CVector3D VecSpeed = new CVector3D();    // Скорость в виде вектора

  public static final float radianCoef = (float)(180/Math.PI);

  //Конструктор
  public Car(Color _color_cover)
  {
    color_cover = _color_cover.getCopyColor(_color_cover);
  }
  //Статический конструктор
  static
  {
    cover = new Cover();
    wheel = new Wheel();
  }

  public void physics(float DeltaTime/*время в секундах*/)
  {
    //DeltaTime = 3.0f;
    //anfW = 30f;
    if(anfW == 0)
      anfW = 0.00001f;

    float S = DeltaTime * Speed;
    float lambda = (float)(SpeedAngle * Math.PI/180);
    CVector2D A = new CVector2D(Position.vx, -Position.vz);
    float alfa = (float)(anfW * Math.PI / 180);
    float r = (float)(Math.cos(alfa)*(LBase/Math.sin(alfa)));
    float gamma = (float)(S/r);
    CVector2D C = new CVector2D((float)(Math.cos(gamma)*r - r), (float)(Math.sin(gamma)*r));
    //Доворачиваем
    CMatrix22 matRot = new CMatrix22(new CVector2D( (float)Math.cos(lambda) , (float)Math.sin(lambda)),
        new CVector2D( -(float)Math.sin(lambda), (float)Math.cos(lambda)) );
    matRot.mul(C);
    //Перемещаем
    A.addVector(C);
    Position.vx = A.vx; Position.vz = -A.vy;

    //Найдем новый угол вектора скорости
    SpeedAngle = lambda*radianCoef + gamma*radianCoef;
    if(SpeedAngle > 360)
      SpeedAngle -= 360;
    if(SpeedAngle < 0)
      SpeedAngle += 360;

    CVector2D SpVec = new CVector2D(0.0f, -1.0f);
    CMatrix22 matRotSpeedVector = new CMatrix22(new CVector2D( (float)Math.cos(SpeedAngle/radianCoef) , -(float)Math.sin(SpeedAngle/radianCoef)),
        new CVector2D( (float)Math.sin(SpeedAngle/radianCoef), (float)Math.cos(SpeedAngle/radianCoef)) );
    matRotSpeedVector.mul(SpVec);
    VecSpeed.vx = SpVec.vx; VecSpeed.vz = SpVec.vy;
  }

  float angRot = 0.0f;
  CVector3D vec = new CVector3D(0.0f, 1.0f, 0.0f);

  public float anfW = 0.0f;
  public void Draw(GL10 gl)
  {
    try
    {
      angRot += Speed*2;
      if(angRot > 360)
        angRot -= 360;

      cover.TranslateAfterRotateFar(gl, Position);
      cover.Rotate(gl, SpeedAngle, vec);
      cover.Draw(gl, this);

      //Передние колеса
      wheel.TranslateAfterRotateFar(gl, Position);
      wheel.RotateFar(gl, SpeedAngle, vec);
      wheel.Translate(gl, wheel_fr);
      wheel.Rotate(gl, anfW + 180.0f, wheel_axis);
      wheel.Rotate(gl, angRot, wheel_rotate);
      wheel.Draw(gl, null);

      wheel.TranslateAfterRotateFar(gl, Position);
      wheel.RotateFar(gl, SpeedAngle, vec);
      wheel.Translate(gl, wheel_fl);
      wheel.Rotate(gl, anfW, wheel_axis);
      wheel.Rotate(gl, -angRot, wheel_rotate);
      wheel.Draw(gl, null);

      //Задние колеса
      wheel.TranslateAfterRotateFar(gl, Position);
      wheel.RotateFar(gl, SpeedAngle, vec);
      wheel.Translate(gl, wheel_br);
      wheel.Rotate(gl, 180.0f, wheel_axis);
      wheel.Rotate(gl, angRot, wheel_rotate);
      wheel.Draw(gl, null);

      wheel.TranslateAfterRotateFar(gl, Position);
      wheel.RotateFar(gl, SpeedAngle, vec);
      wheel.Translate(gl, wheel_bl);
      wheel.Rotate(gl, -angRot, wheel_rotate);
      wheel.Draw(gl, null);

    }
    catch(D3D_objects.D3D_obj_Exception e)
    {
      int fdfdfd=0;
    }
  }

  @Override
  public boolean I_replace_ambient_and_diffuse_color(int IndexMaterial, Color color)
  {
    switch(IndexMaterial)
    {
      case 1:
        color.copyFrom(color_cover);
        return true;
    }
    return false;
  }

}
