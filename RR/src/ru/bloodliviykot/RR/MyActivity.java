package ru.bloodliviykot.RR;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;

import ru.bloodliviykot.RR.game.*;

public class MyActivity
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

    SensorManager manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
    if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() == 0)
    {    }
    else
    {
      Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
      manager.registerListener(gamerender, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }
    glView.setOnTouchListener(gamerender);
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
























