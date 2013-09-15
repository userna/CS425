package server;

import java.io.IOException;
import java.net.ServerSocket;


public class Server{
	int numOfClients;
	private ListeningRequestThread lRT;
	private ServerSocket serverSocket=null;
	public Server(int serverPort){
		numOfClients=0;
		try {
			serverSocket=new ServerSocket(serverPort);
		} catch (IOException e) {
			System.out.println("Something is wrong in the connection");
			e.printStackTrace();
		}
	}

	public void startRunning(){
		lRT=new ListeningRequestThread(this);
		lRT.start();  //this thread will handle the listening part
	}
	public ServerSocket getServerSocket(){
		return this.serverSocket;
	}

}








