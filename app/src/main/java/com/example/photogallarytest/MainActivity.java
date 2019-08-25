package com.example.photogallarytest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    //private OkHttpClient client;
    //public static final int PICK_IMAGE = 1;
    //public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    //private void fetchData(){
    //    client = new OkHttpClient.Builder().build();
    //}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.d("tf","tf");
        Button btn = (Button) findViewById(R.id.next);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this , onboard.class);
                startActivity(intent);

            }
        });
        //Log.d("tf","tfxcsddd");
    }
}
/*
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        fetchData();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            if (data == null) {
                return;
            }
            final Uri pickedImage = data.getData();
            post("https://api.imgur.com/3/image", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String responseStr = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            jsonObject=new JSONObject(jsonObject.getString("data"));
                            responseStr=jsonObject.getString("link");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Do what you want to do with the response.
                        JavaSample getPercentDamaged = new JavaSample(responseStr);
                        System.out.println("percent not dmged : " + getPercentDamaged.percentTotalled);

                    } else {
                    }
                }
            },pickedImage);
        }
    }
    Call post(String url, Callback callback, Uri pickedImage) {
        try{
            final InputStream imageStream = getContentResolver().openInputStream(pickedImage);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            String str = encodeImage(selectedImage);
            RequestBody formBody = new FormBody.Builder()
                    .add("image", str)
                    .build();
            Request request = new Request.Builder()
                    .url("https://api.imgur.com/3/image")
                    .post(formBody)
                    .addHeader("Authorization", "Client-ID 0ece7e25c3c12c7")
                    .addHeader("User-Agent","Otto")
                    .build();
            Call call = client.newCall(request);
            call.enqueue(callback);
            return call;}
        catch (Exception e){
            return null;
        }
    }
    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }
    private String getPercentTotalled(JavaSample getPercentDamaged){

        String totalled = getPercentDamaged.getPercentTotalled();
        while(totalled == null || totalled.equals("")){
            totalled = getPercentDamaged.getPercentTotalled();
            System.out.println("totalled : " + totalled);
        }
        return totalled;
    }

}

//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.StrictMode;
//import android.provider.MediaStore;
//import android.telecom.Call;
//import android.util.Base64;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.MalformedURLException;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//
//import okhttp3.Callback;
//import okhttp3.FormBody;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class MainActivity extends AppCompatActivity {
//
//    private static final int PICK_IMAGE = 1;
//    Uri imageUri = null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
////        if (android.os.Build.VERSION.SDK_INT > 9)
////        {
////            StrictMode.ThreadPolicy policy = new
////                    StrictMode.ThreadPolicy.Builder().permitAll().build();
////            StrictMode.setThreadPolicy(policy);
////        }
//
//        final ImageView imageView = findViewById(R.id.imageView);
//        Button uploadImage = findViewById(R.id.uploadImage);
//        Button loadImage = findViewById(R.id.loadImage);
//
//        uploadImage.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
//                openGallary();
//            }
//        });
//
//        loadImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (imageUri != null) {
//                    imageView.setImageURI(imageUri);
//
//                }
//            }
//        });
//    }
//
//    public void openGallary() {
//        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        startActivityForResult(gallery, 100);
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        if (requestCode == PICK_IMAGE) {
//            if (data == null) {
//                Log.d("fuck3","fuck");
//                return;
//            }
//            final Uri pickedImage = data.getData();
//            post("https://api.imgur.com/3/image", new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Log.d("fail","fuck");
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    if (response.isSuccessful()) {
//                        String responseStr = response.body().string();
//                        try {
//                            JSONObject jsonObject = new JSONObject(responseStr);
//                            jsonObject=new JSONObject(jsonObject.getString("data"));
//                            responseStr=jsonObject.getString("link");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        // Do what you want to do with the response.
//                        Log.d("ans",responseStr);
//                    } else {
//                        Log.d("fuck4",response.toString());
//                    }
//                }
//            },pickedImage);
//        }
//    }
//    Call post(String url, Callback callback, Uri pickedImage) {
//        try{
//            final InputStream imageStream = getContentResolver().openInputStream(pickedImage);
//            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//            String str = encodeImage(selectedImage);
//            RequestBody formBody = new FormBody.Builder()
//                    .add("image", str)
//                    .build();
//            Request request = new Request.Builder()
//                    .url("https://api.imgur.com/3/image")
//                    .post(formBody)
//                    .addHeader("Authorization", "Client-ID 0ece7e25c3c12c7")
//                    .addHeader("User-Agent","Otto")
//                    .build();
//            Call call = client.newCall(request);
//            call.enqueue(callback);
//            return call;}
//        catch (Exception e){
//            Log.d("error",e.toString());
//            return null;
//        }
//    }
//    private String encodeImage(Bitmap bm)
//    {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
//        byte[] b = baos.toByteArray();
//        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
//
//        return encImage;
//    }
//
//}
 */
