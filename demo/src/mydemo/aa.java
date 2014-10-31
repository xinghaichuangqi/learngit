
package mydemo;

public class aa{
	
	public static final int month = 15;
	
	public static void main (String[] args) 
	{
		long f1=1L,f2=2L;
		long f;
		for (int i=3; i<month; i++)
		{
			f=f2;
			f2=f1 + f2;
			f1=f;
			System.out.print("第" + i + "个月的对数:");
			System.out.println(" " + f2);
		}	
		
	}
}
