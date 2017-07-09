#ifndef PIN_h
#define PIN_h

/**
* Соответствие ножек проца выполняемым функциям
*/
enum class PIN
{
  pinForINT0 = 2,  //Прерывание INT0
  pinForINT1 = 3,  //Прерывание INT1

  pinForBit0 = 4,              //Номер ножки 0 бита
  pinForBit1 = pinForBit0 + 1, //Номер ножки 1 бита
  pinForBit2 = pinForBit0 + 2, //Номер ножки 2 бита
  pinForBit3 = pinForBit0 + 3, //Номер ножки 3 бита
  pinForBit4 = pinForBit0 + 4, //Номер ножки 4 бита
  pinForBit5 = pinForBit0 + 5, //Номер ножки 5 бита
  pinForBit6 = pinForBit0 + 6, //Номер ножки 6 бита
  pinForBit7 = pinForBit0 + 7, //Номер ножки 7 бита

  pinForINH = 12, //импульс для INH счетчика дешифратора (импульсно активируем выход)
  pinForCLK = 13, //импульс для переключения выхода счетчика дешифратора
};

#endif // !PIN_h


