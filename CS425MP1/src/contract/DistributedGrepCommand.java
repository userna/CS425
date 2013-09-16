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
	private String logfile = "machine.log";

	/**
	 * the command is pre validated on input to get rid of erroneous command
	 * @param command
	 */
	public DistributedGrepCommand(String command){
		String [] commands = command.split(" ");
		key = null;
		value = null;
		for(int i = 1; i<commands.length; i+=2){
			String flag = commands[i];
			String regex = commands[i+1];
			
			if(flag.equals("-v")){
				value = regex;
			}
			else if(flag.equals("-k")){
				
				key = regex;
			}
			if(i==commands.length-3){
				logfile = commands[i+2];
				break;
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
	
	//getters and setters
	
	public String getLogfile() {
		return logfile;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setLogfile(String logfile) {
		this.logfile = logfile;
	}

}
