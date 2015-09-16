package ru.bloodliviykot.footboll;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity
  extends Activity
{
  GLSurfaceView glView;
  WakeLock wakeLock;
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    
    PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
    wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MainActivity");
    
    glView = new GLSurfaceView(this);
    GameRender gr = new GameRender(glView, this, wakeLock);
    glView.setRenderer(gr);
    setContentView(glView);
    
    SensorManager manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
    if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() == 0)
    {    }
    else
    {
      Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
      manager.registerListener(gr, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }
  }

  @Override
  public void onResume()
  {
    //wakeLock.acquire();
    super.onResume();
    glView.onResume();
  }
  
  @Override
  public void onPause()
  {
    //wakeLock.release();
    super.onPause();
    glView.onPause();
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings)
    {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
