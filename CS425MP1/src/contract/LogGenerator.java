package contract;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LogGenerator {
	private String inputTextDir;
	private String outputTextDir;
	public static void main(String argv){
		LogGenerator test =new LogGenerator();
		test.generate();
	}
	public LogGenerator(){
		this.inputTextDir="original.txt";
		this.outputTextDir="example.txt";
	}
	public void setInputDir(String inputTextDir){
		this.inputTextDir=inputTextDir;
	}
	public void setOutputDir(String outputTextDir){
		this.outputTextDir=outputTextDir;
	}
	public void generate(){
		BufferedReader br=null;
		BufferedWriter output=null;
		try {
			File fileWrite = new File(outputTextDir);
			br = new BufferedReader(new FileReader(inputTextDir));
			output = new BufferedWriter(new FileWriter(fileWrite));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String line=null;
		try {
			line = br.readLine();
			while (line != null) {
				outputToInAFile(generateTheLogFile(line), output);
				line = br.readLine();
			}
			br.close();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String generateTheLogFile(String input){
		String result="";
		int i;
		if(input==null||input.length()<=10||input.indexOf(' ')==-1)
			return null;
		input=input.toLowerCase();
		StringBuilder key=new StringBuilder();
		for (i = 0; i < input.length(); i++) {
			if(input.charAt(i)!=' '){
				if(input.charAt(i)<='z'&&input.charAt(i)>='a')
					key.append(input.charAt(i));
				}
			else break;
		}
		if(key.length()==0)
			return null;
		result=key.toString()+":"+input.substring(i+1,input.length()>=61?60:input.length()).trim();
		return result;
	}
	public void outputToInAFile(String needToBeOutPut,BufferedWriter output){
		if(needToBeOutPut==null)
			return ;
		try {
			output.write(needToBeOutPut);
			output.write('\n');
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
}
