package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import contract.CommunicationDirectives;
import contract.PortNumbers;
// TODO need to check if one client has failed to connect and handel that

public class ClientWorker extends Thread{
	private String serverAddress;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private String command;
	private List<String> synchronizedResult;
	private int id;
	private ClientWorkerStarter starter;
	private Object lock;
	
	public ClientWorker(String ipAddress, String command, List<String> synchronizedResult, int id, Object lock, ClientWorkerStarter clientWorkerStarter){
		this.serverAddress = ipAddress;
		this.command = command;
		this.synchronizedResult = synchronizedResult;
		this.id = id;
		this.starter = clientWorkerStarter;
		this.lock = lock;		
	}
	public void run(){
		socket = null;
		in = null;
		out = null;
		try {
			socket = new Socket(serverAddress, PortNumbers.SERVER_PORT.getValue());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		}
		catch (IOException e){
			handleException();
			return;
		}
		try {
			String returnedLine;
			System.out.println("Sending Command: "+command);
			out.println(command);
			//while not reading a tear down signal add to synchronizedResult
			while(!((returnedLine = in.readLine()).equals(CommunicationDirectives.SHUT_DOWN.getVaLue()))){
				synchronizedResult.add(returnedLine+" from "+serverAddress);
			}			
		} catch (Exception e) {
			handleException();
			return;
		}
		finally{
			try{
				in.close();
				out.close();
				socket.close();
				System.out.println("Thread "+id+" has finished!");
				starter.setFailed(false);
				lock.notify();
			}
			catch(IOException e){
				System.out.println("Thread "+Integer.toString(id)+" didn't successfully close the sockets");
				return;
			}
		}	
	}
	private void handleException(){
		System.out.println("Thread "+Integer.toString(id)+" has failed us....restarting");
		lock.notify();
	}
}
