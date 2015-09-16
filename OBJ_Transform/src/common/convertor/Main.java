package common.convertor;

import java.io.*;
import java.util.ArrayList;

import javafx.util.Pair;
//import android.util.Pair;
import java.util.*;
import java.util.regex.Pattern;

public class Main
{
  public static void main(String[] args)
  {
    Parse(args[0]);
    D3DModelFileReader mod = new D3DModelFileReader();

    Obj obj = mod.Read3dfFile("C:\\android\\WorkSpace2\\OBJ_Transform\\obj_filed\\23\\tetrahedron1.3df");
  }

  // ������� ������ �����
  static void Parse(String _FileName)
  {
    List<String> lines = new ArrayList<String>();
    try
    {
      File inFile = new File(_FileName);
      FileInputStream inStream = new FileInputStream(inFile);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
      String line = null;
      //������� ������ c ���������, ���������, ����������, ����������� � �������
      while( (line = reader.readLine()) != null )
      {
        lines.add(line);
      }
      inStream.close();

    }
    catch (Exception ex) {}

    try
    {
      List<MaterialEx> materials = null;
      List<String> lines_vertices = new ArrayList<String>();  //� ���� �������
      List<String> lines_tex_coods = new ArrayList<String>(); //  �����
      List<String> lines_normals = new ArrayList<String>();   //  �������, ���������� ����������
      List<String> lines_mtl_faces = new ArrayList<String>(); //  �������, ���������, � ����� ��� ����� �������
      String[] tokens;
      tokens = _FileName.split(Pattern.quote(File.separator)); //������� ��� ����� �� ����
      int i = _FileName.lastIndexOf(tokens[tokens.length-1]);
      String Path = _FileName.substring(0, i);
      for(String line : lines)
      {
        if(line.startsWith("mtllib ")) //���� ����������
        {

          tokens = line.split("[ ]+");
          materials = ReadMaterials(Path + tokens[1]);
        }
        else if(line.startsWith("v "))
          lines_vertices.add(line);
        else if(line.startsWith("vt "))
          lines_tex_coods.add(line);
        else if(line.startsWith("vn "))
          lines_normals.add(line);
      }
      List<Obj> objs = new ArrayList<Obj>();
      Obj obj = null;
      for(String line : lines)
      {
        if(line.startsWith("o "))
        {
          if(obj != null) //������ ��� �� ������
          {
            //��������� ����������
            ParseObj(obj, materials, lines_vertices, lines_tex_coods, lines_normals, lines_mtl_faces);
          }
          //����� �������
          tokens = line.split("[ ]+");
          objs.add(obj = new Obj(tokens[1]));
          lines_mtl_faces.clear();
        }
        else if(line.startsWith("usemtl ") || line.startsWith("f "))
          lines_mtl_faces.add(line);
      }
      ParseObj(obj, materials, lines_vertices, lines_tex_coods, lines_normals, lines_mtl_faces);
      // �������� ������� � ��������
      tokens = _FileName.split(Pattern.quote(File.separator)); //������� ��� ����� �� ����
      tokens = tokens[tokens.length-1].split("\\.");           //������� ������ ��� ��� ����������
      SaveBinare(objs, Path + tokens[0]);
    }
    catch(Exception e)
    {
      String s = e.toString();
      int fdfd = 0;
    }
  }
  static void ParseObj(Obj obj, List<MaterialEx> materials,
                       List<String> lines_vertices, List<String> lines_tex_coods,
                       List<String> lines_normals,  List<String> lines_mtl_faces )
  {
    String[] tokens;
    List<Integer> indices = null;
    MaterialEx material = null;
    for(String line : lines_mtl_faces)
    {
      //������� � ������ ���������� �������� ����� �������� � ���� ��� ������ ������� ��������
      if(line.startsWith("usemtl "))
      {
        tokens = line.split("[ ]+");
        for(MaterialEx mat : materials)
        {
          material = mat;
          if(material.name.compareTo(tokens[1]) == 0)
          {
            obj.materials.add(material);
            indices = new ArrayList<Integer>();
            obj.indices.add( new Pair<Integer, List<Integer>>(obj.materials.size()-1, indices) );
            break;
          }
        }
      }
      else if( line.startsWith("f "))
      {
        int iv, it, in;
        String[] parts;
        tokens = line.split("[ ]+");
        for(int itoks = 0; itoks<3; itoks++)
        {
          parts = tokens[itoks+1].split("/");
          iv = it = in = 0;
          if(parts[0].length() > 0)
            iv = Integer.parseInt(parts[0]) - 1;
          if(parts[1].length() > 0)
            it = Integer.parseInt(parts[1]) - 1;
          if(parts[2].length() > 0)
            in = Integer.parseInt(parts[2]) - 1;
          //������� �������, ��� ������ ��� ����� ����
          Vertex v = new Vertex();
          parts = lines_vertices.get(iv).split("[ ]+");
          v.x = Float.parseFloat(parts[1]);
          v.y = Float.parseFloat(parts[2]);
          v.z = Float.parseFloat(parts[3]);
          if(it != 0)
          {
            parts = lines_tex_coods.get(it).split("[ ]+");
            v.xt = Float.parseFloat(parts[1]);
            v.yt = Float.parseFloat(parts[2]);
          }
          parts = lines_normals.get(in).split("[ ]+");
          v.xn = Float.parseFloat(parts[1]);
          v.yn = Float.parseFloat(parts[2]);
          v.zn = Float.parseFloat(parts[3]);
          //���� �������� � �������� (���������� � ���������)
          v.color = material.color_ambient; // ��� ���� ������� material.color_diffuse
          //������� ������, �������� ��� �� ������� ����� �������
          //  ���� ���� �� ���������� ������ ���������, ���� ��� ������� �� � ���������� �� ������
          int index = 0;
          for(; index<obj.vertices.size(); index++)
            if(v.CompareTo(obj.vertices.get(index)))
              break;
          if(index == obj.vertices.size())
            obj.vertices.add(v);
          indices.add(index);

        }
      }
    }
  }

  //������� ��������
  static List<MaterialEx> ReadMaterials(String _F_Material)
  {
    List<MaterialEx> materials = null;
    //������ ������
    try
    {
      File inFile = new File(_F_Material);
      FileInputStream inStream = new FileInputStream(inFile);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
      List<String> lines = new ArrayList<String>();
      String line = null;
      //������� ������ c ���������, ���������, ����������, ����������� � �������
      while( (line = reader.readLine()) != null )
      {
        lines.add(line);
      }
      inStream.close();
      materials = ParseMaterials(lines);
    }
    catch (Exception ex) {}
    return materials;
  }
  static List<MaterialEx> ParseMaterials(List<String> _s)
  {
    List<MaterialEx> materials = new ArrayList<MaterialEx>();
    try
    {
      MaterialEx material = null;
      String[] tokens;
      for(String line : _s)
      {
        if(line.startsWith("newmtl "))
        {
          tokens = line.split("[ ]+");
          materials.add(material = new MaterialEx(tokens[1]));
        }
        else if(line.startsWith("Ka "))
        {
          tokens = line.split("[ ]+");
          material.color_ambient.r = Float.parseFloat(tokens[1]);
          material.color_ambient.g = Float.parseFloat(tokens[2]);
          material.color_ambient.b = Float.parseFloat(tokens[3]);
        }
        else if(line.startsWith("Kd "))
        {
          tokens = line.split("[ ]+");
          material.color_diffuse.r = Float.parseFloat(tokens[1]);
          material.color_diffuse.g = Float.parseFloat(tokens[2]);
          material.color_diffuse.b = Float.parseFloat(tokens[3]);
        }
        else if(line.startsWith("Ks "))
        {
          tokens = line.split("[ ]+");
          material.color_specular.r = Float.parseFloat(tokens[1]);
          material.color_specular.g = Float.parseFloat(tokens[2]);
          material.color_specular.b = Float.parseFloat(tokens[3]);
        }
        else if(line.startsWith("Ns "))
        {
          tokens = line.split("[ ]+");
          material.k_specular = Float.parseFloat(tokens[1]);
        }
        else if(line.startsWith("d ") || line.startsWith("Tr "))
        {
          tokens = line.split("[ ]+");
          float a = Float.parseFloat(tokens[1]);
          material.color_ambient.a = material.color_diffuse.a = material.color_specular.a = a;
        }
      }
    }
    catch(Exception e)
    {   }
    return materials;
  }

  static void SaveBinare(List<Obj> _objs, String _src_fileName)
  {
    try
    {
      for(Obj obj : _objs)
      {
        File myPath = new File(_src_fileName);
        myPath.mkdirs();
        File outFile = new File(_src_fileName + "\\" + obj.name + ".3df"); //���������� � ����� ������ ����� �����
        DataOutputStream outStream = new DataOutputStream(new FileOutputStream(outFile));
        outStream.writeChars("3DM");   // ��������� �����
        outStream.writeInt(obj.vertices.size()); //���������� ������
        for(Vertex v : obj.vertices)
        {                                        //�������
          outStream.writeFloat(v.x);
          outStream.writeFloat(v.y);
          outStream.writeFloat(v.z);
          outStream.writeFloat(v.color.r);
          outStream.writeFloat(v.color.g);
          outStream.writeFloat(v.color.b);
          outStream.writeFloat(v.color.a);
          outStream.writeFloat(v.xt);
          outStream.writeFloat(v.yt);
          outStream.writeFloat(v.xn);
          outStream.writeFloat(v.yn);
          outStream.writeFloat(v.zn);
        }
        outStream.writeInt(obj.materials.size()); //���������� ����������
        for(Material material : obj.materials)
        {                                         //���������
          outStream.writeFloat(material.color_specular.r);
          outStream.writeFloat(material.color_specular.g);
          outStream.writeFloat(material.color_specular.b);
          outStream.writeFloat(material.color_specular.a);
          outStream.writeFloat(material.k_specular);
        }
        for(Pair<Integer, List<Integer>> mIndex : obj.indices) //����� ���� ����� ����������-��������
        {
          outStream.writeInt(mIndex.getKey());           //����� ���������
          outStream.writeInt(mIndex.getValue().size());  //���������� �������� ��� ����� ���������
          for(Integer index : mIndex.getValue())
            outStream.writeInt(index);                   //���� �������
        }
        outStream.close();
      }
    }
    catch (Exception ex) {}
  }

}

































