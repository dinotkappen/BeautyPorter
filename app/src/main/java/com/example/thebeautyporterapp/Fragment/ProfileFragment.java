package com.example.thebeautyporterapp.Fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;


import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.thebeautyporterapp.Activity.MainActivity;
import com.example.thebeautyporterapp.Other.Utilities;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressFlower;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;
import static com.example.thebeautyporterapp.Activity.MainActivity.backArrow;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgFilter;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgMenu;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgSearch;
import static com.example.thebeautyporterapp.Activity.MainActivity.linearActionBar;
import static com.example.thebeautyporterapp.Activity.MainActivity.txt_actionbar_Title;


public class ProfileFragment extends Fragment {

    View rootView;

    ImageView imgEditNmae, imagEditPhone;
    TextView txtPassword, txtAddress, txt_customer_name;
    LinearLayout linearChangepwd, linearAddress;
    FrameLayout frameProfile;
    EditText editName, editEmail, editPhone;
    String strName, strUserId, strPhoto, strEmail, strPhone, strToken;

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int MY_GALLERY_REQUEST_CODE = 101;
    CircleImageView profile;
    ArrayList<Uri> img_List = new ArrayList<>();
    Bitmap photo = null;
    Uri selectedMediaUri;
    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    String encodedImage;
    Utilities utilities;
    int logedIn;
    String user_id, access_token;
    public static String photoResponse;
    CardView btnCardBookNow;
    EditText edtInput;
    TextView txtCancel, txtSave;
    Dialog profileUpdate;
    String field;
    String HawkAddress;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        strUserId = Hawk.get("user_id");
        strName = Hawk.get("name");
        strPhoto = Hawk.get("photo");
        strEmail = Hawk.get("email");
        strPhone = Hawk.get("phone");
        strToken = Hawk.get("access_token");
        logedIn = Hawk.get("logedIn", 0);
        user_id = Hawk.get("user_id", "");
        access_token = Hawk.get("access_token", "");
        Log.v("user_id", "" + user_id);
        Log.v("access_token", access_token);

        linearActionBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        imgMenu.setVisibility(View.GONE);
        backArrow.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setText(getString(R.string.my_account));
        imgFilter.setVisibility(View.GONE);
        imgSearch.setVisibility(View.GONE);


        Hawk.get("logInMetod", "normal");
        HawkAddress= Hawk.get("HawkAddress",HawkAddress);
        linearChangepwd = rootView.findViewById(R.id.linearChangepwd);
        linearAddress = rootView.findViewById(R.id.linearAddress);
        editName = rootView.findViewById(R.id.edt_Name);
        editEmail = rootView.findViewById(R.id.edt_Email);
        editPhone = rootView.findViewById(R.id.edt_Phone);
        txtAddress = rootView.findViewById(R.id.txt_address);
        imgEditNmae = rootView.findViewById(R.id.img_edit_name);
        imagEditPhone = rootView.findViewById(R.id.img_edit_phone);
        profile = rootView.findViewById(R.id.profile_image);
        txt_customer_name = (TextView) rootView.findViewById(R.id.txt_customer_name);
        frameProfile = rootView.findViewById(R.id.frameProfile);
        btnCardBookNow = (CardView) rootView.findViewById(R.id.btnCardBookNow);

        profileUpdate = new Dialog(getActivity());
        profileUpdate.requestWindowFeature(Window.FEATURE_NO_TITLE);
        profileUpdate.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        profileUpdate.setCancelable(false);
        profileUpdate.setContentView(R.layout.dilog_profile);
        edtInput = (EditText) profileUpdate.findViewById(R.id.edtInput);
        txtCancel = (TextView) profileUpdate.findViewById(R.id.txtCancel);
        txtSave = (TextView) profileUpdate.findViewById(R.id.txtSave);

        if (HawkAddress != null && !HawkAddress.isEmpty() && !HawkAddress.equals("null")) {
            txtAddress.setText(HawkAddress);
        }

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileUpdate.dismiss();
            }
        });
        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (field != null && !field.isEmpty() && !field.equals("null")) {
                    if(field.equals("Name"))
                    {
                        String name= edtInput.getText().toString();
                        if (name != null && !name.isEmpty() && !name.equals("null")) {
                            editName.setText(name);
                        }
                    }
                    else if(field.equals("Phone"))
                    {
                        String phone= edtInput.getText().toString();
                        if (phone != null && !phone.isEmpty() && !phone.equals("null")) {
                            editPhone.setText(phone);
                        }
                    }

                }

                profileUpdate.dismiss();
            }
        });


        if (strName != null && !strName.isEmpty() && !strName.equals("null")) {
            txt_customer_name.setText(strName);
        }
        if (strPhoto != null && !strPhoto.isEmpty() && !strPhoto.equals("null")) {
            Glide.with(getActivity())
                    .load(strPhoto)
                    .apply(new RequestOptions().placeholder(R.mipmap.logo).error(R.mipmap.logo))
                    .into(profile);
        }
        editName.setText(strName);
        editEmail.setText(strEmail);
        editPhone.setText(strPhone);

        btnCardBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    submitForm();
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                } catch (Exception ex) {
                    String msg = ex.getMessage().toString();
                    Log.v("btn_Update", msg);
                }
            }
        });

        permissionStatus = getActivity().getSharedPreferences("permissionStatus", MODE_PRIVATE);
        frameProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        imgEditNmae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edtInput.setText("");
                edtInput.setHint("Name");
                field="Name";
                profileUpdate.show();

            }
        });
        imagEditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtInput.setText("");
                edtInput.setHint("Phone");

                field="Phone";
                profileUpdate.show();

            }
        });


        linearChangepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                fragmentTransaction.replace(R.id.fragment_container, changePasswordFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        linearAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LocationFragment locationFragment = new LocationFragment();
                fragmentTransaction.replace(R.id.fragment_container, locationFragment);
                fragmentTransaction.addToBackStack(null);


                Bundle bundle = new Bundle();
                bundle.putString("name", editName.getText().toString());
                bundle.putString("phone", editPhone.getText().toString());
                bundle.putString("email", editEmail.getText().toString());
                locationFragment.setArguments(bundle);
                fragmentTransaction.commit();
            }
        });
        return rootView;
    }

    private void submitForm() {

        if (!validateName()) {
            return;
        }

        if (!validatePhone()) {
            return;
        }


        Update_Profile_api();
    }

    private boolean validateName() {
        if (editName.getText().toString().trim().isEmpty()) {
            editName.setError(getString(R.string.validFirstName));
            requestFocus(editName);
            return false;
        }

        return true;
    }

    private boolean validatePhone() {
        if (editPhone.getText().toString().trim().isEmpty()) {
            editPhone.setError(getString(R.string.validPhone));
            requestFocus(editPhone);
            return false;
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void selectImage() {
        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getActivity(), permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getActivity(), permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissionsRequired[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissionsRequired[1])
                        || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissionsRequired[2])) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Need Multiple Permissions");
                    builder.setMessage("This app needs Camera and Gallery permissions.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(getActivity(), permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                        }
                    });
                    builder.setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else if (permissionStatus.getBoolean(permissionsRequired[0], false)) {
                    //Previously Permission Request was cancelled with 'Dont Ask Again',
                    // Redirect to Settings after showing Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Need Multiple Permissions");
                    builder.setMessage("This app needs Camera and Gallery permissions.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            sentToSettings = true;
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                            Toast.makeText(getContext(), "Go to Permissions to Grant  Camera and Gallery", Toast.LENGTH_LONG).show();
                        }
                    });
                    builder.setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    //just request the permission
                    ActivityCompat.requestPermissions(getActivity(), permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                }


                SharedPreferences.Editor editor = permissionStatus.edit();
                editor.putBoolean(permissionsRequired[0], true);
                editor.commit();
            } else {
                //You already have the permission, just go ahead.
                proceedAfterPermission();
            }
        } catch (Exception ex) {
            Log.v("cv", ex.getMessage().toString());
        }
    }

    private void proceedAfterPermission() {


        // Toast.makeText(getContext(), "We got All Permissions", Toast.LENGTH_LONG).show();
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                //  boolean result=Utility.checkPermission(getActivity());
                if (items[item].equals("Take Photo")) {

                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, MY_CAMERA_REQUEST_CODE);

                } else if (items[item].equals("Choose from Library")) {

                    final Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    galleryIntent.setType("image/*");
                    galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), MY_GALLERY_REQUEST_CODE);
                    // startActivityForResult(galleryIntent, MY_GALLERY_REQUEST_CODE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {

            case 100:
                if (requestCode == 100) {
                    img_List.clear();
                    try {
                        if (requestCode == MY_CAMERA_REQUEST_CODE) {
                            photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
                            Log.v("photoMM", "" + photo);
                            profile.setImageBitmap(photo);
                            UploadImg();


                        }

                        Log.v("selectedMediaUri", "" + selectedMediaUri);
                    } catch (Exception ex) {
                        //Intent in = new Intent(getContext(), ThankyouActivity.class);
                        Log.v("cameraError", ex.getMessage().toString());

                        galleryAddPic();
                        Log.v("galleryAddPicMain", "galleryAddPic");


                    }
                }

                break;
            case 101:
                if (requestCode == 101) {
                    try {

                        selectedMediaUri = imageReturnedIntent.getData();
                        img_List.add(selectedMediaUri);

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                            // Log.d(TAG, String.valueOf(bitmap));


                            profile.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    } catch (Exception ex) {
                        String msg = ex.getMessage().toString();
                        Log.v("msg_error", msg);
                    }

                }

                break;
        }

    }

    public void UploadImg() {
        try {
            ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
            byte[] imageBytes = baos1.toByteArray();
            String imagePathStr = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), photo, "title", null);
            //  String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            selectedMediaUri = Uri.parse(imagePathStr);
            Log.v("photo", "" + photo);
            Log.v("b", "" + selectedMediaUri);
            Log.v("imagePathStr", "" + imagePathStr);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedMediaUri);
                // Log.d(TAG, String.valueOf(bitmap));


//                profileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            img_List.add(selectedMediaUri);
            String imageString = Arrays.toString(imageBytes);
            if (imageString.equals(null)) {
                encodedImage = "";
            } else {
                encodedImage = imageString;
            }
            //  encodedImage = imageString.replaceAll("\n", "");


//        JSONArray arr = new JSONArray();
//        arr.put("data:image/jpeg;base64," + encodedImage);
        } catch (Exception ex) {
            String msg = ex.getMessage().toString();
            Log.v("UploadImg", msg);
        }

    }

    String currentPhotoPath;

    private void galleryAddPic() {
        try {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File(currentPhotoPath);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            img_List.add(contentUri);
            String url = "" + contentUri;

//            Glide.with(getContext())
//                    .load(url)
//                    .placeholder(R.drawable.avatar)
//                    .into(profileImage);

            Glide.with(getContext())
                    .load(url)
                    .apply(new RequestOptions().placeholder(R.mipmap.logo).error(R.mipmap.logo))
                    .into(profile);
            Log.v("contentUri", "" + contentUri);
            getActivity().sendBroadcast(mediaScanIntent);
        } catch (Exception ex) {
            String msg = ex.getMessage().toString();
            Log.v("galleryAddPic", msg);
        }
    }







    private void Update_Profile_api() {

        final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                .build();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        String phone = editPhone.getText().toString();


        String URL_UPDATE_PROFILE = utilities.GetUrl() + "update_profile";

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL_UPDATE_PROFILE,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

//                        avi.hide();
//                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        try {

                            JSONObject obj = new JSONObject(new String(response.data));


                            String success = obj.getString("status");
                            if (success.equals("success")) {
                                String message = obj.getString("message");
                                String lkg;
                                // Toast.makeText(getContext(),""+message,Toast.LENGTH_SHORT).show();
                                JSONObject jsonObjectcontent = obj.getJSONObject("content");
                                if (jsonObjectcontent.length() > 0) {
                                    String user_id = jsonObjectcontent.getString("user_id");
                                    String email = jsonObjectcontent.getString("email");
                                    photoResponse = jsonObjectcontent.getString("photo");
                                    String phone = jsonObjectcontent.getString("phone");
                                    if (phone != null && !phone.isEmpty() && !phone.equals("null")) {
                                        editPhone.setText(phone);
                                    }
                                    if (photoResponse != null && !photoResponse.isEmpty() && !photoResponse.equals("null")) {

                                        try {
                                            Glide.with(getContext())
                                                    .load(photoResponse)
                                                    .apply(new RequestOptions().placeholder(R.mipmap.logo).error(R.mipmap.logo))
                                                    .into(profile);
                                        } catch (Exception ex) {
                                            Log.v("ex", ex.getMessage().toString());
                                        }
                                    }
                                }

                                Hawk.put("user_id", user_id);
                                Hawk.put("name", name);
                                Hawk.put("photo", photoResponse);
                                Hawk.put("email", email);
                                Hawk.put("phone", phone);
                                Hawk.put("access_token", access_token);
                                Hawk.put("logedIn", 1);


                                Log.v("nameprofileName", name);
                                Log.v("photoResponseProfile", photoResponse);

                                lkg = photoResponse;
                                MainActivity.updateNavHeaderView(photoResponse);
                                dialog.dismiss();
                                Toast.makeText(getContext(),"Profile updated successfully",Toast.LENGTH_SHORT).show();


//                                Glide.with(profile_image.getContext()).load(profile_img_str).placeholder(R.drawable.avatar)
//                                        .dontAnimate().into(profile_image);


                                //  ((MainActivity) getActivity()).updateNavHeaderView();


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Log.v("TimeoutError", "TimeoutError");
                            //This indicates that the reuest has either time out or there is no connection
                        } else if (error instanceof AuthFailureError) {
                            Log.v("AuthFailureError", "AuthFailureError");
                            //Error indicating that there was an Authentication Failure while performing the request
                        } else if (error instanceof ServerError) {
                            Log.v("ServerError", "ServerError");
                            //Indicates that the server responded with a error response
                        } else if (error instanceof NetworkError) {
                            Log.v("NetworkErrorParseError", "NetworkError");
                            //Indicates that there was network error while performing the request
                        } else if (error instanceof ParseError) {
                            Log.v("ParseError", "ParseError");
                            // Indicates that the server response could not be parsed
                        }
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("contact_no", phone);
                params.put("user_id", user_id);

                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                for (int i = 0; i < img_List.size(); i++) {
                    InputStream iStream = null;
                    try {
                        Log.v("msg_att", "");
                        iStream = getActivity().getContentResolver().openInputStream(img_List.get(i));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                    params.put("photo", new DataPart("file_avatar.jpg",
                            getFileDataFromDrawable(getActivity(), img_List.get(i))));
                    // params.put("images[" + i + "]", new DataPart("image" + i + ".jpg", inputData, "image/jpeg"));


                }


                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + access_token);
                headers.put("Accept", "application/json");

                return headers;
            }
        };

//        //adding the request to volley
//        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);


        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding the string request to request queue
        requestQueue.add(volleyMultipartRequest);


    }

    public byte[] getFileDataFromDrawable(Context context, Uri uri) {
        String sd = uri.toString();
        Log.v("sd", sd);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Log.v("image", "" + uri.toString().contains("image"));
        if (uri.toString().contains("image")) {

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();


        }


        return byteArrayOutputStream.toByteArray();
    }
}
