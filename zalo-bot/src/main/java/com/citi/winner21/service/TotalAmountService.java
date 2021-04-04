package com.citi.winner21.service;

import com.citi.winner21.model.TotalAmountModel;
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
public class TotalAmountService {
    private  static  final  String COLLECTION_NANME = "wefinex_total_amount";    
    public  String saveDoc(TotalAmountModel p) throws ExecutionException, InterruptedException {
          Firestore firestore = FirestoreClient.getFirestore(); 
          p.setLastUpdate(new Date());
          ApiFuture<WriteResult> collectionApi =  firestore.collection(COLLECTION_NANME).document(p.getId()).set(p);
          return  collectionApi.get().getUpdateTime().toString();
    }
    public TotalAmountModel getDocId(String id)  throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(COLLECTION_NANME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();
        TotalAmountModel product =  null ;
        if(documentSnapshot.exists()) {
            product = documentSnapshot.toObject(TotalAmountModel.class);
            return  product;
        } else  {
            return  null;
        }
    }
    public  String updateDoc(TotalAmountModel p) throws ExecutionException, InterruptedException {
        p.setLastUpdate(new Date());
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApi =  firestore.collection(COLLECTION_NANME).document(p.getId()).set(p);
        return  collectionApi.get().getUpdateTime().toString();
    }
    
    public ArrayList<TotalAmountModel> getlist() throws ExecutionException, InterruptedException{
    	 ArrayList<TotalAmountModel> list= new ArrayList<TotalAmountModel>();
        Firestore firestore = FirestoreClient.getFirestore();
    	CollectionReference users = firestore.collection(COLLECTION_NANME); 
    	
    	Iterable<DocumentReference> documentReferences = users.listDocuments();
    	
    	documentReferences.forEach(documentReference -> { 
    		try { 
    		    ApiFuture<DocumentSnapshot> future = documentReference.get();
    	        DocumentSnapshot documentSnapshot = future.get();
    	        TotalAmountModel product =  null ;
    	        if(documentSnapshot.exists()) {
    	            product = documentSnapshot.toObject(TotalAmountModel.class);
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
