package com.jchappelle.sg.audio;

public enum MusicId {

    Theme1("music/theme1.wav");

    private String path;

    private MusicId(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
