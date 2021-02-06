package com.connotation_vibes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;

import com.connotation_vibes.R;
import com.connotation_vibes.handlers.ArticlesAdapter;
import com.connotation_vibes.handlers.FirebaseHandler;
import com.connotation_vibes.models.Article;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;
import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity {

    ArrayList<Article> articles;
    ArticlesAdapter articlesAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        articles = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        articlesAdapter = new ArticlesAdapter(this, articles);
        recyclerView.setAdapter(articlesAdapter);
        searchTree("trump", articlesAdapter, recyclerView);
    }

    private void searchTree(String query, final ArticlesAdapter articlesAdapter, final RecyclerView recyclerView) {
        // Function that gets the data and sets the relevant data in the articles array list
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        final ArrayList<Article> forUpdate = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshotRow1: snapshot.getChildren()) {
                    forUpdate.add(snapshotRow1.getValue(Article.class));
                    articlesAdapter.updateData(forUpdate);
                    Log.d("RUDOV", Objects.requireNonNull(snapshotRow1.getValue()).toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Opening of the search view
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // TODO: Tree search for keys that includes the key words
        return true;
    }
}
