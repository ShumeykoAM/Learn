package ru.bloodliviykot.RR.game;

import ru.bloodliviykot.RR.game.Color;
import ru.bloodliviykot.RR.game.Material;

public class MaterialEx
  extends Material
{
  public Color color_ambient;  // Цвет для окружающего освещения
  public Color color_diffuse;  // Цвет для диффузного освещения
  public MaterialEx()
  {
    super("");
    color_ambient = new Color();
    color_diffuse = new Color();
  }
}
