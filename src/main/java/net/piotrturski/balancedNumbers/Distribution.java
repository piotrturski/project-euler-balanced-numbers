package net.piotrturski.balancedNumbers;
import static com.google.common.base.MoreObjects.firstNonNull;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TEN;
import static java.math.BigInteger.ZERO;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableMap;

/**
 * stores information about all numbers (not just balanced) with specific number of digits 
 */
class Distribution {
			
	private static final DigitsSumData NO_DIGITS_SUM_DATA = new DigitsSumData(ZERO, ZERO);
	final private int sizeInDigits;
	
	// sum of digits -> (sum of all numbers with that sum of digits, # of numbers with that sum of digits)
	final protected ImmutableMap<BigInteger, DigitsSumData> digitSumInformation;// = new HashMap<BigInteger, DigitsSumData>();
	
	Distribution(int sizeInDigits, Map<BigInteger, DigitsSumData> digitSumInformation1) {
		this.sizeInDigits = sizeInDigits;
		this.digitSumInformation = ImmutableMap.copyOf(digitSumInformation1);
	}

	private DigitsSumData getDigitsSumData(Map<BigInteger, DigitsSumData> digitSumInformation, BigInteger sumOfDigits) {
		return firstNonNull(digitSumInformation.get(sumOfDigits), NO_DIGITS_SUM_DATA);
	}
	
	private static BigInteger shiftLeftDecimal(BigInteger number, int n) {
		return TEN.pow(n).multiply(number);
	}
	
	/**
	 *  return sum of all balanced numbers composed of (this ++ end)
	 */
	public BigInteger append(Distribution end) {
		BigInteger tmpResult, result = ZERO;
		
		for (Entry<BigInteger, DigitsSumData> e : digitSumInformation.entrySet()) {
			BigInteger sumOfDigits = e.getKey();
			DigitsSumData endDigitsSumData = getDigitsSumData(end.digitSumInformation, sumOfDigits);
			DigitsSumData beginDigitsSumData = e.getValue();
			
			tmpResult = shiftLeftDecimal(beginDigitsSumData.sum, end.sizeInDigits).multiply(endDigitsSumData.amountOfNumbers)
				.add((endDigitsSumData.sum.multiply(beginDigitsSumData.amountOfNumbers)));
			
			result = result.add(tmpResult);
		}
		return result;
	}

	/**
	 *  return sum of all balanced numbers composed of (this ++ x ++ end)
	 */
	public BigInteger appendAndInsert(Distribution end) {
		
		BigInteger result = ZERO;
		for (Entry<BigInteger, DigitsSumData> e : digitSumInformation.entrySet()) {
			BigInteger sumOfDigits = e.getKey();
			DigitsSumData endDigitsSumData = getDigitsSumData(end.digitSumInformation, sumOfDigits);
			DigitsSumData beginDigitsSumData = e.getValue();
			
			BigInteger newDigitValue = shiftLeftDecimal(BigInteger.valueOf(45), end.sizeInDigits);
			
			BigInteger tmpResult = shiftLeftDecimal(beginDigitsSumData.sum, end.sizeInDigits).multiply(TEN)
				.multiply(endDigitsSumData.amountOfNumbers)
				.add(endDigitsSumData.sum.multiply(beginDigitsSumData.amountOfNumbers));
			
			tmpResult = tmpResult.multiply(TEN);
			tmpResult = tmpResult.add((newDigitValue.multiply(beginDigitsSumData.amountOfNumbers).multiply(endDigitsSumData.amountOfNumbers)));
			
			result = result.add(tmpResult);
		}
		return result;
	}
	
	/**
	 * single digit numbers. starting with specified digit
	 */
	public static Distribution singleDigitNumbers(int start) {
		Map<BigInteger, DigitsSumData> digitSumInfo = new HashMap<BigInteger, DigitsSumData>();
		for (int i = start; i < 10; i++) {
			DigitsSumData value = new DigitsSumData(BigInteger.valueOf(i), ONE); //(1,1), ... (9,1): sum, #numbers
			digitSumInfo.put(BigInteger.valueOf(i), value);
		} // 1 -> (1,1),.., 9 -> (9,1)
		return new Distribution(1, digitSumInfo);
	}
	
	/**
	 * calculate information about 1 digit longer numbers
	 */
	public Distribution generate1digitLonger() {
		Map<BigInteger, DigitsSumData> newDigitSumInformation = new HashMap<BigInteger, DigitsSumData>();
		
		for (int i = 0; i <= 9; i++) {
			for (Entry<BigInteger, DigitsSumData> e : digitSumInformation.entrySet()) {
				
				//add new digit at the end. what about multiplication?!
				BigInteger bDigit = BigInteger.valueOf(i);
				BigInteger oldDigitSum = e.getKey();
				DigitsSumData oldDigitsSumData = e.getValue();
				
				BigInteger newDigitSum = oldDigitSum.add(bDigit);
				DigitsSumData currentStatsAboutNewDigitsSum = getDigitsSumData(newDigitSumInformation, newDigitSum);

				BigInteger sum = oldDigitsSumData.sum.multiply(TEN).add(bDigit.multiply(oldDigitsSumData.amountOfNumbers)).add(currentStatsAboutNewDigitsSum.sum); //all that are stored + all new transformed
				BigInteger amountOfNumbers = currentStatsAboutNewDigitsSum.amountOfNumbers.add(oldDigitsSumData.amountOfNumbers); //all stored + all new
				
				newDigitSumInformation.put(newDigitSum, new DigitsSumData(sum, amountOfNumbers));
			}
		}
		return new Distribution(sizeInDigits+1, newDigitSumInformation);
	}
}
