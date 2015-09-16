package ru.bloodliviykot.x0;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener
{
  //Интерфейс игровой логики, надо где то его реализовать
  public interface I_Game
  {
    enum STATUS
    {
      OK,
      YOU_WIN,
      I_WIN,
      FRIEND_WIN,
      UNCUR_MOTION //Некорректный ход
    }
    //Игровые исключения
    public static class GameExeption extends Throwable
    {
      public final STATUS stat;
      GameExeption(STATUS _stat)
      {
        stat = _stat;
      }
    }
    //Координаты на игровой матрице (0;0)-слева сверху, (2;2)-справа внизу
    public static class PoinGM
    {
      public int x, y;
      public PoinGM(int _x, int _y)
      {
        x = _x;
        y = _y;
      }
      public PoinGM()
      {
        x = y = -1;
      }
    }
    //Обнулить состояние игры
    void Clear();
    //Получить состояние игры
    STATUS getStatus();
    //Совершить очередной ход
    PoinGM setNextMotion(PoinGM _pg) throws GameExeption;    
  }
  
  ImageView imageView[][] = new ImageView[3][3];
  TextView tw;
  
  I_Game game = new X0Game();
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    imageView[0][0] = (ImageView)findViewById(R.id.imageView1);
    imageView[0][0].setOnClickListener(this);
    imageView[1][0] = (ImageView)findViewById(R.id.imageView2);
    imageView[1][0].setOnClickListener(this);
    imageView[2][0] = (ImageView)findViewById(R.id.imageView3);
    imageView[2][0].setOnClickListener(this);
    imageView[0][1] = (ImageView)findViewById(R.id.imageView4);
    imageView[0][1].setOnClickListener(this);
    imageView[1][1] = (ImageView)findViewById(R.id.imageView5);
    imageView[1][1].setOnClickListener(this);
    imageView[2][1] = (ImageView)findViewById(R.id.imageView6);
    imageView[2][1].setOnClickListener(this);
    imageView[0][2] = (ImageView)findViewById(R.id.imageView7);
    imageView[0][2].setOnClickListener(this);
    imageView[1][2] = (ImageView)findViewById(R.id.imageView8);
    imageView[1][2].setOnClickListener(this);
    imageView[2][2] = (ImageView)findViewById(R.id.imageView9);
    imageView[2][2].setOnClickListener(this);
    
    tw = (TextView)findViewById(R.id.textView1);
    tw.setText("Начинайте");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onClick(View v)
  {
    if(game.getStatus() != I_Game.STATUS.OK)
      return;

    ImageView imView = (ImageView)v;
    int x=-1, y=-1;
    labelfor:
    for(x=0; x<3; x++)
      for(y=0; y<3; y++)
        if(imView == imageView[x][y])
          break labelfor;
    
    try
    {
      I_Game.PoinGM pg = game.setNextMotion( new I_Game.PoinGM(x, y) );
      imView.setImageResource(R.drawable.cross);
      switch(game.getStatus())
      {
        case OK:
          imageView[pg.x][pg.y].setImageResource(R.drawable.zero);
          tw.setText("Продолжайте");          
          break;
        case I_WIN:
          imageView[pg.x][pg.y].setImageResource(R.drawable.zero);
          tw.setText("Я победил");
          break;
        case YOU_WIN:
          tw.setText("Вы победили");
          break;
        case FRIEND_WIN:
          tw.setText("Ничья");
          break;
      }
    
    }
    catch (I_Game.GameExeption e)
    {
      switch(e.stat) //По идее будет одно исключение означающее неверный ход
      {
        case UNCUR_MOTION:
          break;
      }
    }

  }
  
  @Override
  public boolean onTouchEvent(MotionEvent event)
  {
    if(game.getStatus() == I_Game.STATUS.OK)
      return true;
    game.Clear();
    for(int x=0; x<3; x++)
      for(int y=0; y<3; y++)
        imageView[x][y].setImageResource(R.drawable.square);
    tw.setText("Начинайте");
    return true;    
  }
  
  

  
}

















































