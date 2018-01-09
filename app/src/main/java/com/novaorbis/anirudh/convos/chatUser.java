package com.novaorbis.anirudh.convos;

import java.util.ArrayList;
import java.util.List;

public class chatUser
{
    public int imageId;
    public String username;
    public String chatcrumb;
    private List<chatUser> dataList;
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChatcrumb() {
        return chatcrumb;
    }

    public void setChatcrumb(String chatcrumb) {
        this.chatcrumb = chatcrumb;
    }
    public chatUser()
    {

    }
    public chatUser(int imgId,String usrnme,String chtcrmb)
    {
        this.imageId =imgId;
        this.chatcrumb = chtcrmb;
        this.username =usrnme;
    }

}
