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

//����������� ������� ���������� �� ����. �����
unsigned char Register::Quit::goNextQuit()
{
  const QUIT countQuits = QUIT::OVERFLOW; //���������� ������� �������� �����������
                                          //���� ������� �������� �����������
  digitalWrite((uint8_t)PIN::pinForCLK, HIGH);
  digitalWrite((uint8_t)PIN::pinForCLK, LOW);
  curQuit = (QUIT)((int)curQuit + 1);
  if (curQuit == countQuits)
    curQuit = QUIT::Q00;
}

//������ ������� �����
void Register::Quit::setQuit(QUIT quit)
{
  if (curQuit == quit)
    return;
  //����������� ���� ����� � ����� ����� (��� �� �� ������� ���)
  for (uint8_t pin = (uint8_t)PIN::pinForBit0; pin <= (uint8_t)PIN::pinForBit7; ++pin)
    pinMode(pin, INPUT);
  //������������ � ������� ������
  while (curQuit != quit)
    goNextQuit();
  //���� ��� ����� ������, �� ����������� ����� ����� � ����� ������
  if (getIo(curQuit) == OUTPUT)
    for (uint8_t pin = (uint8_t)PIN::pinForBit0; pin <= (uint8_t)PIN::pinForBit7; ++pin)
      pinMode(pin, OUTPUT);
}
//������ �������� ��� ������ ������ � ��������, (INH), ������� ����� ���������� �������� �� ����� ��������
void Register::Quit::INH()
{
  digitalWrite((uint8_t)PIN::pinForINH, LOW);
  digitalWrite((uint8_t)PIN::pinForINH, HIGH);
}
//�������� ������� ��� ������
void Register::Quit::INH_on()
{
  digitalWrite((uint8_t)PIN::pinForINH, LOW);
}
//��������� ������� ��� ������
void Register::Quit::INH_off()
{
  digitalWrite((uint8_t)PIN::pinForINH, HIGH);
}

//������� ��� �������� ������
int Register::Quit::getIo(QUIT quit)
{
  int io;
  switch (quit)
  {
    //���� ��� ���������� �������� � ������ � ��. ���������.
  case QUIT::Q00:
    //��� ����������� 3 �������� ����
    io = INPUT;
    break;
    //���� ��� ������ �������� �� ��������.
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