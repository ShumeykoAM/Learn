package com.spring.di.configuration.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.annotation.Resource;

/**
 * Пример внедрения коллекций
 * @author Alexander Shumeyko
 * @ created 07.11.2020
 */
@Component
public class ContainCollections
{
	@Autowired
	private Map<String, MoreImplementations> map;

	@Autowired
	private Set<MoreImplementations> set;

	@Autowired
	private List<MoreImplementations> list;

	@Autowired
	private Properties customIdProperties; //Имя поля совпадает с идентификатором бина

	@Autowired
	@Qualifier("someProperties2")
	private Properties propertiesA;
	//или одной аннотацией
	@Resource(name = "someProperties2")
	private Properties propertiesB;
}
