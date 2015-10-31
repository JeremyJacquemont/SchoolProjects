package com.lpiem.apps.loupelec.dao.entities;

/**
 * Media Class
 */
public class Media {

    /*
    Constants
     */
    public static final  String TABLE_MEDIA = "media";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PATH = "path";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_ID_KEYWORD = "id_keyword";
    public static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_MEDIA + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PATH + " TEXT NOT NULL, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_TYPE + " TEXT NOT NULL, "
            + COLUMN_ID_KEYWORD + " INTEGER);";
    public static final String DATABASE_DELETE = "DROP TABLE IF EXISTS " + TABLE_MEDIA;

    /*
    Fields
     */
    private long id;
    private String path;
    private String name;
    private String type;
    private Keyword keyword;

    /*
    Methods
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Media media = (Media) o;

        return id == media.id &&
                !(keyword != null ? !keyword.equals(media.keyword) : media.keyword != null) &&
                !(name != null ? !name.equals(media.name) : media.name != null) &&
                ((!(path != null ? !path.equals(media.path) : media.path != null))
                        && !(type != null ? !type.equals(media.type) : media.type != null));
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (keyword != null ? keyword.hashCode() : 0);
        return result;
    }
}
