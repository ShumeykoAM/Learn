package common.convertor;

public class Vertex
{
  public float x, y, z;     // ���������� � ������������
  public Color color;       // ���� ������ ��� ������� � ���������� ���� ��������� �������
  public float xt, yt;      // ���������� ��������
  public float xn, yn, zn;  // ������� �������

  public boolean CompareTo(Vertex v)
  {
    return x==v.x && y==v.y && z==v.z &&
        color.CompareTo(v.color) &&
        xt==v.xt && yt==v.yt &&
        xn==v.xn && yn==v.yn && zn==v.zn;
  }
}
