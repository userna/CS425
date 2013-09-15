package testCase;

import junit.framework.Assert;

import main.LittleDistributedProgram;

import org.junit.Test;

import server.Server;

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
	/*
	 * this test is for grep command get all data from a doma distributed system
	 */
	public void testGrepCommandInDistributedSystem(){
		LittleDistributedProgram test=new LittleDistributedProgram();
		
	}
}
