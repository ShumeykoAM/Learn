#include "src\iron\PIN.h"
#include "src\iron\QUIT.h"
#include "src\iron\Register.h"
#include "src\Interrupts.h"
#include "src\Loopable.h"
#include "src\entities\StepperEngine.h"
#include "src\entities\ErrorLed.h"


Register sen0(QUIT::Q00); //Кнопки, датчики
Register reg0(QUIT::Q01); //Сигнальные огни и
Register reg1(QUIT::Q02); //  омыватели
Register reg2(QUIT::Q03); //Шаговые двигатели

//Далее идут наследники от Loopable
//!!! ErrorLed должен быть первым, дальше остальные
ErrorLed &errorLed = ErrorLed::getErrorLed(reg1, 1); //Светодиод с ошибкой

StepperEngine inWidth(reg2, 0, 1); //Двигатель движения по ширине
StepperEngine rotater(reg2, 2, 3); //Двигатель вращающий руку
StepperEngine inLength(reg2, 4, 5); //Двигатель движения по длине


//Изначальные установки
void setup()
{
  Interrupts::setup();
  inWidth.rotate(StepperEngine::DIRECTION::LEFT, 360, 3);
  rotater.rotate(StepperEngine::DIRECTION::RIGHT, 180, 3);
  inLength.rotate(StepperEngine::DIRECTION::LEFT, 270, 3);
}

//Рабочий цикл
void loop()
{
  Interrupts::eventHandler();
  Loopable::loopAll();
}




