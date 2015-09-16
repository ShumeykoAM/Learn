package common.geometry;

//Вектор или точка в двумерном пространстве
public class CVector2D
  implements CVector<CVector2D>
{
  public float vx, vy;
  public CVector2D()
  {
    vx = 1;
    vy = 0;
  }
  public CVector2D(float _vx, float _vy)
  {
    vx = _vx;
    vy = _vy;
  }  
  //Вектор по двум точкам
  public CVector2D(CVector2D _p1, CVector2D _p2)
  {
    vx = _p2.vx - _p1.vx;
    vy = _p2.vy - _p1.vy;
  }
  public CVector2D getCopy()
  {
    return new CVector2D(vx, vy);
  }
  @Override
  public CVector2D to(CVector2D _v)
  {
    vx = _v.vx;
    vy = _v.vy;
    return this;
  }
  @Override
  public CVector2D addVector(CVector2D _v)
  {
    vx += _v.vx;
    vy += _v.vy;
    return this;
  }
  @Override
  public CVector2D subVector(CVector2D _v)
  {
    vx -= _v.vx;
    vy -= _v.vy;
    return this;
  }
  @Override
  public CVector2D mulScalar(float _s)
  {
    vx *= _s;
    vy *= _s;
    return this;
  }
  @Override
  public CVector2D subScalar(float _s)
  {
    vx /= _s;
    vy /= _s;
    return this;
  }
  @Override
  public float scalarMulVectror(CVector2D _v)
  {
    return (vx * _v.vx) + (vy * _v.vy);
  }
  @Override
  public float module()
  {
    return (float)Math.sqrt( vx*vx + vy*vy );
  }
  @Override
  public float projectionS(CVector2D _v)
  {
    return scalarMulVectror(_v) / _v.module();
  }
  @Override
  public float cosCorner(CVector2D _v)
  {
    return (scalarMulVectror(_v))/(module()*_v.module());
  }
  @Override
  public CVector2D projectionV(CVector2D _v)
  {
    float projS = projectionS(_v);
    to(_v);
    normalize();
    mulScalar(projS);      
    return this;
  }
  @Override
  public CVector2D normalize()
  {
    subScalar(module());
    return this;
  }
  @Override
  public CVector2D ortogonal()
  {
    if(vx == 0.0f)
    {
      vx = 1;
      vy = 0.0f;
    }
    else
    {
      vx = -vy/vx;
      vy = 1.0f;
    }
    return this;
  }
  
    
}







































