package cabiso.daphny.com.gcompanion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import cabiso.daphny.com.gcompanion.Fragments.ImageHolder;

public class Camera extends AppCompatActivity{

    static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    private ImageView imageView;
    private Button button;
    private FrameLayout firstFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        firstFrame = (FrameLayout)findViewById(R.id.FirstFrame);
        button = (Button)findViewById(R.id.btnSave);
        imageView = (ImageView)findViewById(R.id.imgPhotoSaver);

            if(savedInstanceState==null){
                Fragment one = ImageHolder.newInstance("INPUTS");
                FragmentTransaction first = getSupportFragmentManager().beginTransaction();
                first.replace(R.id.FirstFrame,one).commit();

            }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Camera.this,MainActivity.class);
                startActivity(intent);
            }
        });
        dispatchTakePictureIntent();

    }

//    @Override
//    public void onClick(View v) {
//        startSaving();
//    }
//
//    // Save button funtion
//    private void startSaving() {
//        mProgress.setMessage("Uploading Your Data..");
//
////        final String descrition_txt = mDescription.getText().toString().trim();
////        if (!TextUtils.isEmpty(descrition_txt) && imageBitmap!=null){
////            mProgress.show();
//            StorageReference filepath= mStorage.child("Saved_Images").child(imageUri.getLastPathSegment());
//            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(Camera.this,"Uploading Finished!",Toast.LENGTH_LONG).show();
//
//                }
//            }) ;
//            mProgress.dismiss();
////        }
//    }
//    @Override
//    protected void onStart() {
//        if (imageBitmap==null ) {
//            Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent,CAMERA_REQUEST_CODE);
//        }
//
//        super.onStart();
//    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==CAMERA_REQUEST_CODE && resultCode==RESULT_OK){
//            Bundle extras = data.getExtras();
//            imageBitmap = (Bitmap) extras.get("data");
//            imageView.setImageBitmap(imageBitmap);
//// Actually this uri is null, im confuse in this
//
//            Uri imageuri= data.getData();
//            imageView.setImageURI(imageuri);
//
//
//            StorageReference filepath= mStorage.child("Saved_Images").child(imageUri.getLastPathSegment());
//            filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                    String user_id = mAuth.getInstance().getCurrentUser().getUid();
//                    if(imageView!=null){
////                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
////                        //DatabaseReference newPost = mDatabase.child(user_id).push();
////                        DatabaseReference newPost = mDatabase.push();
////                        // newPost.child("title").setValue(title_txt);
////
////                        newPost.child("image").setValue(downloadUrl.toString());
//                    }
//
//                    Toast.makeText(Camera.this, "worked ", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }}


        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == MainActivity.RESULT_OK){
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // convert byte array to Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);
                imageView.setImageBitmap(bitmap);

                //fragment
            }
        }
    }

}
