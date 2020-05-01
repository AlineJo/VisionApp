package com.programining.visionapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.programining.visionapp.R;
import com.programining.visionapp.dialogfragments.ChooseDialogFragment;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadImageFragment extends Fragment implements ChooseDialogFragment.ChooseDialogInterface {


    private static final int KEY_PICK_IMAGE = 100;
    private static final int KEY_CAPTURE_IMAGE = 200;
    private Context mContext;
    private Uri mImageUri;
    private ImageView ivImg;

    public UploadImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_upload_image, container, false);

        ivImg = parentView.findViewById(R.id.iv_img);
        Button btnChoose = parentView.findViewById(R.id.btn_choose);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * display choose dialog
                 */
                ChooseDialogFragment dialog = new ChooseDialogFragment();
                dialog.setChooseDialogListener(UploadImageFragment.this);
                dialog.show(getChildFragmentManager(), ChooseDialogFragment.class.getSimpleName());
            }
        });


        return parentView;
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
                Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
                mImageUri = data.getData(); //uri
                ivImg.setImageBitmap(capturedImage);

            }

        } else if (requestCode == KEY_PICK_IMAGE) {

            if (data == null) {
                Toast.makeText(mContext, "Unexpected Error Happened while selecting  picture!", Toast.LENGTH_SHORT).show();

            } else {

                try {
                    Uri imgUri = data.getData();//1
                    InputStream imageStream = mContext.getContentResolver().openInputStream(imgUri);//2
                    Bitmap selectedImageBitmap = BitmapFactory.decodeStream(imageStream);//3}
                    mImageUri = imgUri;
                    ivImg.setImageBitmap(selectedImageBitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }


    }


}
