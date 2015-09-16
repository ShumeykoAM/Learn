package ru.bloodliviykot.RR.game.objects;

import android.app.Activity;
import ru.bloodliviykot.RR.MyActivity;
import ru.bloodliviykot.RR.game.D3D_objects;

public class Trace
  extends D3D_objects
{
  private static final int TEXTURE_ID = 0;
  public Trace()
  {
    super("objects/trace/Trace.3df", MyActivity.activity, "objects/trace/Trace.png");

  }

}
