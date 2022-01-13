package com.example.tastyhotplace;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.nio.channels.InterruptedByTimeoutException;

public class WritePlace extends AppCompatActivity {

    private final int GET_GALLERY_IMAGE = 200;

    ImageView foodImgInput;

    EditText nameEdt, locationEdt, menuEdt, sideEdt, priceEdt, timeEdt, telEdt, reviewEdt, noteEdt;

    Uri selectionImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_place);

        nameEdt = (EditText)findViewById(R.id.nameEdt);
        locationEdt = (EditText)findViewById(R.id.locationEdt);
        menuEdt = (EditText)findViewById(R.id.menuEdt);
        sideEdt = (EditText)findViewById(R.id.sideEdt);
        priceEdt = (EditText)findViewById(R.id.priceEdt);
        timeEdt = (EditText)findViewById(R.id.timeEdt);
        telEdt = (EditText)findViewById(R.id.telEdt);
        reviewEdt = (EditText)findViewById(R.id.reviewEdt);
        noteEdt = (EditText)findViewById(R.id.noteEdt);

        Button saveBtn = (Button)findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameEdt.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "상호명을 입력해주세요. ", Toast.LENGTH_SHORT).show();
                } else {
                    SendData();
                }
            }
        });

        foodImgInput = (ImageView)findViewById(R.id.foodImgInput);
        foodImgInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            selectionImageUri = data.getData();
            foodImgInput.setImageURI(selectionImageUri);
        }
    }

    public void SendData() {

        /*
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable)foodImgInput.getDrawable()).getBitmap(); // VectorDrawble을 BitmapDrawble로 캐스팅 할 수 없음(둘 다 Drawble 클래스의 서브 클래스)
        // -> 드로어블에서 비트맵을 가져오려면 드로어블 메타 데이터에서 비트맵을 만들어야함

        float scale = (float)(1024/(float)bitmap.getWidth());
        int img_w = (int)(bitmap.getWidth() * scale);
        int img_h = (int)(bitmap.getHeight() * scale);
        Bitmap resize = Bitmap.createScaledBitmap(bitmap, img_w, img_h, true); // createScaleBitmap() : 사이즈가 큰 비트맵을 원하는 사이즈에 맞게 리사이징
        resize.compress(Bitmap.CompressFormat.JPEG, 100, stream); // compress() : 이미지의 사이즈는 그대로 두고 퀄리티를 조절
        byte[] byteArray = stream.toByteArray(); */

        String sendName = nameEdt.getText().toString();
        String sendLocation = locationEdt.getText().toString();
        String sendMenu = menuEdt.getText().toString();
        String sendSide = sideEdt.getText().toString();
        String sendPrice = priceEdt.getText().toString();
        String sendTime = timeEdt.getText().toString();
        String sendTel = telEdt.getText().toString();
        String sendReview = reviewEdt.getText().toString();
        String sendNote = noteEdt.getText().toString();

        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra("itemImg", byteArray);
        intent.putExtra("sendName", sendName);
        intent.putExtra("sendLocation", sendLocation);
        intent.putExtra("sendMenu", sendMenu);
        intent.putExtra("sendSide", sendSide);
        intent.putExtra("sendPrice", sendPrice);
        intent.putExtra("sendTime", sendTime);
        intent.putExtra("sendTel", sendTel);
        intent.putExtra("sendReview", sendReview);
        intent.putExtra("sendNote", sendNote);
        setResult(RESULT_OK, intent);
        finish();

        Toast.makeText(getApplicationContext(), "저장되었습니다. ", Toast.LENGTH_SHORT).show();
    }
}

// java.lang.RuntimeException: Unable to start activity ComponentInfo{com.example.tastyhotplace/com.example.tastyhotplace.WritePlace}: 액티비티가 시작될 때 화면을 로드하지 못할 경우 생기는 에러
// java.lang.NullPointerException: Attempt to invoke virtual method 'void android.widget.Button.setVisibility(int)' on a null object reference