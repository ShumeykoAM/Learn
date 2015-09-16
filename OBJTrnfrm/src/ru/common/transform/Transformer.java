package ru.common.transform;


import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Transformer
{
  public String FileName;
  public Transformer(String _FileName)
  {
    FileName = _FileName;
    List<String> lines = new ArrayList<String>();
    try
    {
      File inFile = new File(_FileName);
      FileInputStream inStream = new FileInputStream(inFile);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
      String line;
      //Считаем строки c вершинами, нормалями, текстурами, материалами и фейсами
      while( (line = reader.readLine()) != null )
      {
        lines.add(line);
      }
      inStream.close();

    }
    catch (Exception ex){}
    parse(lines);
  }

  private void parse(List<String> lines)
  {
    try
    {
      List<MaterialEx> materials = null;
      List<String> lines_vertices = new ArrayList<String>();  //В этих списках
      List<String> lines_tex_coods = new ArrayList<String>(); //  лежат
      List<String> lines_normals = new ArrayList<String>();   //  вершины, текстурные координаты
      List<String> lines_mtl_faces = new ArrayList<String>(); //  нормали, материалы, и фейсы ДЛЯ текущ объекта
      String[] tokens;
      tokens = FileName.split(Pattern.quote(File.separator)); //Отделим имя файла от пути
      int i = FileName.lastIndexOf(tokens[tokens.length-1]);
      String Path = FileName.substring(0, i);
      for(String line : lines)
      {
        if(line.startsWith("mtllib ")) //Файл материалов
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
          if(obj != null) //Объект уже не первый
          {
            //Распарсим предыдущий
            ParseObj(obj, materials, lines_vertices, lines_tex_coods, lines_normals, lines_mtl_faces);
          }
          //Новый объектк
          tokens = line.split("[ ]+");
          objs.add(obj = new Obj(tokens[1]));
          lines_mtl_faces.clear();
        }
        else if(line.startsWith("usemtl ") || line.startsWith("f "))
          lines_mtl_faces.add(line);
      }
      ParseObj(obj, materials, lines_vertices, lines_tex_coods, lines_normals, lines_mtl_faces);
      // Сохраним объекты в бинарник
      tokens = FileName.split(Pattern.quote(File.separator)); //Отделим имя файла от пути
      tokens = tokens[tokens.length-1].split("\\.");           //Оставим только имя без расширения

      // Тестируем
      /*
      int real = 0;
      int max = 0;
      int old = -1;
      for(Pair<Integer, List<Integer>> li : objs.get(1).indices)
      {
        max = 0;
        for(int ind : li.getValue())
        {
          if(max < ind)
            max = ind;

          if(max <= old)
          {
            //error
            real = max;
          }
        }
        old = max;
      }
      //
      */
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
      //Добавим в список материалов индексов новый материал с пока что пустым набором индексов
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
            obj.indices.add( new Pair<Integer, List<Integer>>(material.IDMaterial, indices) );
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
          //Создаем вершину, все данные для этого есть
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
          v.IDMaterial = material.IDMaterial; //
          //Вершина готова, проверим нет ли такойже самой вершины
          //  если есть то используем индекс найденной, если нет добавим ее и используем ее индекс
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

  static void SaveBinare(List<Obj> _objs, String _src_fileName)
  {
    try
    {
      for(Obj obj : _objs)
      {
        File myPath = new File(_src_fileName);
        myPath.mkdirs();
        File outFile = new File(_src_fileName + "\\" + obj.name + ".3df"); //Расширение у наших файлов будет такое
        DataOutputStream outStream = new DataOutputStream(new FileOutputStream(outFile));
        outStream.writeChars("3DM");   // Сигнатура файла
        outStream.writeInt(obj.vertices.size());    //Общее количество вершин
        int common_count_indices = 0;
        for(Pair<Integer, List<Integer>> mIndex : obj.indices)
          common_count_indices += mIndex.getValue().size();
        outStream.writeInt(common_count_indices);   //Общее количество индексов
        outStream.writeInt(obj.materials.size()); //кол-во блоков материал-вершины-индексы
        for(MaterialEx material : obj.materials)
        {
          //Запишем очередной блок материал-вершины-индексы
          //Записываем материал
          for(int irgba=0; irgba<4; irgba++)         // Цвет для окружающего освещения
            outStream.writeFloat(material.color_ambient.rgba[irgba]);
          for(int irgba=0; irgba<4; irgba++)         // Цвет для диффузного освещения
            outStream.writeFloat(material.color_diffuse.rgba[irgba]);
          for(int irgba=0; irgba<4; irgba++)         // Цвет для зеркального освещения
            outStream.writeFloat(material.color_specular.rgba[irgba]);
          outStream.writeFloat(material.k_specular); // Коэффициент зеркального отражения

          //Записываем вершины этого материала
          int count_vertices = 0;  //Посчитаем вершины
          for(Vertex v: obj.vertices)
            if(v.IDMaterial == material.IDMaterial)
              count_vertices++;
          outStream.writeInt(count_vertices); //Записываем количество вершин
          for(Vertex v: obj.vertices)
            if(v.IDMaterial == material.IDMaterial)
            {
              //Координаты вершин
              outStream.writeFloat(v.x);
              outStream.writeFloat(v.y);
              outStream.writeFloat(v.z);
              //Координаты текстуры
              outStream.writeFloat(v.xt);
              outStream.writeFloat(v.yt);
              //Нормали вершины
              outStream.writeFloat(v.xn);
              outStream.writeFloat(v.yn);
              outStream.writeFloat(v.zn);
            }

          //Записываем индексы этого материала
          L_circle_indices:
          for(Pair<Integer, List<Integer>> mIndex : obj.indices)
            if(mIndex.getKey() == material.IDMaterial)
            {
              outStream.writeInt(mIndex.getValue().size()); //Записываем кол-во индексов
              for(int index : mIndex.getValue())   //Записываем сами индексы
                outStream.writeInt(index);
              break L_circle_indices;
            }
        }
        outStream.close();
      }
    }
    catch (Exception ex) {}
  }

  //Парсить материал =================================================================================================
  static List<MaterialEx> ReadMaterials(String _F_Material)
  {
    List<MaterialEx> materials = null;
    //Чтение текста
    try
    {
      File inFile = new File(_F_Material);
      FileInputStream inStream = new FileInputStream(inFile);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
      List<String> lines = new ArrayList<String>();
      String line = null;
      //Считаем строки c вершинами, нормалями, текстурами, материалами и фейсами
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
      int IDMaterial = 0;
      for(String line : _s)
      {
        if(line.startsWith("newmtl "))
        {
          IDMaterial++;
          tokens = line.split("[ ]+");
          materials.add(material = new MaterialEx(tokens[1], IDMaterial));
        }
        else if(line.startsWith("Ka "))
        {
          tokens = line.split("[ ]+");
          material.color_ambient = new Color( Float.parseFloat(tokens[1]),
                                              Float.parseFloat(tokens[2]),
                                              Float.parseFloat(tokens[3]),
                                              1.0f );
        }
        else if(line.startsWith("Kd "))
        {
          tokens = line.split("[ ]+");
          material.color_diffuse= new Color( Float.parseFloat(tokens[1]),
                                             Float.parseFloat(tokens[2]),
                                             Float.parseFloat(tokens[3]),
                                             1.0f );
        }
        else if(line.startsWith("Ks "))
        {
          tokens = line.split("[ ]+");
          material.color_specular= new Color( Float.parseFloat(tokens[1]),
                                              Float.parseFloat(tokens[2]),
                                              Float.parseFloat(tokens[3]),
                                              1.0f );
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
          material.color_ambient.rgba[3] = material.color_diffuse.rgba[3] = material.color_specular.rgba[3] = a;
        }
      }
    }
    catch(Exception ignored)
    {   }
    return materials;
  }



}
