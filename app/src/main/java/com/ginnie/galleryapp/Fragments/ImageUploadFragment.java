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
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ginnie.galleryapp.MultipartEntity;
import com.ginnie.galleryapp.R;

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
import java.util.LinkedList;


public class ImageUploadFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    TextView first_name,user_name;
    public ImageUploadFragment() {
    }

    RelativeLayout post;
    LinearLayout hidden_layout;
    ImageView arrow,video_day,image_day;
    boolean flag=false;





    private static final String TAG_SUCCESS = "code";
    private static final String TAG_MESSAGE = "message";
    LinearLayout done,changepassword,logout;
    View  view;
    SharedPreferences loginPreferences;
    String name,firstname,lastname,phno,zip,locations,cityy,stater,email_id;
    RelativeLayout back;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Bitmap imagebitmap=null;
    private String imagepath=null;
    Bitmap imagepic;
    String url;
    String success;
    ScrollView scrolllayout;
ImageView imagesetup,takepik;
    private static String LOGIN_URL = "";





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    /* Initialize recyclerview */

        view=inflater.inflate(R.layout.fragment_imageupload, container, false);
        imagesetup=(ImageView)view.findViewById(R.id.imagesetup);
        takepik=(ImageView)view.findViewById(R.id.takepik);

        takepik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        return view;
    }


    private class UploadTask extends AsyncTask<Bitmap, Void, Void> {
        @Override
        protected void onPreExecute() {


            super.onPreExecute();

        }

        private static final String TAG ="success" ;

        protected Void doInBackground(Bitmap... bitmaps) {

            if(isCancelled()){
                // end task right away
                return null;
            }

            if (bitmaps[0] == null)
                return null;
            //setProgress(0);

            Bitmap bitmap = bitmaps[0];
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // convert Bitmap to ByteArrayOutputStream
            InputStream in = new ByteArrayInputStream(stream.toByteArray()); // convert ByteArrayOutputStream to ByteArrayInputStream

            DefaultHttpClient httpclient = new DefaultHttpClient();
            try {


                HttpPost httppost = new HttpPost("http://203.196.159.37/lab4/grocerry/appconnect/login_signup.php?mode=change_profile_picture&user_id="+APP_DATA.userid); // server

                MultipartEntity reqEntity = new MultipartEntity();
                reqEntity.addPart("imagefile",
                        System.currentTimeMillis() + ".jpg", in);
                httppost.setEntity(reqEntity);

                Log.i(TAG, "request" + httppost.getRequestLine());
                HttpResponse response = null;
                try {
                    String exception = "";
                    String responseMSG = "";
                    String urlResponse = "";
                    String  message = "";
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
                    JSONObject c=jOBJ.getJSONObject("response");
                    APP_DATA.image=c.getString("image");

                    // shared = getSharedPreferences("loginPreferences", 0);

//                    SharedPreferences.Editor editor = loginPreferences.edit();
//
//                    editor.putString("image",APP_DATA.image);
//
//                    editor.commit();
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

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return null;
        }

        protected void onPostExecute(Void resultt) {
            super.onPostExecute(resultt);

            Toast.makeText(getActivity(), "Profile pic updated succesfully",
                    Toast.LENGTH_SHORT).show();
            ;
        }
    }
    //--------------------------------for selecting image----------------------------------//
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
        imagepath=destination.getPath();

        //dialog = ProgressDialog.show(this.getActivity(), "", "Uploading file...", true);

//        uploadFile(imagepath);

        // Picasso.with(this).load(destination).fit().into(imageView);
        //prfpic.setImageBitmap(thumbnail);
        imagesetup.setImageBitmap(thumbnail);
        imagepic=thumbnail;

        if (imagepic != null) {
            new UploadTask().execute(imagepic);

//                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }else {Toast.makeText(getActivity(),"error in pik",Toast.LENGTH_LONG).show();}
//        new UploadTask().execute(imagepic);
    }




//--------------------------------take picture from gallary----------------------------------//



    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getActivity().getContentResolver().query(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        imagepath=getRealPathFromURI(selectedImageUri);

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
            bm=getBitmapFromUri(selectedImageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Toast.makeText(this,imagepath,Toast.LENGTH_LONG).show();
        // uploadFile(imagepath);
        // Picasso.with(RegestrationStepThreeActivity.this).load(imagepath).fit().into(imageView);
        //   prfpic.setImageBitmap(bm);
        imagesetup.setImageBitmap(bm);
        imagepic=bm;
        if (imagepic != null) {
            new UploadTask().execute(imagepic);

//                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }else {Toast.makeText(getActivity(),"error in pik",Toast.LENGTH_LONG).show();}
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
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }




    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;

    }
}
