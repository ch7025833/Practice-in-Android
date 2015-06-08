package com.example.chenhao.photogallery;

/**
 * Created by chenhao on 4/30/2015.
 */
public class GalleryItem {

    private String mCaption;
    private String mId;
    private String mUrl;

    public String toString(){
        return mCaption;
    }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String mCaption) {
        this.mCaption = mCaption;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }
}
