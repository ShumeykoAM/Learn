package ru.bloodliviykot.RR.game;

import ru.bloodliviykot.RR.game.Color;

public class Material
{
  public Color color_specular; // Цвет для зеркального освещения
  public float k_specular;     // Коэффициент зеркального отражения
  public String name;
  public int ID;
  public Material(String _name)
  {
    color_specular = new Color();
    name = _name;
  }
  public Material(short _ID)
  {
    color_specular = new Color();
    ID = _ID;
  }
}
