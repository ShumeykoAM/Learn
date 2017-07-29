#include "src\Loopable.h"

Loopable *Loopable::firstLoopable = nullptr;
unsigned long Loopable::MAX_U_LONG = -1L;

void Loopable::setInterval(unsigned long interval)
{
  //����� interval �� ������ ���� ����� MAX_U_LONG - ����� ������ ������ ������� �����, �������� �� ����� 70 �����, �������� �� ���� ������
  if (this->interval == 0)
  {
    this->interval = interval;
    this->startTime = micros();
  }
}

Loopable::Loopable()
  :nextLoopable(nullptr), interval(0)
{
  //������� � ������ ��� ���� �������������� ��������
  if (firstLoopable == nullptr)
    firstLoopable = this;
  else
  {
    Loopable *l = firstLoopable;
    while (l->nextLoopable != nullptr)
      l = l->nextLoopable;
    l->nextLoopable = this;
  }
}

void Loopable::loopAll()
{
  //������� ��� ���������� ������ ���� �������������� ��������, ����� ������ ��� �� ������ ������
  Loopable *l = firstLoopable;
  //��������: �� ��������� �� ������� � ��������� ������
  bool isError = ((ErrorLed *)l)->getError() != ErrorLed::ERROR::NONE;
  do
  {
    unsigned long currentTime = micros();
    if (l->interval != 0)
    {
      if (((currentTime >= l->startTime) && (currentTime - l->startTime >= l->interval)) || ((currentTime < l->startTime) && (MAX_U_LONG - l->startTime + currentTime >= l->interval)))
      {
        l->interval = 0;
        l->loop();
      }
    }
    if (isError)
      break;
    l = l->nextLoopable;
  } while (l != nullptr);
}