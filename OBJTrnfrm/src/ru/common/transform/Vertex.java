package ru.common.transform;

public class Vertex
{
  public float x, y, z;     // ���������� � ������������
  public float xt, yt;      // ���������� ��������
  public float xn, yn, zn;  // ������� �������
  public int IDMaterial;    // ID ��������� ���� �������

  public boolean CompareTo(Vertex v)
  {
    return  x==v.x  &&  y==v.y  &&  z==v.z  &&
           xt==v.xt && yt==v.yt &&
           xn==v.xn && yn==v.yn && zn==v.zn &&
          IDMaterial == v.IDMaterial;
  }
}
