#include "src\entities\StepperEngine.h"
#include "src\iron\Register.h"
#include "src\Loopable.h"
#include "src\entities\AllEntities.h"

StepperEngine::StepperEngine(Register &reg, uint8_t directionBit, uint8_t stepBit)
  :reg(reg), directionBit(directionBit), stepBit(stepBit), stepAngle(1), restAngle(0), currentDirection(DIRECTION::RIGHT)
{   }

/**Повернуть на угол
* @Param direction направление вращения
* @Param angle
* @Param speed
* @Throw StepperEngineException
*/
void StepperEngine::rotate(DIRECTION direction, unsigned int angle, unsigned int speed)
{
  //if ((double)angle / (double)stepAngle != unsigned int(angle / stepAngle))
  //  return;
  //errorLed.setError(ErrorLed::ERROR::E_STEPPER_ENGINE);

  currentDirection = direction;
  restAngle = angle;
  setInterval(1);
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

void StepperEngine::loop()
{
  step(currentDirection);
  restAngle -= stepAngle;
  if(restAngle != 0)
    setInterval(10000);
}