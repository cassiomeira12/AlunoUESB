package com.example.cassio.alunouesb.db;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class References {
    public static String uid = FirebaseAuth.getInstance().getUid();
    public static DocumentReference db = FirebaseFirestore.getInstance().collection("/users").document(uid);
}
