package com.huangyaling.musicplayer.bean;

/**
 * Created by huangyaling on 2017/5/31.
 */
public class MusicInfoBean {
    public String song;//歌曲
    public String singer;//歌手
    public String path;//歌曲存放路径
    public int duration;//歌曲时长
    public long size;//歌曲大小

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
