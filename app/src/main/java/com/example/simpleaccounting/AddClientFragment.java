package com.example.simpleaccounting;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddClientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddClientFragment extends Fragment {




    private static final String TAG = "AddClientFragment";
    private static final int PICK_CONTACT =1;
    DatabaseHelperAddClient databaseHelperAddClient;

    ImageView iv;
    String myuri=null;
    Bitmap bitmap;
    private static final int REQUEST_CODE = 1;

    private Uri imageUri;
    ImageButton clientNameButton ;
    EditText edClientName;
    EditText edfirmName;
   // EditText edgstin;
    EditText etEmail;
    ImageView client_profile_image;
    static EditText clientPhoneNo;
    private String phoneNumber;
  //  private ImageButton search_gst;
    private Button AddClientButton;
    //image pick constants
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddClientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddClientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddClientFragment newInstance(String param1, String param2) {
        AddClientFragment fragment = new AddClientFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)  {
        super.onViewCreated(view, savedInstanceState);

        clientNameButton = getView().findViewById(R.id.add_client_ClientNameIcon);
        etEmail = getView().findViewById(R.id.add_item_gstinP);
        edfirmName = getView().findViewById(R.id.add_item_hsnCode);
        edClientName = (EditText) getView().findViewById(R.id.add_item_itemName);
        client_profile_image = (ImageView) getView().findViewById(R.id.client_profile_image);
       // edgstin = getView().findViewById(R.id.add_item_barcode);
        clientPhoneNo = (EditText) getView().findViewById(R.id.add_item_purchasePrice);
       // search_gst = getView().findViewById(R.id.add_client_gstinSearchIcon);
        AddClientButton=getView().findViewById(R.id.add_client_Btn);
        databaseHelperAddClient = new DatabaseHelperAddClient(getActivity());


        //init permission arrays
      //  cameraPermissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
       // storagePermissions = new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE};



        client_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                selectImage(getContext());
                //show image pick dialogue
               // requestCameraPermission();
                //requestStoragePermission();
                imagePickDialog();
            }
        });


        AddClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AddData()) {
//                    NavController navController = Navigation.findNavController(view);
//                    navController.navigate(R.id.action_addClientFragment_to_clientAllFragment);
//                    SalesFragment sf = new SalesFragment();
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                    transaction.replace(R.id.mainLayout2,sf);
//                    transaction.commit();
                    SalesFragment sf = new SalesFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.mainLayout,sf);
                    transaction.commit();



                }
            }
        });

        //open GST Search Portal
//        search_gst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gotoUrl("https://services.gst.gov.in/services/searchtp");
//            }
//        });


        //Open Contact App
        clientNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://contacts");
                Intent intent = new Intent(Intent.ACTION_PICK, uri);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    private void imagePickDialog() {
        //options to display in dialog
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose client profile picture");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
//                    if(!checkCameraPermissions()){
//                       // requestCameraPermission();
//                    }else{
                        pickFromCamera();

//                    }

                } else if (options[item].equals("Choose from Gallery")) {
//                    if(!checkStoragePermission()){
//                        requestStoragePermission();
//                    }else{
                        pickFromGallery();
                   // }

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    private void pickFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK);
        pickPhoto.setType("image/*"); // we want only images
        Toast.makeText(getContext(),"pick from gallery",Toast.LENGTH_SHORT).show();
        startActivityForResult(pickPhoto , IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, "Image Title");
//        values.put(MediaStore.Images.Media.DESCRIPTION, "Image description");
//        //put image uri
//        imageUri = getActivity().getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//
//        //intent to open camera for image
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(cameraIntent, IMAGE_PICK_GALLERY_CODE);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission(){
        //check if storage permission is enabled or not

        boolean result = ContextCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }
    private void  requestStoragePermission(){
        //request the storage permission
        Toast.makeText(getContext(), "storage", Toast.LENGTH_SHORT).show();
        //ActivityCompat.requestPermissions(getActivity(),storagePermissions, STORAGE_REQUEST_CODE);
    }
    private boolean checkCameraPermissions(){
        boolean result = ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }


    public static EditText getclientPhoneNo(){
        return clientPhoneNo;
    }



    public boolean AddData(){

        String clientName = edClientName.getText().toString().trim();
        String firmName = edfirmName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = clientPhoneNo.getText().toString().trim();

        if(clientName.isEmpty()){
            edClientName.setError("Client name can't be empty.");
            edClientName.requestFocus();
            return false;
        }
        if(phone.isEmpty()){
            clientPhoneNo.setError("Phone number can't be empty.");
            clientPhoneNo.requestFocus();
            return false;
        }


        if(databaseHelperAddClient.addData(clientName,myuri,firmName,phone,email)){
            Toast.makeText(getContext(),"Client added successfully",Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(getContext(),"Client not added.",Toast.LENGTH_SHORT).show();
            return  false;
        }
    }
//    public Uri getImageUri(Context inContext, Bitmap inImage) {
////        if(inImage==null){
////        Uri imgUri=Uri.parse(String.valueOf(R.drawable.share));
////            //client_profile_image.setImageURI(null);
////           // client_profile_image.setImageURI(imgUri);
////            return imgUri;
////        }else {
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//            String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
////            if(path!=null) {
//                return Uri.parse(path);
////            }else{
////                Uri imgUri=Uri.parse(String.valueOf(R.drawable.share));
// //               return imgUri;
//            }
       // }
//    }
public Uri getImageUri(Context inContext, Bitmap inImage) {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
    String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
    if (path!=null) {
        return Uri.parse(path);
    }
    return null;
}


    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                String[] projection = { ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME };

                Cursor cursor = getContext().getContentResolver().query(uri, projection,
                        null, null, null);
                cursor.moveToFirst();

                int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberColumnIndex);

                int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(nameColumnIndex);

//                int emailColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTENT_URI.toString());
                String email = cursor.getString(nameColumnIndex);
//                Uri my_contact_Uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(Contact_Id));
//                InputStream photo_stream = ContactsContract.Contacts.openContactPhotoInputStream(getContext().getContentResolver(), my_contact_Uri);
//                BufferedInputStream buf = new BufferedInputStream(photo_stream);
//                Bitmap my_btmp = BitmapFactory.decodeStream(buf);
//                client_profile_image.setImageBitmap(my_btmp);


              // client_profile_image.setImageURI(openPhoto());



                clientPhoneNo.setText(number);
                edClientName.setText(name);
                //etEmail.setText(email);

            }
        }
        if (requestCode == IMAGE_PICK_CAMERA_CODE) {
            try {
                bitmap = (Bitmap) data.getExtras().get("data");
                client_profile_image.setImageBitmap(bitmap);

               // img = getEncoded64ImageStringFromBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == IMAGE_PICK_GALLERY_CODE) {

            try {
                if(data!=null)
                { // user selects some Image
                    //filePath = data.getData();

                    if (data.getData() != null) {
                        myuri= data.getData().toString();
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                        client_profile_image.setImageBitmap(bitmap);
                       // img = getEncoded64ImageStringFromBitmap(bitmap);
                    }
                }
                else
                { // user simply backpressed from gallery
                    //Toast.makeText(getContext(),"hello",Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public ArrayList<String> getNameEmailDetails(){
        ArrayList<String> names = new ArrayList<String>();
        ContentResolver cr = getContext().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor cur1 = cr.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                while (cur1.moveToNext()) {
                    //to get the contact names
                    String name=cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    Log.e("Name :", name);
                    String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    Log.e("Email", email);
                    if(email!=null){
                        names.add(name);
                    }
                }
                cur1.close();
            }
        }
        return names;
    }
    public ByteArrayInputStream openPhoto(long contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = getContext().getContentResolver().query(photoUri,
                new String[] {ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    return new ByteArrayInputStream(data);
                }
            }
        } finally {
            cursor.close();
        }
        return null;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_client, container, false);
    }
}