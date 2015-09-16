package common.geometry;

public class CVector3D
  implements CVector<CVector3D>
{
  public float vx, vy, vz;
  public CVector3D()
  {
    vx = 1.0f;
    vy = vz = 0.0f;
  }
  public CVector3D(float _vx, float _vy, float _vz)
  {
    vx = _vx;
    vy = _vy;
    vz = _vz;
  }
  //Вектор по двум точкам
  public CVector3D(CVector3D _p1, CVector3D _p2)
  {
    vx = _p2.vx - _p1.vx;
    vy = _p2.vy - _p1.vy;
    vz = _p2.vz - _p1.vz;
  }
  @Override
  public CVector3D getCopy()
  {
    return new CVector3D(vx, vy, vz);
  }
  @Override
  public CVector3D to(CVector3D _v)
  {
    vx = _v.vx;
    vy = _v.vy;
    vz = _v.vz;
    return this;
  }
  @Override
  public CVector3D addVector(CVector3D _v)
  {
    vx += _v.vx;
    vy += _v.vy;
    vz += _v.vz;
    return this;
  }
  @Override
  public CVector3D subVector(CVector3D _v)
  {
    vx -= _v.vx;
    vy -= _v.vy;
    vz -= _v.vz;
    return this;
  }
  @Override
  public CVector3D mulScalar(float _s)
  {
    vx *= _s;
    vy *= _s;
    vz *= _s;
    return this;
  }
  @Override
  public CVector3D subScalar(float _s)
  {
    vx /= _s;
    vy /= _s;
    vz /= _s;
    return this;
  }
  @Override
  public float scalarMulVectror(CVector3D _v)
  {
    return (vx * _v.vx) + (vy * _v.vy) + (vz * _v.vz);
  }
  @Override
  public float module()
  {
    return (float)Math.sqrt( vx*vx + vy*vy + vz*vz);
  }
   @Override
  public float projectionS(CVector3D _v)
  {
    return scalarMulVectror(_v) / _v.module();
  }
  @Override
  public CVector3D projectionV(CVector3D _v)
  {
    float projS = projectionS(_v);
    to(_v);
    normalize();
    mulScalar(projS);
    return this;
  }
  @Override
  public float cosCorner(CVector3D _v)
  {
    return (scalarMulVectror(_v))/(module()*_v.module());
  }
  @Override
  public CVector3D normalize()
  {
    subScalar(module());
    return this;
  }
  @Override
  public CVector3D ortogonal()
  {
    if(vz == 0.0f)
    {
      vy = -vx/vy;
      vx = 1.0f;
      vz = 0.0f;
    }
    else
    {
      vz = -vx/vz;
      vx = 1.0f;
      vy = 0.0f;
    }
    return this;
  }

}






























