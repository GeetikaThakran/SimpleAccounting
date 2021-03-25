package com.example.simpleaccounting;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddSupplierFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSupplierFragment extends Fragment {

    DatabaseHelperAddSupplier databaseHelperAddSupplier;
    Bitmap bitmap;
    String imagepath;
    private static final int REQUEST_CODE = 1;

    ImageButton supplierNameButton ;
    EditText edSupplierName;
    EditText edfirmNameS;
    EditText etEmailS;
    ImageView supplier_profile_image;
    static EditText supplierPhoneNo;
    private Button AddSupplierButton;
    //image pick constants
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;

    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddSupplierFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddSupplierFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddSupplierFragment newInstance(String param1, String param2) {
        AddSupplierFragment fragment = new AddSupplierFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        supplierNameButton = getView().findViewById(R.id.add_supplier_SupplierNameIcon);
        etEmailS = getView().findViewById(R.id.email_addSupplier);
        edfirmNameS = getView().findViewById(R.id.company_addSupplier);
        edSupplierName = (EditText) getView().findViewById(R.id.supplierName_add_supplier);
        supplier_profile_image = (ImageView) getView().findViewById(R.id.supplier_profile_image);
        // edgstin = getView().findViewById(R.id.add_item_barcode);
        supplierPhoneNo = (EditText) getView().findViewById(R.id.phone_addSupplier);
        // search_gst = getView().findViewById(R.id.add_client_gstinSearchIcon);
        AddSupplierButton=getView().findViewById(R.id.add_supplier_Btn);
        databaseHelperAddSupplier = new DatabaseHelperAddSupplier(getActivity());

        supplier_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
            }
        });

        AddSupplierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AddDataSupplier()) {
                    PurchaseFragment sf = new PurchaseFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.mainLayoutSupplier,sf);
                    transaction.commit();
                }
            }
        });

        //Open Contact App
        supplierNameButton.setOnClickListener(new View.OnClickListener() {
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
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }
    public static EditText getSupplierPhoneNo(){
        return supplierPhoneNo;
    }

    public boolean AddDataSupplier(){
//        String img = getRealPathFromURI(bitmap).toString();


        String supplierName = edSupplierName.getText().toString().trim();
        String firmName = edfirmNameS.getText().toString().trim();
        String email = etEmailS.getText().toString().trim();
        String phone = supplierPhoneNo.getText().toString().trim();

        if(supplierName.isEmpty()){
            edSupplierName.setError("Supplier name can't be empty.");
            edSupplierName.requestFocus();
            return false;
        }
        if(phone.isEmpty()){
            supplierPhoneNo.setError("Phone number can't be empty.");
            supplierPhoneNo.requestFocus();
            return false;
        }
        if(databaseHelperAddSupplier.addDataSupplier(supplierName,imagepath,firmName,phone,email)){
            Toast.makeText(getContext(),"Supplier added successfully",Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(getContext(),"Supplier not added.",Toast.LENGTH_SHORT).show();
            return  false;
        }
    }

//        public Uri getImageUri(Context inContext, Bitmap inImage) {
//            if(inImage!=null) {
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//                String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//                Uri ans = Uri.parse(path);
//                return ans;
//               }else{
//                   Uri imgUri = Uri.parse("android.resource://com.example.simpleaccounting/" + R.drawable.share);
//                   return imgUri;
//               }
//            }
//public  Uri bitmapToFile(Bitmap bitmap) {
//    // File name like "image.png"
//    //create a file to write bitmap data
//    File file = null;
//    try {
//        file = new File(Environment.getExternalStorageDirectory() + File.separator + System.currentTimeMillis()+"jpg");
//        file.createNewFile();
//
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
//        byte[] bitmapdata = bos.toByteArray();
//
//        FileOutputStream fos = new FileOutputStream(file);
//        fos.write(bitmapdata);
//        fos.flush();
//        fos.close();
//        return Uri.parse(file.getAbsolutePath());
//    } catch (Exception e) {
//        e.printStackTrace();
//        return Uri.parse(file.getAbsolutePath()); // it will return null
//    }
//}
public String getRealPathFromURI(Uri contentURI, Activity context) {
    String[] projection = { MediaStore.Images.Media.DATA };
    @SuppressWarnings("deprecation")
    Cursor cursor = context.managedQuery(contentURI, projection, null,
            null, null);
    if (cursor == null)
        return null;
    int column_index = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    if (cursor.moveToFirst()) {
        String s = cursor.getString(column_index);
        return s;
    }
    // cursor.close();
    return null;
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



                supplierPhoneNo.setText(number);
                edSupplierName.setText(name);
                //etEmail.setText(email);

            }
        }
        if (requestCode == IMAGE_PICK_CAMERA_CODE) {
            try {
                imagepath=data.getData().toString();
                bitmap = (Bitmap) data.getExtras().get("data");
                supplier_profile_image.setImageURI(data.getData());

                //img = getEncoded64ImageStringFromBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == IMAGE_PICK_GALLERY_CODE) {
            try {
                if(data!=null)
                { // user selects some Image
                    //filePath = data.getData();
                    if (data.getData() != null) {
                        imagepath=data.getData().toString();
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                        supplier_profile_image.setImageURI(data.getData());
                        //img = getEncoded64ImageStringFromBitmap(bitmap);
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_add_supplier, container, false);
        return view;
    }
}