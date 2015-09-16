package common.convertor;

public class Material
{
  public Color color_specular; // Цвет для зеркального освещения
  public float k_specular;     // Коэффициент зеркального отражения
  public String name;
  public Material(String _name)
  {
    color_specular = new Color();
    name = _name;
  }
}
