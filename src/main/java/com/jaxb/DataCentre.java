package com.jaxb;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author kot
 * @ created 02.04.2017
 * @ $Author$
 * @ $Revision$
 */

//Объект список других объектов
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DataCentre
{
	public DataCentre(){}

	public Integer getNumber()
	{
		return number;
	}

	public void setNumber(Integer number)
	{
		this.number = number;
	}

	public void setList(ArrayList<Data> list)
	{
		this.list = list;
	}

	public boolean add(Data data)
	{
		number++;
		return list.add(data);
	}

	//Список других маршализируемых объектов
	@XmlElement(name = "data")
	private ArrayList<Data> list = new ArrayList<>();

	@XmlElement
	private Integer number = 0;
}
