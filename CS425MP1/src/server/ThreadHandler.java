package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import contract.CommunicationDirectives;
import contract.DistributedGrepCommand;

/**
 * For every request accepted execute grep command
 * @author Yuan Chen
 *
 */
public class ThreadHandler extends Thread{
	private Socket clientSocket;
	private Server parenServer;
	public ThreadHandler(Socket clientSocket,Server parentThread){
		this.clientSocket=clientSocket;
		this.parenServer=parentThread;
	}
	
	
	public void run(){   //this handle all the inner logic
		BufferedReader is;
		try {
			//read and construct custome grep command
			is = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
			String commandFromClient=null;
				commandFromClient=is.readLine();
				System.out.println(commandFromClient);
				ArrayList<String> result = executeGrepCommand(commandFromClient);
				outPutToClient(result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally{
			try {
				clientSocket.close();
				parenServer.numOfClients--;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * executes grep command and return result
	 * @param commandFromClient
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private ArrayList<String> executeGrepCommand(String commandFromClient)
			throws IOException, InterruptedException {
		DistributedGrepCommand grepCommand=new DistributedGrepCommand(commandFromClient);
		ArrayList<String> result=grepCommand.executeCommand();
		return result;
	}
	
	/**
	 * writes to socket and send shut down request at the end of input
	 * @param result
	 * @throws IOException
	 */
	private void outPutToClient(ArrayList<String> result) throws IOException {
		PrintStream os = new PrintStream(clientSocket.getOutputStream(),true);
		for (int i = 0; i < result.size(); i++) {
			os.println(result.get(i));
		}
		os.println(CommunicationDirectives.SHUT_DOWN.getVaLue());
	}
}