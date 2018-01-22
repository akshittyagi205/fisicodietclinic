package in.fisicodietclinic.fisico;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

/**
 * Created by manyamadan on 05/12/17.
 */


public class UploadActivity extends AppCompatActivity {


private static int RESULT_LOAD = 1;
String img_Decodable_Str;
Button loadImageButton;
ImageView imgView1,imgView2;
CardView before,after;
int BEFORE_TAG=0,IMAGE_COUNT,CASE=0;
ProgressDialog dialog;
Uri selectedImage,selectedImage1;




@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.upload_fragment);
    loadImageButton = (Button)findViewById(R.id.buttonLoadPicture);
    imgView1 = (ImageView)findViewById(R.id.imgView1);
    imgView2 = (ImageView)findViewById(R.id.imgView2);
    before = (CardView)findViewById(R.id.beforecard);
   after = (CardView)findViewById(R.id.aftercard);
    dialog=new ProgressDialog(this);

    before.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //dialog.show();
            String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                BEFORE_TAG=1;
            if (EasyPermissions.hasPermissions(UploadActivity.this, galleryPermissions)) {

                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD);

            } else {
                EasyPermissions.requestPermissions(UploadActivity.this, "Access for storage",
                        101, galleryPermissions);
            }



        }
    });

    after.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            BEFORE_TAG=0;
            if (EasyPermissions.hasPermissions(UploadActivity.this, galleryPermissions)) {

                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD);

            } else {
                EasyPermissions.requestPermissions(UploadActivity.this, "Access for storage",
                        101, galleryPermissions);
            }



        }
    });
    loadImageButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.show();
            if(selectedImage != null && selectedImage1!= null)
            {
            IMAGE_COUNT = 2;
            }
            else
            {
                IMAGE_COUNT =1;
            }
            Map config = new HashMap();
            config.put("cloud_name", "diet");
            config.put("api_key", "658118659628992");
            config.put("api_secret", "K5lcTS6bwrgFRUhVo3n1OHwWNH8");
            final Cloudinary cloudinary = new Cloudinary(config);
            try {
                final Runnable runnable = new Runnable() {

                    @Override
                    public void run() {
                        try {
                            if( IMAGE_COUNT ==2 ) {
                                InputStream in = getContentResolver().openInputStream(selectedImage);
                                Map uploadResult = cloudinary.uploader().upload(in, ObjectUtils.emptyMap());
                                InputStream in1 = getContentResolver().openInputStream(selectedImage1);
                                Map uploadResult1 = cloudinary.uploader().upload(in1, ObjectUtils.emptyMap());
                                if(uploadResult1.containsKey("url") && uploadResult.containsKey("url"))
                                {
                                    CASE =1;
                                    Thread.currentThread().interrupt();
                                    runOnUiThread(new Runnable()
                                    {
                                        public void run()
                                        {
                                            dialog.dismiss();

                                            showToast();
                                            //Do your UI operations like dialog opening or Toast here
                                        }
                                    });
                                    //Toast.makeText(getActivity(),"Images Uploaded!!",Toast.LENGTH_LONG).show();
                                }
                                else
                                {

                                    CASE =2;
                                    Thread.currentThread().interrupt();
                                    runOnUiThread(new Runnable()
                                    {
                                        public void run()
                                        {
                                            dialog.dismiss();

                                            showToast();
                                            //Do your UI operations like dialog opening or Toast here
                                        }
                                    });

                                   // Toast.makeText(getActivity(),"Sorry!Images could not be Uploaded",Toast.LENGTH_LONG).show();
                                }

                            }
                            else
                            {

                                CASE=3;
                                Thread.currentThread().interrupt();
                                runOnUiThread(new Runnable()
                                {
                                    public void run()
                                    {
                                        dialog.dismiss();

                                        showToast();
                                        //Do your UI operations like dialog opening or Toast here
                                    }
                                });
                               // Toast.makeText(getActivity(),"Please attach both the images",Toast.LENGTH_LONG).show();

                            }


                        } catch (IOException e) {
                            //TODO: better error handling when image uploading fails
                            e.printStackTrace();
                        }
                    }
                };

                new Thread(runnable).start();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    });
}

public void showToast()
{

    if(CASE==1)
    {
        Toast.makeText(UploadActivity.this,"Images Uploaded!!",Toast.LENGTH_LONG).show();

    }
    else if(CASE ==2)
    {
        Toast.makeText(UploadActivity.this,"Sorry!Images could not be Uploaded",Toast.LENGTH_LONG).show();
    }
    else if(CASE ==3)
    {
        Toast.makeText(UploadActivity.this,"Please attach both the images",Toast.LENGTH_LONG).show();

    }

}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            // When an Image is picked
            if(BEFORE_TAG ==1){


            if (requestCode == RESULT_LOAD && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                 selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                img_Decodable_Str = cursor.getString(columnIndex);
                cursor.close();

                Bitmap d = BitmapFactory
                        .decodeFile(img_Decodable_Str);
                int nh = (int) ( d.getHeight() * (512.0 / d.getWidth()) );
                Bitmap scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
                // Set the Image in ImageView after decoding the String



                    imgView1.setImageBitmap(scaled);







            } else {
                Toast.makeText(UploadActivity.this, "Hey pick your image first",
                        Toast.LENGTH_LONG).show();
            }

            }
            else {
                if (requestCode == RESULT_LOAD && resultCode == RESULT_OK
                        && null != data) {
                    // Get the Image from data

                    selectedImage1 = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage1,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    img_Decodable_Str = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap d = BitmapFactory
                            .decodeFile(img_Decodable_Str);
                    int nh = (int) (d.getHeight() * (512.0 / d.getWidth()));
                    Bitmap scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
                    // Set the Image in ImageView after decoding the String


                    imgView2.setImageBitmap(scaled);

                }
            }

        } catch (Exception e) {
            Toast.makeText(UploadActivity.this, e+"", Toast.LENGTH_LONG)
                    .show();
        }

    }





}