package server;

import java.io.IOException;
import java.net.Socket;

public class ListeningRequestThread extends Thread {
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
				ThreadHandler newThread=new ThreadHandler(newClientSocket,serverToListening);
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
