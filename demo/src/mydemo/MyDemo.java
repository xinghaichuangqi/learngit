package mydemo;

import java.io.*;
import java.util.*;

public class MyDemo {
	
	private String input;
	StringTokenizer st = null;
	public MyDemo()
	{
	  
	   BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	  
	   System.out.println("请输入数字和其数量，中间以空格分开:");
	  
	   try
	   {
	    input = in.readLine();
	    st = new StringTokenizer(input);
	   }
	   catch (Exception e)
	   {
	    System.out.println("请正确输入!");
	   }
	   double num = Double.parseDouble(st.nextToken());
	   int count = Integer.parseInt(st.nextToken());
	   double[][] r = Numbers(num,count);
	   System.out.println(getString(r)+"="+calResult(r)); 
	  
	}

	/**
	* 得到需要的数组
	*/
	public double[][] Numbers(double num,int count)
	{
	   double[][] numbers = new double[1][count];
	   double temp = 0;
	   for (int i=0;i<count ;i++ )
	   {
	    for (int j=0;j<count-i ;j++ )
	    {
	     double a = 10.0;
	     double b = (double)(j);
	        temp += num*Math.pow(a,b);
	    }
	    numbers[0][i] = temp;
	    temp = 0;
	   }
	   return numbers;
	}

	/**
	* 对该数组进行计算
	*/
	public double calResult(double[][] numbers)
	{
	   double result = 0;
	   for (int i=0;i<numbers[0].length ;i++ )
	   {
	    result += numbers[0][i];
	   }
	   return result; 
	}

	/**
	* 取得该数组的字符串形式 
	*/
	public String getString(double[][] numbers)
	{
	   String temp = "";
	   String result;
	   for (int i=0;i<numbers[0].length ;i++ )
	   {
	    temp += numbers[0][i] + "+";
	   }
	   result = temp.substring(0,temp.length()-1);
	   return result;
	}
	public static void main(String args[])
	{
	   new MyDemo();
	}
	}

