package com.example.cassio.alunouesb.db;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class References {
    public static String uid = FirebaseAuth.getInstance().getUid();
    public static DocumentReference db = FirebaseFirestore.getInstance().collection("/users").document(uid);
    public static StorageReference storageProfile = FirebaseStorage.getInstance().getReference("/images" + uid + "profile");
}
