package com.cdi;

import javax.enterprise.inject.Alternative;


//Помечаем реализацию внедряемого компонента как альтернативную
//  это удобно, например для тестирования
//Альтернативы надо не забывать добавлять в bean.xml
@Alternative @EightDigits // необязательный квалификатор @Default может быть заменен на идентифицирующий квалификатор
public class MockGenerator
    implements NumberGenerator
{
    @Override public int generateNumber()
    {
        return 0;
    }
}
