Для начала надо создать папку где ни будь внутри проекта сервера, например папку generate_folder в пакете com.services.jax_ws,
потом используя команду wsimport, находится в JDK, генерим файлы, добавляем к ним PrintFigureService.java

wsimport -d generate_folder -keep http://localhost:8080/myserver/figure?wsdl

//А вот так можно обратиться к сервису
PrintFigureImplService figureService = new PrintFigureImplService();
PrintFigureImpl service = figureService.getPrintFigureImplPort();
String r1 = service.showInfo(CIRCLE, Arrays.asList(2) );
String r2 = service.showInfo(RECTANGLE, Arrays.asList(4, 6));


Данный пример сделан по материалам http://dev-blogs.com/web-service-jax-ws/