#include "src\entities\ErrorLed.h"


ErrorLed::ErrorLed(Register &reg, uint8_t errBit)
  :reg(reg), errBit(errBit), error(ERROR::NONE), isShines(false), countFlash(0)
{   }

ErrorLed *ErrorLed::instance = nullptr;

ErrorLed &ErrorLed::getErrorLed(Register &reg, uint8_t errBit)
{
  if (instance == nullptr)
    instance = new ErrorLed(reg, errBit);
  return *instance;
}

void ErrorLed::setError(ERROR error)
{
  this->error = error;
  setInterval(1);
}
ErrorLed::ERROR ErrorLed::getError()
{
  return error;
}

void ErrorLed::loop()
{
  int needCountFlash = (unsigned char)error;
  if (!isShines)
  {
    isShines = true;
    reg.setBit(errBit, HIGH);
    reg.write();
    setInterval(500000);
  }
  else
  {
    isShines = false;
    reg.setBit(errBit, LOW);
    reg.write();
    countFlash++;
    if (countFlash < needCountFlash)
      setInterval(500000);
    else
    {
      countFlash = 0;
      setInterval(2000000);
    }
  }
}