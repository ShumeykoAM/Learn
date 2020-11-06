package com.spring.di.configuration.xml;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Указываем Spring Xml конфигурацию
 * @author Alexander Shumeyko
 * @ created 05.11.2020
 */
@Configuration
@ImportResource({"classpath*:applicationContext.xml"})
public class XmlConfiguration
{
}
