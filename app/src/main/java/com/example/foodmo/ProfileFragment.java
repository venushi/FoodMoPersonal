package com.example.foodmo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    StorageReference storageReference;
    String storagePath = "Users_Profile_Cover_Imgs/";

    ImageView avatarIv,coverIv;
    TextView nameTv,emailTv,phoneTv;
    FloatingActionButton fab;

    ProgressDialog pd;

    private static final int CAMERA_REQUEST_CODE=100;
    private static final int STORAGE_REQUEST_CODE=200;
    private static final int IMAGE_PICK_GALLERY_CODE=300;
    private static final int IMAGE_PICK_CAMERA_CODE=400;


    String cameraPermissions[];
    String storagePermissions[];

    Uri image_uri;

    String profileOrCoverPhoto;






    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Social_Users");
        storageReference= FirebaseStorage.getInstance().getReference();

        cameraPermissions = new String []{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String []{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        avatarIv = view.findViewById(R.id.avatarIv);
        fab = view.findViewById(R.id.fab);
        coverIv = view.findViewById(R.id.coverIv);


        nameTv = view.findViewById(R.id.nameTv);
        emailTv = view.findViewById(R.id.emailTv);
        phoneTv = view.findViewById(R.id.phoneTv);
        pd=new ProgressDialog(getActivity());


        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    String name = ""+ds.child("name").getValue();
                    String email = ""+ds.child("email").getValue();

                    String phone = ""+ds.child("phone").getValue();
                    String image = ""+ds.child("image").getValue();
                    String cover = ""+ds.child("cover").getValue();


                    nameTv.setText(name);
                    emailTv.setText(email);
                    phoneTv.setText(phone);

                    try{
                        Picasso.get().load(image).into(avatarIv);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.ic_add_image).into(avatarIv);
                    }

                    try{
                        Picasso.get().load(cover).into(coverIv);
                    }
                    catch (Exception e){
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEdit();
                showImagePic();
            }
        });


        return view;


    }

    private boolean checkStoragePer(){

        boolean result = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==(PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestStoragePermission(){
       requestPermissions(storagePermissions,STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPer(){

        boolean result = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)
                ==(PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==(PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void requestCameraPermission(){
        requestPermissions(cameraPermissions,CAMERA_REQUEST_CODE);
    }



    private void showEdit() {

        String options[]={"Edit Profile Picture","Edit Cover Photo","Edit Name","Edit Phone"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Choose Action");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){

                    pd.setMessage("Updating Profile Picture");

                    profileOrCoverPhoto = "image";

                    showImagePic();

                }else if(i ==1){

                    pd.setMessage("Updating Cover Photo");
                    profileOrCoverPhoto = "cover";


                    showImagePic();


                }else if(i ==2){

                    pd.setMessage("Updating Name");
                    showNamePhoneUpdateDialog("name");


                }else if(i ==3){

                    pd.setMessage("Updating Phone");
                    showNamePhoneUpdateDialog("phone");



                }
            }
        });
        builder.create().show();

    }

    private void showImagePic() {

        String options[]={"Camera","Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Pick Image from");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){

                    if (!checkCameraPer()){
                        requestCameraPermission();
                    }
                    else{
                        pickCamera();
                    }




                }else if(i ==1){

                    if(!checkStoragePer()){
                        requestStoragePermission();
                    }
                    else{
                        pickGallery();
                    }



                }
            }
        });
        builder.create().show();




    }

    private void showNamePhoneUpdateDialog(String key) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update"+key);
        LinearLayout linearLayout= new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        EditText editText = new EditText(getActivity());
        editText.setHint("Enter "+key);
        linearLayout.addView(editText);
        linearLayout.setPadding(10,10,10,10);

        builder.setView(linearLayout);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String value = editText.getText().toString().trim();

                if(!TextUtils.isEmpty(value)){
                    pd.show();
                    HashMap<String,Object> result = new HashMap<>();
                    result.put(key,value);
                    databaseReference.child(user.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pd.dismiss();
                                    Toast.makeText(getActivity(),"Updated",Toast.LENGTH_SHORT).show();


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(getActivity(),""+e.getMessage(),Toast.LENGTH_SHORT).show();



                                }
                            });


                }
                else{
                    Toast.makeText(getActivity(),"Please Enter"+key,Toast.LENGTH_SHORT).show();

                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });

       builder.create().show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {



        switch(requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean cameraAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[1]==PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && writeStorageAccepted){
                        pickCamera();
                    }
                    else{

                        Toast.makeText(getActivity(),"Please enable camera and storage permission",Toast.LENGTH_SHORT).show();
                    }

                }
            }
            break;

            case STORAGE_REQUEST_CODE:{

                if (grantResults.length>0){
                    boolean writeStorageAccepted = grantResults[1]==PackageManager.PERMISSION_GRANTED;

                    if( writeStorageAccepted){
                        pickGallery();
                    }
                    else{

                        Toast.makeText(getActivity(),"Please enable storage permission",Toast.LENGTH_SHORT).show();
                    }

                }

            }
            break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if(requestCode ==IMAGE_PICK_GALLERY_CODE){
                if(requestCode ==IMAGE_PICK_GALLERY_CODE){

                    image_uri =data.getData();

                    uploadProfileCover(image_uri);

                }
                if(requestCode ==IMAGE_PICK_GALLERY_CODE){

                    uploadProfileCover(image_uri);


                }
            }
        }
        
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadProfileCover(Uri uri) {


        pd.show();
        String filePathAndName =
                storagePath+""+profileOrCoverPhoto+"_"+user.getUid();

        StorageReference storageReference2nd = storageReference.child(filePathAndName);
        storageReference2nd.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();

                        if(uriTask.isSuccessful()){
                            HashMap<String,Object>results=new HashMap<>();

                            results.put(profileOrCoverPhoto,downloadUri.toString());
                            databaseReference.child(user.getUid()).updateChildren(results)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            pd.dismiss();
                                            Toast.makeText(getActivity(),"Image Updated.....",Toast.LENGTH_SHORT).show();


                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            pd.dismiss();
                                            Toast.makeText(getActivity(),"Error in Image update",Toast.LENGTH_SHORT).show();


                                        }
                                    });

                        }
                        else{
                            pd.dismiss();

                            Toast.makeText(getActivity(),"Some error occured",Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        pd.dismiss();
                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();


                    }
                });


    }

    private void pickCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Temp Pic");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Temp Description");

        image_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);


    }

    private void pickGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,IMAGE_PICK_CAMERA_CODE);
    }
}