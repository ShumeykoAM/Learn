package sbrf.esa2.autnnz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Инициализация и запуск Spring Boot
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
	 * @param args можно переопределить параметры SpringBoot передав из в параметрах args, например: --server.port=8090
	 */
	public static void main(String[] args)
	{
		SpringApplication.run(MainApp.class, args);
	}


	/**
	 * @return Домашняя страница http://localhost:8080
	 */
	@RequestMapping("/")
	@ResponseBody
	String home()
	{
		return "Hello World SpringBoot!";
	}
}
