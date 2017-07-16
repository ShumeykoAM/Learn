#include "src\Loopable.h"

Loopable *Loopable::firstLoopable = nullptr;
unsigned long Loopable::MAX_U_LONG = -1L;

void Loopable::setInterval(unsigned long interval)
{
  //«десь interval не должен быть более MAX_U_LONG - врем€ работы одного полного цикла, примерно не более 70 минут, проверку не стал делать
  if (this->interval == 0)
  {
    this->interval = interval;
    this->startTime = micros();
  }
}

Loopable::Loopable()
  :nextLoopable(nullptr), interval(0)
{
  //ƒобавим в список еще одну обрабатываемую сущность
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
  //—читаем что существует хот€бы одна обрабатываема€ сущность, иначе вообще нет ни какого смысла
  Loopable *l = firstLoopable;
  //ѕроверим: не находитс€ ли система в состо€нии ошибки
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