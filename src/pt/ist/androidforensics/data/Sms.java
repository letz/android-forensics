package pt.ist.androidforensics.data;


public class Sms extends Model {
	
	private String address;
	private String body;
	private String date;
	private String read;
	private String type;
	
	public Sms(String body, String address, String date, String read, String type){
		this.setAddress(address);
		this.setBody(body);
		this.setDate(date);
		this.setRead(read);
		this.setType(type);
		
	}
	@Override
	public String toCSV() {
		return this.getAddress()+"#"+this.getBody()+"#"+this.getDate()+"#"+this.getRead()+"#"+this.getType();
	}

	@Override
	public String toCSVHeader() {
		return "Address#Body#Date#Read#Type";
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
