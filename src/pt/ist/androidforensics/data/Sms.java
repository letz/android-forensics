package pt.ist.androidforensics.data;


public class Sms extends Model {
	
	private String address;
	private String body;
	private String date;
	private String read;
	private String type;
	
	public Sms(String address, String body, String date, String read, String type){
		setAddress(address);
		setBody(body);
		setDate(date);
		setRead(read);
		setType(type);
		
	}
	@Override
	public String toString() {
		return "Sms [address=" + address + ", body=" + body + ", date=" + date
				+ ", read=" + read + ", type=" + type + "]";
	}
	@Override
	public String toCSV() {
		return getAddress() + "#" + getBody()+ "#" + getDate() + "#" + getRead() + "#" + getType();
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
