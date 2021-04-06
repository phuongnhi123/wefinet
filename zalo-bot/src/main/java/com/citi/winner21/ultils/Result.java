package com.citi.winner21.ultils;

public class Result {
	String gio; 
	String kq;
	 
	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Result(String gio, String kq) {
		super();
		this.gio = gio;
		this.kq = kq;
	}
	public String getGio() {
		return gio;
	}
	public void setGio(String gio) {
		this.gio = gio;
	}
	public String getKq() {
		return kq;
	}
	public void setKq(String kq) {
		this.kq = kq;
	}
	@Override
	public String toString() {
		return "Result [gio=" + gio + ", kq=" + kq + "]";
	}
	
}
