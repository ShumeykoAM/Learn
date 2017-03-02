package org.gradle.example.simple;
public class HelloWorld 
{
    public static void main(String args[])
    {
        System.out.println(args.length != 0 ? args[0] : "none" );
    }
}