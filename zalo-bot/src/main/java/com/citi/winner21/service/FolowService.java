package com.citi.winner21.service;

import com.citi.winner21.model.FollowModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient; 
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
@Service()
public class FolowService {
    private static final Random RANDOM = new SecureRandom();
    private  static  final  String COLLECTION_NANME = "follow_bet";
   
    public  String updateDoc(FollowModel p) throws ExecutionException, InterruptedException { 
    	
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApi =  firestore.collection(COLLECTION_NANME).document(p.getId()).set(p);
        return  collectionApi.get().getUpdateTime().toString();
    }
}
