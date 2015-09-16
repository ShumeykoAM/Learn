package ru.common.transform;

public class Vertex
{
  public float x, y, z;     // Координаты в пространстве
  public float xt, yt;      // Координаты текстуры
  public float xn, yn, zn;  // Нормаль вершины
  public int IDMaterial;    // ID материала этой вершины

  public boolean CompareTo(Vertex v)
  {
    return  x==v.x  &&  y==v.y  &&  z==v.z  &&
           xt==v.xt && yt==v.yt &&
           xn==v.xn && yn==v.yn && zn==v.zn &&
          IDMaterial == v.IDMaterial;
  }
}
