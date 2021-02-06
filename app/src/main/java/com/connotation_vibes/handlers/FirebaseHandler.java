package com.connotation_vibes.handlers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.connotation_vibes.models.Article;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class FirebaseHandler {

    private String query;
    private DatabaseReference reference;


    public FirebaseHandler(DatabaseReference reference, String query) {
        this.reference = reference;
        this.query = query;
    }

    public ArrayList<Article> searchTree() {
        final ArrayList<Article> articles = new ArrayList<>();
        this.reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshotChild: snapshot.getChildren()) {
                    for (DataSnapshot snapshotChildSecondNode: snapshotChild.getChildren()) {
                        articles.add(snapshotChildSecondNode.getValue(Article.class));
                        Log.d("Ruby", Objects.requireNonNull(snapshotChildSecondNode.getValue(Article.class)).toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return articles;
    }
}
