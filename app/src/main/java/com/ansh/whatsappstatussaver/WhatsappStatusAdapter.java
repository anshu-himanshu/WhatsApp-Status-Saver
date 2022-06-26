package com.ansh.whatsappstatussaver;

import static com.ansh.whatsappstatussaver.Util.RootDirectoryWhatsapp;
import static com.ansh.whatsappstatussaver.Util.createFileFolder;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WhatsappStatusAdapter extends RecyclerView.Adapter<WhatsappStatusAdapter.ViewHolder> {

    private final ArrayList<WhatsappStatusModel> list;
    private final Context context;
    private final String saveFilePath = RootDirectoryWhatsapp + "/";

    public WhatsappStatusAdapter(ArrayList<WhatsappStatusModel> list, Context context) {
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


        if (item.getUri().toString().endsWith(".jpg") ) {
            Glide.with(context).load(item.getUri()).into(holder.statusImage);
        }else{
            Glide.with(context).load(item.getPath()).into(holder.statusImage);
        }



        holder.downloadLogo.setOnClickListener(v -> {
            createFileFolder();
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

        holder.statusImage.setOnClickListener(v -> {

            final AlertDialog.Builder alertD = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.view_image_full_screen, null);
            alertD.setView(view);

            ImageView imageView = view.findViewById(R.id.idFullScreenIv);
            Glide.with(context).load(item.getUri()).into(imageView);

            AlertDialog alert = alertD.create();
            alert.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alert.show();

        });

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView statusImage, playButton ;
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
