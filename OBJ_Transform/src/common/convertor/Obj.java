package common.convertor;

import javafx.util.Pair;
//import android.util.Pair;
import java.util.*;

public class Obj
{
  public List<Vertex> vertices = null;
  public List<Material> materials = null;
  public String name;

  //������ ���   � ��������� ������ ��������
  public List<Pair<Integer, List<Integer>>> indices = null;
  public Obj(String _name)
  {
    vertices = new ArrayList<Vertex>();
    materials = new ArrayList<Material>();
    indices = new ArrayList<Pair<Integer, List<Integer>>>();
    name = _name;
  }
}
