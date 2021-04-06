package com.citi.winner21.ultils;

public class Chon implements Comparable<Chon>{
int no;
int qty;

public Chon() {
	super();
	// TODO Auto-generated constructor stub
}
public Chon(int no, int qty) {
	super();
	this.no = no;
	this.qty = qty;
}
public int getNo() {
	return no;
}
public void setNo(int no) {
	this.no = no;
}
public int getQty() {
	return qty;
}
public void setQty(int qty) {
	this.qty = qty;
}
@Override
public int compareTo(Chon o) {
	if(this.getQty()>o.getQty()) {
		return  1;
		
	}else if (this.getQty()==o.getQty()) {
		return 0;
	}else {
		return -1;
	}
	 
}

}
