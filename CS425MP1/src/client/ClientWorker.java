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
	public ClientWorker(String ipAddress, String command, List<String> synchronizedResult, int id){
		this.serverAddress = ipAddress;
		this.command = command;
		this.synchronizedResult = synchronizedResult;
		this.id = id;
		
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
			System.out.println("Thread "+Integer.toString(id)+" has failed us....restarting");
			return;
		}
		try {
			String returnedLine;
			System.out.println("Sending Command: "+command);
			out.println(command);
			//while not reading a tear down signal
			while(!((returnedLine = in.readLine()).equals(CommunicationDirectives.SHUT_DOWN.getVaLue()))){
				synchronizedResult.add(returnedLine+" from "+serverAddress);
			}			
		} catch (Exception e) {
			System.out.println("Thread "+Integer.toString(id)+" has failed us....restarting");
			return;
		}
		finally{
			try{
				in.close();
				out.close();
				socket.close();
				System.out.println("Thread "+id+" has finished!");
			}
			catch(IOException e){
				System.out.println("Thread "+Integer.toString(id)+" has failed us....restarting");
				return;
			}
		}	
	}
}