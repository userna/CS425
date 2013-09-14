package client;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Client{
	private String[] servers;
	private ArrayList <ClientWorker> workers;
	//synchronized list of strings
	private List<String> resultStr;
	
	public Client(String[] serverIPs, String command){
		this.servers = serverIPs;
		workers = new ArrayList<ClientWorker>();
		resultStr = Collections.synchronizedList(new ArrayList<String>());
		System.out.println("running client");
		int i = 0;
		for(String serverIP : servers){
			ClientWorker newWorker = new ClientWorker(serverIP, command, resultStr, i);
			newWorker.start();
			workers.add(newWorker);
			i++;
		}
	}
	
	public List<String> getResult() throws InterruptedException{
		//wait for client workers to finish
		for(ClientWorker worker: workers){
			worker.join();
		}
		return resultStr;
	}
}
