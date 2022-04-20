package com.example.fit;

import android.graphics.drawable.Drawable;

//리스트 뷰에 추가될 제목, 글쓴이
public class ListViewItem {

    private String title;
    private String name;
    private String mName;
    private String explain;
    private Drawable iconExer;

    public String getExplain() {    return explain;   }

    public void setExplain(String explain) {     this.explain = explain;    }

    public Drawable getIconExer() {     return iconExer;   }

    public void setIconExer(Drawable image_link) {     this.iconExer = iconExer;   }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(Drawable ic, String mName, String explain){
        iconExer = ic;
        this.mName = mName;
        this.explain = explain;

    }
}
