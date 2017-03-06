В папке \glassfish4\mq\bin

1. запускаем брокера
  start imqbrokerd.exe -port 7677

2. запускаем админа и настраиваем очередь TestQueue, как настроить рассказывает Яков
  start imqadmin.exe


----------------------------------
Потом разберусь с тем что ниже, что то перестает работать MOM сервак (выше), и как коннектиться к MOM через GlassFish пока не понятно

Для работы с MOM серез GlassFish server нужно добавить JMS ресурсы, которые неполучается добавить через панель GlassFish
  так как выскакивает ошибка class java.lang.RuntimeException
  Обойти ошибку можно с помощью командной строки glassfish4\glassfish\bin\asadmin.bat

  - создаем Connection Factorie
  asadmin.bat create-jms-resource --restype javax.jms.ConnectionFactory --description "connection factory for JMS" jms/ConnectionFactory
  - создаем JMS Destination Resource
  asadmin.bat create-jms-resource --restype javax.jms.Queue --property Name=PhysicalDestination jms/TestQueue

  Далее их можно сконфигурировать:

  Сама очередь "TestQueue" уже создана выше через imqadmin.exe, ее можно увидеть в панели GlassFish server на вкладке JMS Physical Destination