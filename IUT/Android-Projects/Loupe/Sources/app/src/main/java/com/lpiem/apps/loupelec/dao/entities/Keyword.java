package com.lpiem.apps.loupelec.dao.entities;

/**
 * Keyword Class
 */
public class Keyword {

    /*
    Constants
     */
    public static final  String TABLE_KEYWORD = "keyword";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_KEYWORD + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL);";
    public static final String DATABASE_DELETE = "DROP TABLE IF EXISTS " + TABLE_KEYWORD;

    /*
    Fields
     */
    private long id;
    private String name;

    /*
    Methods
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Keyword keyword = (Keyword) o;

        return id == keyword.id && !(name != null ? !name.equals(keyword.name) : keyword.name != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
