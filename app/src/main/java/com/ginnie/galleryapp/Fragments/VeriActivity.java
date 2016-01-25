package com.ginnie.galleryapp.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.ginnie.galleryapp.MainActivity;
import com.ginnie.galleryapp.MultipartEntity;
import com.ginnie.galleryapp.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;

public class VeriActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "AORuJpTsqf5Fm4P61VQjsZuo4";
    private static final String TWITTER_SECRET = "Xj0wX4ZpYFOpCbkOHvZb20AcxZ4qZFrM5l3iDrpI1jnCPNtecz";
    private AuthCallback authCallback;
    boolean flage = false;
    Button image_click;
    ImageView image_show;
TextView username;

    SharedPreferences loginPreferences;
    private static final String TAG_SUCCESS = "code";
    private static final String TAG_MESSAGE = "message";

    RelativeLayout back;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Bitmap imagebitmap = null;
    private String imagepath = null;
    Bitmap imagepic;
    String url;
    String success;
    ScrollView scrolllayout;

    String bitmap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
//        Fabric.with(this, new TwitterCore(authConfig), new Digits());
        setContentView(R.layout.veri_main);

        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setCallback(((APP_DATA) getApplication()).getAuthCallback());

        Digits.authenticate(APP_DATA.authCallback, "+918583818151");
        digitsButton.setVisibility(View.INVISIBLE);
        image_click = (Button) findViewById(R.id.image_click);
        image_show = (ImageView) findViewById(R.id.image_show);
        username= (TextView) findViewById(R.id.username);
        username.setText(APP_DATA.user_name);

//if (flage==false){
//
//    flage=true;
//}else {
//    digitsButton.setVisibility(View.GONE);
//    APP_DATA.page_set = "reg";
//    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//}

        image_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //--------------------------------for selecting image----------------------------------//
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(VeriActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    //--------------------------------dialog open----------------------------------//


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }


//--------------------------------take picture from camera----------------------------------//


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap=encodeTobase64(thumbnail);
        //new UploadTask().execute(thumbnail);
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imagepath = destination.getPath();

        //dialog = ProgressDialog.show(this.getActivity(), "", "Uploading file...", true);

//        uploadFile(imagepath);

        // Picasso.with(this).load(destination).fit().into(imageView);
        //prfpic.setImageBitmap(thumbnail);
        image_show.setImageBitmap(thumbnail);
        imagepic = thumbnail;

        if (imagepic != null) {
            new UploadTask().execute();

//                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else {
            Toast.makeText(VeriActivity.this, "error in pik", Toast.LENGTH_LONG).show();
        }
//        new UploadTask().execute(imagepic);
    }


//--------------------------------take picture from gallary----------------------------------//


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = VeriActivity.this.getContentResolver().query(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        imagepath = getRealPathFromURI(selectedImageUri);

        final int REQUIRED_SIZE = 600;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(imagepath, options);
        //new UploadTask().execute(bm);
        try {
            bm = getBitmapFromUri(selectedImageUri);
            bitmap=encodeTobase64(bm);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Toast.makeText(this,imagepath,Toast.LENGTH_LONG).show();
        // uploadFile(imagepath);
        // Picasso.with(RegestrationStepThreeActivity.this).load(imagepath).fit().into(imageView);
        //   prfpic.setImageBitmap(bm);
        image_show.setImageBitmap(bm);
        imagepic = bm;
        if (imagepic != null) {
            new UploadTask().execute();

//                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else {
            Toast.makeText(VeriActivity.this, "error in pik", Toast.LENGTH_LONG).show();
        }
    }

//    public String getPath(Uri uri) {
//        // just some safety built in
//        if( uri == null ) {
//            // TODO perform some logging or show user feedback
//            return null;
//        }
//        // try to retrieve the image from the media store first
//        // this will only work for images selected from gallery
//        String[] projection = { MediaStore.Images.Media.DATA };
//        Cursor cursor = getBitmapFromUri(uri, projection, null, null, null);
//        if( cursor != null ){
//            int column_index = cursor
//                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        }
//        // this is our fallback here
//        return uri.getPath();
//    }


    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = VeriActivity.this.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            ;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                VeriActivity.this.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;

    }



    private class UploadTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {


            super.onPreExecute();

        }

        private static final String TAG = "success";

        protected Void doInBackground(Void... params) {

            if (isCancelled()) {
                // end task right away
                return null;
            }
 // convert ByteArrayOutputStream to ByteArrayInputStream

            DefaultHttpClient httpclient = new DefaultHttpClient();
            try {
                try {
                    bitmap=   URLEncoder.encode(bitmap, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                HttpPost httppost = new HttpPost("http://www.esolz.co.in/lab2/android/prac/user_pic_upload.php?owner_id="+APP_DATA.userid+"&image="+bitmap); // server



                Log.i(TAG, "request" + httppost.getRequestLine());
                HttpResponse response = null;
                try {
                    String exception = "";
                    String responseMSG = "";
                    String urlResponse = "";
                    String message = "";
                    response = httpclient.execute(httppost);
                    HttpEntity httpentity = response.getEntity();
                    InputStream is = httpentity.getContent();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    urlResponse = sb.toString();
                    JSONObject jOBJ = new JSONObject(urlResponse);
                    JSONObject c = jOBJ.getJSONObject("response");
                    APP_DATA.image = c.getString("image");

                    // shared = getSharedPreferences("loginPreferences", 0);

                    SharedPreferences.Editor editor = loginPreferences.edit();

                    editor.putString("image", APP_DATA.image);

                    editor.commit();
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if (response != null)
                        Log.i(TAG, "response " + response.getStatusLine().toString());
                } finally {

                }
            } finally {

            }



            return null;
        }

        protected void onPostExecute(Void resultt) {
            super.onPostExecute(resultt);

            Toast.makeText(VeriActivity.this, "Profile pic updated succesfully",
                    Toast.LENGTH_SHORT).show();
            ;
        }
    }




    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }
}
