package com.citi.winner21.ultils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
public class ReadColor {
	ArrayList<String> listmau=new ArrayList<String>();

	 private static final SimpleDateFormat sdfdatetimemm = new SimpleDateFormat("dd:MM:yyyy HH:mm");

private static Date addMinutesToDate(int minutes, Date beforeTime){
    final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
    long curTimeInMs = beforeTime.getTime();
    Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
    return afterAddingMins;
}
	public static String getname(ArrayList<Integer> l ) {
		
//		252-95-95 do
//		49-186-160  xanh 
//		62-51-90 ko mau
		String mau="ko biet";
		int max= l.get(0);
		
		if(max==62) {
			//System.out.println("ko mau");
			mau="ko mau";
			return mau;
		}
		 for (int i = 0; i <l.size()-1; i++) {
			if( max < l.get(i))
			{
				max=l.get(i);
			}
		}
		 if(max==l.get(0)) {
			//System.out.println("mau do"); 
			mau="mau do";
		 }else  if(max==l.get(1)) {
				//System.out.println("mau xanh "); 
				mau="mau xanh";
			 }
		 else  if(max==l.get(2)) {
				//System.out.println("mau xanh "); 
				mau="mau xanh";
			 }
		return mau;
			 
		 
		 
		
	}
	
	public static 	ArrayList<String>  read(String hinh,int dong) {
		//"hinh.png"
//      FileWriter writer = new FileWriter("pixel_values.txt");
      //Reading the image
		ArrayList<String> listm= new ArrayList<String>();
      File file= new File(hinh);
      BufferedImage img = null;
	try {
		img = ImageIO.read(file);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
      
     int k=0;
     k=35; 
     for (int i = 0; i < 25; i++) {
    	
    	 
    	 if(i==5) {
    		 //System.out.println("cách ----------------------");
    		k=250;
    	}
    	 if(i==10) {
    		 //System.out.println("cách ----------------------");
     		k=467;
     	}
    	 if(i==15) {
    		 //System.out.println("cách ----------------------");
      		k=685;
      	}
    	 if(i==20) {
    		 //System.out.println("cách ----------------------");
       		k=900;
       	}
     	 
    	
    	 int pixel = img.getRGB(k,dong);
    	// //System.out.println("vitri"+ k+"--"+dong2);
    	  //Creating a Color object from pixel value
         Color color = new Color(pixel, true);
         //Retrieving the R G B values
         int red = color.getRed();
         int green = color.getGreen();
         int blue = color.getBlue();
         ArrayList<Integer> l = new ArrayList<Integer>();
         l.add(red);
         l.add(green);
         l.add(blue);
     //    //System.out.println(red+"-"+green+"-"+blue);
        // System.out.print("ô "+i+" ");
      String a=getname(l);
      if(!a.contains("ko")) {
    	  if(a.equals("mau xanh")) {
    		  a="t";
    	  }else if(a.equals("mau do")) {
    		  a="g";
    	  }
         listm.add(a);
         }
    	 k+=31;
	}
     
      //System.out.println("ok nhe");
	return listm;
      
  
	}


public String gettimetrade() {
	 String time="";
	 try {
		 time= sdfdatetimemm.format(new Timestamp(System.currentTimeMillis()))+" :-- ";
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	return time;
}
	
public String getPrevtimetrade(int minus) {
	 String time="";
	 try {
		 time= sdfdatetimemm.format(new Timestamp(System.currentTimeMillis()+(-minus*60000)));
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	return time;
}

	public  ArrayList<String> danhso(){
		listmau.clear();
		ArrayList<String> listf= new ArrayList<String>();
		ArrayList<String> list1=read("hinh.png", 20);
		
		ArrayList<String> list2=read("hinh.png", 82);
		
	
//try {
//            String phut=	time.split(" ")[1].split(":")[1];
//            int p= Integer.parseInt(phut);
//            if(p%2==0||p==0) {
//            	
//            }
//} catch (Exception e) {
//	// TODO: handle exception
//}		
		for (int i = 0; i < list1.size(); i++) {
			try {
				listmau.add(list1.get(i));
				listmau.add(list2.get(i));
			} catch (Exception e) {
				continue;
			}
			
			
		}
		String time=getPrevtimetrade(listmau.size());
		Date d=addMinutesToDate(-1,new Date(System.currentTimeMillis()));
		int k=0;
		for (int i = listmau.size()-1; i >=0; i--) {
			String line=listmau.get(i)+" "+sdfdatetimemm.format(addMinutesToDate(-k, d));
			//System.out.println(line);
			listf.add(line);
			k++;
		}
		
		for (int i = listf.size()-1; i >=0; i--) {
			System.out.println(listf.get(i));
		}
		
		
		
		
		
		
		
		
		return listf;
		
		
		
		
	}
	


	
   public static void main(String args[])throws Exception {
//	  ReadColor r= new ReadColor();
//	  r.danhso();
   }
}