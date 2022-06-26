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

import com.ansh.whatsappstatussaver.databinding.FragmentImageBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ImageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentImageBinding binding;
    private ArrayList<WhatsappStatusModel> list;
    private WhatsappImagesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image, container, false);


        list = new ArrayList<>();
        getData();
        adapter = new WhatsappImagesAdapter(list, getActivity());
        binding.rvStatus.setAdapter(adapter);

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

                if (Uri.fromFile(file).toString().endsWith(".png") || Uri.fromFile(file).toString().endsWith(".jpg")) {
                    model = new WhatsappStatusModel("whatsApp" + i, Uri.fromFile(file), allFiles[i].getAbsolutePath(), file.getName());
                    list.add(model);
                }
            }
        }
        // for WhatsApp Business


        if (allFilesBusiness != null) {
            Arrays.sort(allFilesBusiness, ((o1, o2) -> {
                if (o1.lastModified() > o2.lastModified())
                    return -1;
                else if (o1.lastModified() < o2.lastModified())
                    return +1;
                else
                    return 0;
            }));
        }

        if (allFilesBusiness != null) {
            for (int i = 0; i < allFilesBusiness.length; i++) {
                File file = null;
                if (allFiles != null) {
                    file = allFiles[i];
                }

                if (Uri.fromFile(file).toString().endsWith(".mp4")) {
                    model = new WhatsappStatusModel("whatsBusiness " + i, Uri.fromFile(file),
                            allFilesBusiness[i].getAbsolutePath(), file.getName());

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