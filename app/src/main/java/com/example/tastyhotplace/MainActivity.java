package com.example.tastyhotplace;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TextWatcher{

    RecyclerViewAdapter adapter;
    Uri foodImg;

    EditText searchEdt;
    public static Button homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEdt = (EditText)findViewById(R.id.searchEdt);
        searchEdt.addTextChangedListener(this);
        // https://jootc.com/p/201906042883

        homeBtn = (Button)findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "이거 누르면 검색창에 입력한 텍스트가 널이됨", Toast.LENGTH_SHORT).show();
            }
        });

        loadData();

        foodImg = getIntent().getParcelableExtra("foodImg");

        adapter = new RecyclerViewAdapter(this, getList());

        FloatingActionButton floatingActionButton = findViewById(R.id.floatBtn); // 메모 작성하는 플로팅 버튼
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WritePlace.class);
                startActivityForResult(intent, 97);
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter); // 새로 만들면안되지;;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == 97) {
            if(intent != null) {
                String name = intent.getStringExtra("sendName");
                String location = intent.getStringExtra("sendLocation");
                String menu = intent.getStringExtra("sendMenu");
                String side = intent.getStringExtra("sendSide");
                String price = intent.getStringExtra("sendPrice");
                String time = intent.getStringExtra("sendTime");
                String tel = intent.getStringExtra("sendTel");
                String review = intent.getStringExtra("sendReview");
                String note = intent.getStringExtra("sendNote");

                adapter.addItem(new CardItem(name, location, menu, side, price, time, tel, review, note)); // 모델에는 int인데 foodImg는 Bitmap이라 오류남
                adapter.notifyDataSetChanged();

                saveData();
            }
        }
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("TastyHotPlace", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(RecyclerViewAdapter.cardItems);
        editor.putString("Tasty HotPlace List", json);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveData();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("TastyHotPlace", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Tasty HotPlace List", null);
        Type type = new TypeToken<ArrayList<CardItem>>() {}.getType();
        RecyclerViewAdapter.cardItems = gson.fromJson(json, type);

        if(RecyclerViewAdapter.cardItems == null) {
            RecyclerViewAdapter.cardItems = new ArrayList<>();
        }
    }

    private ArrayList<CardItem> getList() {
        return RecyclerViewAdapter.cardItems;
    }

    @Override
    public void beforeTextChanged(CharSequence c, int i, int i1, int i2) { // 입력하기 전에 처리

    }

    @Override
    public void onTextChanged(CharSequence c, int i, int i1, int i2) { // 변화와 동시에 처리
        adapter.getFilter().filter(c);
    }

    @Override
    public void afterTextChanged(Editable editable) { // 입력이 끝났을 때 처리

    }
}