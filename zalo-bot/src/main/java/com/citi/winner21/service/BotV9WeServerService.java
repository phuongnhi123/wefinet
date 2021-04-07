package com.citi.winner21.service;

import java.io.IOException;
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
import com.citi.winner21.ultils.Lenh;
import com.citi.winner21.ultils.ManagerHistory;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

@DependsOn("FirebaseInitialization")
@Service
public class BotV9WeServerService {
    private static final Logger logger = Logger.getLogger(BotV9WeServerService.class.getName()); 
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
    int countMuc=3;
FollowModel flmodel;
WefinetModel wemodel;
String[] arrayBetT  =  {"G","T","T","G","G","T","T","G","G","T","T","G"};
String[] arrayBetG  =  {"T","G","G","T","T","G","G","T","T","G","G","T"};
int[] arrayPriceBet  = {1,2,4,8,16,32,64,128,256,512,1024,2048};
int countvipthua=0;
@Autowired
TotalAmountService tmservice;

 TotalAmountModel tmmodel;

 TotalAmountModel tmmodeltemp;
 double buget;

private int gttg =1; 

private ManagerHistory managerHistory;


WefinetModel wemodelvip;
FollowModel flmodelvip;
@Autowired
TotalAmountVipService tmvipservice;

 TotalAmountModel tmmodelvip;

 TotalAmountModel tmmodeltempvip;
 double bugetvip;
boolean thiTruongXau=true;
    @PreDestroy
    public void destroy() {
        
    }
    @PostConstruct
    public void init() { 
    	
    	managerHistory= new ManagerHistory();
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
               managerHistory.ghilogvip(list.toString()); 
                if(bet==""){
                    count= 0 ;
                }
                String idwe1=dateToStringPlus(new Date(list.get(0).getCreatedTime()),0);
                String idwe=dateToStringPlus(new Date(list.get(0).getSettledDateTime()),0);
                String timeCk=getnetxtimetrade(-1);
               managerHistory.ghilogvip("lenh cuoi cung"+idwe+"gio lệnh cuối"+timeCk+"--"+idwe1);
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
                     //update kq thua 
                     wemodel.setAction("THUA");
                   
                     try {
                    	 managerHistory.ghilogvip("đẩy lệnh thua" +wemodel.toString());
						prService.updateDoc(wemodel);
						 capnhatLaiLo(wemodel);
						 if(count==countMuc) {
//							 if(countMuc<8) {
//							 countMuc++;}else {
//							 countMuc--;
//							 }
							 countvipthua++;
							 wemodelvip.setAction(wemodel.getAction()); 
							 wemodelvip.setPrice(pricebetvip+""); 
							 prvipService.updateDoc(wemodelvip);
							 capnhatLaiLoVip(wemodelvip);
							managerHistory.ghilogvip("đẩy lệnh vip thua" +wemodelvip.toString());
							  if(countvipthua>3) {
									 countvipthua=0;
								   }
						 }
						 
						 
						 
						 
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						managerHistory.ghilogvip("update kq fail");
					} 
                      count++;
                    
                  }else  
                      {
                	   
                	  //update kq thang
                	  wemodel.setAction("THANG");
                	  //khảo sát lại thị trường
                	  
                	    managerHistory.resetlistsx();
                		try {
						managerHistory.readKqListens(list);
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						};
					 	managerHistory.genlenhtrade();					 	
					 	managerHistory.calculateRs();					 	
						managerHistory.countGapThep();

                	  
                	  try {
                		   if(!wemodel.getType().equals("")&&!thiTruongXau) {
  						prService.updateDoc(wemodel);
  						 capnhatLaiLo(wemodel);
                			   System.out.println("coun"+count);
                			   
							managerHistory.ghilogvip("đẩy lệnh thang" +wemodel.toString());
							 if(count==countMuc) {
//  							 if(countMuc>5) {
//  								 countMuc--;}else {
//  								 countMuc++;
//  								 }
								 countvipthua=0;
							 wemodelvip.setAction(wemodel.getAction()); 
							 wemodelvip.setPrice(pricebetvip+""); 
							managerHistory.ghilogvip("đẩy lệnh vip thang" +wemodelvip.toString());
							 prvipService.updateDoc(wemodelvip);
							 capnhatLaiLoVip(wemodelvip);
						 }
                		   
                		   }
  						
						 
  					} catch (Exception e1) {
  						// TODO Auto-generated catch block
  						e1.printStackTrace();
  						managerHistory.ghilogvip("update kq fail");
  					}
                                
                   count=0;    
                  }
                
                
                
                
               
	    			 Lenh l= managerHistory.getDubaotheothoigian();
	    			 boolean checkquagapthep=managerHistory.isCheckquagapthep();
	    			 if(!checkquagapthep&&thiTruongXau) {
		    				thiTruongXau=false;
		    				 return;
		    			 }
	    			 if(checkquagapthep&&count==0) {
	    				thiTruongXau=true;
	    				System.out.println("thị trường xấu vui long chờ");
	    				 return;
	    			 }
	    			
	    			 
	    			 
	    	    	managerHistory.ghilogvip("lenh"+l.toString());
	    	    	
	    	    	 int chon=l.getChon();
	    	    	 if(chon==1) {
	    	    		 gttg=l.getNo1();
	    	    	 }
	    	    	 if(chon==2) {
	    	    		 gttg=l.getNo2();
	    	    	 }
	    	    	 if(chon==3) {
	    	    		 gttg=l.getNo3();
	    	    	 }
	    	    	 if(chon==4) {
	    	    		 gttg=l.getNo4();
	    	    	 }
	    	    	
	    	    	 
						managerHistory.ghilogvip("chon"+chon+"no."+gttg);
					 
	    			 if(gttg==1||gttg==4) {
          	    	    	bet="G";
          	    	    }else {
          	    	    	bet="T";
          	    	    	
          	    	    }
	    			 
	    			 
                // day lenh sau len server
                String idfl=getnetxtimetrade(0);
   			     flmodel.setTime(idfl); 
                flmodel.setType(bet);
                betBefore=bet+"";
                flmodel.setPrice(arrayPriceBet[count]+"");
                try {
                	managerHistory.ghilogvip("đẩy lệnh lên server"+flmodel.toString());
					flsService.updateDoc(flmodel);
				 
					pricebetvip=arrayPriceBet[countvipthua];
					
					if(count==countMuc) { 
						flmodelvip.setId(flmodel.getId());
						flmodelvip.setPrice(pricebetvip+"");
						flmodelvip.setTime(flmodel.getTime());
						flmodelvip.setType(flmodel.getType()); 
						managerHistory.ghilogvip("đẩy lệnh vip lên server"+flmodelvip.toString());
						flvipsService.updateDoc(flmodelvip);
					}
					
					
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					managerHistory.ghilogvip("update lenh danh fail");
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
	
	managerHistory.ghilogvip("cập nhật lãi lỗ ok");
} catch (Exception e) {
	//
	e.printStackTrace();
	managerHistory.ghilogvip("cập nhật lãi lỗ fail");
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
	
	managerHistory.ghilogvip("cập nhật lãi lỗ vip ok");
} catch (Exception e) {
	//
	e.printStackTrace();
	managerHistory.ghilogvip("cập nhật lãi lỗ vip fail");
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
