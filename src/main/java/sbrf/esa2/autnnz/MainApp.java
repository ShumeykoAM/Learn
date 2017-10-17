package sbrf.esa2.autnnz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Точка входа и инициализации Spring Boot
 *
 * @author sbt-shumeyko-am
 * @ created 17.10.2017
 * @ $Author$
 * @ $Revision$
 */

@Controller
@SpringBootApplication
public class MainApp
{
	/**
	 * Точка входа
	 * @param args можно переопределить параметры приложения Spring, например изменить порт --server.port=8090
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		SpringApplication.run(MainApp.class, args);
	}

	/**
	 * @return Домашняя страница http://localhost:9080/
	 */
	@RequestMapping("/")
	@ResponseBody
	public String home()
	{
		return "Hello World SpringBoot!";
	}
}
