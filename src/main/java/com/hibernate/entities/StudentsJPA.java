package com.hibernate.entities;

import javax.persistence.*;

/**
 * @author Kot
 * @ created 22.04.2017
 * @ $Author$
 * @ $Revision$
 */
@Entity
@Table(name = "Students")
public class StudentsJPA
{
	private int id;
	private String name;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE , generator="sqlite_sequence")
	@TableGenerator(name="sqlite_sequence", table="sqlite_sequence", pkColumnName="name", valueColumnName="seq", allocationSize=1)
	@Column(name = "_id")
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	@Basic @Column(name = "Name") public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	
}
