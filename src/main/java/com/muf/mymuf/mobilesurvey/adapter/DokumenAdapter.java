package com.muf.mymuf.mobilesurvey.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.muf.mymuf.mobilesurvey.R;
import com.muf.mymuf.mobilesurvey.fragment.dokumen.DokumenFragment;
import com.muf.mymuf.mobilesurvey.list.DedupList;
import com.muf.mymuf.mobilesurvey.list.DokumenList;
import com.mvc.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16003041 on 16/02/2017.
 */

public class DokumenAdapter extends RecyclerView.Adapter<DokumenAdapter.MyViewHolder> {

    private List<DokumenList> dokumenList;
    private ArrayList<DokumenList> arrayDokumenList;
    private DokumenFragment dokumenFragment;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dokumen_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DokumenAdapter.MyViewHolder holder, int position) {
        DokumenList resultList = dokumenList.get(position);
        holder.name.setText(resultList.getName());
    }

    @Override
    public int getItemCount() {
        return dokumenList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private Button selectDokumen;
        private Button saveDokumen;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.txt_nama_dokumen);

            selectDokumen = (Button) view.findViewById(R.id.btn_select_document);
            selectDokumen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer position = getAdapterPosition();
                    DokumenList resultList = dokumenList.get(position);
                    Toast.makeText(view.getContext(), "SELECTED " + resultList.getName(), Toast.LENGTH_SHORT).show();

                    /*ImagePicker.setMinQuality(600, 600);
                    ImagePicker.pickImage(dokumenFragment, "wawa");

                    dokumenFragment.onActivityResult(ImagePicker.PICK_IMAGE_REQUEST_CODE, position,
                            ImagePicker.getPickImageIntent(dokumenFragment.getContext(), "wawa"));*/

                    dokumenFragment.getImage(resultList.getName());
                }
            });

            saveDokumen = (Button) view.findViewById(R.id.btn_save_dokumen);
            saveDokumen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer position = getAdapterPosition();
                    DokumenList resultList = dokumenList.get(position);
                    Toast.makeText(view.getContext(), "SAVED " + resultList.getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public DokumenAdapter(List<DokumenList> searchResultList, DokumenFragment dokumenFragment) {
        this.dokumenList = searchResultList;
        this.arrayDokumenList = new ArrayList<DokumenList>();
        this.arrayDokumenList.addAll(searchResultList);
        this.dokumenFragment = dokumenFragment;
    }
}
