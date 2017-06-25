

//Соответствие ножек проца выполняемым функциям
enum PINS
{
  pinForINT0 = 2,  //Прерывание INT0
  pinForINT1 = 3,  //Прерывание INT0

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

//Перечисление выходов счетчика дешифратора
enum QUIT
{
  Q00, //входящие сигналы
  Q01, //TR0
  Q02, //TR1
  Q03, Q04, Q05, Q06, Q07, Q08, Q09, Q10, Q11, Q12, Q13, Q14, Q15
};

//Регистр (один из регистров подключенных через выход или сигналы входа)
class Register
{
  /*Счетчик дешифратор, (Имеет 16 выводов на каждый из которых мы можем повесить или регистр вывода или ввод данных, нужен для последовательной обработки большого количества устройств)
  Настраивает биты процессора, переключает выход
  */
  class Quit
  {
  public:
    static Quit instance;       //Синглтон

    //Задать текущий выход
    void setQuit(QUIT quit)
    {
      if (curQuit == quit)
        return;
      //Переключаем ноги проца в режим входа (что бы не спалить его)
      for (uint8_t pin = pinForBit0; pin <= pinForBit7; ++pin)
        pinMode(pin, INPUT);
      //Перемещаемся к нужному выходу
      while (curQuit != quit)
        goNextQuit();
      //Если это выход вывода, то переключаем ножки проца в режим выхода
      if(getIo(curQuit) == OUTPUT)
        for (uint8_t pin = pinForBit0; pin <= pinForBit7; ++pin)
          pinMode(pin, OUTPUT);
    }
    //Подать импулься для записи данных в регистры, (INH), текущий выход становится активным на время импулься
    void INH()
    {
      digitalWrite(pinForINH, LOW);
      digitalWrite(pinForINH, HIGH);
    }
    //Включить импульс для чтения
    void INH_on()
    {
      digitalWrite(pinForINH, LOW);
    }
    //Выключить импульс для чтения
    void INH_off()
    {
      digitalWrite(pinForINH, HIGH);
    }

    //Вернуть тип текущего выхода
    static int getIo(QUIT quit)
    {
      int io;
      switch (quit)
      {
        //Биты для считывания сигналов с кнопок и др. устройств.
      case Q00:
        //Уже установлены 3 строками выше
        io = INPUT;
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
        io = OUTPUT;
        break;
      }
      return io;
    }

  private:
    Quit()
    {
      pinMode(pinForINH, OUTPUT);
      pinMode(pinForCLK, OUTPUT);
      digitalWrite(pinForINH, HIGH);
      curQuit = Q00;
    }

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
  

  //================================================================
  uint8_t bits; //Биты регистра
  QUIT quit;    //Используемый регистром выход
public:
  Register(QUIT quit)
  {
    bits = 0;
    this->quit = quit;
  }

  //Записать биты в регистр (в соответствующую микросхему)
  void write()
  {
    if (Quit::getIo(quit) == OUTPUT)
    {
      Quit::instance.setQuit(quit);
      for (uint8_t pinPC = pinForBit0, pinR = 0; pinPC <= pinForBit7; ++pinPC, ++pinR)
        digitalWrite(pinPC, getBit(pinR));
      Quit::instance.INH();
    }
  }
  //Прочитать биты
  void read()
  {
    if (Quit::getIo(quit) == INPUT)
    {
      Quit::instance.setQuit(quit);
      Quit::instance.INH_on();
      for (uint8_t pinPC = pinForBit0, pinR = 0; pinPC <= pinForBit7; ++pinPC, ++pinR)
      {
        digitalWrite(1, HIGH);
        delay(500);
        int state = digitalRead(pinPC);
        digitalWrite(0, state);
        setBit(pinR, state);
        delay(500);
        digitalWrite(1, LOW);
        delay(500);
      }
      Quit::instance.INH_off();
    }
  }

  void setBit(uint8_t bit, int state)
  {
    bits |= 1 << bit;
  }
  int getBit(uint8_t bit)
  {
    return (bits & 1 << bit);
  }
  void setRegister(uint8_t bits)
  {
    this->bits = bits;
  }
  uint8_t getRegister()
  {
    return bits;
  }

};
Register::Quit Register::Quit::instance;

Register b1(Q00);
Register r1(Q01);

//Прерывания
class Interrupt
{
public:
  static void handler();
private:

};
void Interrupt::handler()
{

  
  //r1.setBit(0, HIGH);
}

//Изначальные установки
void setup()
{
  pinMode(pinForINT0, INPUT_PULLUP);
  pinMode(pinForINT1, INPUT_PULLUP);

  pinMode(pinForINT0, INPUT_PULLUP); attachInterrupt(0, Interrupt::handler, FALLING);
  pinMode(pinForINT1, INPUT_PULLUP); attachInterrupt(1, globalError, FALLING); //Один пин используем для прерывания в случае глобальной ошибки

  pinMode(1, OUTPUT);
  pinMode(0, OUTPUT);
}

//Обработчик критических ошибок сбоев и т.д.
void globalError()
{

}


//Рабочий цикл
void loop()
{
  
  b1.read();
  for (int i = 0; i <= 7; ++i)
  {
    digitalWrite(1, HIGH);
    delay(500);
    digitalWrite(0, b1.getBit(i));
    delay(500);
    digitalWrite(1, LOW);
    delay(500);

    r1.setBit(i, b1.getBit(i));
  }

  //r1.setRegister(b1.getRegister());
  r1.write();
  delay(9000);


}




