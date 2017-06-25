



enum QUIT
{
  Q00, //входящие сигналы
  Q01, //TR0
  Q02, //TR1
  Q03, Q04, Q05, Q06, Q07, Q08, Q09, Q10, Q11, Q12, Q13, Q14, Q15
};

//Соответствие ножек проца выполняемым функциям
enum PINS
{
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

/*Счетчик дешифратор
Настраивает биты процессора, переключает выход
*/
class Quit
{
public:
  static Quit &getInstance()
  {
    return instance;
  }
  //Задать текущий выход
  void setQuit(QUIT quit)
  {
    if (curQuit == quit)
      return;
    //Переключаем ноги проца в режим входа (что бы не спалить его)
    for (uint8_t pin = pinForBit0; pin <= pinForBit7; ++pin)
      pinMode(pin, INPUT);
    while (curQuit != quit)
      goNextQuit();

    switch (curQuit)
    {
      //Биты для считывания сигналов с кнопок и др. устройств.
    case Q00:
      //Уже установлены 3 строками выше
      break;
      //Биты для подачи сигналов на регистры.
    case Q01:
    case Q02:
    case Q03:
    case Q04:
    case Q05:
    case Q06:
    case Q07:
    case Q08:
    case Q09:
    case Q10:
    case Q11:
    case Q12:
    case Q13:
    case Q14:
    case Q15:
      for (uint8_t pin = pinForBit0; pin <= pinForBit7; ++pin)
        pinMode(pin, OUTPUT);
      break;
    }
  }
  //Подать импулься для записи, чтения (INH), текущий выход становится активным на время импулься
  void INH()
  {
    digitalWrite(pinForINH, LOW);
    digitalWrite(pinForINH, HIGH);
  }

private:
  Quit()
  {
    pinMode(pinForINH, OUTPUT);
    pinMode(pinForCLK, OUTPUT);
    digitalWrite(pinForINH, HIGH);
    curQuit = Q00;
  }
  static Quit instance;       //Синглтон
  QUIT curQuit;  //Текущий активный выход (при старте нулевой)

                              //Переключить счетчик дешифратор на след. выход
  unsigned char goNextQuit()
  {
    const uint8_t countQuits = 16; //Количество выходов счетчика дешифратора
                                   //Даем импульс счетчику дешифратору
    digitalWrite(pinForCLK, HIGH);
    digitalWrite(pinForCLK, LOW);
    curQuit = (QUIT)(curQuit + 1);
    if (curQuit == countQuits)
      curQuit = Q00;
  }
};
Quit Quit::instance;


Quit &quit = Quit::getInstance();
void setup()
{
  
}


void loop()
{
  quit.setQuit(Q02);
  digitalWrite(pinForBit5, HIGH);
  quit.INH();

  quit.setQuit(Q00);
  delay(1000);
  quit.setQuit(Q01);
  delay(1000);
  quit.setQuit(Q02);
  delay(1000);
  quit.setQuit(Q01);
  delay(1000);
  quit.setQuit(Q04);
  delay(1000);
  quit.setQuit(Q05);
  delay(1000);
  quit.setQuit(Q08);
  delay(1000);
  quit.setQuit(Q09);
  delay(1000);
  quit.setQuit(Q03);
  delay(1000);



}




