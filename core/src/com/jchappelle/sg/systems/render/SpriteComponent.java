package com.jchappelle.sg.systems.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteComponent implements Component {

    private static ComponentMapper<SpriteComponent> mapper = ComponentMapper.getFor(SpriteComponent.class);
    public static SpriteComponent get(Entity entity){
        return mapper.get(entity);
    }

    private Texture img;
    public Sprite sprite;

    public SpriteComponent(String internalPath){
        this(internalPath, 0, 0);
    }

    public SpriteComponent(String internalPath, float x, float y){
        img = new Texture(internalPath);
        sprite = new Sprite(img);
        sprite.setX(x);
        sprite.setY(y);
    }

    public SpriteComponent(Sprite sprite){
        this.img = sprite.getTexture();
        this.sprite = sprite;
    }

    public void dispose(){
        img.dispose();
    }
}
