package com.jaxb;

import java.sql.Timestamp;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author kot
 * @ created 02.04.2017
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Data", propOrder = {"name", "telephone", "address", "lastLoginDate"}) // propOrder - позволяет указать порядок маршализации
public class Data
{
	public Data(){}

	public Data(String login, String name, String telephone, Address address, Timestamp lastLoginDate)
	{
		this.login = login;
		this.name = name;
		this.telephone = telephone;
		this.address = address;
		this.lastLoginDate = lastLoginDate;
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

	@XmlJavaTypeAdapter(TSAdapter.class) //Будет маршализироваться как элемент, тип этого элемента предоставит класс адаптера
	private Timestamp lastLoginDate;


	//Адаптер элемента или атрибута
	private static class TSAdapter extends XmlAdapter<Long, Timestamp>
	{
		@Override public Timestamp unmarshal(Long aLong) throws Exception
		{
			return new Timestamp(aLong);
		}

		@Override public Long marshal(Timestamp v) throws Exception
		{
			return v.getTime();
		}
	}

}
