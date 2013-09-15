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
		try {
			is = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
			String commandFromClient=null;
				commandFromClient=is.readLine();
				System.out.println(commandFromClient);
				ArrayList<String> result = executeGrepCommand(commandFromClient);
				outPutToClient(result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
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
	private ArrayList<String> executeGrepCommand(String commandFromClient)
			throws IOException, InterruptedException {
		DistributedGrepCommand grepCommand=new DistributedGrepCommand(commandFromClient);
		ArrayList<String> result=grepCommand.executeCommand();
		return result;
	}
	private void outPutToClient(ArrayList<String> result) throws IOException {
		PrintStream os = new PrintStream(clientSocket.getOutputStream(),true);
		for (int i = 0; i < result.size(); i++) {
			os.println(result.get(i));
		}
		os.println(CommunicationDirectives.SHUT_DOWN.getVaLue());
	}
}