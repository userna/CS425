package client;

import java.util.List;

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

	public void run(){
		while(!failed){
			try {
				//start worker thread
				ClientWorker newWorker = new ClientWorker(serverIP, command, resultStr, threadId, lock, this);
				newWorker.start();
				lock.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Starter for worker "+threadId+ " has finished!");
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}
}
