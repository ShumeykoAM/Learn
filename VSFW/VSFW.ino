



enum QUIT
{
  Q00, //�������� �������
  Q01, //TR0
  Q02, //TR1
  Q03, Q04, Q05, Q06, Q07, Q08, Q09, Q10, Q11, Q12, Q13, Q14, Q15
};

//������������ ����� ����� ����������� ��������
enum PINS
{
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

/*������� ����������
����������� ���� ����������, ����������� �����
*/
class Quit
{
public:
  static Quit &getInstance()
  {
    return instance;
  }
  //������ ������� �����
  void setQuit(QUIT quit)
  {
    if (curQuit == quit)
      return;
    //����������� ���� ����� � ����� ����� (��� �� �� ������� ���)
    for (uint8_t pin = pinForBit0; pin <= pinForBit7; ++pin)
      pinMode(pin, INPUT);
    while (curQuit != quit)
      goNextQuit();

    switch (curQuit)
    {
      //���� ��� ���������� �������� � ������ � ��. ���������.
    case Q00:
      //��� ����������� 3 �������� ����
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
      for (uint8_t pin = pinForBit0; pin <= pinForBit7; ++pin)
        pinMode(pin, OUTPUT);
      break;
    }
  }
  //������ �������� ��� ������, ������ (INH), ������� ����� ���������� �������� �� ����� ��������
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
  static Quit instance;       //��������
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




