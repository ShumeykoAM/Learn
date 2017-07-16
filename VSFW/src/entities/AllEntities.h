#ifndef AllEntities_h
#define AllEntities_h

#include "StepperEngine.h"
#include "ErrorLed.h"

extern ErrorLed &errorLed;     //Светодиод с ошибкой
extern StepperEngine inWidth;  //Двигатель движения по ширине
extern StepperEngine rotater;  //Двигатель вращающий руку
extern StepperEngine inLength; //Двигатель движения по длине

#endif // !AllEntities_h

