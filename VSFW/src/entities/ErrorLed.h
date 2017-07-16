#ifndef ErrorLed_h
#define ErrorLed_h

#include "..\Loopable.h"

//Светодиод сигнализатор об ошибке, предполагается что сбрасываться будет целиком контроллер, ошибки во время выполнения не исправимы
class ErrorLed
  :public Loopable
{

public:
  enum class ERROR
  {
    NONE = 0,
    E_STEPPER_ENGINE = 2  //Ошибка двигателя

  };

private:
  Register &reg; //Регистер на котором находится бит светодиода с ошибкой
  uint8_t errBit; //бит светодиода с ошибкой

  ERROR error; //Текущая ошибка
  bool isShines; //Светодиод светится
  unsigned char countFlash; //Количество произведенных вспышек в текущем цикле, 0 - значит цикл вспышек начат сначала

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