package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server{
	int numOfClients;
	private ListeningRequestThread lRT;
	ServerSocket serverSocket=null;
	public Server(int serverPort){
		numOfClients=0;
		try {
			serverSocket=new ServerSocket(serverPort);
		} catch (IOException e) {
			System.out.println("Something is wrong");
			e.printStackTrace();
		}
	}

	public void startRunning(){
		lRT=new ListeningRequestThread(this);
		lRT.start();  //this thread will handle the listening part
	}


}

class ListeningRequestThread extends Thread {
	boolean flag=true;
	Server serverToListening;
	public ListeningRequestThread(Server serverToListening) {
		this.serverToListening=serverToListening;

	}
	public void run(){
		while(flag){
			try {
				System.out.println("server running");
				Socket newClientSocket=serverToListening.serverSocket.accept();
				ThreadHandling newThread=new ThreadHandling(newClientSocket,serverToListening);
				serverToListening.numOfClients++;
				newThread.start();
				System.out.println("A new thread has been created sucessfully");
				System.out.println("How many computer connected to this machine: "+serverToListening.numOfClients);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void close(){
		flag=false;
	}
}






