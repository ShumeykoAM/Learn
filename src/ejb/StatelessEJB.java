package ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * @author sbt-shumeyko-am
 * @ created 14.03.2017
 * @ $Author$
 * @ $Revision$
 */

// @Stateless - означает что объект ejb существует вне контекста, не привязан к какому либо пользователю,
//   класс такого ejb не должен иметь поля, так как не известно какой пользователь будет
//   использовать этот объект в следующий раз

@Remote//Уровень доступа, есть еще @Local
@Stateless
public class StatelessEJB
{
    public String sayHelloWorld()
    {
        return "Hello Stateless ejb";
    }
    
    
    //Что делать с этими методами не совсем понял, так как полей в классе нету, что инициализировать !?
    @PostConstruct
    private void postConstruct()
    {
        
    }
    @PreDestroy
    private void preDestroy()
    {
        
    }
    
}
