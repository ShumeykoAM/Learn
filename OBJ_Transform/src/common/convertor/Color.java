package common.convertor;

// ���������� ��������
public class Color
{
  public float r, g, b, a; // ����

  public boolean CompareTo(Color c)
  {
    return r==c.r && g==c.g && b==c.b && a==c.a;
  }
}
