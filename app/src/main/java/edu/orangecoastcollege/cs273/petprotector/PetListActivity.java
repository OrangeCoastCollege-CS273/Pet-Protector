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
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity allows user to add a picture of a pet to a list of pets
 *
 * Photo of pet is taken from device's images
 */
public class PetListActivity extends AppCompatActivity {
    private ImageView petImageView;
    private Uri petImageUri;

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

        petImageView = (ImageView) findViewById(R.id.petImageView);
        petImageView.setImageURI(getUriFromResource(this, R.drawable.none));
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
                    petImageView.setImageURI(petImageUri);
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
