package common.convertor;

public class Material
{
  public Color color_specular; // ���� ��� ����������� ���������
  public float k_specular;     // ����������� ����������� ���������
  public String name;
  public Material(String _name)
  {
    color_specular = new Color();
    name = _name;
  }
}
