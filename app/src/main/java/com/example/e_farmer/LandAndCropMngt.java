package com.example.e_farmer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.e_farmer.fragments.MyLandAndCropMngt;
import com.example.e_farmer.models.LandAndCrop;
import com.example.e_farmer.viewmodels.LandAndCropViewmodel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class LandAndCropMngt extends AppCompatActivity {

    private static final String TAG = "LandAndCropMngt";
    private static final int MY_PERMISSIONS_REQUEST_CODE = 154;
    public static final int REQUEST_TAKE_PHOTO = 51;

    private String currentPhotoPath;
    private Bitmap mImageBitmap;

    private Toolbar toolbar;
    private EditText landName, squareNumber, landTask, taskStartDate, taskEndDate, landDescription;

    private Button saveOperations, savingBtn;
    private ProgressBar progressBar;
    private ImageView landImage;
    private String name, square_number, task, start_date, completion_date, description;

    private LandAndCrop landAndCrop;
    private String userId;
    private LandAndCropViewmodel landAndCropViewmodel;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_and_crop_mngt);

        userId = Settings.getUserId();
        landAndCropViewmodel = ViewModelProviders.of(this).get(LandAndCropViewmodel.class);

        landName = findViewById(R.id.land_crop_name);
        squareNumber = findViewById(R.id.land_crop_square);
        landTask = findViewById(R.id.land_crop_task);
        taskStartDate = findViewById(R.id.land_crop_start);
        taskEndDate = findViewById(R.id.land_crop_end);
        landDescription = findViewById(R.id.land_crop_descr);
        landImage = findViewById(R.id.land_crop_image);


        // This convention should follow to have a successful toolbar set with the correct set title.
        toolbar = findViewById(R.id.toolbar_land_crop_mngt);
        intent = getIntent();

        if (intent.hasExtra(MyLandAndCropMngt.LAND_NAME)) {
            toolbar.setTitle("Edit Land & Crop Activity");

            landName.setText(intent.getStringExtra(MyLandAndCropMngt.LAND_NAME));
            squareNumber.setText(intent.getStringExtra(MyLandAndCropMngt.SQUARE_NUMBER));
            landTask.setText(intent.getStringExtra(MyLandAndCropMngt.TASK_NAME));
            taskStartDate.setText(intent.getStringExtra(MyLandAndCropMngt.TASK_START));
            taskEndDate.setText(intent.getStringExtra(MyLandAndCropMngt.TASK_END));
            landDescription.setText(intent.getStringExtra(MyLandAndCropMngt.LAND_DESCRIPTION));
        } else {
            toolbar.setTitle("Add Land & Crop Activity");
        }

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        savingBtn = findViewById(R.id.saving_land_btn);
        progressBar = findViewById(R.id.land_progress_bar);

        saveOperations = findViewById(R.id.save_land_crop_btn);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }
        landImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        saveOperations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLandCropActivity();
            }
        });

        taskStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(taskStartDate);
            }
        });
        taskEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(taskEndDate);
            }
        });
    }

    private void setDate(final EditText editText) {
        DatePickerDialog datePickerDialog;
        int year, month, dayOfMonth;
        Calendar calendar;

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(LandAndCropMngt.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                editText.setText(day + "-" + (month + 1) + "-" + year);

            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();

    }
    protected void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_CONTACTS)
                + ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Do something, when permissions not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Camera, Read Contacts and Write External" +
                        " Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                LandAndCropMngt.this,
                                new String[]{
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.READ_CONTACTS,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
                builder.setNeutralButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_CONTACTS,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        } else {
            // Do something, when permissions are already granted
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                // When request is cancelled, the results array are empty
                if ((grantResults.length > 0) &&(grantResults[0]+ grantResults[1]+ grantResults[2]== PackageManager.PERMISSION_GRANTED)) {
                    // Permissions are granted

                    Toast.makeText(this, "Permissions granted.", Toast.LENGTH_SHORT).show();
                } else {
                    // Permissions are denied
                    Toast.makeText(this, "Permissions denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            This magic saves much when converting a bitmap from a string.
            Toast.makeText(this, "This is image uri : " + currentPhotoPath, Toast.LENGTH_SHORT).show();
            try {
                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(currentPhotoPath));

                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.placeholder);

                Glide.with(getApplicationContext())
                        .applyDefaultRequestOptions(options)
                        .load(mImageBitmap)
                        .into(landImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            animalImage.setImageBitmap(imageBitmap);
        }
    }

    private void addLandCropActivity() {
        name = landName.getText().toString().trim();
        square_number = squareNumber.getText().toString().trim();
        task = landTask.getText().toString().trim();
        start_date = taskStartDate.getText().toString().trim();
        completion_date = taskEndDate.getText().toString().trim();
        description = landDescription.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            landName.setError("Field Required!");
        } else if (TextUtils.isEmpty(square_number)) {
            squareNumber.setError("Field Required!");
        } else if (TextUtils.isEmpty(task)) {
            landTask.setError("Field Required!");
        } else if (TextUtils.isEmpty(start_date)) {
            taskStartDate.setError("Field Required!");
        } else if (TextUtils.isEmpty(completion_date)) {
            taskEndDate.setError("Field Required!");
        } else if (TextUtils.isEmpty(description)) {
            landDescription.setError("Field Required!");
        } else {

            AsyncTask<Void, Void, Void> land = new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressBar.setVisibility(View.VISIBLE);
                    saveOperations.setVisibility(View.GONE);
                    savingBtn.setVisibility(View.VISIBLE);
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        Thread.sleep(1500);

                        if (intent.hasExtra(MyLandAndCropMngt.LAND_NAME)) {
                            LandAndCrop eLandAndCrop = new LandAndCrop(userId, name, square_number, task, start_date, completion_date, description, currentPhotoPath);
                            eLandAndCrop.setId(intent.getIntExtra(MyLandAndCropMngt.LAND_ID,-1));

                            landAndCropViewmodel.update(eLandAndCrop);
                        } else {
                            landAndCrop = new LandAndCrop(userId, name, square_number, task, start_date, completion_date, description, currentPhotoPath);
                            landAndCropViewmodel.insert(landAndCrop);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }


                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    progressBar.setVisibility(View.GONE);
                    saveOperations.setVisibility(View.VISIBLE);
                    savingBtn.setVisibility(View.GONE);

                    Intent toDashboardActivity = new Intent(LandAndCropMngt.this, MainActivity.class);
                    startActivity(toDashboardActivity);
                    finish();
                    Toast.makeText(LandAndCropMngt.this, "Land & Corp added successfully", Toast.LENGTH_SHORT).show();

                }
            };

            land.execute();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
