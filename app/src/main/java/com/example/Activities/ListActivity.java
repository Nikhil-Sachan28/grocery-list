package com.example.Activities;

import android.os.Bundle;

import com.example.Data.DatabaseHandler;
import com.example.Model.Grocery;
import com.example.UI.RecyclerViewAdapter;
import com.example.mygrocerylist.databinding.ActivityListBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygrocerylist.R;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private ActivityListBinding binding;
    private DatabaseHandler db;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);


        binding.fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        db = new DatabaseHandler(this);

        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        groceryList = new ArrayList<>();
        //Get items from database
        List<Grocery> groceryList = db.getAllGroceries();
        Log.d("check: ", "checking");
        List<Grocery> listItems = new ArrayList<>();


        for (Grocery c: groceryList){
            Grocery grocery = new Grocery();
            grocery.setName(c.getName());
            grocery.setQuantity("Qty : " + c.getQuantity());
            grocery.setId(c.getId());
            grocery.setDateItemAdded("Added on : "+ c.getDateItemAdded());
            listItems.add(grocery);
            Log.d("check: ", "Id :" + c.getId() + "\n" +
                    "Name : " + c.getName()+ "\n" +
                    "quantity : " + c.getQuantity() + "\n" +
                    "dateAdded : " + c.getDateItemAdded()+ "\n");
        }

        recyclerViewAdapter = new RecyclerViewAdapter(this, listItems);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

    }

}
