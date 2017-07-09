#ifndef StepperEngine_h
#define StepperEngine_h

/*
* Шаговый мотор
*/
class StepperEngine
{
  Register &reg;
  uint8_t directionBit;
  uint8_t stepBit;

  uint16_t stepAngle; //Угол на который поворачивается данный тип двигателя за один шаг

public:
  StepperEngine(Register &reg, uint8_t directionBit, uint8_t stepBit);

  /*Направление вращения*/
  /*TODO вынести*/
  enum class DIRECTION
  {
    RIGHT = 0,
    LEFT = 1
  };

  /**Повернуть на угол
  * @Param direction направление вращения
  * @Param angle
  * @Param speed
  * @Throw StepperEngineException
  */
  void rotate(DIRECTION direction, unsigned int angle, unsigned int speed);

  //Сделать шаг
  void step(DIRECTION direction);

};


#endif // !StepperEngine_h