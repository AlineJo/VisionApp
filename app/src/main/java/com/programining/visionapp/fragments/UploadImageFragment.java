package com.programining.visionapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.programining.visionapp.R;
import com.programining.visionapp.dialogfragments.ChooseDialogFragment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadImageFragment extends Fragment implements ChooseDialogFragment.ChooseDialogInterface {


    private static final int KEY_PICK_IMAGE = 100;
    private static final int KEY_CAPTURE_IMAGE = 200;
    private static final String KEY_TAG = "UploadImageFragment";
    private Context mContext;
    private Uri mImageUri;
    Bitmap mImageBitmap;
    private ImageView ivImg;
    private TextView tvResults;
    private TextView tvDetectionLabel;
    private int mCurrentSelectedDetection;

    public UploadImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);// in order to display options icon
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_upload_image, container, false);

        ivImg = parentView.findViewById(R.id.iv_img);
        tvResults = parentView.findViewById(R.id.tv_results);
        tvDetectionLabel = parentView.findViewById(R.id.tv_label);
        Button btnChoose = parentView.findViewById(R.id.btn_choose);
        Button btnUpload = parentView.findViewById(R.id.btn_upload);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayChooseDialogFragment();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareVisionCall();
            }
        });


        return parentView;
    }


    /**
     * Create options menu on the ActionBar
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_detect, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Handel on menu item Selected
     * Here we enable user to select the detection type! i.e : which data the user want extract from the image
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        switch (id) {
            case R.id.opt_text_detection:
                tvDetectionLabel.setText(mContext.getString(R.string.menu_text_detection));
                mCurrentSelectedDetection = 0;
                return true;
            case R.id.opt_label_detection:
                tvDetectionLabel.setText(mContext.getString(R.string.menu_lable_detection));
                mCurrentSelectedDetection = 1;
                return true;
            case R.id.opt_landmark_detection:
                tvDetectionLabel.setText(mContext.getString(R.string.landmark_det));
                mCurrentSelectedDetection = 2;
                return true;
            case R.id.opt_facial_detection:
                tvDetectionLabel.setText(mContext.getString(R.string.menu_facial_detection));
                mCurrentSelectedDetection = 3;
                return true;
            case R.id.opt_logo_detection:
                tvDetectionLabel.setText(mContext.getString(R.string.menu_logo_detection));
                mCurrentSelectedDetection = 4;
                return true;
            case R.id.opt_safe_search_detection:
                tvDetectionLabel.setText(mContext.getString(R.string.menu_safe_search));
                mCurrentSelectedDetection = 5;
                return true;
            case R.id.opt_web_detection:
                tvDetectionLabel.setText(mContext.getString(R.string.menu_web_detection));
                mCurrentSelectedDetection = 6;
                return true;
            case R.id.opt_img_properties:
                tvDetectionLabel.setText(mContext.getString(R.string.menu_img_props));
                mCurrentSelectedDetection = 7;
                return true;
            case R.id.opt_obj_locale:
                tvDetectionLabel.setText(mContext.getString(R.string.menu_object_locale));
                mCurrentSelectedDetection = 8;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * this function will check if mImageBitmap is null. Additionally, will handel IOException thrown by uploadImageToVision() function
     */
    private void prepareVisionCall() {
        if (mImageBitmap == null) {
            Toast.makeText(mContext, "Please select an image", Toast.LENGTH_SHORT).show();
        } else {
            try {
                uploadImageToVision();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToVision() throws IOException {

    }


    /**
     * display choose dialog
     */
    private void displayChooseDialogFragment() {
        ChooseDialogFragment dialog = new ChooseDialogFragment();
        dialog.setChooseDialogListener(UploadImageFragment.this);
        dialog.show(getChildFragmentManager(), ChooseDialogFragment.class.getSimpleName());
    }


    @Override
    public void openGallery() {
        Intent i = new Intent();
        i.setType("image/*"); // specify the type of data you expect
        i.setAction(Intent.ACTION_GET_CONTENT); // we need to get content from another act.
        startActivityForResult(Intent.createChooser(i, "choose App"), KEY_PICK_IMAGE);
    }

    @Override
    public void openCamera() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, KEY_CAPTURE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if image from Camera
        if (requestCode == KEY_CAPTURE_IMAGE) {
            if (data == null) {
                Toast.makeText(mContext, "Unexpected Error Happened while capturing the picture!", Toast.LENGTH_SHORT).show();
            } else {
                mImageBitmap = (Bitmap) data.getExtras().get("data");
                //TODO : data.getData() return null in most devices! try to find fix!
                mImageUri = data.getData(); //data.getData() (BUG) - return null in most devices!
                ivImg.setImageBitmap(mImageBitmap);

            }

        } else if (requestCode == KEY_PICK_IMAGE) {

            if (data == null) {
                Toast.makeText(mContext, "Unexpected Error Happened while selecting  picture!", Toast.LENGTH_SHORT).show();

            } else {

                try {
                    Uri imgUri = data.getData();//1
                    InputStream imageStream = mContext.getContentResolver().openInputStream(imgUri);//2
                    mImageBitmap = BitmapFactory.decodeStream(imageStream);//3}
                    mImageUri = imgUri;
                    ivImg.setImageBitmap(mImageBitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }


    }


}
