package com.example.photogallarytest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

public class frontCar extends AppCompatActivity {

    private OkHttpClient client;
    public static final int PICK_IMAGE = 1;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private ProgressDialog dialog;

    private void fetchData(){
        client = new OkHttpClient.Builder().build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_car);
        Button btn = (Button) findViewById(R.id.bottomPicture1);
        Intent tempIntent = getIntent();

        String image_path = tempIntent.getStringExtra("backImage");
        Uri fileUri = Uri.parse(image_path);

        // set imageview
        ImageView backOfCarImageView = findViewById(R.id.backOfCarPicture);
        backOfCarImageView.setImageURI(fileUri);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                fetchData();


            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            if (data == null) {
                return;
            }
            dialog.setMessage("Uploading image. Please wait...");
            dialog.show();
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

                        Intent intent = new Intent(frontCar.this , Results.class);
                        Intent temp=getIntent();
                        intent.putExtra("Policy Number",temp.getStringExtra("Policy Number"));
                        intent.putExtra("VIN",temp.getStringExtra("VIN"));
                        intent.putExtra("Email",temp.getStringExtra("Email"));
                        intent.putExtra("Front",temp.getStringExtra("Front"));
                        intent.putExtra("Back",getPercentDamaged.percentTotalled);
                        dialog.dismiss();
                        startActivity(intent);

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
