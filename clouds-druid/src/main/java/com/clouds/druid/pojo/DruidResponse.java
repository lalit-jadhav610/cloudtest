package com.clouds.druid.pojo;

//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
public class DruidResponse {

	private String timestamp;

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	private Object result;

	@Override
	public String toString() {
		return "{" + "timestamp=" + timestamp + ", result='" + result + '}';
	}
}