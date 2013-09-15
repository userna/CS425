package contract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This class captures the grep command
 *
 * @author gchen10
 *
 */
public class DistributedGrepCommand {
	private String key;
	private String value;
	private static final String logfile = "machine.log";
	
	public DistributedGrepCommand(String command){
		String [] commands = command.split(" ");
		for(int i = 1; i<commands.length; i+=2){
			String flag = commands[i];
			String regex = commands[i+1];
			key = null;
			value = null;
			if(flag.equals("-v")){
				value = regex;
			}
			else if(flag.equals("-k")){
				
				key = regex;
			}
		}		
	}

	/**
	 * execute command using local system
	 * @return resulting lines from grep
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public ArrayList<String> executeCommand() throws IOException, InterruptedException{
		Runtime r = Runtime.getRuntime();
		Process p = null;
		if(key==null){
			p = r.exec("grep [:].*"+value+".* "+logfile);
		}
		else if(value==null){
			p = r.exec("grep .*"+key+".*[:]"+" "+logfile);
		}
		else{
			p = r.exec("grep .*"+key+".*[:] | "+"grep [:].*"+value+".* "+logfile);
		}
		//p.waitFor();
		BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
		ArrayList<String> result = new ArrayList<String>();
		String line = "";
		while((line=b.readLine())!=null){
			result.add(line);
		}
		return result;
	}
	

}
