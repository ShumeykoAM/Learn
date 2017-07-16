#ifndef StepperEngine_h
#define StepperEngine_h

#include "..\Loopable.h"
/*
* ������� �����
*/
class StepperEngine
  :public Loopable
{
public:
  /*����������� ��������*/
  /*TODO �������*/
  enum class DIRECTION
  {
    RIGHT = 0,
    LEFT = 1
  };

private:
  Register &reg; //�������� ������� ����������� ��
  uint8_t directionBit; //��� ����������� �� ��������
  uint8_t stepBit; //��� ���� �� ��������

  uint16_t stepAngle; //���� �� ������� �������������� ������ ��� ��������� �� ���� ���

  DIRECTION currentDirection; //������� ����������� ��������
  unsigned int restAngle;//������� ���������� ���� ��������

  //������� ���
  void step(DIRECTION direction);

public:
  StepperEngine(Register &reg, uint8_t directionBit, uint8_t stepBit);

  /**��������� �� ����
  * @Param direction ����������� ��������
  * @Param angle ���� �� ������� ���� ���������
  * @Param speed 
  * @Throw StepperEngineException
  */
  void rotate(DIRECTION direction, unsigned int angle, unsigned int speed);

protected:
  virtual void loop();
};


#endif // !StepperEngine_h