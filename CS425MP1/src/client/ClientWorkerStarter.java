package client;

import java.util.List;

public class ClientWorkerStarter extends Thread{
	private int threadId;
	private Object lock;
	private String command;
	private String serverIp;
	private boolean succeeded;
	private boolean failed;
	private List<String> resultStr;
	
	public ClientWorkerStarter(int threadId, String command, String serverIp,
			List<String> resultStr) {
		this.threadId = threadId;
		this.command = command;
		this.serverIp = serverIp;
		this.resultStr = resultStr;
		lock = new Object();
		succeeded = false;
		failed = false;
	}

	public void run(){
		while(!succeeded){
			try {
				
				lock.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
