#include "src\iron\PIN.h"
#include "src\iron\QUIT.h"
#include "src\iron\Register.h"
#include "src\Interrupts.h"
#include "src\entities\StepperEngine.h"


Register sen0(QUIT::Q00); //Кнопки, датчики
Register reg0(QUIT::Q01); //Сигнальные огни и
Register reg1(QUIT::Q02); //  омыватели
Register reg2(QUIT::Q03); //Шаговые двигатели


StepperEngine inWidth(reg2, 0, 1); //Двигатель движения по ширине
StepperEngine rotater(reg2, 2, 3); //Двигатель вращающий руку
StepperEngine inLength(reg2, 4, 5); //Двигатель движения по длине


//Изначальные установки
void setup()
{
  Interrupts::setup();

}

//Рабочий цикл
void loop()
{
  Interrupts::eventHandler();
   
  rotater.step(StepperEngine::DIRECTION::RIGHT);
  inLength.step(StepperEngine::DIRECTION::LEFT);
  delay(10);
  inLength.step(StepperEngine::DIRECTION::LEFT);
  delay(10);

}




