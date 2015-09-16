package common.convertor;


//import android.util.Pair;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

//Реадер 3DM файлов
public class D3DModelFileReader
{
  public Obj Read3dfFile(String _path/*Путь к файлу*/)
  {
    Obj obj = null;
    try
    {
      String[] tokens;
      tokens = _path.split(Pattern.quote(File.separator)); //Отделим имя файла от пути
      tokens = tokens[tokens.length - 1].split("\\.");       //Оставим только имя без расширения
      File inFile = new File(_path);
      FileInputStream FinStream = new FileInputStream(inFile);
      DataInputStream inStream = new DataInputStream(FinStream);
      obj = Read(inStream, tokens[0]);
    }
    catch(Exception e){}
    return obj;
  }

  public Obj Read(DataInputStream inStream, String _fName)
  {
    Obj obj = null;
    try
    {
      char ch[] = new char[3];
      ch[0] = inStream.readChar(); ch[1] = inStream.readChar(); ch[2] = inStream.readChar(); // Читаем символ
      String signature = new String(ch);
      if(signature.compareTo("3DM") != 0)
        return null;
      obj = new Obj(_fName);
      int count_vertices = inStream.readInt();

      for(int i=0; i<count_vertices; i++)
      {
        Vertex v = new Vertex();
        v.x = inStream.readFloat();
        v.y = inStream.readFloat();
        v.z = inStream.readFloat();
        v.color = new Color();
        v.color.r = inStream.readFloat();
        v.color.g = inStream.readFloat();
        v.color.b = inStream.readFloat();
        v.color.a = inStream.readFloat();
        v.xt = inStream.readFloat();
        v.yt = inStream.readFloat();
        v.xn = inStream.readFloat();
        v.yn = inStream.readFloat();
        v.zn = inStream.readFloat();
        obj.vertices.add(v);
      }
      int count_materials = inStream.readInt();
      for(int i=0; i<count_materials; i++)
      {
        Material m = new Material(String.valueOf(i));
        m.color_specular = new Color();
        m.color_specular.r = inStream.readFloat();
        m.color_specular.g = inStream.readFloat();
        m.color_specular.b = inStream.readFloat();
        m.color_specular.a = inStream.readFloat();
        m.k_specular = inStream.readFloat();
        obj.materials.add(m);
      }
      for(int i=0; i<count_materials; i++)
      {
        int numMaterial = inStream.readInt();
        List<Integer> indices;
        Pair<Integer, List<Integer>> pair =
            new Pair<Integer, List<Integer>>(numMaterial, indices = new ArrayList<Integer>());
        obj.indices.add(pair);
        int count_indices = inStream.readInt();
        for(int j=0; j<count_indices; j++)
          indices.add(inStream.readInt());
      }
      inStream.close();
    }
    catch (Exception ex) {}
    return obj;
  }

}
