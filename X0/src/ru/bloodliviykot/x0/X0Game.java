package ru.bloodliviykot.x0;

import ru.bloodliviykot.x0.MainActivity;

import java.util.Random;
import java.lang.Math;

public class X0Game implements MainActivity.I_Game
{
  public X0Game()
  {
    Clear();
  }
  enum GMS
  {
    EMPTY,
    CROSS,
    ZERO
  }  
  GMS matrix[][] = new GMS[3][3];
  //+//Обнулить игровую матрицу
  @Override
  public void Clear()
  {
    for(int x=0; x<3; x++)
      for(int y=0; y<3; y++)
        matrix[x][y] = GMS.EMPTY;
  }
  //+//Возвращает состояние игры
  @Override
  public STATUS getStatus()
  {
    return getMStatus(matrix);
  }
  //+//Возвращает состояние игровой матрицы
  private STATUS getMStatus(GMS _matrix[][])
  {
    STATUS result = STATUS.OK;
    GMS row[]  = new GMS[3+3+2];
    for(int i=0; i<3; i++)
    {
      row[i]   = (_matrix[i][0] == _matrix[i][1] && _matrix[i][1] == _matrix[i][2]) ? _matrix[i][0] : GMS.EMPTY;
      row[i+3] = (_matrix[0][i] == _matrix[1][i] && _matrix[1][i] == _matrix[2][i]) ? _matrix[0][i] : GMS.EMPTY;
    }
    row[6] = (_matrix[0][0] == _matrix[1][1] && _matrix[1][1] == _matrix[2][2]) ? _matrix[0][0] : GMS.EMPTY;
    row[7] = (_matrix[0][2] == _matrix[1][1] && _matrix[1][1] == _matrix[2][0]) ? _matrix[0][2] : GMS.EMPTY;
    for(int i=0; i<3+3+2; i++)
    {
      if(row[i] != GMS.EMPTY)
      {
        result = row[i] == GMS.CROSS ? STATUS.YOU_WIN : STATUS.I_WIN;
        break;
      }
    }
    if( result == STATUS.OK && getCountEmpty(_matrix) == 0 )
      result = STATUS.FRIEND_WIN;
    return result;
  }
  //+//Посчитать пустые ячейки матрицы
  int getCountEmpty(GMS _matrix[][])
  {
    int result = 0;
    for(int x=0; x<3; x++)
      for(int y=0; y<3; y++)
        if( _matrix[x][y] == GMS.EMPTY )
          result++;    
    return result;
  }
  //+//Получить копию текущей игровой матрицы
  GMS[][] getCopyMatryx()
  {
    GMS result[][] = new GMS[3][3];
    for(int x=0; x<3; x++)
      for(int y=0; y<3; y++)
        result[x][y] = matrix[x][y];
    return matrix;
  }
  
  //Узел дерева вариантов
  private class node
  {
    GMS motion;      //Чей ход
    PoinGM point_g;  //Координаты хода
    STATUS stat;     //Результат хода
    int count_m_win; //Количество ходов до победы
    int count_win;   //Количество победных исходов
    int count_loser; //Количество проигрышных исходов
    int count_draw;  //Количество ничьих
    node subnodes[]; //Подузлы
    
  }
  private class tree
  {
    node n[] = null;
    node[] work_matrix(GMS _m[][], GMS _motion )
    {
      node result[] = null;
      int countempty = getCountEmpty(_m);
      if( countempty > 0 )
      {
        result = new node[countempty];
        int i = 0;
        for(int x=0; x<3; x++)
          for(int y=0; y<3; y++)
            if(_m[x][y] == GMS.EMPTY) //Ищем очередную пустую ячейку и проставляем туда ход
            {
              _m[x][y] = _motion;
              result[i] = new node();
              result[i].motion = _motion;
              result[i].point_g = new MainActivity.I_Game.PoinGM(x, y);
              result[i].stat = getMStatus(_m);
              if( result[i].stat == STATUS.OK )
              {
                node subnodes[] = result[i].subnodes = work_matrix(_m, _motion == GMS.ZERO ? GMS.CROSS : GMS.ZERO );
                if(subnodes != null)
                {
                  result[i].count_m_win = 100; //Максимум
                  for(int j=0; j<subnodes.length; j++)
                  {
                    if(result[i].count_m_win > subnodes[j].count_m_win)
                      result[i].count_m_win = subnodes[j].count_m_win; 
                    result[i].count_win += subnodes[j].count_win;
                    if( subnodes[j].stat == STATUS.I_WIN )
                      result[i].count_win++;                    
                    result[i].count_loser += subnodes[j].count_loser;
                    if( subnodes[j].stat == STATUS.YOU_WIN )
                      result[i].count_loser++;                    
                    result[i].count_draw += subnodes[j].count_draw;
                    if( subnodes[j].stat == STATUS.FRIEND_WIN)
                      result[i].count_draw++;
                  }
                  result[i].count_m_win++;
                  if(result[i].count_win == 0)
                    result[i].count_m_win = -1; //Нет победных вариантов в этой ветке
                }                
              }              
              _m[x][y] = GMS.EMPTY;
              i++;
            }
      }
      return result;
    }    
    tree()
    {
      //дерево строится когда наш ход
      GMS m[][] = getCopyMatryx();
      n = work_matrix(m, GMS.ZERO);      
    }
    
  }
  
  //Пользователь сделал ход, возвращаем ход ИИ в out пвраметре
  @Override
  public PoinGM setNextMotion(PoinGM _pg) throws GameExeption
  {
    if(matrix[_pg.x][_pg.y] != GMS.EMPTY || getStatus() != STATUS.OK )
      throw new GameExeption(STATUS.UNCUR_MOTION);
    matrix[_pg.x][_pg.y] = GMS.CROSS;

    if(getStatus() != STATUS.OK)
      return new PoinGM();
      
    //Строим дерево всех возможных вариантов развития событий
    tree t = new tree();
    int j = 0;
    int countwin = -2;
    for(int i=0; i<t.n.length; i++)
    {
      node n = t.n[i];
      if( countwin < n.count_win)
      {
        boolean nextloser = false;
        if(n.subnodes != null && n.subnodes.length > 0)
        {
          labelf:
          for(int k=0; k<n.subnodes.length; k++)
          {
            node subn = n.subnodes[k];
            if(subn.stat == STATUS.YOU_WIN)
            {
              nextloser = true;
              break;
            }
            if(subn.subnodes != null)
            {
              for(int d=0; d<subn.subnodes.length; d++)
              {
                int countsubsubwin = 0;
                node nd = subn.subnodes[d];
                if(nd.subnodes != null && nd.subnodes.length >=2)
                  for(int m=0; m<nd.subnodes.length; m++)
                  {
                    if(nd.subnodes[m].stat == STATUS.YOU_WIN)
                      countsubsubwin++;
                  }
                if(countsubsubwin >= 2)
                {
                  nextloser = true;
                  break labelf;
                }                
              }                
            }
          }
        }
        if(!nextloser)
        {
          countwin = n.count_win;
          j = i;
        }
      }
      if(n.stat == STATUS.I_WIN)
      {
        j = i;
        break;
      }      
    }
    if(t.n[j].stat != STATUS.I_WIN)
      for(int w=0; w<t.n.length; w++)
      {
        boolean loser = false;
        int len = t.n[j].subnodes.length;
        for(int i=0; i<len; i++)
        {
          if(t.n[j].subnodes[i].stat == STATUS.YOU_WIN)
          {
            loser = true;
            break;
          }
        }
        if(loser)
        {
          if(j != w)
            j = w;
        }
        else
          break;
      }
    matrix[t.n[j].point_g.x][t.n[j].point_g.y] = GMS.ZERO;
    return t.n[j].point_g;
  }

  
}




































