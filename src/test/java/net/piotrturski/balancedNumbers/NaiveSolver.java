package net.piotrturski.balancedNumbers;
import java.math.BigInteger;

import net.piotrturski.balancedNumbers.Calculator;

public class NaiveSolver {

	public static void main(String... args) {
		generateTestData(7);
	}

	public static void generateTestData(int n) {
		long j = BigInteger.TEN.pow(n).longValue();
		BigInteger sum = BigInteger.ZERO;
		long limit = 1;
		for (long i = 1; i <= j; i++) {
			if (i == Math.pow(10, limit)) {
				System.out.println("\""+limit+", "+sum.mod(Calculator.MODULO)+"\",");
				limit++;
			}
			if (balanced(i)) {
				sum = sum.add(BigInteger.valueOf(i));
			}
		}
	}

	private static boolean balanced(long n) {
		String number = ""+n;
		double half = (number.length() + 0.0) / 2;
		double ceil2 = Math.ceil(half);
		Double c = ceil2;
		int intHalf = c.intValue();
		
		int digitSumUp = 0;
		int digitSumDown = 0;
		for (int i = 0; i < intHalf; i++) {
			digitSumUp += Integer.parseInt(""+number.charAt(i));
			digitSumDown += Integer.parseInt(""+number.charAt(number.length()-1-i));
		}
		return digitSumUp == digitSumDown;
	}
}
