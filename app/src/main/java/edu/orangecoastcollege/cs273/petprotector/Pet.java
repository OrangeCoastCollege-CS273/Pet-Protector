package edu.orangecoastcollege.cs273.petprotector;


import android.net.Uri;

import java.io.Serializable;

public class Pet implements Serializable{
    private int mID;
    private String mName;
    private String mDetails;
    private String mPhone;
    private transient Uri mImage;

    /**
     * Creates a new pet
     * @param ID ID of database entry
     * @param name name of pet
     * @param details info about pet
     * @param phone contact phone number
     * @param image image of pet
     */
    public Pet(int ID, String name, String details, String phone, Uri image) {
        mID = ID;
        mName = name;
        mDetails = details;
        mPhone = phone;
        mImage = image;
    }

    /**
     * Creates a new pet without database
     * @param name name of pet
     * @param details info about pet
     * @param phone contact phone number
     * @param image image of pet
     */
    public Pet(String name, String details, String phone, Uri image) {
        mName = name;
        mDetails = details;
        mPhone = phone;
        mImage = image;
    }

    /**
     * Fetches ID of pet
     * @return
     */
    public int getID() {
        return mID;
    }

    /**
     * Fetches name of pet
     * @return pet name
     */
    public String getName() {
        return mName;
    }

    /**
     * Updates name of pet
     * @param name new pet name
     */
    public void setName(String name) {
        mName = name;
    }

    /**
     * Fetches details of pet
     * @return pet details
     */
    public String getDetails() {
        return mDetails;
    }

    /**
     * Updates details of pet
     * @param details New pet details
     */
    public void setDetails(String details) {
        mDetails = details;
    }

    /**
     * Fetches phone number
     * @return phone number
     */
    public String getPhone() {
        return mPhone;
    }

    /**
     * Updates phone number
     * @param phone phone number
     */
    public void setPhone(String phone) {
        mPhone = phone;
    }

    /**
     * Fetches the Image URI
     * @return Image URI
     */
    public Uri getImage() {
        return mImage;
    }

    /**
     * Updates image uri
     * @param image New Image URI
     */
    public void setImage(Uri image) {
        mImage = image;
    }

    /**
     * Builds string representing pet
     * @return pet string
     */
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
