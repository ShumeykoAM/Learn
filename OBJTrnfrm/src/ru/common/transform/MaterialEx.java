package ru.common.transform;

public class MaterialEx
  extends Material
{
  public Color color_ambient;  // Цвет для окружающего освещения
  public Color color_diffuse;  // Цвет для диффузного освещения
  public MaterialEx(String _name, int _IDMaterial)
  {
    super(_name, _IDMaterial);
    color_ambient = new Color();
    color_diffuse = new Color();
  }
}
