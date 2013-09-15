package main;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import server.Server;

//import contract.DistributedGrepCommand;
import contract.DistributedGrepCommand;
import contract.LogGenerator;
import contract.PortNumbers;

import client.Client;

/**
 * Main program
 * @author gchen10
 *
 */
public class LittleDistributedProgram {
	private static String[] serverAddress;
	
	public LittleDistributedProgram(String[] serverAddress){
		this.serverAddress = serverAddress;
	}
	
	public void run() throws IOException, InterruptedException {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String buffer;
		PrintWriter outfile = new PrintWriter(new FileWriter("RESAULT.txt"));
		Client client;
		//start server
		Server testServer=new Server(PortNumbers.SERVER_PORT.getValue());
		testServer.startRunning();
		while(true){
			print("Input grep command");
			buffer = stdin.readLine();
			if(isExit(buffer)){
				System.exit(0);
			}
			if(isBadCommand(buffer)){
				continue;
			}
			long timeElapsed = System.nanoTime();
			client = new Client(serverAddress, buffer);
			List<String>result = client.getResult();
			ArrayList<String> localResult = new DistributedGrepCommand(buffer).executeCommand();
			result.addAll(localResult);
			for(String line: result){
				outfile.println(line);
				outfile.flush();
			}
			timeElapsed = System.nanoTime()-timeElapsed;
			System.out.println("Time elapsed: "+(double)timeElapsed/1000000.0+"ms");
		}
	}
		
	private static boolean isBadCommand(String command){
		if ((command.split(" ")).length!=3 && (command.split(" ")).length!=5){
			print("Bad command, reenter");
			return true;
		}
		String[] literals = command.split(" ");
		if(literals[0].equals("grep")){
			if(literals.length==3){
				//if the grep command contains the correct flags
				if(literals[1].equals("-v")||literals[1].equals("-k")){
					return false;
				}
			}
			else if(literals.length==5){
				//if the grep command contains the correct flags.
				if((literals[1].equals("-v")||literals[1].equals("-k")) && 
						(literals[3].equals("-v")||literals[3].equals("-k"))){
					return false;
				}
			}
		}
		print("Bad command, reenter");
		return true;
	}
	private static boolean isExit(String command){
		if(command.equals("exit")||command.equals("quit")){
			return true;
		}
		return false;
	}
	private static void print(String str){
		System.out.println(str);
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		String[] serverAddress = {"192.17.11.217","192.17.11.216","192.17.11.218"};
		LittleDistributedProgram myProg = new LittleDistributedProgram(serverAddress);
		myProg.run();
	}
}
