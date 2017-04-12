Создаем папку generate_folder, курсор фара на данной папке,
потом используя команду wsimport, находится в JDK, генерим файлы

wsimport -d generate_folder -keep http://localhost:8080/myserver/my_url_pattern?wsdl
Используем сгенерированные файлы для открытия клиента и вызова методов сервиса

Еще по WSDL можно сгенерить и серверную часть

Данный пример сделан по материалам http://dev-blogs.com/web-service-jax-ws/