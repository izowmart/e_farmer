package com.example.e_farmer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.example.e_farmer.models.Finance;
import com.example.e_farmer.models.LandAndCrop;
import com.example.e_farmer.models.User;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class LandAndCropMngt extends AppCompatActivity {

    private static final String TAG = "LandAndCropMngt";

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
    private Box<LandAndCrop> landAndCropBox;
    private BoxStore farmerApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_and_crop_mngt);

        //objectBox initialization
        farmerApp = FarmerApp.getBoxStore();
        landAndCropBox = farmerApp.boxFor(LandAndCrop.class);

        // This convention should follow to have a successful toolbar set with the correct set title.
        toolbar = findViewById(R.id.toolbar_land_crop_mngt);
        toolbar.setTitle("Add Land & Crop Activity");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        landName = findViewById(R.id.land_crop_name);
        squareNumber = findViewById(R.id.land_crop_square);
        landTask = findViewById(R.id.land_crop_task);
        taskStartDate = findViewById(R.id.land_crop_start);
        taskEndDate = findViewById(R.id.land_crop_end);
        landDescription = findViewById(R.id.land_crop_descr);
        landImage = findViewById(R.id.land_crop_image);

        savingBtn = findViewById(R.id.saving_land_btn);
        progressBar = findViewById(R.id.land_progress_bar);

        saveOperations = findViewById(R.id.save_land_crop_btn);

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

    private void setDate(final EditText editText){
        DatePickerDialog datePickerDialog;
        int year,month,dayOfMonth;
        Calendar calendar;

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(LandAndCropMngt.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                editText.setText(day+"-"+(month+1)+"-"+year);

            }
        },year, month, dayOfMonth);

        datePickerDialog.show();

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

                        // This where everything is collected and stored in our local database
                        User user = new User();
                        landAndCrop = new LandAndCrop();

                        landAndCrop.setName(name);
                        landAndCrop.setSquare_number(square_number);
                        landAndCrop.setTask(task);
                        landAndCrop.setStart_date(start_date);
                        landAndCrop.setCompletion_date(completion_date);
                        landAndCrop.setDescription(description);
                        landAndCrop.setLandImage(currentPhotoPath);

                        landAndCrop.user.setTarget(user);
                        landAndCropBox.put(landAndCrop);
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
