package hibernate;

/**
 * @author sbt-shumeyko-am
 * @ created 06.04.2017
 * @ $Author$
 * @ $Revision$
 */
public class Students
{
	private Integer id;
	private String name;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Students students = (Students) o;

		if (id != null ? !id.equals(students.id) : students.id != null)
			return false;
		if (name != null ? !name.equals(students.name) : students.name != null)
			return false;

		return true;
	}

	@Override public int hashCode()
	{
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}
}
