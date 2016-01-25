package com.ginnie.galleryapp.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import com.ginnie.galleryapp.MultipartEntity;
import com.ginnie.galleryapp.R;
import com.ginnie.galleryapp.helper.ConnectionDetector;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.picasso.Picasso;

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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CreateGroup extends Fragment {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	TextView done;
	ProgressDialog pDialog;
	ConnectionDetector cd;
	private static final String TAG1 = "Tab1";
	Context context;
	int success;
	ImageView grpimage;
	EditText ssubject;
	Bitmap imagebitmap=null;
	private static final String TAG = "upload";
	SharedPreferences shared;
	String responseMSG = "", exception = "", urlResponse = "", message = "";
	String usrid,group_id;
	int REQUEST_CAMERA = 0, SELECT_FILE = 1;
	private String imagepath=null,grpsubject;
	private static final String TAG_SUCCESS = "code";
	private static final String TAG_MESSAGE = "message";
ImageButton back;
	String Addurl= APP_DATA.BASE_URL1+"mode=add_group_team&group_name=&created_by=";
String upLoadServerUri= APP_DATA.BASE_URL1+"mode=change_group_picture&group_id="+group_id;
View view;

	TextView donerela;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.add_group,container,false);

		grpimage=(ImageView)view.findViewById(R.id.grpicon);
		ssubject=(EditText)view.findViewById(R.id.grpsub);
		donerela=(TextView)view.findViewById(R.id.create_grp);
		cd = new ConnectionDetector(getActivity());

		usrid =APP_DATA.userid;
		grpimage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectImage();
			}
		});

		donerela.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				grpsubject=ssubject.getText().toString();
				if(grpsubject!=null && grpsubject.length()>0)
				{
					if(imagebitmap==null)
					{
						imagebitmap = BitmapFactory.decodeResource(CreateGroup.this.getResources(),
								R.drawable.grpicon1);
						//new UploadTask().execute(imagebitmap);
						//new AddGrp().execute();
							if (cd.isConnectingToInternet()) {

							AddGrp();
						} else {
							Toast.makeText(getActivity(),
									"No Internet Connection", Toast.LENGTH_LONG)
									.show();
						}
					}
					else
					{
						//new AddGrp().execute();
						if (cd.isConnectingToInternet()) {
							AddGrp();
						} else {
							Toast.makeText(getActivity(),
									"No Internet Connection", Toast.LENGTH_LONG)
									.show();
						}

						//new UploadTask().execute(imagebitmap);
					}
				}
				else
				{
					ssubject.setError("Please Fill Subject");
					ssubject.requestFocus();
				}
			}
		});
		return view;
	}


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
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == SELECT_FILE)
				onSelectFromGalleryResult(data);
			else if (requestCode == REQUEST_CAMERA)
				onCaptureImageResult(data);
		}
	}

	private void onCaptureImageResult(Intent data) {
		Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();

		//new UploadTask().execute(thumbnail);
		thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
		imagebitmap=thumbnail;
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

//		Picasso.with(getActivity()).load(destination).resize(400,400).centerInside().placeholder(R.drawable.abc_dialog_material_background_dark) // optional
//				.error(R.drawable.abc_dialog_material_background_light).into(grpimage);
		//prfpic.setImageBitmap(thumbnail);
	}

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


		final int REQUIRED_SIZE = 200;
		int scale = 1;
		while (options.outWidth / scale / 2 >= REQUIRED_SIZE
				&& options.outHeight / scale / 2 >= REQUIRED_SIZE)
			scale *= 2;
		options.inSampleSize = scale;
		options.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeFile(selectedImagePath, options);
		imagebitmap=bm;
		//new UploadTask().execute(bm);
		 imagepath = selectedImagePath;
		// uploadFile(imagepath);
//		Picasso.with(getActivity()).load(selectedImagePath).resize(400, 400).centerInside().placeholder(R.drawable.abc_dialog_material_background_dark) // optional
//				.error(R.drawable.abc_dialog_material_background_light).into(grpimage);
//		//   prfpic.setImageBitmap(bm);
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

//					SharedPreferences.Editor editor = loginPreferences.edit();
//
//					editor.putString("image",APP_DATA.image);
//
//					editor.commit();
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


	private void AddGrp() {

		Addurl= APP_DATA.BASE_URL1+"mode=add_group_team&group_name="+grpsubject+"&created_by="+APP_DATA.userid;

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
				Addurl, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				Log.d(TAG1, response.toString());

				try {

					success = response.getInt(TAG_SUCCESS);

					if (success == 0) {

						Log.d("Group Created!", response.toString());

						JSONObject c=response.getJSONObject("response");


						group_id=c.getString("group_id").toString();
						upLoadServerUri= APP_DATA.BASE_URL1+"mode=change_group_picture&group_id="+group_id;
						//APP_DATA.location = c.getString("location");
						new UploadTask().execute(imagebitmap);
						APP_DATA.group_id=group_id;
						//return json.getString(TAG_MESSAGE);

					}else{


						Log.d("Add Group failure!", response.getString(TAG_MESSAGE));

						// json.getString(TAG_MESSAGE);



					}


				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(context,
							"Error: " + e.getMessage(),
							Toast.LENGTH_LONG).show();
				}

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG1, "Error: " + error.getMessage());
				Toast.makeText(context,
						error.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});

		APP_DATA.getInstance().addToRequestQueue(jsonObjReq);


	}


}
