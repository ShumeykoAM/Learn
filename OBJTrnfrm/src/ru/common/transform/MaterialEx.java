package ru.common.transform;

public class MaterialEx
  extends Material
{
  public Color color_ambient;  // ���� ��� ����������� ���������
  public Color color_diffuse;  // ���� ��� ���������� ���������
  public MaterialEx(String _name, int _IDMaterial)
  {
    super(_name, _IDMaterial);
    color_ambient = new Color();
    color_diffuse = new Color();
  }
}
