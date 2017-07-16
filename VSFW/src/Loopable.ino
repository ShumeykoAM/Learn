#include "src\Loopable.h"

Loopable *Loopable::firstLoopable = nullptr;
unsigned long Loopable::MAX_U_LONG = -1L;

void Loopable::setInterval(unsigned long interval)
{
  //Здесь interval не должен быть более MAX_U_LONG - время работы одного полного цикла, примерно не более 70 минут, проверку не стал делать
  if (this->interval == 0)
  {
    this->interval = interval;
    this->startTime = micros();
  }
}

Loopable::Loopable()
  :nextLoopable(nullptr), interval(0)
{
  //Добавим в список еще одну обрабатываемую сущность
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
  //Считаем что существует хотябы одна обрабатываемая сущность, иначе вообще нет ни какого смысла
  Loopable *l = firstLoopable;
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
    l = l->nextLoopable;
  } while (l != nullptr);
}