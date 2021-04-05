package com.citi.winner21.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.citi.winner21.model.FollowModel;
import com.citi.winner21.model.TotalAmountModel;
import com.citi.winner21.model.WefinetModel;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

@DependsOn("FirebaseInitialization")
@Service
public class BotWeServerService {
    private static final Logger logger = Logger.getLogger(BotWeServerService.class.getName()); 
//    @Autowired
//    ZaloService zaloService; 
    @Autowired
    FolowService flsService;
    @Autowired
    ProductService prService;
    @Autowired
    FolowVipService flvipsService;
    @Autowired
    ProductVipService prvipService;
    private static final SimpleDateFormat sdfdatetimemm = new SimpleDateFormat("dd:MM:yyyy HH:mm");
    String bet="";
    String betBefore="";
    int pricebetvip=1;
    String thualandau="G";
    int count=0;
FollowModel flmodel;
WefinetModel wemodel;
String[] arrayBetG  =  {"G","T","T","G","G","T","T","G","G","T","T","G"};
String[] arrayBetT  =  {"T","G","G","T","T","G","G","T","T","G","G","T"};
int[] arrayPriceBet  = {1,2,4,8,16,32,64,128,256,512,1024,2048};

@Autowired
TotalAmountService tmservice;

 TotalAmountModel tmmodel;

 TotalAmountModel tmmodeltemp;
 double buget;


WefinetModel wemodelvip;
FollowModel flmodelvip;
@Autowired
TotalAmountVipService tmvipservice;

 TotalAmountModel tmmodelvip;

 TotalAmountModel tmmodeltempvip;
 double bugetvip;

    @PreDestroy
    public void destroy() {
        
    }
    @PostConstruct
    public void init() { 
    	buget=130;
    	bugetvip=15;
    	flmodel= new FollowModel();
    	 wemodel=new WefinetModel();
    	 tmmodeltemp= new TotalAmountModel();
    	 tmmodel= new TotalAmountModel();
    	 flmodelvip= new FollowModel();
    	 tmmodeltempvip= new TotalAmountModel();
    	 tmmodelvip= new TotalAmountModel();
    	 wemodelvip=new WefinetModel();
    		EventListener<QuerySnapshot> eventListener = (documentSnapshot, e) -> { 
    			
    			 List<WefinexResult> list= documentSnapshot.toObjects(WefinexResult.class); 
    			 flmodel.setId("command"); 
                System.out.println(list.toString()); 
                if(bet==""){
                    count= 0 ;
                }
                String idwe1=dateToStringPlus(new Date(list.get(0).getCreatedTime()),0);
                String idwe=dateToStringPlus(new Date(list.get(0).getSettledDateTime()),0);
                String timeCk=getnetxtimetrade(-1);
                System.out.println("lenh cuoi cung"+idwe+"gio lệnh cuối"+timeCk+"--"+idwe1);
                if(idwe1.equals(timeCk)) {
                wemodel.setId(idwe1);
                wemodel.setTime(idwe1);
                wemodel.setType(betBefore); 
                wemodel.setLastUpdate(new Date());
                wemodel.setPrice(arrayPriceBet[count]+"");
                
                //set betvip 
				 wemodelvip.setId(wemodel.getId());
				 wemodelvip.setLastUpdate(wemodel.getLastUpdate());				
				 wemodelvip.setTime(wemodel.getTime());
				 wemodelvip.setType(wemodel.getType());
                
                
                if(!bet.equals("")&&!list.get(0).getType().equals(betBefore)){ 
                     System.out.println("lenh nay thua bet truoc do "+betBefore+ "lan thua" +count); 
                      //nếu bet thua đánh xen ke giam tang tang giam  Gấp thếp
                     //update kq thua 
                     wemodel.setAction("THUA");
                   
                     try {
                    	 
						prService.updateDoc(wemodel);
						 capnhatLaiLo(wemodel);
						 if(count>4&&count<9) {
							 wemodelvip.setAction(wemodel.getAction()); 
							 wemodelvip.setPrice(pricebetvip+""); 
							 prvipService.updateDoc(wemodelvip);
							 capnhatLaiLoVip(wemodelvip);
						 }
						 
						 
						 
						 
					} catch (ExecutionException | InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.out.println("update kq fail");
					}
                    
                     if(count==0) {
                    	 thualandau=bet+"";
                    	 System.out.println("thua lan đầu");
                    	   bet=list.get(0).getType();
                     }else { 
                         if(thualandau.equals("G")){
                         bet=arrayBetG[count] ;
                         }else if(thualandau.equals("T")){
                         bet=arrayBetT[count] ;
                          }
                     }
                  
                      count++;
                    
                  }else{
                	  
                	  
                	  if(bet.equals("")) {
                		   bet=list.get(0).getType();
                	  }else {
                       // nếu bet trước thắng thì đẩy lệnh bet lên  
                	  //update kq thang
                	  wemodel.setAction("THANG");
                	  try {
  						prService.updateDoc(wemodel);
  						 capnhatLaiLo(wemodel);
  						 if(count>4&&count<9) {
							 wemodelvip.setAction(wemodel.getAction()); 
							 wemodelvip.setPrice(pricebetvip+""); 
							 prvipService.updateDoc(wemodelvip);
							 capnhatLaiLoVip(wemodelvip);
						 }
						 
  					} catch (ExecutionException | InterruptedException e1) {
  						// TODO Auto-generated catch block
  						e1.printStackTrace();
  						System.out.println("update kq fail");
  					}
                      
                   System.out.println("lenh nay thang bet truoc do "+bet+ "lan thua" +count);            
                   count=0;   
                   bet=list.get(0).getType();
                	  }
                  }
                
                // day lenh sau len server
                String idfl=getnetxtimetrade(0);
   			     flmodel.setTime(idfl); 
                flmodel.setType(bet);
                betBefore=bet+"";
                flmodel.setPrice(arrayPriceBet[count]+"");
                try {
                	System.out.println("đẩy lệnh lên server"+flmodel.toString());
					flsService.updateDoc(flmodel);
				 
					switch (count) {
					case 5:
						pricebetvip=arrayPriceBet[0];
						break;
					case 6:
						pricebetvip=arrayPriceBet[1];
						break;
					case 7:
						pricebetvip=arrayPriceBet[2];
						break;
					case 8:
						pricebetvip=arrayPriceBet[3];
						break;
					default:
						pricebetvip=0;
						break;
					}
					if(count>4&&count<9) { 
						flmodelvip.setId(flmodel.getId());
						flmodelvip.setPrice(pricebetvip+"");
						flmodelvip.setTime(flmodel.getTime());
						flmodelvip.setType(flmodel.getType()); 
						System.out.println("đẩy lệnh vip lên server"+flmodelvip.toString());
						flvipsService.updateDoc(flmodelvip);
					}
					
					
					
				} catch (ExecutionException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("update lenh danh fail");
				}
                 if(count>11) {
                    count=0;
                 }
                    
                }
             
            };
    		 Firestore firestore = FirestoreClient.getFirestore();
    	        firestore.collection("wefinex_chart").orderBy("settledDateTime", Query.Direction.DESCENDING).limit(50).addSnapshotListener(eventListener);
              
    		
            
            

    }


	
    private void capnhatLaiLo(WefinetModel wemodel2) throws ExecutionException, InterruptedException {
	try {

	String id=wemodel2.getId().split(" ")[0];
	tmmodeltemp=tmservice.getDocId(id);
	tmmodel.setId(id);
	tmmodel.setBudget(buget);
	tmmodel.setLastUpdate(new Date());
	double profit=0;
    	if(wemodel2.getAction().equals("THANG")) {
    		//thang
	 profit=Double.parseDouble(wemodel2.getPrice().trim())*0.95;  
	
	}else {
		//thua
	 profit= Double.parseDouble(wemodel2.getPrice().trim())*-1;  
	}
	if(tmmodeltemp!=null) {
		//update	
        tmmodel.setProfit(profit+tmmodeltemp.getProfit());	
        tmmodel.setTotalAmount((buget+profit+tmmodeltemp.getProfit())+"");
        tmservice.updateDoc(tmmodel);
	}else {	 
        tmmodel.setProfit(profit);	
        tmmodel.setTotalAmount((buget+profit)+""); 
        tmservice.saveDoc(tmmodel);
	} 
	
	System.out.println("cập nhật lãi lỗ ok");
} catch (Exception e) {
	//
	e.printStackTrace();
	System.out.println("cập nhật lãi lỗ fail");
}	
	
		
	}
    
    

	
    private void capnhatLaiLoVip(WefinetModel wemodel2) throws ExecutionException, InterruptedException {
	try {

	String id=wemodel2.getId().split(" ")[0];
	tmmodeltempvip=tmvipservice.getDocId(id);
	tmmodelvip.setId(id);
	tmmodelvip.setBudget(bugetvip);
	tmmodelvip.setLastUpdate(new Date());
	double profit=0;
    	if(wemodel2.getAction().equals("THANG")) {
    		//thang
	 profit=Double.parseDouble(wemodel2.getPrice().trim())*0.95;  
	
	}else {
		//thua
	 profit= Double.parseDouble(wemodel2.getPrice().trim())*-1;  
	}
	if(tmmodeltempvip!=null) {
		//update	
        tmmodelvip.setProfit(profit+tmmodeltempvip.getProfit());	
        tmmodelvip.setTotalAmount((bugetvip+profit+tmmodeltempvip.getProfit())+"");
        tmvipservice.updateDoc(tmmodelvip);
	}else {	 
        tmmodelvip.setProfit(profit);	
        tmmodelvip.setTotalAmount((bugetvip+profit)+""); 
        tmvipservice.saveDoc(tmmodelvip);
	} 
	
	System.out.println("cập nhật lãi lỗ vip ok");
} catch (Exception e) {
	//
	e.printStackTrace();
	System.out.println("cập nhật lãi lỗ vip fail");
}	
	
		
	}
        
public String getnetxtimetrade(int minus) {
	 String time="";
	 try {
		 time= sdfdatetimemm.format(new Timestamp(System.currentTimeMillis()+(minus*60000)));
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	return time;
}
public String dateToStringPlus  (Date date,int minus) {
	 String time="";
	 try {
		 time= sdfdatetimemm.format(new Timestamp(date.getTime()+(minus*60000)));
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	return time;
}

public String dateToString(Date date) {
	 String time="";
	 try {
		 time= sdfdatetimemm.format(date);
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	return time;
}
    
}
