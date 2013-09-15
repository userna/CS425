package contract;

/**
 * Communication directives between server and client
 * @author gchen10
 *
 */
public enum CommunicationDirectives {
	SHUT_DOWN("TEAR_DOWN_CONNECTION");
	
	private String strVal;
	
	CommunicationDirectives(String strVal){
		this.strVal = strVal;
	}
	
	public String getVaLue(){
		return strVal;
	}
}