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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ginnie.galleryapp.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
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


/**
 * Created by su on 7/8/15.
 */
public class LandingFragment extends Fragment {

    TextView username;
    LinearLayout profile_image_list,create_group,profile;
    public LandingFragment()
    {

    }
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Bitmap imagebitmap = null;
    private String imagepath = null;
    Bitmap imagepic;
    String url;
    String success;
    ScrollView scrolllayout;
ImageView image_show,image_load;
    ImageButton click;
    String bitmap;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_landing, container, false);
        image_show=(ImageView)view.findViewById(R.id.image);

        click=(ImageButton)view.findViewById(R.id.click);
        username=(TextView)view.findViewById(R.id.username);
        profile_image_list=(LinearLayout)view.findViewById(R.id.profile_image_list);
        create_group=(LinearLayout)view.findViewById(R.id.create_group);
                profile=(LinearLayout)view.findViewById(R.id.profile);
        username.setText(APP_DATA.user_name);



        create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment CreateGroupFragment = new CreateGroupFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.main_container, CreateGroupFragment);
                transaction.commit();

            }
        });



        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
selectImage();
            }
        });
        return view;
    }

    //--------------------------------for selecting image----------------------------------//
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

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
            Toast.makeText(getActivity(), "error in pik", Toast.LENGTH_LONG).show();
        }
//        new UploadTask().execute(imagepic);
    }


//--------------------------------take picture from gallary----------------------------------//


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getActivity().getContentResolver().query(selectedImageUri, projection, null, null,
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
            Toast.makeText(getActivity(), "error in pik", Toast.LENGTH_LONG).show();
        }
    }




    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
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
                getActivity().getContentResolver().openFileDescriptor(uri, "r");
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
                    bitmap=   URLEncoder.encode(bitmap,"UTF-8");
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

            Toast.makeText(getActivity(), "Profile pic updated succesfully",
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
