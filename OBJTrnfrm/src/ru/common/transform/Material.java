package ru.common.transform;

public class Material
{
  public Color color_specular; // ���� ��� ����������� ���������
  public float k_specular;     // ����������� ����������� ���������
  public String name;          // ��� ���������
  public int IDMaterial;       // ID ���������
  public Material(String _name, int _IDMaterial)
  {
    color_specular = new Color();
    name = _name;
    IDMaterial = _IDMaterial;
  }
}
