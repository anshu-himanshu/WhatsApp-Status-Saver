package com.ansh.whatsappstatussaver;

import android.net.Uri;

public class StatusModel {
    String UriText;
    Uri imageUri;

    public StatusModel(String uriText, Uri imageUri) {
        UriText = uriText;
        this.imageUri = imageUri;
    }

    public String getUriText() {
        return UriText;
    }

    public void setUriText(String uriText) {
        UriText = uriText;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
