package common.geometry;

//������ ��� �����
public interface CVector<T>
{
  public T     getCopy();              // �������� ����� �������
  public T     to(T _v);               // ���������� � ������� _v
  public T     addVector(T _v);        // ��������� ������ _v
  public T     subVector(T _v);        // ������ ������ _v
  public T     mulScalar(float _s);    // �������� �� ������ _s
  public T     subScalar(float _s);    // ��������� �� ������ _s
  public float scalarMulVectror(T _v); // �������� �������� �� ������ _v
  public float module();               // ������ (����� �������)
  public float projectionS(T _v);      // �������� �� ������ _v, ����� �������� (��� ������������ ����������� ������������)
  public T     projectionV(T _v);      // ������������� �� ��� _v (����������� _v �� �����)
  public float cosCorner(T _v);        // ������� ���� � ������������� ������� _v
  public T     normalize();            // ����������� ������ (�������� � ��������� �����)
  public T     ortogonal();            // ����� ������������� ��������
  
}
