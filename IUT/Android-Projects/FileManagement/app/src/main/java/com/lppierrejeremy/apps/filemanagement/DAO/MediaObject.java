package com.lppierrejeremy.apps.filemanagement.DAO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * MediaObject Class
 */
public class MediaObject implements Parcelable {
    /*
    Constants
     */
    public static final String TABLE_MEDIA = "media";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PATH = "path";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_VERSION_CODE = "version_code";
    public static final String COLUMN_INSTALLED = "installed";
    public static final String COLUMN_UPDATED = "updated";
    public static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_MEDIA + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PATH + " TEXT NOT NULL, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_TYPE + " TEXT NOT NULL, "
            + COLUMN_VERSION_CODE + " INTEGER NOT NULL, "
            + COLUMN_INSTALLED + " INTEGER NOT NULL DEFAULT 0, "
            + COLUMN_UPDATED + " INTEGER NOT NULL DEFAULT 0) ;";
    public static final String DATABASE_DELETE = "DROP TABLE IF EXISTS " + TABLE_MEDIA;
    public static final int KEY_NOT_UPDATED = 0;
    public static final int KEY_UPDATED = 1;
    public static final int KEY_NOT_INSTALLED = 0;
    public static final int KEY_INSTALLED = 1;

    /*
    Fields
     */
    protected long id;
    protected String name;
    protected String path;
    protected int versionCode;
    protected String type;
    protected int installed;
    protected int updated;

    /*
    Constructors
     */
    public MediaObject(){ }
    public MediaObject(Parcel parcel){
        this.id = parcel.readLong();
        this.name = parcel.readString();
        this.path = parcel.readString();
        this.versionCode = parcel.readInt();
        this.type = parcel.readString();
        this.installed = parcel.readInt();
        this.updated = parcel.readInt();
    }

    /*
    Methods
     */

    public long getId(){ return id; }
    public void setId(long id){ this.id= id; }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public int getVersionCode() {
        return versionCode;
    }
    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
    public int getInstalled() {
        return installed;
    }
    public void setInstalled(int installed) {
        this.installed = installed;
    }
    public int getUpdated() {
        return updated;
    }
    public void setUpdated(int updated) {
        this.updated = updated;
    }

    /**
     * describeContents Method (Override)
     * @return int
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * writeToParcel Method (Override)
     * @param dest Parcel
     * @param flags int
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(path);
        dest.writeInt(versionCode);
        dest.writeString(type);
        dest.writeInt(installed);
        dest.writeInt(updated);
    }

    /**
     * CREATOR Parcel
     */
    public static final Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new MediaObject(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new MediaObject[size];
        }
    };

    /**
     * equals Method (Override)
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaObject that = (MediaObject) o;

        if (id != that.id) return false;
        if (installed != that.installed) return false;
        if (versionCode != that.versionCode) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    /**
     * hashCode Method (Override)
     * @return int
     */
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + versionCode;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + installed;
        return result;
    }
}
