package ru.bloodliviykot.russianroad;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;

import ru.bloodliviykot.russianroad.game.*;

public class MainActivity
  extends Activity
{
  public static Activity activity;
  private PowerManager.WakeLock wakeLock; //Для использования объекта нужно добавить разрешение в манифест
  private GLSurfaceView glView;
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    activity = this;
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
    wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MainActivity");
    glView = new GLSurfaceView(this);
    GameRender gamerender = new GameRender();
    glView.setRenderer(gamerender);
    setContentView(glView);

  }

  @Override
  public void onResume()
  {
    super.onResume();
    wakeLock.acquire();
    glView.onResume();
  }

  @Override
  public void onPause()
  {
    super.onPause();
    wakeLock.release();
    glView.onPause();
  }


}
























