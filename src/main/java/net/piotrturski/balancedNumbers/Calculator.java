package net.piotrturski.balancedNumbers;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class Calculator {

	// # of digits -> info about all numbers (not only balanced) without leading 0
	static private ImmutableMap<Integer, Distribution> begins;
	
	// # of digits -> info about all numbers (not only balanced)
	static private ImmutableMap<Integer, Distribution> ends;
	
	static final public BigInteger MODULO = BigInteger.valueOf(3).pow(15); 
	
	/**
	 * # digits -> info about all numbers (not just balanced) with that # of digits 
	 */
	static public ImmutableMap<Integer, Distribution> generateAllNumbersDistributions(int maxDigits, int startingDigit) {
		Distribution singleDigitDistribution = Distribution.singleDigitNumbers(startingDigit);
		Map<Integer, Distribution> result = new HashMap<Integer, Distribution>();
		result.put(1, singleDigitDistribution);
		
		Distribution longer = singleDigitDistribution;
		for (int i = 2; i <= maxDigits; i++) {
			longer = longer.generate1digitLonger();
			result.put(i, longer);
		}
		return ImmutableMap.copyOf(result);
	}

	/**
	 * use dynamic programming to calculate T(n) for all n = 1..maxDigits  
	 */
	static private BigInteger sumOfBalancedNumbers(int maxDigits) {
		// # of digits (n) -> sum of all balanced numbers with up to n digits (smaller than 10^n); T(n)
		Map<Integer, BigInteger> length2Sum = new HashMap<Integer, BigInteger>();
		length2Sum.put(1, BigInteger.valueOf(45)); // sum 1..9
		for (int i = 2; i <= maxDigits; i++) {
			int half = i / 2;
			Distribution begin = begins.get(half);
			Distribution end = ends.get(half);
			BigInteger sum;
			if (i % 2 == 0) {
				sum = begin.append(end);
			} else {
				sum = begin.appendAndInsert(end);
			}
			length2Sum.put(i, length2Sum.get(i-1).add(sum));
		}
		return length2Sum.get(maxDigits);
	}
	
	static public BigInteger sumModulo(int maxDigits) {
		begins = generateAllNumbersDistributions(maxDigits, 1);
		ends = generateAllNumbersDistributions(maxDigits, 0);
		
		return sumOfBalancedNumbers(maxDigits).mod(MODULO);
	}
	
	public static void main(String[] args) {
		System.out.println(sumModulo(47));
	}
	
}
