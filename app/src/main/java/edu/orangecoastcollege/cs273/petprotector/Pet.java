package edu.orangecoastcollege.cs273.petprotector;


import android.net.Uri;

public class Pet {
    private int mID;
    private String mName;
    private String mDetails;
    private String mPhone;
    private Uri mImage;

    public Pet(int ID, String name, String details, String phone, Uri image) {
        mID = ID;
        mName = name;
        mDetails = details;
        mPhone = phone;
        mImage = image;
    }

    public Pet(String name, String details, String phone, Uri image) {
        mName = name;
        mDetails = details;
        mPhone = phone;
        mImage = image;
    }

    public int getID() {
        return mID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDetails() {
        return mDetails;
    }

    public void setDetails(String details) {
        mDetails = details;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public Uri getImage() {
        return mImage;
    }

    public void setImage(Uri image) {
        mImage = image;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "mID=" + mID +
                ", mName='" + mName + '\'' +
                ", mDetails='" + mDetails + '\'' +
                ", mPhone='" + mPhone + '\'' +
                ", mImage=" + mImage +
                '}';
    }
}
