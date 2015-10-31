package com.mdl.utils;

/**
 * EntryItem class
 */

public class EntryItem {

    private int id;
    private String title;
    private String dateStart;
    private String dateEnd;

    public EntryItem(int id, String title, long dateStart, long dateEnd){
        this.id = id;
        this.title = title;
        this.dateStart = Utils.formatDate(dateStart, false);
        this.dateEnd = Utils.formatDate(dateEnd, false);
    }

    public int getId(){ return this.id; }
    public void setId(int id){ this.id = id; }

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getDateStart(){
        return this.dateStart;
    }
    public void setDateStart(long timestamp){
        this.dateStart = Utils.formatDate(timestamp, false);
    }

    public String getDateEnd(){
        return this.dateEnd;
    }
    public void setDateEnd(long timestamp){
        this.dateEnd = Utils.formatDate(timestamp, false);
    }
}