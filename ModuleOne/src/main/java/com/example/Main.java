package com.example;

public class Main
{
	public static void main(String[] args)
	{
		MyObject myObject = new MyObject();
		myObject.i = 23;
		System.out.println("I'm module one!");
		System.out.println("myObject.i = " + new Integer(myObject.i).toString() );
	}
}