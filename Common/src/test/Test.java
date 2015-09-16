package test;

import common.geometry.*;

public class Test
{

  public static void main(String[] args)
  {
    
    CVector2D p1 = new CVector2D(-4, 0);
    CVector2D p2 = new CVector2D(-4,-5);
    CVector2D p3 = new CVector2D( 0, 0);
    CVector2D p4 = new CVector2D(-4,-5);
    
    CVector2D v1 = new CVector2D(p1, p2);
    CVector2D v2 = new CVector2D(p3, p4);
    v2.ortogonal();
    v1.projectionV(v2);


    CVector3D vi1 = new CVector3D( 8, 4, 0);
    CVector3D vi2 = vi1.getCopy();
    //vi1.ortogonal();
    float skalmul = vi1.scalarMulVectror(vi2);

    float angle = 180;
    CMatrix22 matrix = new CMatrix22(new CVector2D( (float)Math.cos(angle/57.2957795) , (float)Math.sin(angle/57.2957795)),
                                     new CVector2D( -(float)Math.sin(angle/57.2957795), (float)Math.cos(angle/57.2957795)) );
    CVector2D vec1 = new CVector2D(1.0f, 1.0f);
    matrix.mul(vec1);


    int dsfdfd = 0;
  }

}





















