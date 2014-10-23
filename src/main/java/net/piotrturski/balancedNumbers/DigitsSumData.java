package net.piotrturski.balancedNumbers;
import java.math.BigInteger;

/**
 * information about numbers that digits sum up to the same value 
 *
 */
class DigitsSumData {

	/**
	 * sum modulo of all numbers with the same sum of digits
	 */
	final public BigInteger sum;// = BigInteger.ZERO;
	
	/**
	 * number of numbers with the same sum of digits
	 */
	final public BigInteger amountOfNumbers;// = BigInteger.ZERO;

	
	public DigitsSumData(BigInteger sum, BigInteger amountOfNumbers) {
		this.sum = sum;
		this.amountOfNumbers = amountOfNumbers;
	}

}
