#ifndef QUIT_h
#define QUIT_h

/*
* Наименования выходов счетчика дешифратора
*/
enum class QUIT
{
  Q00, //входящие сигналы
  Q01, //TR0
  Q02, //TR1
  Q03, Q04, Q05, Q06, Q07, Q08, Q09, Q10, Q11, Q12, Q13, Q14, Q15,
  OVERFLOW
};

#endif // !QUIT_h