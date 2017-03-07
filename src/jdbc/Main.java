package jdbc;

import java.sql.*;

/**
 * @author sbt-shumeyko-am
 * @ created 07.03.2017
 * @ $Author$
 * @ $Revision$
 */
public class Main
{                           
    public static final String pathDB = "jdbc:sqlite:C:\\work\\MyLearn\\web\\web3\\db\\db_first.db";
    public static void main(String ... args) throws ClassNotFoundException, SQLException
    {
        //1) Первый менее предпочтительный для многопользовательской работы способ - это спользовать DriverManager
        //Надо найти класс JDBC иначе DriverManager.getConnection не проходит, может быть это только для Sqlite ?!
        Class.forName("org.sqlite.JDBC");
        //Создаем соединение, получаем высказываьеля и делаем запрос к СУБД обрабатываем ответ
        try( Connection connection = DriverManager.getConnection(pathDB);
             //Вместо Statement лучше всегда использовать PreparedStatement (см. ниже)
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT Students.Name FROM Students");
            )
        {
            while(resultSet.next())
            {
                System.out.println(resultSet.getString("Name"));
            }
        }
        try( Connection connection = DriverManager.getConnection(pathDB);
            //Для перкомпиляции запроса с параметрами можно использовать PreparedStatement,
             //         так гораздо эффективнее и безопаснее
            PreparedStatement preparedStatement =
                 connection.prepareStatement("SELECT Students.Name FROM Students WHERE Students._id = ?");
        )
        {
            preparedStatement.setInt(1, 1);// пара - номер задаваемого ?, задаваемое значение
            try(ResultSet resultSet = preparedStatement.executeQuery())
            {
                while(resultSet.next())
                {
                    System.out.println(resultSet.getString("Name"));
                }
            }
        }
        //CallableStatement - нужно использовать для хранимых процедур
        
        //пример транзакции
        try( Connection connection = DriverManager.getConnection(pathDB);
             Statement statement = connection.createStatement(); )
        {
            connection.setAutoCommit(false); //Отменить автоматический коммит после каждого запроса
            try
            {
                statement.addBatch("INSERT INTO Students (Name) VALUES('JSmith')");
                statement.addBatch("INSERT INTO Students (Name) VALUES('OSmith')");
                int results[] = statement.executeBatch();
                int i = results[0]; //Для каждого addBatch возвращает количество вставленных записей
                connection.commit();
            }
            catch(SQLException e)
            {
                connection.rollback();
            }
        }
        
        
        
        
        //2) Второй способ (предпочтительнее для многопользовательской работы) -
        //      это использовать javax.sql.DataSource, объект можно получить через JNDI или иначе
        
    }
}
