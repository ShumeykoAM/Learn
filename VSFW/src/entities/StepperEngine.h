#ifndef StepperEngine_h
#define StepperEngine_h

#include "..\Loopable.h"
/*
* Шаговый мотор
*/
class StepperEngine
  :public Loopable
{
public:
  /*Направление вращения*/
  /*TODO вынести*/
  enum class DIRECTION
  {
    RIGHT = 0,
    LEFT = 1
  };

private:
  Register &reg; //Регистер которым управляется ШМ
  uint8_t directionBit; //Бит направления на регистре
  uint8_t stepBit; //Бит шага на регистре

  uint16_t stepAngle; //Угол на который поворачивается данный тип двигателя за один шаг

  DIRECTION currentDirection; //Текущее направление движения
  unsigned int restAngle;//Текущий оставшийся угол поворота

  //Сделать шаг
  void step(DIRECTION direction);

public:
  StepperEngine(Register &reg, uint8_t directionBit, uint8_t stepBit);

  /**Повернуть на угол
  * @Param direction направление вращения
  * @Param angle угол на который надо повернуть
  * @Param speed 
  * @Throw StepperEngineException
  */
  void rotate(DIRECTION direction, unsigned int angle, unsigned int speed);

protected:
  virtual void loop();
};


#endif // !StepperEngine_h