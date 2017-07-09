#include "src\iron\PIN.h"
#include "src\iron\QUIT.h"
#include "src\iron\Register.h"
#include "src\Interrupts.h"


Register sen0(QUIT::Q00); //Кнопки, датчики
Register reg0(QUIT::Q01); //Сигнальные огни и
Register reg1(QUIT::Q02); //  омыватели
Register reg2(QUIT::Q03); //Шаговые двигатели




//Изначальные установки
void setup()
{
  Interrupts::setup();

}

//Рабочий цикл
void loop()
{
  Interrupts::eventHandler();
   


}




