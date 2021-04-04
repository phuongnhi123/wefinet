package com.citi.winner21.service;

import com.citi.winner21.model.WefinetModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.Query;
  
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
@Service()
public class ProductService {
    private static final Random RANDOM = new SecureRandom();
    private  static  final  String COLLECTION_NANME = "wefinex";
//    String autoId() {
//        StringBuilder builder = new StringBuilder();
//        int maxRandom = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".length();
//
//        for(int i = 0; i < 20; ++i) {
//            builder.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".charAt(RANDOM.nextInt(maxRandom)));
//        }
//
//        return builder.toString();
//    }
//    
    
    public  String saveDoc(WefinetModel p) throws ExecutionException, InterruptedException {
          Firestore firestore = FirestoreClient.getFirestore();
          String id  = p.getTime();
          p.setId(id);
          p.setLastUpdate(new Date());
          ApiFuture<WriteResult> collectionApi =  firestore.collection(COLLECTION_NANME).document(id).set(p);
          DocumentReference collectionApilist = firestore.collection(COLLECTION_NANME).document();
        return  collectionApi.get().getUpdateTime().toString();
    }
    public WefinetModel getDocId(String id)  throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(COLLECTION_NANME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();
        WefinetModel product =  null ;
        if(documentSnapshot.exists()) {
            product = documentSnapshot.toObject(WefinetModel.class);
            return  product;
        } else  {
            return  null;
        }
    }
    public  String updateDoc(WefinetModel p) throws ExecutionException, InterruptedException {
        p.setLastUpdate(new Date());
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApi =  firestore.collection(COLLECTION_NANME).document(p.getId()).set(p);
        return  collectionApi.get().getUpdateTime().toString();
    }
    
    public ArrayList<WefinetModel> getlist() throws ExecutionException, InterruptedException{
    	 ArrayList<WefinetModel> list= new ArrayList<WefinetModel>();
        Firestore firestore = FirestoreClient.getFirestore();
    	CollectionReference users = firestore.collection(COLLECTION_NANME); 
    	
    	Iterable<DocumentReference> documentReferences = users.listDocuments();
    	
    	documentReferences.forEach(documentReference -> { 
    		try { 
    		    ApiFuture<DocumentSnapshot> future = documentReference.get();
    	        DocumentSnapshot documentSnapshot = future.get();
    	        WefinetModel product =  null ;
    	        if(documentSnapshot.exists()) {
    	            product = documentSnapshot.toObject(WefinetModel.class);
    	        	list.add(product); 
    	        } else  {
    	        System.out.println("fail");
    	        }
    			
    		
			} catch (Exception e) {
				// TODO: handle exception
			}
    	});
		return list;
    }
    
}
