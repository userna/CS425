package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import contract.CommunicationDirectives;
import contract.DistributedGrepCommand;

public class ThreadHandling extends Thread{
	private Socket clientSocket;
	private Server parenServer;
	public ThreadHandling(Socket clientSocket,Server parentThread){
		this.clientSocket=clientSocket;
		this.parenServer=parentThread;
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
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				clientSocket.close();
				parenServer.numOfClients--;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}