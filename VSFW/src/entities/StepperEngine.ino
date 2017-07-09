#include "src\entities\StepperEngine.h"
#include "src\iron\Register.h"

StepperEngine::StepperEngine(Register &reg, uint8_t directionBit, uint8_t stepBit)
  :reg(reg), directionBit(directionBit), stepBit(stepBit)
{   }

/**Повернуть на угол
* @Param direction направление вращения
* @Param angle
* @Param speed
* @Throw StepperEngineException
*/
void StepperEngine::rotate(DIRECTION direction, unsigned int angle, unsigned int speed)
{
  //if ((double)angle / (double)stepAngle != angle / stepAngle)
  //  throw StepperEngineException();


}

//Сделать шаг
void StepperEngine::step(DIRECTION direction)
{
  reg.setBit(directionBit, (int)direction);
  reg.setBit(stepBit, HIGH);
  reg.write();
  reg.setBit(stepBit, LOW);
  reg.write();
}