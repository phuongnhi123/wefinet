package com.citi.winner21.ultils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.codec.digest.DigestUtils;

import com.citi.winner21.service.WefinexResult;

import net.bytebuddy.dynamic.scaffold.InstrumentedType.Frozen;
public class ManagerHistory   {
 PrintWriter writer1;
 PrintWriter writer2;
 PrintWriter writervip;
 ArrayList<String> listtradewin;
 ArrayList<String> listtradelose;
 ArrayList<Lenh> listlenhtrade  ;  
 ArrayList<String> duptime;
 boolean checkquagapthep=false;
 ArrayList<Result> listkq ; 
 ArrayList<Result> listkqtrade ; 
 PrintWriter writer3;
 String pathlic ="";
 String licence="";
 String key="";
 int chon=1;
 ArrayList<Chon> listsx;
 ArrayList<Chon> listGapThep;
 ArrayList<Integer> list1;
 ArrayList<Integer> list2;
 ArrayList<Integer> list3;
 ArrayList<Integer> list4;
 int gapmax=4;
 
 
 
 private static final SimpleDateFormat sdflog = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
 private static final SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
 private static final SimpleDateFormat sdfdatetimemm = new SimpleDateFormat("dd:MM:yyyy HH:mm");
 private static final SimpleDateFormat sdfdate = new SimpleDateFormat("dd_MM_yyyy");
 private static final SimpleDateFormat sdfdate1 = new SimpleDateFormat("yyyy-MM-dd");
public ManagerHistory() {
	
	this.listtradewin= new ArrayList<String>();
	this.listtradelose=new ArrayList<String>(); 
	
	this.listlenhtrade =new ArrayList<Lenh>();  
	this.listkq= new ArrayList<Result>();
	this.listsx= new ArrayList<Chon>(); 
	this.listGapThep= new ArrayList<Chon>(); 
	
	this.listkqtrade= new ArrayList<Result>();
	this.list1= new ArrayList<Integer>() ;
	this.list2= new ArrayList<Integer>() ;
	this.list3= new ArrayList<Integer>() ;
	this.list4= new ArrayList<Integer>() ;
	duptime=new ArrayList<String>(); 
    try {
    	 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    	 String tenTmp= "logTradeApp/";
    	 String tenTm=tenTmp+sdfdate.format(timestamp);
    	 
    	 
    	 File thumuc=new File(tenTm);
     	if(!thumuc.exists()) {
     		thumuc.mkdirs();
     	}
    	File log=new File(tenTm+"/log"+sdf.format(timestamp)+".txt");
    	if(!log.exists()) {
    		log.createNewFile();
    	}
    		 
        	File logDetail=new File(tenTm+"/logDetail"+sdf.format(timestamp)+".txt");
        	if(!logDetail.exists()) {
        		logDetail.createNewFile();
    	}
        	 
       	    File thumuckq=new File("KetQua");
        	File kqrs=new File(thumuckq.getAbsoluteFile()+"/kqrs.txt");
        	if(!kqrs.exists()) {
        		System.out.println("chua co kqrs");
        		kqrs.createNewFile();
    	}
    	  
		writer1 = new PrintWriter(log, "UTF-8");
		writer2 = new PrintWriter(logDetail, "UTF-8");
		writer3 = new PrintWriter(new FileWriter(thumuckq.getAbsoluteFile()+"/kqrs.txt",true));
		
	
		
    } catch (IOException e) {
	
	}
//    
   
}
public static String getname(ArrayList<Integer> l ) {
	
//	252-95-95 do
//	49-186-160  xanh 
//	62-51-90 ko mau
	
	int max= l.get(0);
	String mau="";
	if(max==62) {
		System.out.println("ko mau");
		return "ko mau";
	}
	 for (int i = 0; i <l.size()-1; i++) {
		if( max < l.get(i))
		{
			max=l.get(i);
		}
	}
	 if(max==l.get(0)) {
		System.out.println("mau do"); 
		return "mau do";
	 }else  if(max==l.get(1)) {
			System.out.println("mau xanh ");
			return "mau xanh";
		 }
	 else  if(max==l.get(2)) {
			System.out.println("mau xanh "); 
			return "mau xanh";
		 }
	return mau;
		 
	 
	 
	
}

public static void readColor(int dong) throws IOException {
	 File file= new File("hinh.png");
     BufferedImage img = ImageIO.read(file);

//     int dong2=50;
//     int dong3=115;
      
    int k=0;
    k=35; 
    for (int i = 0; i < 25; i++) {
   	
   	 
   	 if(i==5) {
   		 System.out.println("cách ----------------------");
   		k=250;
   	}
   	 if(i==10) {
   		 System.out.println("cách ----------------------");
    		k=467;
    	}
   	 if(i==15) {
   		 System.out.println("cách ----------------------");
     		k=685;
     	}
   	 if(i==20) {
   		 System.out.println("cách ----------------------");
      		k=900;
      	}
    	 
   	
   	 int pixel = img.getRGB(k,dong);
   	// System.out.println("vitri"+ k+"--"+dong2);
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
    //    System.out.println(red+"-"+green+"-"+blue);
        System.out.print("ô "+i+" ");
        getname(l);
   	 k+=31;
	}
    
     System.out.println("ok nhe");
  
}


public void writeRs(Result rs) {
	String []dt=rs.getGio().split(" ");
	
	writer3.println(rs.getKq()+"\t"+dt[0].trim()+"\t"+dt[1].trim().replace(":", "--"));
	writer3.flush(); 
}
public void resetlistsx() {
	listkq.clear();
	listlenhtrade.clear();
	listsx.clear();
}

public void calculateRs() {
System.out.println("listlenhtrade"+listlenhtrade.size());

System.out.println(listlenhtrade.get(0).toString());
System.out.println(listkq.get(0).toString());
System.out.println(listkq.get(listkq.size()-1).toString());
int count1=0;
	int count2=0;
	int count3=0;
	int count4=0;
	int size= listkq.size();  
	  int count=0;
	for (int i = 0; i < size; i++) {
		Lenh l= listlenhtrade.get(i);
		
		  int k=i-count; 
		Result rs=listkq.get(k); 
		if(l.getGio().equals(rs.getGio()) ) {
			if(l.getLenh1().equals(rs.getKq())) {
				listlenhtrade.get(i).setTrangthai1("TRUE");
				count1++;
			}else {
				listlenhtrade.get(i).setTrangthai1("FALSE");
			}
			
			
			
			if(l.getLenh2().equals(rs.getKq())) {
				listlenhtrade.get(i).setTrangthai2("TRUE");
				count2++;
			}else {
				listlenhtrade.get(i).setTrangthai2("FALSE");
			}
			
			
			if(l.getLenh3().equals(rs.getKq())) {
				listlenhtrade.get(i).setTrangthai3("TRUE");
				count3++;
			}else {
				listlenhtrade.get(i).setTrangthai3("FALSE");
			}
			
			
			if(l.getLenh4().equals(rs.getKq())) {
				listlenhtrade.get(i).setTrangthai4("TRUE");
				count4++;
			} else {
				listlenhtrade.get(i).setTrangthai4("FALSE");
			}
		}else { 
			 count++;
		}
		
	}
	 
	
	listsx.add(new Chon(1,count1));
	listsx.add(new Chon(2,count2));
	listsx.add(new Chon(3,count3));
	listsx.add(new Chon(4,count4));
	
	Collections.sort(listsx);
	System.out.println(listsx.get(0).getQty()+"chon"+listsx.get(0).getNo()); 
	System.out.println(listsx.get(1).getQty()+"--"+listsx.get(1).getNo());
	System.out.println(listsx.get(2).getQty()+"--"+listsx.get(2).getNo());
	System.out.println(listsx.get(3).getQty()+"--"+listsx.get(3).getNo());
	chon=listsx.get(0).getNo();
}
private int solangap(ArrayList<Integer> list) { 
	int min=0;
	if(list.size()>0) {
		Collections.sort(list);
		min=list.get(list.size()-1); 
	}
	list.clear();
	
	return min;
}
public void countGapThep() {
	int countLose1=0;
	int countLose2=0;
	int countLose3=0;
	int countLose4=0;
 
	 
	for (int i = 0; i < listlenhtrade.size(); i++) {
		Lenh l= listlenhtrade.get(i);
	if (l.getTrangthai1()!=null&&l.getTrangthai1().equals("FALSE")) {	
		countLose1++;
		}else {
		countLose1=0;
		}
	//add max 1
	
	list1.add(countLose1);
	
	
	if (l.getTrangthai2()!=null&&l.getTrangthai2().equals("FALSE")) {	
		countLose2++;
		}else {
		countLose2=0;
		}
	//add max 2
	list2.add(countLose2);
	
	
	if (l.getTrangthai3()!=null&&l.getTrangthai3().equals("FALSE")) {	
		countLose3++;
		}else {
		countLose3=0;
		}
	//add max 3
	list3.add(countLose3);
	
	
	if (l.getTrangthai4()!=null&&l.getTrangthai4().equals("FALSE")) {	
		countLose4++;
		}else {
		countLose4=0;
		}
	//add max 4
	list4.add(countLose4);
	
	}
	listGapThep.clear();
	listGapThep.add(new Chon(1, solangap(list1)));
	listGapThep.add(new Chon(2, solangap(list2)));
	listGapThep.add(new Chon(3, solangap(list3)));
	listGapThep.add(new Chon(4, solangap(list4)));
	
	Collections.sort(listGapThep);
	int sz=listGapThep.size();
	System.out.println(sz);
	System.out.println("gap max "+listGapThep.get(0).getQty()+"chon"+listGapThep.get(0).getNo()); 
	System.out.println("gap max "+listGapThep.get(1).getQty()+"--"+listGapThep.get(1).getNo());
	System.out.println("gap max "+listGapThep.get(2).getQty()+"--"+listGapThep.get(2).getNo());
	System.out.println("gap max "+listGapThep.get(3).getQty()+"--"+listGapThep.get(3).getNo());
	chon=listGapThep.get(0).getNo();
	gapmax=listGapThep.get(0).getQty();
	int gapthep=4;
	//||listGapThep.get(1).getQty()>gapthep||listGapThep.get(2).getQty()>gapthep||listGapThep.get(3).getQty()>gapthep
	if(listGapThep.get(0).getQty()>gapthep) {
		checkquagapthep=true;
	}
	else {
		checkquagapthep=false;
	}
	
	
}
 

public int getGapmax() {
	return gapmax;
}
public void setGapmax(int gapmax) {
	this.gapmax = gapmax;
}
public boolean isCheckquagapthep() {
	return checkquagapthep;
}
public void setCheckquagapthep(boolean checkquagapthep) {
	this.checkquagapthep = checkquagapthep;
}
public void readKq() throws IOException {

	  File f= new File("KetQua");
	   if(!f.exists()) {
		   System.out.println("chua tao file nay");
		   f.mkdirs();
	   }
	   File fac= new File(f.getAbsoluteFile()+"/kq.txt");
	 if(fac.exists()) {
	  BufferedReader in= new BufferedReader(
			  new InputStreamReader(  new FileInputStream(fac), "UTF-8") );
	  String str;
      while ((str = in.readLine()) != null) {
    	 // System.out.println(str);
    	  String[] line= str.split("\t");
    	  if(line.length>1) {
    		  line.toString();
    	 String gio =line[2].replace("--", ":");
    	  String ngay=line[1];
    	  String kq=line[0];
    	  listkq.add(new Result(ngay+" "+gio,kq));
    	  }
      }
	  
	  in.close(); 
	 }else {
		 System.out.println("chua tao file nay");
		 fac.createNewFile();
	}
	 
	
	
}


public void readKqTuImage() throws IOException {

	 ReadColor r= new ReadColor();
	  ArrayList<String >ll=r.danhso();
	  resetlistsx();
	  String gio ="";
	  String ngay="";
	  String kq="";
	for (int i = ll.size()-1; i >=0; i--) {
		String llll=ll.get(i);
		System.out.println("te"+llll);
		 String[] line=llll.split(" ");
		  gio =line[2];
   	   ngay=line[1];
   	   kq=line[0];
		
		 listkq.add(new Result(ngay+" "+gio,kq));
		
		
	}
}


public void readKqListens(List<WefinexResult> list) throws IOException {
//	 
//	System.out.println(list.size()); 
//	for (int i = 0; i < list.size(); i++) {
//		System.out.println(list.get(i).getCreatedTime()+"--"+dateToString(new Date(list.get(i).getCreatedTime())));
//		
//	}
	if(list.size()>0) {
	for (int i = list.size()-1; i >=0; i--) {
		addkq(listkq,new Result(dateToString(new Date(list.get(i).getCreatedTime())), list.get(i).getType()));
		
		 
	} }
	duptime.clear();
//	for (Result r : listkq) {
//		System.out.println(r.toString());
//	}
//	for (WefinexResult wefinexResult : list) {
//		 listkq.add(new Result(dateToString(new Date(wefinexResult.getCreatedTime())), wefinexResult.getType())); 
////			
//	}
}

private void addkq(ArrayList<Result> listkq2, Result result) {
	if(listkq.size()==0||!duptime.contains(result.getGio())){
		 listkq.add(result); 
		 duptime.add(result.getGio());
	}
	
	
}
public static String dateToStringPlus  (Date date,int minus) {
	 String time="";
	 try {
		 time= sdfdatetimemm.format(new Timestamp(date.getTime()+(minus*60000)));
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	return time;
}

public static String dateToString(Date date) {
	 String time="";
	 try {
		 time= sdfdatetimemm.format(date);
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	return time;
}
public void readKqAuto() throws IOException {

	  File f= new File("KetQua");
	   if(!f.exists()) {
		   System.out.println("chua tao file nay");
		   f.mkdirs();
	   }
	   File fac= new File(f.getAbsoluteFile()+"/kqrs.txt");
	 if(fac.exists()) {
	  BufferedReader in= new BufferedReader(
			  new InputStreamReader(  new FileInputStream(fac), "UTF-8") );
	  String str;
    while ((str = in.readLine()) != null) {
  	 // System.out.println(str);
  	  String[] line= str.split("\t");
  	  if(line.length>1) {
  		  line.toString();
  	 String gio =line[2].replace("--", ":");
  	  String ngay=line[1];
  	  String kq=line[0];
  	  listkq.add(new Result(ngay+" "+gio,kq));
  	  }
    }
	  
	  in.close(); 
	 }else {
		 System.out.println("chua tao file nay");
		 fac.createNewFile();
	}
	 
	
	
}
 
 

public ArrayList<String> splitkey(String key){
	ArrayList<String> genkey = new ArrayList<String>();
	
	int size= key.length()/8;
	for (int i = 0; i < size; i++) {
		  String k1= key.substring(i*size*2,8*(i+1));
		  genkey.add(k1);
	} 
//	2F534D8C-45AC2E73-C83AF4F2-C91C89B3 
	return genkey;
	
}
public String  genkey(String key,String key1){
	 ArrayList< String > k= splitkey(key);
	 ArrayList< String > k1= splitkey(key1);
     String genkey="";
     try {
		
	
     genkey=k.get(0) +"--"+k1.get(0) +"--"+k.get(2)+"--"+k1.get(2)+"--"+k.get(1)+"--"+k1.get(1)+"--"+k.get(3)+"--"+k1.get(3);
     } catch (Exception e) {
 		// TODO: handle exception
 	}
//     0 4 2 6
//     1 5 3 7 
     
	return genkey;
	
}
public String getlic(String genkey ){
	 

	 String  genlic="";
    String []kk=(String[]) genkey.split("--");
try {
	
  genlic=kk[1]+kk[5]+kk[3]+kk[7]; 
//    1 5 3 7 
} catch (Exception e) {
	// TODO: handle exception
}
	return genlic;
	
}
public String getkey(String genkey ){
	 
	 
    String []kk=  genkey.split("--");
  
  String  genk=kk[0] +kk[4]+kk[2]+kk[6];
//    0 4 2 6 
    
	return genk;
	
}

public String readLicence() throws IOException {
       
	try {
		
	if(pathlic.equals("")) {
	  File f= new File("Licence");
	   if(!f.exists()) {
		   System.out.println("chua tao file nay");
		 return "";
	   }
	   File fac= new File(f.getAbsoluteFile()+"/licence.nhat");
	 if(fac.exists()) {
	  DataInputStream in= new DataInputStream(new FileInputStream(fac));
	  in.readInt();
	  in.readDouble();
	  in.readLong();;
	  in.readUTF( );
	  in.readUTF( );
	  in.readLong();
	  in.readDouble();
	  in.readInt(); 
	  String lic= in.readUTF();
	  licence= getlic(lic); 
	   key= getkey(lic); 
	  in.close(); 
	 }else {
		 System.out.println("chua tao file nay");
		 return "";
	}
	 
	
	}else {
		 File fac= new File(pathlic);
		 if(fac.exists()) {
		  DataInputStream in= new DataInputStream(new FileInputStream(fac));
		  in.readInt();
		  in.readDouble();
		  in.readLong();;
		  in.readUTF( );
		  in.readUTF( );
		  in.readLong();
		  in.readDouble();
		  in.readInt(); 
		  String lic= in.readUTF();
		  licence= getlic(lic); 
		   key= getkey(lic); 
		 in.close(); 
		 }
		 
	}
	} catch (Exception e) {
		System.out.println("loi file  ");
		// TODO: handle exception
		 return "";
		
		 
	}
	return licence;
	 
	   
	}


 
 

public static String addDay(String date,int day) {
    return LocalDate
      .parse(date)
      .plusDays(day)
      .toString();
}

public String currentTime() {
	String cr="";
	try { 
	 cr= sdflog.format(new Timestamp(System.currentTimeMillis()))+" -:- ";
	} catch (Exception e) {
		// TODO: handle exception
	}
	return cr;
}


public void ghilog(String log) { 
	writer1.append(currentTime()+log+"\n");
	writer1.flush();
	removeLenhWin();
 }
public void ghilogconsole(String log) { 
	writer2.append(currentTime()+log+"\n");
	writer2.flush(); 
 }
public void ghilogvip(String log) { 
	System.out.println(log);
	try {
	
	 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	 String tenTmp= "logVip/";
	 String tenTm=tenTmp+sdfdate.format(timestamp); 
	 File thumuc=new File(tenTm);
 	if(!thumuc.exists()) {
 		thumuc.mkdirs();
 	}
	File flog=new File(tenTm+"/log"+sdf.format(timestamp)+".txt");
	if(!flog.exists()) {
		flog.createNewFile();
	} 
	writervip = new PrintWriter(new FileWriter(flog,true)); 
	writervip.append(log+"\n");
	writervip.flush(); 
	
	} catch (Exception e) {
		// TODO: handle exception
	}
 }


public void dongfile() { 
	if(writer1!=null) {
	writer1.close();}
 }
public void addlistWin(String lenh) {
	this.listtradewin.add(lenh);
}
public void removeLenhWin() {
	int size=this.listtradewin.size();
	int solenhCanXoa=0;
	if(size>20) {
		solenhCanXoa=size-20;
	}
	for (int i = 0; i < solenhCanXoa; i++) {
		this.listtradewin.remove(0);
	}
}
public void addlistLose(String lenh) {
	this.listtradelose.add(lenh);
}
public void removeLenhLose() {
	int size=this.listtradelose.size();
	int solenhCanXoa=0;
	if(size>20) {
		solenhCanXoa=size-20;
	}
	for (int i = 0; i < solenhCanXoa; i++) {
		this.listtradelose.remove(0);
	}
}

public void inwin() {
	System.out.println(listtradewin.size());
	for (String string : listtradewin) {
		System.out.println(string);
	}
}

public void inlose() {
	System.out.println(listtradelose.size());
	for (String string : listtradelose) {
		System.out.println(string);
	}
}
 public int qtyWinGannhat(int solanGanNhat, String type) {
	 int count =0;
	 int limit=this.listtradewin.size()-solanGanNhat;
	  if(this.listtradewin.size()>solanGanNhat) {
		  for (int i = this.listtradewin.size()-1; i >= limit; i--) {
			  
			if(this.listtradewin.get(i).toUpperCase().equals(type.toUpperCase())) {
//				System.out.println("phan tu thu "+i +" lenh "+this.listtradewin.get(i));
				count++;
			}
		}
	  }
	return count;
	 
 }
 public int qtyLoseGannhat(int solanGanNhat, String type) {
	 int count =0;
	 int limit=this.listtradelose.size()-solanGanNhat;
	  if(this.listtradelose.size()>solanGanNhat) {
		  for (int i = this.listtradelose.size()-1; i >= limit; i--) {
			  
			if(this.listtradelose.get(i).toUpperCase().equals(type.toUpperCase())) {
//				System.out.println("phan tu thu "+i +" lenh "+this.listtradewin.get(i));
				count++;
			}
		}
	  }
	return count;
	 
 }
 public void addlistlenh(Lenh lenh,ArrayList<Lenh> listlenh) {
	              listlenh.add(lenh);
	}
 
 public void genlenhtrade() {
	String starttime=gettimeStarttrade();
	 Date d;
	 int gttg1=1;
	 int gttg2=4;
	 int gttg3=3;
	 int gttg4=2;
	 Date d1;
	 String gio="";
	 Lenh l;
	 String ldat1;
	 String ldat2;
	 String ldat3;
	 String ldat4;
	try {
		d = sdfdatetimemm.parse(starttime);
		 d1=addMinutesToDate(0, d);
		Date d2=addDate(1, d);
		 gio=sdfdatetimemm.format(d1);
		l= new Lenh(gio, "G","G","T","T","","","","",gttg1,gttg2,gttg3,gttg4);
		addlistlenh(l,listlenhtrade);
		while (d1.before(d2)) { 
			gttg1++;
			gttg2++;
			gttg3++;
			gttg4++;
			if(gttg1==5 ) {
				
				gttg1=1; 
			}
			if(gttg2==5 ) {
				
				gttg2=1; 
			}
			if(gttg3==5 ) {
				
				gttg3=1; 
			}			
			if(gttg4==5 ) {
				
				gttg4=1; 
			}			
			
			
			if(gttg1==1||gttg1==4) {
				ldat1="G";
			}else {
				ldat1="T";
			}
			if(gttg2==1||gttg2==4) {
				ldat2="G";
			}else {
				ldat2="T";
			}
			if(gttg3==1||gttg3==4) {
				ldat3="G";
			}else {
				ldat3="T";
			}
			if(gttg4==1||gttg4==4) {
				ldat4="G";
			}else {
				ldat4="T";
			}
			
			
			d1=addMinutesToDate(1, d1);
			 gio=sdfdatetimemm.format(d1);
				l= new Lenh(gio, ldat1,ldat2,ldat3,ldat4,"","","","",gttg1,gttg2,gttg3,gttg4); 
				addlistlenh(l,listlenhtrade);
		}

		
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
}
public Lenh getDubaotheothoigian() {
	String time =getnetxtimetrade(0);
	for (int i = 0; i < listlenhtrade.size(); i++) {
		if(listlenhtrade.get(i).getGio().equals(time)) {
			listlenhtrade.get(i).setChon(chon);
			return listlenhtrade.get(i);
		}
	}
	
	return null;
	
}


private static Date addMinutesToDate(int minutes, Date beforeTime){
    final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
    long curTimeInMs = beforeTime.getTime();
    Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
    return afterAddingMins;
}
private static Date addDate(int day, Date beforeTime){
    final long ONE_MINUTE_IN_MILLIS = 24*60*60000;//millisecs
    long curTimeInMs = beforeTime.getTime();
    Date afterAddingMins = new Date(curTimeInMs + (day * ONE_MINUTE_IN_MILLIS));
    return afterAddingMins;
}

public String gettimeStarttrade() {
	 Result rs= new Result();
	 
	 String gio=  dateToStringPlus(new Date(),-50);
	if(listkq.size()>0) {
		rs=listkq.get(0);
//		System.out.println(rs.gio+""+rs.kq);
		gio= rs.getGio().replace("--", ":");}
	return gio;
	 
}


public String addLicence(String lic) throws IOException {
	 
   File f= new File("Licence");
   if(!f.exists()) {
	   f.mkdirs();
   }
   File fac= new File(f.getAbsoluteFile()+"/licence.nhat");
  DataOutputStream out= new DataOutputStream(new FileOutputStream(fac)); 
  
  out.writeInt(1);
  out.writeDouble(1);
  out.writeLong(1);;
  out.writeUTF("1");
  out.writeUTF("1");
  out.writeLong(1);
  out.writeDouble(1);
  out.writeInt(1);
  String licc=lic.replace("-", "");
  String md5Hex1 = DigestUtils
		      .md5Hex(licc).toUpperCase();  
  String key=genkey(licc, md5Hex1);
  out.writeUTF(key); 
  
  out.close();
  pathlic=fac.getAbsolutePath();
return pathlic;
   
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
public String getnetxtimetrade(int minus) {
	 String time="";
	 try {
		 time= sdfdatetimemm.format(new Timestamp(System.currentTimeMillis()+(minus*60000)));
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	return time;
}

//public static void main(String[] args) throws IOException {
// 	 ManagerHistory managerHistory= new ManagerHistory();
////	 ArrayList<String> listtradewin =new ArrayList<String>();
////	 listtradewin.add("T");
////	 listtradewin.add("G");
////	 listtradewin.add("T");
////	 listtradewin.add("T");
////	 
////	 managerHistory.listtradewin=listtradewin;
////	  managerHistory.in();
////	// System.out.println(managerHistory.qtyWinGannhat(4, "t"));
////	  managerHistory.removeLenhWin() ;
////	  managerHistory.in();
////	 managerHistory.dongfile();
//	
//// 	 managerHistory.addLicence("2F-53-4D-8C-45-AC-2E-73-C8-3A-F4-F2-C9-1C-89-B3");
////// 	 ArrayList<String> list= managerHistory.splitkey("2F534D8C45AC2E73C83AF4F2C91C89B3");
////// 	 System.out.println(list.toString());
//// 	Licence a=managerHistory.checklicence();
//// 	String key=managerHistory.key;
//// 	System.out.println(key);
////    if(a.getDay()>0) {
////    	 System.out.println("hạn dùng "+a.getDay()+" ngày"); System.out.println("OK ");
////    }else {
////    	System.out.println("Licence ko đúng");
////    }
//// 	managerHistory.readKqTuImage();
//// 	 
//// 	managerHistory.genlenhtrade();
//// 	
//// 	managerHistory.calculateRs();
//// 	
//// 	
////	managerHistory.countGapThep();
//// 	for (int i = 0; i < managerHistory.listlenhtrade.size(); i++) {
//// 		System.out.println(managerHistory.listlenhtrade.get(i).toString());  
////	} 
//// 	Lenh l= managerHistory.getDubaotheothoigian();
//// 	System.out.println("lenh"+l.toString());
//// 	 	 
// 	 	 
//}
//
// 

}
