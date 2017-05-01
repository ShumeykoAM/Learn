::Следующая команда применит все непримененные изменения из файла changeDB.sql
liquibase --driver=oracle.jdbc.OracleDriver --classpath=D:\gradle\src\library\ojdbc6_g.jar --changeLogFile=changeDB.sql --url="jdbc:oracle:thin:@//localhost:1521/orcl" --username=CSA --password=123456 update

::Следующая команда отменит последнее изменение из файла changeDB.sql
::liquibase --driver=oracle.jdbc.OracleDriver --classpath=D:\gradle\src\library\ojdbc6_g.jar --changeLogFile=changeDB.sql --url="jdbc:oracle:thin:@//localhost:1521/orcl" --username=CSA --password=123456 rollbackCount 1