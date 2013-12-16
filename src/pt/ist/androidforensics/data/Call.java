package pt.ist.androidforensics.data;

import java.util.Date;

public class Call extends Model {
	
	private String phoneNumber;
	private String callType;
	private Date callDate;
	private String callDuration;
	
	public Call(String number, String type, Date date, String duration) {
		this.phoneNumber = number;
		this.callType = type;
		this.callDate = date;
		this.callDuration = duration;
	}
	
	@Override
	public String toString() {
		return "Call [phoneNumber=" + phoneNumber + ", callType=" + callType
				+ ", callDate=" + callDate + ", callDuration=" + callDuration
				+ "]";
	}

	public void setPhoneNumber(String number) {
		this.phoneNumber = number;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setCallType(String type) {
		this.callType = type;
	}
	
	public String getCallType() {
		return callType;
	}
	
	public void setCallSate(Date date) {
		this.callDate = date;
	}
	
	public Date getCallDate() {
		return callDate;
	}
	
	public void setCallDuration(String duration) {
		this.callDuration = duration;
	}
	
	public String getCallDuration() {
		return callDuration;
	}
	
	@Override
	public String toCSV() {
		return this.getCallType()+"#"+this.getPhoneNumber()+"#"+this.getCallDate()+"#"+this.getCallDuration();
	}

	@Override
	public String toCSVHeader() {
		return "Type#PhoneNumber#Date#Duration";
	}

}
