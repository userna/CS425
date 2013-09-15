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

/**
 * Worker class that delivers commands to server and get results
 * @author gchen10
 *
 */
public class ClientWorker extends Thread{
	private String serverAddress;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private String command;
	private List<String> synchronizedResult;
	private int id;
	//reference to the starter for this worker so when worker fails it can signal starter to restart worker
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
	
	/**
	 * Opens connection
	 * Delivers commands
	 * Read from server's results
	 */
	public void run(){
		socket = null;
		in = null;
		out = null;
		//open connection
		try {
			socket = new Socket(serverAddress, PortNumbers.SERVER_PORT.getValue());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		}
		catch (IOException e){
			restartWorker();
			return;
		}
		//deliver commands and receive results
		try {
			String returnedLine;
			System.out.println("Sending Command: "+command);
			out.println(command);
			//while not reading a tear down signal add to synchronizedResult
			while(!((returnedLine = in.readLine()).equals(CommunicationDirectives.SHUT_DOWN.getVaLue()))){
				synchronizedResult.add(returnedLine+" from "+serverAddress);
			}
			synchronized(lock){
				starter.setFailed(false);
				lock.notify();
			}
		} catch (Exception e) {
			restartWorker();
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
				System.out.println("Thread "+Integer.toString(id)+" didn't successfully close the sockets");
				return;
			}
		}	
	}
	
	/**
	 * helper function that restarts this worker thread
	 */
	private void restartWorker(){
		System.out.println("Thread "+Integer.toString(id)+" has failed us....restarting");
		synchronized(lock){
			starter.setFailed(true);
			lock.notify();
		}
	}
}
