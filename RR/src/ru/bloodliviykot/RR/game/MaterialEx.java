package ru.bloodliviykot.RR.game;

import ru.bloodliviykot.RR.game.Color;
import ru.bloodliviykot.RR.game.Material;

public class MaterialEx
  extends Material
{
  public Color color_ambient;  // ���� ��� ����������� ���������
  public Color color_diffuse;  // ���� ��� ���������� ���������
  public MaterialEx()
  {
    super("");
    color_ambient = new Color();
    color_diffuse = new Color();
  }
}
