package edu.orangecoastcollege.cs273.petprotector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity allows user to add a picture of a pet to a list of pets
 *
 * Photo of pet is taken from device's images
 */
public class PetListActivity extends AppCompatActivity {
    private ImageView mPetImageView;
    private EditText mNameEditText;
    private EditText mDetailsEditText;
    private EditText mPhoneNumberEditText;
    private ListView mPetsListView;

    private List<Pet> mPetList;
    private PetListAdapter mPetListAdapter;

    private Uri petImageUri;

    private  DBHelper mDBHelper;

    public static final int GRANTED = PackageManager.PERMISSION_GRANTED;
    public static final int DENIED = PackageManager.PERMISSION_DENIED;

    /**
     * Called when the activity is starting
     * Connects the {@link ImageView} to its programmatic counterpart
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        mDBHelper = new DBHelper(this);
        mDBHelper.deleteAllPets();

        mPetImageView = (ImageView) findViewById(R.id.petImageView);
        mNameEditText = (EditText) findViewById(R.id.nameTextView);
        mDetailsEditText = (EditText) findViewById(R.id.detailsTextView);
        mPhoneNumberEditText = (EditText) findViewById(R.id.phoneNumberTextView);
        mPetsListView = (ListView) findViewById(R.id.petsListView);

        mPetList = mDBHelper.getAllPets();
        mPetListAdapter = new PetListAdapter(this, R.layout.pet_list_item, mPetList);
        mPetsListView.setAdapter(mPetListAdapter);

        petImageUri = getUriFromResource(this, R.drawable.none);
        mPetImageView.setImageURI(petImageUri);
    }

    /**
     * Takes the data inputted by the user and creates a new pet
     * Uses this new pet to create a new entry in the database
     * @param view The add pet button
     */
    public void addPet(View view) {
        String name = mNameEditText.getText().toString();
        String details = mDetailsEditText.getText().toString();
        String phoneNumber = mPhoneNumberEditText.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(details) || TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(this, "Please fill out all text fields", Toast.LENGTH_SHORT).show();
        } else {
            int id = mDBHelper.addPet(new Pet(name, details, phoneNumber, petImageUri));

            mPetList.add(new Pet(id, name, details, phoneNumber, petImageUri));
            mPetListAdapter.notifyDataSetChanged();

            mNameEditText.setText("");
            mDetailsEditText.setText("");
            mPhoneNumberEditText.setText("");
            petImageUri = getUriFromResource(this, R.drawable.none);
            mPetImageView.setImageURI(petImageUri);
        }
    }

    /**
     * Views the details of a selected pet
     * @param v The selected linearlayout
     */
    public void viewDetails(View v) {
        Pet pet = (Pet) v.getTag();

        Intent intent = new Intent(this, PetDetailsActivity.class);
        intent.putExtra("pet", pet);
        intent.putExtra("uri", pet.getImage().toString());

        startActivity(intent);
    }

    /**
     * Requests permissions needed
     *
     * If all permissions are granted, launches the gallery for photo selection
     *
     * @param view Add pet button
     */
    public void selectPetImage(View view) {
        List<String> permsList = new ArrayList<>();

        int hasCameraPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (hasCameraPerm == DENIED)
            permsList.add(Manifest.permission.CAMERA);

        int hasReadPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasReadPerm == DENIED)
            permsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        int hasWritePerm = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWritePerm == DENIED)
            permsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if (permsList.size() > 0) {
            String[] permsArray = new String[permsList.size()];
            permsList.toArray(permsArray);
            ActivityCompat.requestPermissions(this,permsArray, 1337);
        }

        if (hasCameraPerm == GRANTED && hasReadPerm == GRANTED && hasWritePerm == GRANTED) {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery, 1);
        }
    }

    /**
     * Called when result is recieved
     *
     * Only situated for photo selection result
     *
     * @param requestCode Code that is sent with startActivityForResult
     * @param resultCode Code that is sent after activity is complete
     * @param data the intent that called activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case(1):
                if (resultCode == RESULT_OK && data != null) {
                    petImageUri = data.getData();
                    mPetImageView.setImageURI(petImageUri);
                }
        }
    }

    /**
     *  Method that makes generating a URI from a resource easy
     *
     * @param context Context of resource
     * @param resID ID of resource
     * @return Generated URI
     */
    public static Uri getUriFromResource(Context context, int resID) {
        Resources res = context.getResources();
        String uri = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + res.getResourcePackageName(resID) + "/"
                + res.getResourceTypeName(resID) + "/"
                + res.getResourceEntryName(resID);
        return Uri.parse(uri);
    }
}
