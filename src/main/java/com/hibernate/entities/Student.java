package com.hibernate.entities;

/**
 * @author Kot
 * @ created 24.04.2017
 * @ $Author$
 * @ $Revision$
 */
public class Student
{
	private long id;
	private String name;
	private Course course;

	public Course getCourse()
	{
		return course;
	}

	public void setCourse(Course course)
	{
		this.course = course;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
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

}
