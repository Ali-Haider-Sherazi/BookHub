package com.project.booklisting;

import android.graphics.Bitmap;

public class BookInfo {

    private String bookName;
    private String authorName;
    private String date;
    private String publisherName;
    private String img;
    String preview;

    public BookInfo()
    {

    }

    public BookInfo(String bookName, String authorName, String date, String publisherName, String mImgResourceId, String preview)
    {
        this.bookName = bookName;
        this.authorName = authorName;
        this.date = date;
        this.publisherName = publisherName;
        this.img = mImgResourceId;
        this.preview =  preview;

    }

    public String getImg()
    {
        return img;
    }

    public String getPreview()
    {
        return preview;
    }


    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getDate() {
        return date;
    }

    public String getPublisherName() {
        return publisherName;
    }




}
