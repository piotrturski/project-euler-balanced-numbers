package net.piotrturski.balancedNumbers;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;

import net.piotrturski.balancedNumbers.Calculator;

import org.junit.runner.RunWith;

import com.googlecode.zohhak.api.Coercion;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class CalculatorTest {

	@TestWith({
		"1,  45",
		"2,  540",
		"3,  50040",
		"4,  3364890",
		"5,  4771029",
		"6,  6645504",
		"7,  9356034",
		"8,  4913436",
		"9,  8361561",
		"10, 10771919",
		"48, 5437457"
	})
	public void should_calculate_sum_of_balanced_numbers_modulo(int numberOfDigits, BigInteger expectedSumModulo) {
		assertThat(Calculator.sumModulo(numberOfDigits)).isEqualTo(expectedSumModulo);
	}
	
	@Coercion
	public BigInteger toBigInteger(String integer) {
		return new BigInteger(integer);
	}
}
