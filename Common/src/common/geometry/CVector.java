package common.geometry;

//Вектор или точка
public interface CVector<T>
{
  public T     getCopy();              // Получить копию вектора
  public T     to(T _v);               // Приравнять к вектору _v
  public T     addVector(T _v);        // Прибавить вектор _v
  public T     subVector(T _v);        // Отнять вектор _v
  public T     mulScalar(float _s);    // Умножить на скаляр _s
  public T     subScalar(float _s);    // Разделить на скаляр _s
  public float scalarMulVectror(T _v); // Скалярно умножить на вектор _v
  public float module();               // Модуль (длина вектора)
  public float projectionS(T _v);      // Проекция на вектор _v, длина проекции (при несовпадении направления отрицательна)
  public T     projectionV(T _v);      // Спроецировать на ось _v (направление _v не важно)
  public float cosCorner(T _v);        // Косинус угла к сонаправлению вектора _v
  public T     normalize();            // Нормализуем вектор (приводим к единичной длине)
  public T     ortogonal();            // Стать ортогональным вектором
  
}
