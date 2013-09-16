package testCase;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import main.LittleDistributedProgram;

import org.junit.Test;

import contract.PortNumbers;

import server.Server;

import main.LittleDistributedProgram;

public class TestCaseForConnectionBetweenMachine {

	@Test
	/*
	 * this test is for testing server could be built
	 */
	public void testServerListeningForRequest() {
		Server test=new Server(9987);//test the Server could run as expected
		test.startRunning();
		Assert.assertNotNull(test.getServerSocket());
	}
	@Test
	/**
	 * Test query item exist only in one file
	 */
	public void testExistOneFile() throws IOException, InterruptedException{
		String[] serverAdresses = {"192.17.11.72","192.17.11.68","192.17.11.67"};
		String commandToRun = "grep -k log2 unique.log";
		LittleDistributedProgram test = new LittleDistributedProgram(serverAdresses, commandToRun);
		test.run();
		List<String> result = test.getResult();
		System.out.println(result.get(0));
		Assert.assertEquals(1, result.size());
		Assert.assertEquals("log2:unique from 192.17.11.67", result.get(0));
	}
	
	@Test
	/**
	 * Test query item exist in no file
	 */
	public void testExistNoFile() throws IOException, InterruptedException{
		String[] serverAdresses = {"192.17.11.72","192.17.11.68","192.17.11.67"};
		String commandToRun = "grep -k log no.log";
		LittleDistributedProgram test = new LittleDistributedProgram(serverAdresses, commandToRun);
		test.run();
		List<String> result = test.getResult();
		Assert.assertEquals(0, result.size());
	}
	
	@Test
	/**
	 * Test query item exist in some files
	 */
	public void testExistSomeFile() throws IOException, InterruptedException{
		String[] serverAdresses = {"192.17.11.72","192.17.11.68","192.17.11.67"};
		String commandToRun = "grep -k some some.log";
		LittleDistributedProgram test = new LittleDistributedProgram(serverAdresses, commandToRun);
		test.run();
		List<String> result = test.getResult();
		Assert.assertEquals(2, result.size());
		Assert.assertEquals(true, result.contains("some:some1 from 192.17.11.67"));
		Assert.assertEquals(true, result.contains("some:some2 from 192.17.11.67"));
	}
	
	@Test
	/**
	 * Test query item exist in all files
	 */
	public void testExistAllFile() throws IOException, InterruptedException{
		String[] serverAdresses = {"192.17.11.72","192.17.11.68","192.17.11.67"};
		String commandToRun = "grep -k all all.log";
		LittleDistributedProgram test = new LittleDistributedProgram(serverAdresses, commandToRun);
		test.run();
		List<String> result = test.getResult();
		Assert.assertEquals(9, result.size());
		Assert.assertEquals(true, result.contains("all:all1 from 192.17.11.72"));
		Assert.assertEquals(true, result.contains("all:all2 from 192.17.11.72"));
		Assert.assertEquals(true, result.contains("all:all3 from 192.17.11.72"));
		Assert.assertEquals(true, result.contains("all:all1 from 192.17.11.68"));
		Assert.assertEquals(true, result.contains("all:all2 from 192.17.11.68"));
		Assert.assertEquals(true, result.contains("all:all3 from 192.17.11.68"));
		Assert.assertEquals(true, result.contains("all:all1 from 192.17.11.67"));
		Assert.assertEquals(true, result.contains("all:all2 from 192.17.11.67"));
		Assert.assertEquals(true, result.contains("all:all3 from 192.17.11.67"));
	}
}
