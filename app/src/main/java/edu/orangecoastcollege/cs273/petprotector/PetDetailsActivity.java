package edu.orangecoastcollege.cs273.petprotector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class PetDetailsActivity extends AppCompatActivity {
    private TextView mNameTextView;
    private TextView mDetailsTextView;
    private TextView mPhoneNumberTextView;
    private ImageView mPetImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        mNameTextView = (TextView) findViewById(R.id.nameTextView);
        mDetailsTextView = (TextView) findViewById(R.id.detailsTextView);
        mPhoneNumberTextView = (TextView) findViewById(R.id.phoneNumberTextView);
        mPetImageView = (ImageView) findViewById(R.id.petImageView);

        Intent intent = getIntent();
        Pet pet = (Pet) intent.getSerializableExtra("pet");

        mNameTextView.setText(pet.getName());
        mDetailsTextView.setText(pet.getDetails());
        mPhoneNumberTextView.setText(pet.getPhone());
        mPetImageView.setImageURI(pet.getImage());
    }
}
