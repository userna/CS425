package contract;

/**
 * Agreed port numbers between server and client
 * @author gchen10
 *
 */
public enum PortNumbers {
	SERVER_PORT(9881);
	
	private int intVal;
	
	PortNumbers(int intVal){
		this.intVal = intVal;
	}

	public int getValue() {
		return intVal;
	}
}

