
//������������ ����� ����� ����������� ��������
enum PINS
{
  pinForINT0 = 2,  //���������� INT0
  pinForINT1 = 3,  //���������� INT1

  pinForBit0 = 4,              //����� ����� 0 ����
  pinForBit1 = pinForBit0 + 1, //����� ����� 1 ����
  pinForBit2 = pinForBit0 + 2, //����� ����� 2 ����
  pinForBit3 = pinForBit0 + 3, //����� ����� 3 ����
  pinForBit4 = pinForBit0 + 4, //����� ����� 4 ����
  pinForBit5 = pinForBit0 + 5, //����� ����� 5 ����
  pinForBit6 = pinForBit0 + 6, //����� ����� 6 ����
  pinForBit7 = pinForBit0 + 7, //����� ����� 7 ����

  pinForINH = 12, //������� ��� INH �������� ����������� (��������� ���������� �����)
  pinForCLK = 13, //������� ��� ������������ ������ �������� �����������
};


//������� (���� �� ��������� ������������ ����� ����� ��� ������� �����)
class Register
{
  //������������ ������� �������� �����������
public:
  enum QUIT
  {
    Q00, //�������� �������
    Q01, //TR0
    Q02, //TR1
    Q03, Q04, Q05, Q06, Q07, Q08, Q09, Q10, Q11, Q12, Q13, Q14, Q15
  };

  /*������� ����������, (����� 16 ������� �� ������ �� ������� �� ����� �������� ��� ������� ������ ��� ���� ������, ����� ��� ���������������� ��������� �������� ���������� ���������)
  ����������� ���� ����������, ����������� �����
  */
private:
  class Quit
  {
  public:
    static Quit instance;       //��������

                                //������ ������� �����
    void setQuit(QUIT quit)
    {
      if (curQuit == quit)
        return;
      //����������� ���� ����� � ����� ����� (��� �� �� ������� ���)
      for (uint8_t pin = pinForBit0; pin <= pinForBit7; ++pin)
        pinMode(pin, INPUT);
      //������������ � ������� ������
      while (curQuit != quit)
        goNextQuit();
      //���� ��� ����� ������, �� ����������� ����� ����� � ����� ������
      if (getIo(curQuit) == OUTPUT)
        for (uint8_t pin = pinForBit0; pin <= pinForBit7; ++pin)
          pinMode(pin, OUTPUT);
    }
    //������ �������� ��� ������ ������ � ��������, (INH), ������� ����� ���������� �������� �� ����� ��������
    void INH()
    {
      digitalWrite(pinForINH, LOW);
      digitalWrite(pinForINH, HIGH);
    }
    //�������� ������� ��� ������
    void INH_on()
    {
      digitalWrite(pinForINH, LOW);
    }
    //��������� ������� ��� ������
    void INH_off()
    {
      digitalWrite(pinForINH, HIGH);
    }

    //������� ��� �������� ������
    static int getIo(QUIT quit)
    {
      int io;
      switch (quit)
      {
        //���� ��� ���������� �������� � ������ � ��. ���������.
      case Q00:
        //��� ����������� 3 �������� ����
        io = INPUT;
        break;
        //���� ��� ������ �������� �� ��������.
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

    QUIT curQuit;  //������� �������� ����� (��� ������ �������)

                   //����������� ������� ���������� �� ����. �����
    unsigned char goNextQuit()
    {
      const uint8_t countQuits = 16; //���������� ������� �������� �����������
                                     //���� ������� �������� �����������
      digitalWrite(pinForCLK, HIGH);
      digitalWrite(pinForCLK, LOW);
      curQuit = (QUIT)(curQuit + 1);
      if (curQuit == countQuits)
        curQuit = Q00;
    }
  };


  //================================================================
  uint8_t bits; //���� ��������
  QUIT quit;    //������������ ��������� �����
public:
  Register(QUIT quit)
  {
    bits = 0;
    this->quit = quit;
  }

  //�������� ���� � ������� (� ��������������� ����������)
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
  //��������� ����
  void read()
  {
    if (Quit::getIo(quit) == INPUT)
    {
      Quit::instance.setQuit(quit);
      Quit::instance.INH_on();
      for (uint8_t pinPC = pinForBit0, pinR = 0; pinPC <= pinForBit7; ++pinPC, ++pinR)
        setBit(pinR, digitalRead(pinPC));
      Quit::instance.INH_off();
    }
  }

  void setBit(uint8_t bit, int state)
  {
    if (state == HIGH)
      bits |= 1 << bit;
    else
      bits &= ~(1 << bit);
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

Register sensors(Register::Q00); //������, �������
Register r1(Register::Q01);


//����������
class Interrupts
{
  static const uint8_t P_INT0 = 0;
  static const uint8_t P_INT1 = 1;

  //����� ���������� ���� ��� ��������� �������
  static bool needEvent;

  //���������� ����������� ������ ����� � �.�.
  static void error()
  {
    /*TODO*/

  }
  //��������� ������� (�������� ������, ������ ������ � �.�.)
  static void event()
  {
    needEvent = true;
  }
public:
  static void setup()
  {
    pinMode(pinForINT0, INPUT_PULLUP); attachInterrupt(P_INT0, &event, FALLING);
    pinMode(pinForINT1, INPUT_PULLUP); attachInterrupt(P_INT1, &error, FALLING);
    needEvent = true;
  }
  //���������� ��� �������, ���� ��� ����
  static void eventHandler()
  {
    if (needEvent)
    {
      sensors.read();
      needEvent = false;

      r1.setRegister(sensors.getRegister());
      r1.write();

      
    }
  }
};
bool Interrupts::needEvent;


//����������� ���������
void setup()
{
  Interrupts::setup();

}

//������� ����
void loop()
{
  Interrupts::eventHandler();
   
 

}




