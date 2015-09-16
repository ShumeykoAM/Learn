package ru.common.transform;

public class Material
{
  public Color color_specular; // Цвет для зеркального освещения
  public float k_specular;     // Коэффициент зеркального отражения
  public String name;          // Имя материала
  public int IDMaterial;       // ID материала
  public Material(String _name, int _IDMaterial)
  {
    color_specular = new Color();
    name = _name;
    IDMaterial = _IDMaterial;
  }
}
