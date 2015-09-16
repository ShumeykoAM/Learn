package common.convertor;

public class Vertex
{
  public float x, y, z;     // Координаты в пространстве
  public Color color;       // Цвет вершин или фоновый и рассеянный цвет материала вершины
  public float xt, yt;      // Координаты текстуры
  public float xn, yn, zn;  // Нормаль вершины

  public boolean CompareTo(Vertex v)
  {
    return x==v.x && y==v.y && z==v.z &&
        color.CompareTo(v.color) &&
        xt==v.xt && yt==v.yt &&
        xn==v.xn && yn==v.yn && zn==v.zn;
  }
}
