package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import server.Server;

//import contract.DistributedGrepCommand;
import contract.PortNumbers;

import client.Client;

public class LittleDistributedProgram {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String buffer;
		Client client;
		String [] serverAddress = {"192.17.11.199","192.17.11.198"};
		//start server
		Server testServer=new Server(PortNumbers.SERVER_PORT.getValue());
		testServer.startRunning();
		while(true){
			print("Input grep command");
			buffer = stdin.readLine();
			if(isBadCommand(buffer)){
				continue;
			}
			client = new Client(serverAddress, buffer);
			List<String>result = client.getResult();
			for(String line: result){
				print(line);
			}
		}
	}
	private static boolean isBadCommand(String command){
		if ((command.split(" ")).length!=3 && (command.split(" ")).length!=5){
			print("Bad command, reenter");
			return true;
		}
		return false;
	}
	private static void print(String str){
		System.out.println(str);
	}
}
