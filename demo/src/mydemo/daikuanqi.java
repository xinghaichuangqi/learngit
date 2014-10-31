package mydemo;

import javax.swing.*;
import java.text.*;

public class daikuanqi {
	public static void main (String[] args){
		final int MONTHS_IN_YEAR=12;
		double loanAmount,annualInterestRate;
		double monthlyPayment,totalPayment;
		double monthlyInterestRate;
		int loanPeriod  ;
		int numberOfPayments;
		String inputStr;
		DecimalFormat df =new DecimalFormat("0.00");
		
		inputStr=JOptionPane.showInputDialog(null,"������(��ȷ������)");
		loanAmount=Double.parseDouble(inputStr);
		inputStr=JOptionPane.showInputDialog(null,"������");
		annualInterestRate=Double.parseDouble(inputStr);
		inputStr=JOptionPane.showInputDialog(null,"��������");
		loanPeriod= Integer.parseInt(inputStr);
		
		monthlyInterestRate=annualInterestRate/MONTHS_IN_YEAR/100;
		numberOfPayments= loanPeriod * MONTHS_IN_YEAR;
		
		monthlyPayment=(loanAmount*monthlyInterestRate)/(1-Math.pow(1/(1+monthlyInterestRate),numberOfPayments));
		totalPayment=numberOfPayments*monthlyPayment;
		
		System.out.println("�����$"+loanAmount);
		System.out.println("������"+annualInterestRate+"%");
		System.out.println("�������ޣ�"+loanPeriod);
		System.out.println("");
		System.out.println("�¸�����"+df.format(monthlyPayment));
		System.out.println("�ܸ�����"+df.format(totalPayment));
		System.out.println("");
		
	}
	
}