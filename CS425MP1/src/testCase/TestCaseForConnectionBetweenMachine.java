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
	 * this test is for grep command get all data from a distributed system
	 */
	public void testGrepCommandInDistributedSystem(){
		String[] serverAddress = {"192.17.11.187","192.17.11.186","192.17.11.199"};
		LittleDistributedProgram test=new LittleDistributedProgram(serverAddress);
		
	}
}
