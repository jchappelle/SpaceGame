package com.jchappelle.sg;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationComponent implements Component {
    private static ComponentMapper<AnimationComponent> mapper = ComponentMapper.getFor(AnimationComponent.class);
    public static AnimationComponent get(Entity entity){
        return mapper.get(entity);
    }

    public Animation<TextureRegion> animation;
    public float stateTime;

    public AnimationComponent(Animation<TextureRegion> animation){
        this.animation = animation;
    }
}
