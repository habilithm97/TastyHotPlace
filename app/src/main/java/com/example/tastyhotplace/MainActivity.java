package com.example.tastyhotplace;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<CardItem> cardItems, filteredList;
    //ArrayList<CardItem> filteredList;
    RecyclerViewAdapter adapter;
    ImageView foodImg;

    EditText searchEdt;
    public static Button homeBtn;

    Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodImg = (ImageView)findViewById(R.id.foodImg);

        cardItems = new ArrayList<>();
        filteredList = new ArrayList<>();

        searchEdt = (EditText)findViewById(R.id.searchEdt);
        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { // 입력하기 전에 처리

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { // 입력과 동시에 처리
                //adapter.getFilter().filter(charSequence); // 어댑터 클래스의 Filter 클래스를 사용할 때 사용
            }

            @Override
            public void afterTextChanged(Editable editable) { // 입력 후에 처리
                String str = searchEdt.getText().toString();
                searchFilter(str);

            }
        });

        homeBtn = (Button)findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEdt.setText(null);
            }
        });

        //loadData();

        FloatingActionButton floatingActionButton = findViewById(R.id.floatBtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WritePlace.class);
                startActivityForResult(intent, 97);
            }
        });

        //adapter = new RecyclerViewAdapter(this, getList()); // 어댑터 클래스의 Filter 클래스를 사용할 때 사용
        adapter = new RecyclerViewAdapter(cardItems, this); // 이 부분에서 어댑터 클래스와 연결

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter); 
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == 97) { // 새로운 값들을 WritePlace에서 받아옴
            if(intent != null) {
                /*
                byte[] arr = getIntent().getByteArrayExtra("image");
                image = BitmapFactory.decodeByteArray(arr, 0, arr.length); */
                byte[] byteArray = getIntent().getByteArrayExtra("itemImg");
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                String name = intent.getStringExtra("sendName");
                String location = intent.getStringExtra("sendLocation");
                String menu = intent.getStringExtra("sendMenu");
                String side = intent.getStringExtra("sendSide");
                String price = intent.getStringExtra("sendPrice");
                String time = intent.getStringExtra("sendTime");
                String tel = intent.getStringExtra("sendTel");
                String review = intent.getStringExtra("sendReview");
                String note = intent.getStringExtra("sendNote");

                adapter.addItem(new CardItem(bitmap, name, location, menu, side, price, time, tel, review, note));
                adapter.notifyDataSetChanged();

                //saveData();
            }
        } else if(requestCode == 98) { // 수정할 시에 어댑터에서 받아옴
            byte[] byteArray = getIntent().getByteArrayExtra("itemImg");

            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length); // 이 부분에서 오류뜸 -> 배열 데이터가 없다 -> 배열 데이터가 null일 경우에 대한 처리 추가 요망

            String name = intent.getStringExtra("itemName");
            String location = intent.getStringExtra("itemLocation");
            String menu = intent.getStringExtra("itemMenu");
            String side = intent.getStringExtra("itemSide");
            String price = intent.getStringExtra("itemPrice");
            String time = intent.getStringExtra("itemTime");
            String tel = intent.getStringExtra("itemTel");
            String review = intent.getStringExtra("itemReview");
            String note = intent.getStringExtra("itemNote");

            adapter.updateItem(RecyclerViewAdapter.position, new CardItem(bitmap, name, location, menu, side, price, time, tel, review, note));
        }
    }

    public void saveData() { // 임시로 주석처리
        SharedPreferences sharedPreferences = getSharedPreferences("TastyHotPlace", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cardItems);
        editor.putString("Tasty HotPlace List", json);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //loadData();
    }

    @Override
    protected void onPause() {
        super.onPause();

        //saveData();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("TastyHotPlace", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Tasty HotPlace List", null);
        Type type = new TypeToken<ArrayList<CardItem>>() {}.getType();
        cardItems = gson.fromJson(json, type);

        if(cardItems == null) {
            cardItems = new ArrayList<>();
        }
    }

    /*
    private ArrayList<CardItem> getList() {
        return RecyclerViewAdapter.cardItems;
    } */

    public void searchFilter(String str) { // 검색창에 입력한 문자열이 인자로 전달됨
        filteredList.clear();

        for(int i = 0; i < cardItems.size(); i++) {
            if(cardItems.get(i).getName().toLowerCase().contains(str.toLowerCase()) || cardItems.get(i).getLocation().toLowerCase().contains(str.toLowerCase()) ||
                    cardItems.get(i).getMenu().toLowerCase().contains(str.toLowerCase()) || cardItems.get(i).getSide().toLowerCase().contains(str.toLowerCase()) ||
                    cardItems.get(i).getPrice().toLowerCase().contains(str.toLowerCase()) || cardItems.get(i).getTime().toLowerCase().contains(str.toLowerCase()) ||
                    cardItems.get(i).getTel().toLowerCase().contains(str.toLowerCase()) || cardItems.get(i).getReview().toLowerCase().contains(str.toLowerCase()) ||
                    cardItems.get(i).getNote().toLowerCase().contains(str.toLowerCase())) {
                filteredList.add(cardItems.get(i));
            }
        }
        adapter.filterList(filteredList);
    }
}