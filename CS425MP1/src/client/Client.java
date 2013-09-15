package client;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Client{
	private String[] servers;
	private ArrayList <ClientWorkerStarter> starters;
	//synchronized list of strings
	private List<String> resultStr;
	
	public Client(String[] serverIPs, String command){
		this.servers = serverIPs;
		starters = new ArrayList<ClientWorkerStarter>();
		resultStr = Collections.synchronizedList(new ArrayList<String>());
		System.out.println("running client");
		int i = 0;
		for(String serverIP : servers){
			ClientWorkerStarter newStarter = new ClientWorkerStarter(i, command, serverIP, resultStr);
			newStarter.run();
			starters.add(newStarter);
			i++;
		}
	}
	
	public List<String> getResult() throws InterruptedException{
		//wait for client workers to finish
		for(ClientWorkerStarter starter: starters){
			starter.join();
		}
		return resultStr;
	}
}
