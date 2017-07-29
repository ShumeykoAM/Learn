#ifndef ErrorLed_h
#define ErrorLed_h

#include "..\Loopable.h"

//��������� ������������ �� ������, �������������� ��� ������������ ����� ������� ����������, ������ �� ����� ���������� �� ���������
class ErrorLed
  :public Loopable
{

public:
  enum class ERROR
  {
    NONE = 0,
    E_STEPPER_ENGINE = 2  //������ ���������

  };

private:
  Register &reg; //�������� �� ������� ��������� ��� ���������� � �������
  uint8_t errBit; //��� ���������� � �������

  ERROR error; //������� ������
  bool isShines; //��������� ��������
  unsigned char countFlash; //���������� ������������� ������� � ������� �����, 0 - ������ ���� ������� ����� �������

  ErrorLed(Register &reg, uint8_t errBit);
  static ErrorLed *instance;

public:
  static ErrorLed &getErrorLed(Register &reg, uint8_t errBit);

  void setError(ERROR error);
  ERROR getError();

protected:
  virtual void loop() override;

};


#endif // !ErrorLed_h