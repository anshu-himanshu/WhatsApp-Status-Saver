package com.ansh.whatsappstatussaver;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WhatsappVideosAdapter extends RecyclerView.Adapter<WhatsappVideosAdapter.ViewHolder> {

    private final ArrayList<WhatsappStatusModel> list;
    private final Context context;
    private final String saveFilePath = Util.RootDirectoryWhatsapp + "/";

    public WhatsappVideosAdapter(ArrayList<WhatsappStatusModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.status_item_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        WhatsappStatusModel item = list.get(position);




        if (item.getUri().toString().endsWith(".mp4")) {
            holder.playButton.setVisibility(View.VISIBLE);

        } else {
            holder.playButton.setVisibility(View.GONE);
        }
      //  Glide.with(context).load(item.getUri()).into(holder.statusImage);
      //  Glide.with(context).load(item.getPath()).into(holder.statusImage);

        Uri uri = Uri.fromFile(new File(item.getPath()));
        Glide.with(context).
                load(uri).
                thumbnail(0.1f).
                into(holder.statusImage);


        holder.downloadLogo.setOnClickListener(v -> {
            Util.createFileFolder();
            final String path = item.getPath();
            final File file = new File(path);
            File destFile = new File(saveFilePath);

            try {
                FileUtils.copyFileToDirectory(file, destFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(context, "Saved to : " + saveFilePath, Toast.LENGTH_SHORT).show();
        });

        LayoutInflater inflater = LayoutInflater.from(context);
        final View view1 = inflater.inflate(R.layout.view_video_full_screen, null);

        holder.statusImage.setOnClickListener(v -> {

            final AlertDialog.Builder alertDg = new AlertDialog.Builder(context);

            FrameLayout mediaControls = view1.findViewById(R.id.videoViewWrapper);

            if (view1.getParent() != null) {
                ((ViewGroup) view1.getParent()).removeView(view1);
            }

            alertDg.setView(view1);

        final VideoView videoView = view1.findViewById(R.id.video_full);

        final MediaController mediaController = new MediaController(context, false);

        videoView.setOnPreparedListener(mp -> {

            mp.start();
            mediaController.show(0);
            mp.setLooping(true);
        });

        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        videoView.setVideoURI(item.getUri());
        videoView.requestFocus();

        ((ViewGroup) mediaController.getParent()).removeView(mediaController);

        if (mediaControls.getParent() != null) {
            mediaControls.removeView(mediaController);
        }

        mediaControls.addView(mediaController);

        final AlertDialog alert2 = alertDg.create();

        alert2.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
        alert2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        alert2.show();

    });

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView  statusImage ,playButton, shareBtnIcon;
        ImageButton downloadLogo ;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            statusImage = itemView.findViewById(R.id.statusImage);
            playButton = itemView.findViewById(R.id.playButton);
            downloadLogo = itemView.findViewById(R.id.download_logo);
        }
    }

}
