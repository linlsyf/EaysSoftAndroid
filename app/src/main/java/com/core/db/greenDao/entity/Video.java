package com.core.db.greenDao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lindanghong on 2018/6/8.
 */
@Entity
public class Video {
    @Id
    private String id;
    private String name;
    private String data;
    private String thumbPath;

    private long duration;
    private long size;
    private String durationString;



    @Generated(hash = 2017673484)
    public Video(String id, String name, String data, String thumbPath,
            long duration, long size, String durationString) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.thumbPath = thumbPath;
        this.duration = duration;
        this.size = size;
        this.durationString = durationString;
    }

    @Generated(hash = 237528154)
    public Video() {
    }

  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDurationString() {
        return durationString;
    }

    public void setDurationString(String durationString) {
        this.durationString = durationString;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
