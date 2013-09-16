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
	private String commandToRun;
	private List<String> result;
	
	public LittleDistributedProgram(String[] serverAddress, String commandToRun){
		this.serverAddress = serverAddress;
		this.commandToRun = commandToRun;
	}
	
	/**
	 * Starts server
	 * Input and validate command
	 * Save results into a file
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void run() throws IOException, InterruptedException {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String buffer;
		//use to run the pre-specified command only once
		boolean loop = true;
		PrintWriter outfile = new PrintWriter(new FileWriter("RESAULT.txt"));
		Client client;
		//start server
		Server server=new Server(PortNumbers.SERVER_PORT.getValue());
		server.startRunning();
		while(loop){
			print("Input grep command");
			if(commandToRun==null){
				buffer = stdin.readLine();
			}
			else{
				buffer = commandToRun;
				print(commandToRun);
				loop = false;
			}
			if(isExit(buffer)){
				System.exit(0);
			}
			if(isBadCommand(buffer)){
				continue;
			}
			long timeElapsed = System.nanoTime();
			client = new Client(serverAddress, buffer);
			result = client.getResult();
			ArrayList<String> localResult = new DistributedGrepCommand(buffer).executeCommand();
			result.addAll(localResult);
			for(String line: result){
				outfile.println(line);
				outfile.flush();
			}
			timeElapsed = System.nanoTime()-timeElapsed;
			print("Time elapsed: "+(double)timeElapsed/1000000.0+"ms");
		}
	}
	
	/**
	 * validate command
	 * @param command
	 * @return
	 */
	private static boolean isBadCommand(String command){
		if ((command.split(" ")).length!=4 && (command.split(" ")).length!=6){
			print("Bad command, reenter");
			return true;
		}
		String[] literals = command.split(" ");
		if(literals[0].equals("grep")){
			if(literals.length==4){
				//if the grep command contains the correct flags
				if(literals[1].equals("-v")||literals[1].equals("-k")){
					return false;
				}
			}
			else if(literals.length==6){
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
	
	/**
	 * see if it's a quit command
	 * @param command
	 * @return
	 */
	private static boolean isExit(String command){
		if(command.equals("exit")||command.equals("quit")){
			return true;
		}
		return false;
	}
	
	/**
	 * helper print
	 * @param str
	 */
	private static void print(String str){
		System.out.println(str);
	}

	
	/**
	 * main function
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		String[] serverAddress = {"192.17.11.72","192.17.11.68","192.17.11.67"};
		LittleDistributedProgram myProg = new LittleDistributedProgram(serverAddress, null);
		myProg.run();
	}
	
	//getters setters
	
	public String getCommandToRun() {
		return commandToRun;
	}

	public void setCommandToRun(String commandToRun) {
		this.commandToRun = commandToRun;
	}

	public List<String> getResult() {
		return result;
	}

	public void setResult(List<String> result) {
		this.result = result;
	}
}
