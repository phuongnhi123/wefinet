package com.citi.winner21.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor

@NoArgsConstructor
public class WefinexResult {
	private  double closePrice  ; 
	
	
	private double  highPrice ;  

	private double  lowPrice ;  

	private double openPrice ;  

	private Long settledDateTime;  
	private Long 	createdTime;
	private double status;  
	private String type;  
}
