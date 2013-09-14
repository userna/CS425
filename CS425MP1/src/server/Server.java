package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;

import contract.CommunicationDirectives;
import contract.DistributedGrepCommand;

public class Server{
	int numOfClients;
	ArrayList<Socket> clients=new ArrayList<Socket> ();
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

//	public static void main(String argv[]) throws IOException{
//		System.out.println("server is running");
//		Server testServer=new Server(9881);
//		testServer.startRunning();
//
//	}
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
				serverToListening.clients.add(newClientSocket); //this add a client to the queue
				ThreadHandling newThread=new ThreadHandling(newClientSocket);
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

class ThreadHandling extends Thread{
	private Socket clientSocket;
	public ThreadHandling(Socket clientSocket){
		this.clientSocket=clientSocket;
	}
	public void run(){   //this handle all the inner logic
		BufferedReader is;
		PrintStream os;
		try {
			is = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
			os = new PrintStream(clientSocket.getOutputStream(),true);
			String commandFromClient=null;
			try {
				while(!clientSocket.isClosed()){//check every 100mi
					Thread.sleep(100);
					commandFromClient=is.readLine();
					break;
				}
				System.out.println(commandFromClient);
				DistributedGrepCommand grepCommand=new DistributedGrepCommand(commandFromClient);
				ArrayList<String> result=grepCommand.executeCommand();
				for (int i = 0; i < result.size(); i++) {
					os.println(result.get(i));
				}
				os.println(CommunicationDirectives.SHUT_DOWN.getVaLue());
				clientSocket.close();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}




//	public static void main(String argc[]) {
//		ServerSocket disServer = null;
//		BufferedReader is;
//		PrintStream os;
//		Socket clientSocket = null;
//		try {
//			disServer=new ServerSocket(9876);
//			clientSocket = disServer.accept();
//			is = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
//			os = new PrintStream(clientSocket.getOutputStream(),true);
//			os.println("fucker");
//			while (true) {
//				String line = is.readLine();
//				System.out.println(line);
//				os.println(line); 
//			}
//		} catch (IOException e) {
//			System.out.println("something is wrong");
//			e.printStackTrace();
//		}
//		finally {
//
//			try {
//				disServer.close();
//				clientSocket.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

