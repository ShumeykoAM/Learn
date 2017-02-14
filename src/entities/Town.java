package entities;

import javax.persistence.*;

/**
 * Этот класс сгенерился через Persistence П.К.М generate persistence mapping/ставим галочку на нужном классе и...
 */
@Entity
@Table(name = "Town", schema = "", catalog = "")
public class Town
{
  public Town()
  {     }
  public Town(int outId, String typeCreate, String name, String birthday)
  {
    this.outId = outId;
    this.typeCreate = typeCreate;
    this.name = name;
    this.birthday = birthday;
  }
  
  private Integer id;
  private int outId;
  private String typeCreate;
  private String name;
  private String birthday;
  
  @Id
  //@GeneratedValue( strategy = GenerationType.IDENTITY) //автоинкремент не работает наверное потому что СУБД SQLite
  @Column(name = "id", nullable = true)
  public Integer getId()
  {
    return id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  @Basic
  @Column(name = "out_id", nullable = false)
  public int getOutId()
  {
    return outId;
  }
  
  public void setOutId(int outId)
  {
    this.outId = outId;
  }
  
  @Basic
  @Column(name = "typeCreate", nullable = false, length = 0)
  public String getTypeCreate()
  {
    return typeCreate;
  }
  
  public void setTypeCreate(String typeCreate)
  {
    this.typeCreate = typeCreate;
  }
  
  @Basic
  @Column(name = "name", nullable = false, length = 0)
  public String getName()
  {
    return name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  @Basic
  @Column(name = "birthday", nullable = false, length = 0)
  public String getBirthday()
  {
    return birthday;
  }
  
  public void setBirthday(String birthday)
  {
    this.birthday = birthday;
  }
  
  @Override
  public boolean equals(Object o)
  {
    if(this == o)
      return true;
    if(o == null || getClass() != o.getClass())
      return false;
    
    Town that = (Town)o;
    
    if(outId != that.outId)
      return false;
    if(id != null ? !id.equals(that.id) : that.id != null)
      return false;
    if(typeCreate != null ? !typeCreate.equals(that.typeCreate) : that.typeCreate != null)
      return false;
    if(name != null ? !name.equals(that.name) : that.name != null)
      return false;
    if(birthday != null ? !birthday.equals(that.birthday) : that.birthday != null)
      return false;
    
    return true;
  }
  
  @Override
  public int hashCode()
  {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + outId;
    result = 31 * result + (typeCreate != null ? typeCreate.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
    return result;
  }
}
