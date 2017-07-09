#ifndef StepperEngine_h
#define StepperEngine_h

/*
* ������� �����
*/
class StepperEngine
{
  Register &reg;
  uint8_t directionBit;
  uint8_t stepBit;

  uint16_t stepAngle; //���� �� ������� �������������� ������ ��� ��������� �� ���� ���

public:
  StepperEngine(Register &reg, uint8_t directionBit, uint8_t stepBit);

  /*����������� ��������*/
  /*TODO �������*/
  enum class DIRECTION
  {
    RIGHT = 0,
    LEFT = 1
  };

  /**��������� �� ����
  * @Param direction ����������� ��������
  * @Param angle
  * @Param speed
  * @Throw StepperEngineException
  */
  void rotate(DIRECTION direction, unsigned int angle, unsigned int speed);

  //������� ���
  void step(DIRECTION direction);

};


#endif // !StepperEngine_h