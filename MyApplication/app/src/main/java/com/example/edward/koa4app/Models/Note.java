package com.example.edward.koa4app.Models;

/**
 * Created by Edward on 30-Jan-17.
 */

public class Note
{
    private String id;
    private String text;
    private int lastUpdate;
    private int version;

    public Note(String id, String text, int lastUpdate, int version)
    {
        this.id = id;
        this.version = version;
        this.lastUpdate = lastUpdate;
        this.text = text;
    }

    public Note() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return "Note{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", lastUpdate=" + lastUpdate +
                ", version=" + version +
                '}';
    }

    public String toJson()
    {
        return "{id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", lastUpdate=" + lastUpdate +
                ", version=" + version +
                '}';
    }

}
