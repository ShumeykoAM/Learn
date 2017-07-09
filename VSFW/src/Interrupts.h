#ifndef Interrupts_h
#define Interrupts_h

/*
* Класс занимается обработкой прерываний
*/
class Interrupts
{
  static const uint8_t P_INT0 = 0; //Номера портов
  static const uint8_t P_INT1 = 1; //  прерываний на микросхеме

  //Флаг показывающий что нужно обработать одно или несколько событий
  static bool needEvent;

  //Обработчик критических ошибок сбоев и т.д.
  static void error();

  //Произошло событие (сработал датчик, кнопка нажата и т.д.)
  static void event();

public:
  //Первоначальные установки
  static void setup();

  //Обработать все события, если они есть
  static void eventHandler();
};


#endif // !Interrupts_h