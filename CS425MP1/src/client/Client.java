package client;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author gchen10
 *	Client class is the master who run client worker starters
 */
public class Client{
	private String[] servers;
	private ArrayList <ClientWorkerStarter> starters;
	//synchronized list of strings that stores the final result
	//each worker thread will attempt to update this list
	private List<String> resultStr;
	
	public Client(String[] serverIPs, String command){
		this.servers = serverIPs;
		starters = new ArrayList<ClientWorkerStarter>();
		resultStr = Collections.synchronizedList(new ArrayList<String>());
		System.out.println("running client");
		int i = 0;
		//start a thread to connect to each server IP
		for(String serverIP : servers){
			ClientWorkerStarter newStarter = new ClientWorkerStarter(i, command, serverIP, resultStr);
			newStarter.run();
			starters.add(newStarter);
			i++;
		}
	}
	
	/**
	 * Waits for all the workers to finish then re turn the final result
	 * @return the list containing the final result
	 * @throws InterruptedException
	 */
	public List<String> getResult() throws InterruptedException{
		//wait for client workers to finish
		for(ClientWorkerStarter starter: starters){
			starter.join();
		}
		return resultStr;
	}
}
