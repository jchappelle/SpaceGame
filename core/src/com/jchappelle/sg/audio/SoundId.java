package com.jchappelle.sg.audio;

public enum SoundId {

    ASTEROID_EXPLOSION("sounds/asteroid_explosion.ogg"),
    BULLET("sounds/shoot.wav");

    private String path;

    private SoundId(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
