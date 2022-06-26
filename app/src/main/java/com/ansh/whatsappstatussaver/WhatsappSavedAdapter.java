package com.ansh.whatsappstatussaver;

import static android.view.View.GONE;

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
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class WhatsappSavedAdapter extends RecyclerView.Adapter<WhatsappSavedAdapter.ViewHolder> {

    private final ArrayList<WhatsappStatusModel> list;
    private final Context context;
    private final String saveFilePath = Util.RootDirectoryWhatsapp + "/";

    public WhatsappSavedAdapter(ArrayList<WhatsappStatusModel> list, Context context) {
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
            Glide.with(context).load(item.getPath()).into(holder.statusImage);
        } else {
            holder.playButton.setVisibility(GONE);
            Glide.with(context).load(item.getPath()).into(holder.statusImage);
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        final View view1 = inflater.inflate(R.layout.view_video_full_screen, null);

        holder.statusImage.setOnClickListener(v -> {

            if (item.getUri().toString().endsWith(".mp4")) {

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
                videoView.setVideoURI(Uri.fromFile(new File(item.getPath())));
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

            } else {

                final AlertDialog.Builder alertD = new AlertDialog.Builder(context);
                LayoutInflater inflater2 = LayoutInflater.from(context);
                View view = inflater2.inflate(R.layout.view_image_full_screen, null);
                alertD.setView(view);

                ImageView imageView = view.findViewById(R.id.idFullScreenIv);
                Glide.with(context).load(item.getPath()).into(imageView);

                AlertDialog alert = alertD.create();
                alert.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
                alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alert.show();

            }

        });
        

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView statusImage ,playButton ;
        ImageButton  downloadLogo;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            statusImage = itemView.findViewById(R.id.statusImage);
            playButton = itemView.findViewById(R.id.playButton);
            downloadLogo = itemView.findViewById(R.id.download_logo);
            downloadLogo.setVisibility(GONE);
        }
    }

}
