package testCase;
import static org.junit.Assert.*;
import junit.framework.Assert;
import contract.*;

import org.junit.Test;

import contract.LogGenerator;

public class testCase1 {

	@Test
	/*
	 * test for normal String
	 */
	public void testLineGenerator() {
		LogGenerator test=new LogGenerator();
		String result=test.generateTheLogFile("this is a test of see if it is work as we wish");
		Assert.assertEquals("this:is a test of see if it is work as we wish", result);
	}
	@Test
	/*
	 * test for null String
	 */
	public void testSpecialCaseForLineGenerator1(){
		LogGenerator test=new LogGenerator();
		String result=test.generateTheLogFile("");
		Assert.assertNull(result);
	}
	@Test
	/*
	 * test for String with special sign
	 */
	public void testSpecialSignInStringForLineGenerator(){
		LogGenerator test=new LogGenerator();
		String result=test.generateTheLogFile("!?this!? is a test of see if it is work as we wish");
		System.out.print(result);
		Assert.assertEquals("this:is a test of see if it is work as we wish", result);
	}
}
