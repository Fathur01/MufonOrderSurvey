package com.muf.mymuf.mobilesurvey.fragment.dokumen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.activity.FormDokumenOthersActivity;
import com.muf.mymuf.mobilesurvey.adapter.DedupAdapter;
import com.muf.mymuf.mobilesurvey.adapter.DokumenAdapter;
import com.muf.mymuf.mobilesurvey.list.DedupList;
import com.muf.mymuf.mobilesurvey.list.DokumenList;
import com.muf.mymuf.mobilesurvey.util.Dialogs;
import com.mvc.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.List;

public class DokumenFragment extends Fragment {

    private List<DokumenList> resultList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DokumenAdapter mAdapter;
    private String imageName;
    private Handler mHandler;

    public DokumenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dokumen, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionListener dialogPermissionListener =
                    DialogOnDeniedPermissionListener.Builder
                            .withContext(getContext())
                            .withTitle("Storage permission")
                            .withMessage("Storage permission is needed !")
                            .withButtonText(android.R.string.ok)
                            .withIcon(R.mipmap.ic_launcher)
                            .build();

            Dexter.withActivity(getActivity())
                    .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(dialogPermissionListener)
                    .check();
        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        resultList.add(new DokumenList("KK/Akte Nikah/Akte Kelahiran", false));
        resultList.add(new DokumenList("KTP/KTP Sementara/Resi KTP", false));
        resultList.add(new DokumenList("KTP/Resi/KTP Sementara Pemohon", false));
        resultList.add(new DokumenList("KTP/Resi/KTP Sementara Spouse", false));
        //resultList.add(new DokumenList("Other"));

        mAdapter = new DokumenAdapter(resultList, DokumenFragment.this);

        mAdapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab_dokumen);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FormDokumenOthersActivity.class);
                startActivityForResult(intent, 9999);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mHandler = new Handler(Looper.getMainLooper());
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {

            if(resultCode != 0) {
                if(requestCode == 9999) {
                    String documentOtherName = data.getStringExtra("DOCUMENT_OTHER_NAME");

                    resultList.add(new DokumenList(documentOtherName, true));
                    mAdapter.notifyDataSetChanged();
                }
                else {
                    if(resultCode != 0) {
                        Bitmap bitmap = ImagePicker.getImageFromResult(getContext(), requestCode, resultCode, data);

                        Log.d("debug_image_name", imageName);

                        if(bitmap != null) {
                            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap,
                                    imageName + ".jpg" , "Description " + imageName);
                        }
                    }
                }
            }
        }
        else {
            Dialogs.showDialog(mHandler, getActivity(), "Warning", "Permission Needed !", false);
        }
    }

    public void getImage(String name) {
        ImagePicker.setMinQuality(600, 600);
        ImagePicker.pickImage(DokumenFragment.this, "Select Image From :");
        this.imageName = name;
    }
}
