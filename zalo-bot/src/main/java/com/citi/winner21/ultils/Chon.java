package com.citi.winner21.ultils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor

@NoArgsConstructor
public class Chon implements Comparable<Chon>{
int no; 
int qty;

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
