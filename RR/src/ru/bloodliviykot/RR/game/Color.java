package ru.bloodliviykot.RR.game;

public class Color
{
  public float[] rgba;
  public Color()
  {
    rgba = new float[4];
    rgba[0] = rgba[1] = rgba[2] = rgba[3] = 0.0f;
  }
  public Color(float _r, float _g, float _b)
  {
    this(_r, _g, _b, 1.0f);
  }
  public Color(float _r, float _g, float _b, float _a)
  {
    rgba = new float[4];
    rgba[0] = _r;
    rgba[1] = _g;
    rgba[2] = _b;
    rgba[3] = _a;
  }
  public Color(float[] _rgba)
  {
    rgba = new float[4];
    rgba[0] = _rgba[0];
    rgba[1] = _rgba[1];
    rgba[2] = _rgba[2];
    rgba[3] = _rgba[3];
  }

  public boolean CompareTo(Color c)
  {
    return  rgba[0] == c.rgba[0] &&
            rgba[1] == c.rgba[1] &&
            rgba[2] == c.rgba[2] &&
            rgba[3] == c.rgba[3];
  }

  public Color getCopyColor(Color color)
  {
    return new Color(color.rgba);
  }

  public void copyFrom(Color color)
  {
    for(int i=0; i<4; i++)
      rgba[i] = color.rgba[i];
  }
}
