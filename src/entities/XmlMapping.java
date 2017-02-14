package entities;

/**
 * @author SBT-Shumeyko-AM
 * @ created 14.02.2017
 * @ $Author$
 * @ $Revision$
 */
public class XmlMapping
{
  private Integer id;
  private String name;
  
  public Integer getId()
  {
    return id;
  }
  
  public XmlMapping setId(Integer id)
  {
    this.id = id;
    return this;
  }
  
  public String getName()
  {
    return name;
  }
  
  public XmlMapping setName(String name)
  {
    this.name = name;
    return this;
  }
  
  @Override
  public boolean equals(Object o)
  {
    if(this == o)
      return true;
    if(o == null || getClass() != o.getClass())
      return false;
    
    XmlMapping that = (XmlMapping)o;
    
    if(id != null ? !id.equals(that.id) : that.id != null)
      return false;
    if(name != null ? !name.equals(that.name) : that.name != null)
      return false;
    
    return true;
  }
  
  @Override
  public int hashCode()
  {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }
}
