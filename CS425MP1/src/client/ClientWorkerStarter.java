package client;

import java.util.List;

/**
 * Starts and restarts worker
 * @author gchen10
 *
 */
public class ClientWorkerStarter extends Thread{
	private int threadId;
	private Object lock;
	private String command;
	private String serverIP;
	private boolean failed;
	private List<String> resultStr;
	
	public ClientWorkerStarter(int threadId, String command, String serverIP,
			List<String> resultStr) {
		this.threadId = threadId;
		this.command = command;
		this.serverIP = serverIP;
		this.resultStr = resultStr;
		lock = new Object();
		failed = true;
	}

	/**
	 * pass worker the relevant information
	 * then wait for worker to signal success or failure
	 */
	public void run(){
		//while the worker failed restart the worker then wait for its signal
		synchronized(lock){
		while(failed){
			try {
				//start worker thread
				ClientWorker newWorker = new ClientWorker(serverIP, command, resultStr, threadId, lock, this);
				newWorker.start();
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			System.out.println("Starter for worker "+threadId+ " has finished!");
		}
	}

	/**
	 * Getter for failed variable
	 * @param failed
	 */
	public void setFailed(boolean failed) {
		this.failed = failed;
	}
}
