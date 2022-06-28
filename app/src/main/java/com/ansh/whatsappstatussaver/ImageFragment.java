package com.ansh.whatsappstatussaver;

import static android.os.Build.VERSION.SDK_INT;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ansh.whatsappstatussaver.databinding.FragmentImageBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ImageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentImageBinding binding;
    private ArrayList<WhatsappStatusModel> list;
    private WhatsappImagesAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image, container, false);


        list = new ArrayList<>();

        getData();



        binding.refreshLayout.setRefreshing(false);
        binding.refreshLayout.setOnRefreshListener(ImageFragment.this);
        return binding.getRoot();
    }

    private void getData() {

        WhatsappStatusModel model;
        String targetPath;
        String targetPathBusiness;


        if (SDK_INT >= Build.VERSION_CODES.R) {
            targetPath = Environment.getExternalStorageDirectory() +
                    File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses";
        } else {
            targetPath = Environment.getExternalStorageDirectory() +
                    File.separator + "WhatsApp/Media/.Statuses";
        }




        File targetDirector = new File(targetPath);
        File[] allFiles = targetDirector.listFiles();

        // for WhatsApp Business


        if (SDK_INT >= Build.VERSION_CODES.R) {
            targetPathBusiness = Environment.getExternalStorageDirectory() +
                    File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses";
        } else {
            targetPathBusiness = Environment.getExternalStorageDirectory() +
                    File.separator + "WhatsApp/Media/.Statuses";
        }
        File targetDirectorBusiness = new File(targetPathBusiness);
        File[] allFilesBusiness = targetDirectorBusiness.listFiles();


        new Thread(new Runnable(){
            WhatsappStatusModel model;
            @Override
            public void run() {
                if (allFiles != null) {
                    Arrays.sort(allFiles, (o1, o2) -> {
                        return Long.compare(o2.lastModified(), o1.lastModified());
                    });
                }

                if (allFiles != null) {
                    for (int i = 0; i < allFiles.length; i++) {
                        File file = allFiles[i];

                        if (Uri.fromFile(file).toString().endsWith(".png") || Uri.fromFile(file).toString().endsWith(".jpg")) {
                            model = new WhatsappStatusModel("whatsApp" + i, Uri.fromFile(file), allFiles[i].getAbsolutePath(), file.getName());
                            list.add(model);
                        }
                    }
                }
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        adapter = new WhatsappImagesAdapter(list, getActivity());
                        binding.rvStatus.setAdapter(adapter);
                    }
                });
            }
        }).start();
        // for WhatsApp Business


        if (allFilesBusiness != null) {
            Arrays.sort(allFilesBusiness, ((o1, o2) -> {
                return Long.compare(o2.lastModified(), o1.lastModified());
            }));
        }

        if (allFilesBusiness != null) {
            for (int i = 0; i < allFilesBusiness.length; i++) {
                File file = null;
                if (allFiles != null) {
                    file = allFiles[i];
                }

                if (Uri.fromFile(file).toString().endsWith(".mp4")) {

                }
            }
        }




    }

    @Override
    public void onRefresh() {
        list.clear();
        getData();
        binding.refreshLayout.setRefreshing(false);
    }
}