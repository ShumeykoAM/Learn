package common.geometry;

public class CMatrix22
{
  public CVector2D column1;
  public CVector2D column2;
  public CMatrix22(CVector2D _col1, CVector2D _col2)
  {
    column1 = _col1.getCopy();
    column2 = _col2.getCopy();
  }

  public CVector2D mul(CVector2D _v)
  {
    float x = column1.vx * _v.vx + column2.vx * _v.vy;
    _v.vy = column1.vy * _v.vx + column2.vy * _v.vy;
    _v.vx = x;
    return _v;
  }

}
