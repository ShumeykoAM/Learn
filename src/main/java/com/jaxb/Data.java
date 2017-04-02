package com.jaxb;

import javax.xml.bind.annotation.*;

/**
 * @author kot
 * @ created 02.04.2017
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Data", propOrder = {"name", "telephone", "address"}) // propOrder - позволяет указать порядок маршализации
public class Data
{
	public Data(){}

	public Data(String login, String name, String telephone, Address address)
	{
		this.login = login;
		this.name = name;
		this.telephone = telephone;
		this.address = address;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTelephone()
	{
		return telephone;
	}

	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}

	public Address getAddress()
	{
		return address;
	}

	public void setAddress(Address address)
	{
		this.address = address;
	}

	@XmlRootElement
	@XmlType(name = "address", propOrder = {"country"})
	public static class Address
	{
		public Address(){}

		public Address(String country)
		{
			this.country = country;
		}

		private String country;

		public String getCountry()
		{
			return country;
		}

		public void setCountry(String country)
		{
			this.country = country;
		}
	}


	@XmlAttribute(name = "nameInXml", required = true) //Будет маршализироваться как атрибут
	private String login;

	@XmlElement(required = true)   //Будет маршализироваться как элемент
	private String name;

	@XmlElement(required = true)
	private String telephone;

	@XmlElement
	private Address address = new Address();

}
