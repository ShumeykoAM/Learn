#include "src\Interrupts.h"

void Interrupts::error()
{
  /*TODO*/

}
  
void Interrupts::event()
{
  needEvent = true;
}

void Interrupts::setup()
{
  pinMode((uint8_t)PIN::pinForINT0, INPUT_PULLUP); attachInterrupt(P_INT0, &event, FALLING);
  pinMode((uint8_t)PIN::pinForINT1, INPUT_PULLUP); attachInterrupt(P_INT1, &error, FALLING);
  needEvent = true;
}

void Interrupts::eventHandler()
{
  if (needEvent)
  {
    /*TODO*/
    sen0.read();
    needEvent = false;

    reg0.setRegister(sen0.getRegister());
    reg0.write();


  }
}

bool Interrupts::needEvent;