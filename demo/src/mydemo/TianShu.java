package mydemo;

import java.util.Scanner;
//import java.io.*;

public class TianShu {
	public static void main (String[] args)
	{
		
		int year,month,day;
		int days=0;
		int d=0;
		
		TianShu fymd =new TianShu();
		
		System.out.print("input the year");
		year=fymd.input();
		System.out.print("input the month");
		month=fymd.input();
		System.out.print("input the day");
		day=fymd.input();
		
		if(year<0 || month<0 || month>12 ||day <0 || day>31) {
			System.out.println("input error");
			System.exit(0);
		}
		for (int i=1; i<month ; i++ ) {
			
			switch (i) {
			
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				days=31;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				days=30;
				break;
			case 2:
				if ((year%400==0)||(year%4==0&&year%100!=0)) {
					days=29;
				}else {
					days=28;
				}
				break;
			
			}
			d +=days;
		}
	    System.out.println(year+":"+month+":"+day+"是今年的第"+(d+day)+"天");
		
	}
	public int input()
	{
		int value = 0;
		Scanner s=new Scanner(System.in);
		value=s.nextInt();
		return value;
	}
	
	
}
