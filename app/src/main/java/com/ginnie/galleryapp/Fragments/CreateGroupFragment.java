package com.ginnie.galleryapp.Fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ginnie.galleryapp.MainActivity;
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
public class CreateGroupFragment extends Fragment {

EditText username,paswrd;
    Button login;
    String url,success,message,group="";
    String bitmap;
    EditText grp_name;
    ImageView image_show,image_load;
LinearLayout profile_image_list;
    Bitmap imagepic;
    public CreateGroupFragment()
    {

    }

    private static final String TAG_SUCCESS = "code";
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Bitmap imagebitmap = null;
    private String imagepath = null;
    TextView create_group;
    ImageView back;
    private static final String TAG = "SignupActivity";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_grup, container, false);
        image_load=(ImageView)view.findViewById(R.id.image_load);
//        create_group=(LinearLayout)view.findViewById(R.id.create_group);
        profile_image_list=(LinearLayout)view.findViewById(R.id.profile_image_list);
        grp_name=(EditText)view.findViewById(R.id.grp_name);
        create_group=(TextView)view.findViewById(R.id.grp_name);

//        create_group.setClickable(false);
        profile_image_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LandingFragment fragment2 = new LandingFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.main_container, fragment2);
                transaction.commit();

            }
        });


        create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                group=grp_name.getText().toString();


              String  vacationtype = "1";
                url= APP_DATA.BASE_URL_TRAVEL+"mode=add_vacation&userid="+ APP_DATA.userid+"&vacation_name="+group+"&vacation_type="+vacationtype;

                CreateUser(url);
//                new AddNewTrip().execute();


//                Intent intent = new Intent(context,TrackMyTravel.class);
//                context.startActivity(intent);
            }
        });




//        image_load.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            selectImage();
////                create_group.setClickable(true);
//
//            }
//        });
//        create_group.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                APP_DATA.group_name = grp_name.getText().toString();
//
//                try {
//                    bitmap=   URLEncoder.encode(bitmap, "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//
//                url = "http://www.esolz.co.in/lab2/android/prac/group_create.php?group_name=" + APP_DATA.group_name + "&owner_id=" + APP_DATA.userid + "&image=" + bitmap;
//                makeJsonObjectRequest();
//
//
//            }
//        });


        return view;
    }





    private void makeJsonObjectRequest() {



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    String code = response.getString("code");
                    Log.d("User Login!", response.toString());
//                    if(code.equals("0"))
//                    {
//
//                        JSONObject c=response.getJSONObject("response");
//
//                        APP_DATA.userid=c.getString("user_id");
//                        APP_DATA.user_name=c.getString("user_name");
//                        APP_DATA.first_name=c.getString("first_name");
//                        APP_DATA.last_name=c.getString("last_name");
//                        APP_DATA.email=c.getString("email");
//                        APP_DATA.dob=c.getString("dob");
//                        APP_DATA.phone_no=c.getString("phone");
//                        APP_DATA.image=c.getString("image");
//
//
//                        Toast.makeText(getActivity(),
//                                response.getString("message"),
//                                Toast.LENGTH_LONG).show();
//                        APP_DATA.page_set="main";
//                        Intent intent = new Intent(getActivity(), MainActivity.class);
//                        startActivity(intent);
//                        Toast.makeText(getActivity(), "Login Succesfully", Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                    {
//                        Toast.makeText(getActivity(),
//                                response.getString("message"),
//                                Toast.LENGTH_LONG).show();
//                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(getActivity(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        APP_DATA.getInstance().addToRequestQueue(jsonObjReq);


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


//    private class UploadTask extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected void onPreExecute() {
//
//
//            super.onPreExecute();
//
//        }
//
//        private static final String TAG = "success";
//
//        protected Void doInBackground(Void... params) {
//
//            if (isCancelled()) {
//                // end task right away
//                return null;
//            }
//            // convert ByteArrayOutputStream to ByteArrayInputStream
//
//            DefaultHttpClient httpclient = new DefaultHttpClient();
//            try {
//
//                try {
//                    bitmap=   URLEncoder.encode(bitmap, "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//
//                HttpPost httppost = new HttpPost("http://www.esolz.co.in/lab2/android/prac/user_pic_upload.php?owner_id="+APP_DATA.userid+"&image="+bitmap); // server
//
//
//
//                Log.i(TAG, "request" + httppost.getRequestLine());
//                HttpResponse response = null;
//                try {
//                    String exception = "";
//                    String responseMSG = "";
//                    String urlResponse = "";
//                    String message = "";
//                    response = httpclient.execute(httppost);
//                    HttpEntity httpentity = response.getEntity();
//                    InputStream is = httpentity.getContent();
//                    BufferedReader reader = new BufferedReader(
//                            new InputStreamReader(is, "iso-8859-1"), 8);
//                    StringBuilder sb = new StringBuilder();
//                    String line = null;
//                    while ((line = reader.readLine()) != null) {
//                        sb.append(line + "\n");
//                    }
//                    is.close();
//                    urlResponse = sb.toString();
//                    JSONObject jOBJ = new JSONObject(urlResponse);
//                    JSONObject c = jOBJ.getJSONObject("response");
//                    APP_DATA.image = c.getString("image");
//
//                    // shared = getSharedPreferences("loginPreferences", 0);
//
//
//                } catch (ClientProtocolException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    if (response != null)
//                        Log.i(TAG, "response " + response.getStatusLine().toString());
//                } finally {
//
//                }
//            } finally {
//
//            }
//
//
//
//            return null;
//        }
//
//        protected void onPostExecute(Void resultt) {
//            super.onPostExecute(resultt);
//
//            Toast.makeText(getActivity(), "Profile pic updated succesfully",
//                    Toast.LENGTH_SHORT).show();
//            ;
//        }
//    }
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

    private void CreateUser(String url) {
        Log.i("Delete_url", url.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try {
                    success = response.getString(TAG_SUCCESS);
                    message=response.getString("message");
                    String response_edit=response.getString("response");



                } catch (JSONException e) {
                    e.printStackTrace();


                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volly_error", "Error: " + error.getMessage());

                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

        APP_DATA.getInstance().addToRequestQueue(jsonObjReq);

    }

}
