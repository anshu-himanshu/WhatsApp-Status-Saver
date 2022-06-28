package com.ansh.whatsappstatussaver;

import static android.os.Build.VERSION.SDK_INT;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ansh.whatsappstatussaver.WhatsappStatusModel;
import com.ansh.whatsappstatussaver.WhatsappVideosAdapter;
import com.ansh.whatsappstatussaver.databinding.FragmentVideoBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class VideoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentVideoBinding binding;
    private ArrayList<WhatsappStatusModel> list;
    private WhatsappVideosAdapter adapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video, container, false);

        binding.refreshLayout.setRefreshing(true);

        list = new ArrayList<>();


        binding.refreshLayout.setOnRefreshListener(VideoFragment.this);
        getData();


        binding.refreshLayout.setRefreshing(false);
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

        new Handler().post(new Runnable(){

            WhatsappStatusModel model;

            @Override
            public void run() {



                File targetDirector = new File(targetPath);
                File[] allFiles = targetDirector.listFiles();




                if (allFiles != null) {
                    Arrays.sort(allFiles, (o1, o2) -> {
                        return Long.compare(o2.lastModified(), o1.lastModified());
                    });
                }

                if (allFiles != null) {
                    for (int i = 0; i < allFiles.length; i++) {
                        File file = allFiles[i];

                        if (Uri.fromFile(file).toString().endsWith(".mp4")) {
                            model = new WhatsappStatusModel("whatsApp" + i, Uri.fromFile(file), allFiles[i].getAbsolutePath(), file.getName());
                            list.add(model);
                        }
                    }
                }
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        adapter = new WhatsappVideosAdapter(list, getActivity());
                        binding.rvVideoStatus.setAdapter(adapter);
                    }
                });
            }
        });








        if (SDK_INT >= Build.VERSION_CODES.R) {
            targetPathBusiness = Environment.getExternalStorageDirectory() +
                    File.separator + "Android/media/com.whatsapp/WhatsApp Business/Media/.Statuses";
        } else {
            targetPathBusiness = Environment.getExternalStorageDirectory() +
                    File.separator + "WhatsApp Business/Media/.Statuses";
        }
        File targetDirectorBusiness = new File(targetPathBusiness);
        File[] allFilesBusiness = targetDirectorBusiness.listFiles();





        if (allFilesBusiness != null) {
            Arrays.sort(allFilesBusiness, ((o1, o2) -> {
                return Long.compare(o2.lastModified(), o1.lastModified());
            }));
        }

        if (allFilesBusiness != null) {
            for (int i = 0; i < allFilesBusiness.length; i++) {
                File file = null;
             //   if (allFiles != null) {
            //        file = allFiles[i];
                }

             //   if (Uri.fromFile(file).toString().endsWith(".mp4")) {

                }
            }



    @Override
    public void onRefresh() {
        list.clear();
        getData();
        binding.refreshLayout.setRefreshing(false);
    }
}