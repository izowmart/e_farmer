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

import com.example.e_farmer.models.Machine;
import com.example.e_farmer.viewmodels.MachineViewmodel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyMachinery extends AppCompatActivity {
    private static final String TAG = "MyMachinery";
    public static final int REQUEST_TAKE_PHOTO = 61;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 2548;
    private String currentPhotoPath;

    private Bitmap mImageBitmap;
    private Toolbar toolbar;
    private EditText machineName, machineType, machineRegYear, machinePurchaceDate, machineOriginalPrice, machineCurrentPrice, machinemilage, machineNotes;
    private Button saveMachine, savingBtn;
    private ProgressBar progressBar;
    private ImageView machineImage;
    private String name, type, registration_year, purchase_date, original_price, current_price, milage, notes;

    private Machine machine;
    private String userId;
    private MachineViewmodel machineViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_machinery);

        userId = Settings.getUserId();
        machineViewmodel = ViewModelProviders.of(this).get(MachineViewmodel.class);

        // This convention should follow to have a successful toolbar set with the correct set title.
        toolbar = findViewById(R.id.toolbar_machinery);
        toolbar.setTitle("Add Machine");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        machineName = findViewById(R.id.machinery_name);
        machineType = findViewById(R.id.machinery_type);
        machineRegYear = findViewById(R.id.registration_year);
        machinePurchaceDate = findViewById(R.id.date_of_purchase);
        machineOriginalPrice = findViewById(R.id.price);
        machineCurrentPrice = findViewById(R.id.current_price);
        machinemilage = findViewById(R.id.miles_per_hour);
        machineNotes = findViewById(R.id.machine_notes);

        savingBtn = findViewById(R.id.saving_machinery_btn);
        progressBar = findViewById(R.id.machinery_progress_bar);

        saveMachine = findViewById(R.id.save_machinery_btn);
        machineImage = findViewById(R.id.machine_image);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }
        machineImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        saveMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAnimal();
            }
        });

        machinePurchaceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(machinePurchaceDate);
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

        datePickerDialog = new DatePickerDialog(MyMachinery.this, new DatePickerDialog.OnDateSetListener() {
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
                                MyMachinery.this,
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
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            This magic saves much whe converting a bitmap from a string.
            Toast.makeText(this, "This is image uri : " + currentPhotoPath, Toast.LENGTH_SHORT).show();
            try {
                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(currentPhotoPath));
                machineImage.setImageBitmap(mImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            animalImage.setImageBitmap(imageBitmap);
        }
    }

    private void addAnimal() {
        name = machineName.getText().toString().trim();
        type = machineType.getText().toString().trim();
        registration_year = machineRegYear.getText().toString().trim();
        purchase_date = machinePurchaceDate.getText().toString().trim();
        original_price = machineOriginalPrice.getText().toString().trim();
        current_price = machineCurrentPrice.getText().toString().trim();
        milage = machinemilage.getText().toString().trim();
        notes = machineNotes.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            machineName.setError("Field Required!");
        } else if (TextUtils.isEmpty(type)) {
            machineType.setError("Field Required!");
        } else if (TextUtils.isEmpty(registration_year)) {
            machineRegYear.setError("Field Required!");
        } else if (TextUtils.isEmpty(purchase_date)) {
            machinePurchaceDate.setError("Field Required!");
        } else if (TextUtils.isEmpty(original_price)) {
            machineOriginalPrice.setError("Field Required!");
        } else if (TextUtils.isEmpty(current_price)) {
            machineCurrentPrice.setError("Field Required!");
        } else if (TextUtils.isEmpty(milage)) {
            machinemilage.setError("Field Required!");
        } else if (TextUtils.isEmpty(notes)) {
            machineNotes.setError("Field Required!");
        } else {


            AsyncTask<Void, Void, Void> machinery = new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressBar.setVisibility(View.VISIBLE);
                    saveMachine.setVisibility(View.GONE);
                    savingBtn.setVisibility(View.VISIBLE);
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        Thread.sleep(1500);

                        machine = new Machine(userId, name, type, registration_year, purchase_date, original_price, current_price, milage, notes, currentPhotoPath);
                        machineViewmodel.insert(machine);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }


                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    progressBar.setVisibility(View.GONE);
                    saveMachine.setVisibility(View.VISIBLE);
                    savingBtn.setVisibility(View.GONE);

                    Intent toDashboardActivity = new Intent(MyMachinery.this, MainActivity.class);
                    startActivity(toDashboardActivity);
                    finish();
                    Toast.makeText(MyMachinery.this, "Machinery added successfully", Toast.LENGTH_SHORT).show();

                }
            };

            machinery.execute();


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
