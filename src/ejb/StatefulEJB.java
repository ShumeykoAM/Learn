package ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.*;

/**
 * @author sbt-shumeyko-am
 * @ created 14.03.2017
 * @ $Author$
 * @ $Revision$
 */

// @StateFul - означает что объект ejb существует в контексте пользователя, привязан к сессии,
// класс такого ejb может иметь поля, в которых можно хранить информацию между запросами
@Remote
@Stateful
public class StatefulEJB
{

    public String sayHelloWorld()
    {
        count++;
        return "Hello StatefulEJB " + count + " count";
    }
    
    //Если вызвать метод аннотированный @Remove, сессия закончится, но этот же объект может потом быть использован
    //   другим пользователем, в другой сессии, по этому !!! ОЧИСТКУ объекта нужно сделать самому
    @Remove
    public void endSession()
    {
        
    }
    

    @PostConstruct
    private void postConstruct()
    {
        //Делаем инициализацию
        count = 0;
    }
    @PreDestroy
    private void preDestroy()
    {
        //Делаем очистку
    }
    
    private int count;
}

//Пока что почему то не совсем срабатывает @Remove, потом какая то фигня в GlassFish
//Так же надо разобраться с транзакциями