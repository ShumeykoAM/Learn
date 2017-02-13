package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Этот класс сгенерился через Persistence П.К.М generate persistence mapping/ставим галочку на нужном классе и...
 */
//Используем аннотации для описания POJO класса, можно так же использовать xml mapping
@Entity
@Table(name="table_name")
public class TableNameEntity
{
  //Hibernate требует конструктор без параметров
  public TableNameEntity()
  {  }
  public TableNameEntity(int id)
  {
    this.setId(id);
  }
  
  //Поля POJO объекта
  private Integer id;
  @Id
  public Integer getId()
  {
    return id;
  }
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  //Нужны (наверное не всегда) методы сравнения объектов
  @Override
  public boolean equals(Object o)
  {
    if(this == o)
      return true;
    if(o == null || getClass() != o.getClass())
      return false;
    
    TableNameEntity that = (TableNameEntity)o;
    
    if(id != null ? !id.equals(that.id) : that.id != null)
      return false;
    
    return true;
  }
  @Override
  public int hashCode()
  {
    return id != null ? id.hashCode() : 0;
  }
}
