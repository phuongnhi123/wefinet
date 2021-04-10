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
    boolean dangthua=false;
    boolean landau=true;
    String thualandau="";
    int count=0;
FollowModel flmodel;
WefinetModel wemodel;
String[] arrayBetT  =  {"T","T","T","T","T","T","T","T","T","T","T","T"};
String[] arrayBetG  =  {"G","G","G","G","G","G","G","G","G","G","G","G"};
int[] arrayPriceBet  = {1,2,4,8,16,32,64,128,256,512,1024,2048};
int countMuc=3;
@Autowired
TotalAmountService tmservice;

 TotalAmountModel tmmodel;

 TotalAmountModel tmmodeltemp;
 double buget;

//
//WefinetModel wemodelvip;
//FollowModel flmodelvip;
@Autowired
TotalAmountVipService tmvipservice;
//
// TotalAmountModel tmmodelvip;
//
// TotalAmountModel tmmodeltempvip;
// double bugetvip;

  //  @PreDestroy
    public void destroy() {
        
    }
    @PostConstruct
    public void init() { 
    	buget=130;
//    	bugetvip=15;
    	flmodel= new FollowModel();
    	 wemodel=new WefinetModel();
    	 tmmodeltemp= new TotalAmountModel();
    	 tmmodel= new TotalAmountModel();
//    	 flmodelvip= new FollowModel();
//    	 tmmodeltempvip= new TotalAmountModel();
//    	 tmmodelvip= new TotalAmountModel();
//    	 wemodelvip=new WefinetModel();
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
                	if(landau) {
                 		// nếu lân đầu kiểm tra đúng đúp thì gửi lệnh 
                	if(checkDupColor(list,countMuc)) {
                	  	  bet=(list.get(0).getType().equals("G")?"T":"G");
                	  	  String idfl=getnetxtimetrade(0);
                	     flmodel.setId("command");
            			 flmodel.setTime(idfl); 
                         flmodel.setType(bet);
                         betBefore=bet+"";
                         flmodel.setPrice(arrayPriceBet[count]+"");
                         try { 
                         	System.out.println("đẩy lệnh lên server"+flmodel.toString());
         					flsService.updateDoc(flmodel); 
         					 landau=false; 
         				} catch (ExecutionException | InterruptedException e1) {
         					// TODO Auto-generated catch block
         					e1.printStackTrace();
         					System.out.println("update lenh danh fail");
         				}
                	}
                		   
                	}else {
                		//nếu khong phải lần đầu thì kiểm tra kết quả
                	       wemodel.setId(idwe1);
                           wemodel.setTime(idwe1);
                           wemodel.setType(betBefore); 
                           wemodel.setLastUpdate(new Date());
                           wemodel.setPrice(arrayPriceBet[count]+"");
                		
                		if(ketqua(betBefore,list.get(0).getType())) {
                			// thang
                			 wemodel.setAction("THANG");
                			 try {
                				 System.out.println("update thang"+wemodel.toString());
          						prService.updateDoc(wemodel);
          						 capnhatLaiLo(wemodel);
          					} catch (ExecutionException | InterruptedException e1) {
          						// TODO Auto-generated catch block
          						e1.printStackTrace();
          						System.out.println("update kq fail");
          					}
                			count=0;
                			landau=true;
                		}else {
                			//thua
                			 wemodel.setAction("THUA");
                			 try {
         						prService.updateDoc(wemodel);
         						 capnhatLaiLo(wemodel);
         					} catch (ExecutionException | InterruptedException e1) {
         						// TODO Auto-generated catch block
         						e1.printStackTrace();
         						System.out.println("update kq fail");
         					} 
                			count++;
                			//kiểm tra đúng đup muc thêm phát nữa
                		    // day lenh sau len server 
                			if((checkDupColor(list,countMuc))) {
                            String idfl=getnetxtimetrade(0); 
                   	        flmodel.setId("command");
               			    flmodel.setTime(idfl); 
                            flmodel.setType(bet);
                            betBefore=bet+"";
                            flmodel.setPrice(arrayPriceBet[count]+"");
                            try {
                            	
                            	System.out.println("đẩy lệnh lên server"+count+""+flmodel.toString());
            					flsService.updateDoc(flmodel);
                            	 
            				} catch (ExecutionException | InterruptedException e1) {
            					// TODO Auto-generated catch block
            					e1.printStackTrace();
            					System.out.println("update lenh danh fail");
            				}
                			}
                			
                		}
                    	
                	}
                 }
             
            };
    		 Firestore firestore = FirestoreClient.getFirestore();
    	        firestore.collection("wefinex_chart").orderBy("settledDateTime", Query.Direction.DESCENDING).limit(50).addSnapshotListener(eventListener);
              
    		
            
            

    }


	
    private boolean ketqua(String betBefore2, String type) {
		if(betBefore2.equals(type)) {
			return true;
		}
		return false;
	}
	private boolean checkDupColor(List<WefinexResult> list,int k) {
    	int count =1;
    
	  for (int i = 1; i < k; i++) {
		  if(list.get(0).getType().equals(list.get(i).getType())) {
			  count++;
		  }
		
	}  System.out.println("count dup"+count);
	  if(count==k) {
		  return true;
	  }
	
	return false;
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
