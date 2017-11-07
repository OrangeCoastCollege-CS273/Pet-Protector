package edu.orangecoastcollege.cs273.petprotector;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class PetListAdapter extends ArrayAdapter {
    private Context mContext;
    private  int mResourceID;
    private List<Pet> mPetList = new ArrayList<>();

    /**
     * Creates a {@link PetListAdapter}
     * @param context Context of adapter
     * @param resource layout id
     * @param pets list of pets to be adapted
     */
    public PetListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Pet> pets) {
        super(context, resource, pets);
        mContext = context;
        mResourceID = resource;
        mPetList = pets;
    }

    /**
     * Inflates the item
     * @param position Position of item to be inflated
     * @param convertView not used
     * @param parent not used
     * @return calling view
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceID, null);

        LinearLayout petListLinearLayout = (LinearLayout) view.findViewById(R.id.petListLinearLayout);
        ImageView petListImageView = (ImageView) view.findViewById(R.id.petListImageView);
        TextView petListNameTextView = (TextView) view.findViewById(R.id.petListNameTextView);
        TextView petListDetailsTextView = (TextView) view.findViewById(R.id.petListDetailsTextView);

        Pet pet = mPetList.get(position);
        petListImageView.setImageURI(pet.getImage());
        petListNameTextView.setText(pet.getName());
        petListDetailsTextView.setText(pet.getDetails());

        petListLinearLayout.setTag(pet);

        return view;
    }
}
