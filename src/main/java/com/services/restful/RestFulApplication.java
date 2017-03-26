package com.services.restful;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author kot
 * @ created 26.03.2017
 * @ $Author$
 * @ $Revision$
 */

//Рестфул сервисы не имеют дескриптора развертывания, поэтому надо создать класс наследник от
//   javax.ws.rs.core.Application - JEE сервер поймет что у нас есть restful сервис
@ApplicationPath("rf_resources") //Указываем префикс в пути к классам restful сервисов, в данном случае это "http://localhost:8080/myserver/rf_resources"
public class RestFulApplication
    extends Application
{
    
}
