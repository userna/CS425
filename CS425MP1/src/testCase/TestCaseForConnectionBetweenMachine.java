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
	/**
	 * Test query item exist only in one file
	 */
	public void testExistOneFile(){
		
	}
	
	@Test
	/**
	 * Test query item exist in no file
	 */
	public void testExistNoFile(){
		
	}
	
	@Test
	/**
	 * Test query item exist in some files
	 */
	public void testExistSomeFile(){
		
	}
	
	@Test
	/**
	 * Test query item exist in all files
	 */
	public void testExistAllFile(){
		
	}
}
