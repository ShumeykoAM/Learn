package common.convertor;

/**
 * Created by Kot on 20.01.2015.
 */
public class MaterialEx
  extends Material
{
  public Color color_ambient;  // Цвет для окружающего освещения
  public Color color_diffuse;  // Цвет для диффузного освещения
  public MaterialEx(String _name)
  {
    super(_name);
    color_ambient = new Color();
    color_diffuse = new Color();
  }
}
