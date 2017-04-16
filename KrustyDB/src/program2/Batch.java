package program2;

public class Batch {
private String cookieName, bDate, eDate;

	public Batch(String cName, String bDate, String eDate) {
		cookieName = cName;
		this.bDate = bDate;
		this.eDate = eDate;
	}
	
	public String getCookieName(){
		return cookieName;
	}
	
	public String getBeginningDate(){
		return bDate;
	}
	
	public String getEndingDate(){
		return eDate;
	}
}
