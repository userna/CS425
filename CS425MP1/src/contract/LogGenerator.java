package contract;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LogGenerator {

	public static void main(String argv){
		LogGenerator test =new LogGenerator();
		test.file();
	}

	public void file(){
		BufferedReader br=null;
		BufferedWriter output=null;
		try {
			File file = new File("example.txt");
			br = new BufferedReader(new FileReader("original"));
			output = new BufferedWriter(new FileWriter(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String line=null;
		try {
			line = br.readLine();
			while (line != null) {
				line = br.readLine();
				outputToInAFile(generateTheLogFile(line), output);
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
			if(input.charAt(i)!=' '&&input.charAt(i)<='z'&&input.charAt(i)>='a')
				key.append(input.charAt(i));
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
