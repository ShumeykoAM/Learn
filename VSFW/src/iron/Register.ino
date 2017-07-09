#include "src\iron\Register.h"
#include "src\iron\PIN.h"
#include "src\iron\QUIT.h"

Register::Register(QUIT quit)
{
  bits = 0;
  this->quit = quit;
}

void Register::write()
{
  if (Quit::getIo(quit) == OUTPUT)
  {
    Quit::instance.setQuit(quit);
    for (uint8_t pinPC = (uint8_t)PIN::pinForBit0, pinR = 0; pinPC <= (uint8_t)PIN::pinForBit7; ++pinPC, ++pinR)
      digitalWrite(pinPC, getBit(pinR));
    Quit::instance.INH();
  }
}

void Register::read()
{
  if (Quit::getIo(quit) == INPUT)
  {
    Quit::instance.setQuit(quit);
    Quit::instance.INH_on();
    for (uint8_t pinPC = (uint8_t)PIN::pinForBit0, pinR = 0; pinPC <= (uint8_t)PIN::pinForBit7; ++pinPC, ++pinR)
      setBit(pinR, digitalRead(pinPC));
    Quit::instance.INH_off();
  }
}

void Register::setBit(uint8_t bit, int state)
{
  if (state == HIGH)
    bits |= 1 << bit;
  else
    bits &= ~(1 << bit);
}

int Register::getBit(uint8_t bit)
{
  return (bits & 1 << bit);
}

void Register::setRegister(uint8_t bits)
{
  this->bits = bits;
}

uint8_t Register::getRegister()
{
  return bits;
}


//========================================
Register::Quit::Quit()
{
  pinMode((uint8_t)PIN::pinForINH, OUTPUT);
  pinMode((uint8_t)PIN::pinForCLK, OUTPUT);
  digitalWrite((uint8_t)PIN::pinForINH, HIGH);
  curQuit = QUIT::Q00;
}

//Переключить счетчик дешифратор на след. выход
unsigned char Register::Quit::goNextQuit()
{
  const QUIT countQuits = QUIT::OVERFLOW; //Количество выходов счетчика дешифратора
                                          //Даем импульс счетчику дешифратору
  digitalWrite((uint8_t)PIN::pinForCLK, HIGH);
  digitalWrite((uint8_t)PIN::pinForCLK, LOW);
  curQuit = (QUIT)((int)curQuit + 1);
  if (curQuit == countQuits)
    curQuit = QUIT::Q00;
}

//Задать текущий выход
void Register::Quit::setQuit(QUIT quit)
{
  if (curQuit == quit)
    return;
  //Переключаем ноги проца в режим входа (что бы не спалить его)
  for (uint8_t pin = (uint8_t)PIN::pinForBit0; pin <= (uint8_t)PIN::pinForBit7; ++pin)
    pinMode(pin, INPUT);
  //Перемещаемся к нужному выходу
  while (curQuit != quit)
    goNextQuit();
  //Если это выход вывода, то переключаем ножки проца в режим выхода
  if (getIo(curQuit) == OUTPUT)
    for (uint8_t pin = (uint8_t)PIN::pinForBit0; pin <= (uint8_t)PIN::pinForBit7; ++pin)
      pinMode(pin, OUTPUT);
}
//Подать импулься для записи данных в регистры, (INH), текущий выход становится активным на время импулься
void Register::Quit::INH()
{
  digitalWrite((uint8_t)PIN::pinForINH, LOW);
  digitalWrite((uint8_t)PIN::pinForINH, HIGH);
}
//Включить импульс для чтения
void Register::Quit::INH_on()
{
  digitalWrite((uint8_t)PIN::pinForINH, LOW);
}
//Выключить импульс для чтения
void Register::Quit::INH_off()
{
  digitalWrite((uint8_t)PIN::pinForINH, HIGH);
}

//Вернуть тип текущего выхода
int Register::Quit::getIo(QUIT quit)
{
  int io;
  switch (quit)
  {
    //Биты для считывания сигналов с кнопок и др. устройств.
  case QUIT::Q00:
    //Уже установлены 3 строками выше
    io = INPUT;
    break;
    //Биты для подачи сигналов на регистры.
  case QUIT::Q01:
  case QUIT::Q02:
  case QUIT::Q03:
  case QUIT::Q04:
  case QUIT::Q05:
  case QUIT::Q06:
  case QUIT::Q07:
  case QUIT::Q08:
  case QUIT::Q09:
  case QUIT::Q10:
  case QUIT::Q11:
  case QUIT::Q12:
  case QUIT::Q13:
  case QUIT::Q14:
  case QUIT::Q15:
    io = OUTPUT;
    break;
  }
  return io;
}

Register::Quit Register::Quit::instance;