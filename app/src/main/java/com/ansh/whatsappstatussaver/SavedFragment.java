package com.ansh.whatsappstatussaver;

import static android.os.Build.VERSION.SDK_INT;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.ansh.whatsappstatussaver.databinding.FragmentSavedBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class SavedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentSavedBinding binding;
    private ArrayList<WhatsappStatusModel> list;
    private WhatsappSavedAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved, container, false);


        list = new ArrayList<>();

        new Thread() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        getData();

                    }
                });
            }
        }.start();

        binding.refreshLayout.setRefreshing(false);
        binding.refreshLayout.setOnRefreshListener(SavedFragment.this);
        return binding.getRoot();

    }

    private void getData() {

        WhatsappStatusModel model;
        String targetPath;


        if (SDK_INT >= Build.VERSION_CODES.R) {
            targetPath = Environment.getExternalStorageDirectory() +
                    File.separator + "/WhatsApp Status/WhatsApp";
        } else {
            targetPath = Environment.getExternalStorageDirectory() +
                    File.separator + "/WhatsApp Status/WhatsApp";
        }


        File targetDirector = new File(targetPath);
        File[] allFiles = targetDirector.listFiles();

        // for WhatsApp Business


        if (allFiles != null) {
            Arrays.sort(allFiles, (o1, o2) -> {
                if (o1.lastModified() > o2.lastModified())
                    return -1;
                else if (o1.lastModified() < o2.lastModified())
                    return +1;
                else
                    return 0;
            });
        }

        if (allFiles != null) {
            for (int i = 0; i < allFiles.length; i++) {
                File file = allFiles[i];

                if (Uri.fromFile(file).toString().endsWith(".mp4") || Uri.fromFile(file).toString().endsWith(".jpg") || Uri.fromFile(file).toString().endsWith(".png")) {
                    model = new WhatsappStatusModel("whatsApp" + i, Uri.fromFile(file), allFiles[i].getAbsolutePath(), file.getName());
                    list.add(model);
                }
            }
        }

        // for WhatsApp Business

        adapter = new WhatsappSavedAdapter(list, getActivity());

        binding.rvSavedStatus.setAdapter(adapter);


    }


    @Override
    public void onRefresh() {
        list.clear();
        getData();
        binding.refreshLayout.setRefreshing(false);
    }

}