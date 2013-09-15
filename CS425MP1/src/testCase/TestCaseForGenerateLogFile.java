package testCase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import contract.LogGenerator;

public class TestCaseForGenerateLogFile {

	@Test
	/*
	 * test for known String
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
		Assert.assertEquals("this:is a test of see if it is work as we wish", result);
	}
	@Test
	/*
	 * test for store and get process from the physical file
	 */
	public void testTheWholePart() throws IOException{
		LogGenerator test=new LogGenerator();
		test.setInputDir("test/test1");
		test.setOutputDir("test/expected");
		test.generate();
		BufferedReader br = new BufferedReader(new FileReader("test/expected"));
		String line=br.readLine();
		System.out.println(line);
		Assert.assertEquals("this:is a test of see if it is work as we wish abcdef", line);
	}
}
