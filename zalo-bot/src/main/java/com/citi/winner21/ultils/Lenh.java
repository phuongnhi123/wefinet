package com.citi.winner21.ultils;

public class Lenh {
String gio;
String lenh1;
String lenh2;
String lenh3;
String lenh4;
String trangthai1;
String trangthai2;
String trangthai3;
String trangthai4; 
int no1;
int no2;
int no3;
int no4;

int chon;
public Lenh() {
	super();
	// TODO Auto-generated constructor stub
}
  
  

public Lenh(String gio, String lenh1, String lenh2, String lenh3, String lenh4, String trangthai1, String trangthai2,
		String trangthai3, String trangthai4, int no1, int no2, int no3, int no4) {
	super();
	this.gio = gio;
	this.lenh1 = lenh1;
	this.lenh2 = lenh2;
	this.lenh3 = lenh3;
	this.lenh4 = lenh4;
	this.trangthai1 = trangthai1;
	this.trangthai2 = trangthai2;
	this.trangthai3 = trangthai3;
	this.trangthai4 = trangthai4;
	this.no1 = no1;
	this.no2 = no2;
	this.no3 = no3;
	this.no4 = no4;
}



public int getChon() {
	return chon;
}



public void setChon(int chon) {
	this.chon = chon;
}



public int getNo1() {
	return no1;
}



public void setNo1(int no1) {
	this.no1 = no1;
}



public int getNo2() {
	return no2;
}



public void setNo2(int no2) {
	this.no2 = no2;
}



public int getNo3() {
	return no3;
}



public void setNo3(int no3) {
	this.no3 = no3;
}



public int getNo4() {
	return no4;
}



public void setNo4(int no4) {
	this.no4 = no4;
}



public String getTrangthai1() {
	return trangthai1;
}

public void setTrangthai1(String trangthai1) {
	this.trangthai1 = trangthai1;
}

public String getTrangthai2() {
	return trangthai2;
}

public void setTrangthai2(String trangthai2) {
	this.trangthai2 = trangthai2;
}

public String getTrangthai3() {
	return trangthai3;
}

public void setTrangthai3(String trangthai3) {
	this.trangthai3 = trangthai3;
}

public String getTrangthai4() {
	return trangthai4;
}

public void setTrangthai4(String trangthai4) {
	this.trangthai4 = trangthai4;
}
 

public String getGio() {
	return gio;
}
public void setGio(String gio) {
	this.gio = gio;
}
 
 

public String getLenh1() {
	return lenh1;
}

public void setLenh1(String lenh1) {
	this.lenh1 = lenh1;
}

public String getLenh2() {
	return lenh2;
}

public void setLenh2(String lenh2) {
	this.lenh2 = lenh2;
}

public String getLenh3() {
	return lenh3;
}

public void setLenh3(String lenh3) {
	this.lenh3 = lenh3;
}

public String getLenh4() {
	return lenh4;
}

public void setLenh4(String lenh4) {
	this.lenh4 = lenh4;
}



@Override
public String toString() {
	return "Lenh  " + gio + "	" + lenh1 + "	" + lenh2 + "	" + lenh3 + "	" + lenh4
			+ ", trangthai1=" + trangthai1 + ", trangthai2=" + trangthai2 + ", trangthai3=" + trangthai3+ ", trangthai4=" + trangthai4
			+ ", no1=" + no1 + ", no2=" + no2 + ", no3=" + no3 + ", no4=" + no4
			+ ", chon=" + chon + "]";
}
			  
 


 
 
 

}
