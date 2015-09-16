package ru.common.transform;

import javafx.util.Pair;
import java.util.*;

public class Obj
{
  public List<Vertex> vertices = null;
  public List<MaterialEx> materials = null;
  public String name;

  //Список пар   № Материала список индексов
  public List<Pair<Integer, List<Integer>>> indices = null;
  public Obj(String _name)
  {
    vertices = new ArrayList<Vertex>();
    materials = new ArrayList<MaterialEx>();
    indices = new ArrayList<Pair<Integer, List<Integer>>>();
    name = _name;
  }
}
