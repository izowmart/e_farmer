package com.example.e_farmer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.e_farmer.models.Animals;
import com.example.e_farmer.models.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class AddAnimal extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "AddAnimal";
    public static final int REQUEST_TAKE_PHOTO = 1;
    private String currentPhotoPath;
    private Bitmap mImageBitmap;

    private Toolbar toolbar;
    private EditText animalType, animalColour, animalTag, animalBreed, animalWeight, animalKids, animalSource, animalAge;
    private Spinner animalHornType;
    private String[] items;
    private Button addPhoto, saveAnimal;
    private ImageView animalImage;
    private RadioButton radioDoe, radioBuck;
    private RadioGroup radioGroup;
    private String type, tag, colour, breed, sex, horntype, source, weight, age, kids;

    private Animals animals;
    private Box<Animals> animalBox;
    private BoxStore farmerApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);

//        objectBox initialization
        farmerApp = FarmerApp.getBoxStore();
        animalBox = farmerApp.boxFor(Animals.class);

        // This convention should follow to have a successful toolbar set with the correct set title.
        toolbar = findViewById(R.id.toolbar_animal);
        toolbar.setTitle("Add Animal");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        animalType = findViewById(R.id.animal_type);
        animalColour = findViewById(R.id.animal_colour);
        animalTag = findViewById(R.id.animal_tag);
        animalBreed = findViewById(R.id.animal_breed);
        animalWeight = findViewById(R.id.animal_weight);
        animalKids = findViewById(R.id.animal_kids);
        animalSource = findViewById(R.id.animal_source);
        animalAge = findViewById(R.id.animal_age);

        animalHornType = findViewById(R.id.horn_type);
        addPhoto = findViewById(R.id.add_photo_btn);
        saveAnimal = findViewById(R.id.save_btn);
        animalImage = findViewById(R.id.animal_image);

        radioDoe = findViewById(R.id.radio_doe);
        radioBuck = findViewById(R.id.radio_buck);
        radioGroup = findViewById(R.id.radio_group);

        // Setting up spinner arrayAdapter
        // Setting up spinner using arrayAdapter
        items = new String[]{"Horned", "No Horn"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        animalHornType.setAdapter(adapter);

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        saveAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAnimal();
            }
        });
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
                animalImage.setImageBitmap(mImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            animalImage.setImageBitmap(imageBitmap);
        }
    }

    private void addAnimal() {
        type = animalType.getText().toString().trim();
        tag = animalTag.getText().toString().trim();
        colour = animalColour.getText().toString().trim();
        breed = animalBreed.getText().toString().trim();
        source = animalSource.getText().toString().trim();
        weight = animalWeight.getText().toString().trim();
        age = animalAge.getText().toString().trim();
        kids = animalKids.getText().toString().trim();



        if (TextUtils.isEmpty(type)) {
            animalType.setError("Field Required!");
        } else if (TextUtils.isEmpty(tag)) {
            animalTag.setError("Field Required!");
        } else if (TextUtils.isEmpty(colour)) {
            animalColour.setError("Field Required!");
        } else if (TextUtils.isEmpty(breed)) {
            animalBreed.setError("Field Required!");
        } else if (TextUtils.isEmpty(source)) {
            animalSource.setError("Field Required!");
        } else if (TextUtils.isEmpty(weight)) {
            animalWeight.setError("Field Required!");
        } else if (TextUtils.isEmpty(age)) {
            animalAge.setError("Field Required!");
        } else if (TextUtils.isEmpty(kids)) {
            animalKids.setError("Field Required!");
        } else {


            if (radioBuck.isChecked()) {
                sex = radioBuck.getText().toString().trim();
            } else if (radioDoe.isChecked()) {
                sex = radioDoe.getText().toString().trim();
            } else {
                radioBuck.setError("!");
            }
            // This where everything is collected and stored in our local database
            User user = new User();

            animals = new Animals();

            animals.setAge(age);
            animals.setImage(currentPhotoPath);
            animals.setType(type);
            animals.setTag(tag);
            animals.setColour(colour);
            animals.setBreed(breed);
            animals.setSex(sex);
            animals.setHorn_type("horntype");
            animals.setWeight(weight);
            animals.setKids(kids);
            animals.setSource(source);

            animals.user.setTarget(user);
            animalBox.put(animals);



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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        horntype = String.valueOf(adapterView.getItemAtPosition(i));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Another interface callback
    }
}
