package com.jchappelle.sg.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jchappelle.sg.audio.SoundId;
import com.jchappelle.sg.components.DespawnComponent;
import com.jchappelle.sg.components.SpawnComponent;
import com.jchappelle.sg.components.TransformComponent;
import com.jchappelle.sg.systems.render.AnimationComponent;

class ExplosionEntityFactory implements EntityFactory {

    //TODO: Move to asset manager
    private static Texture explosionSheet = new Texture(Gdx.files.internal("explosion.png"));

    public Entity make(String prefabId){
        return make(prefabId, new TransformComponent(0, 0, 0, 32, 32));
    }

    public Entity make(String prefabId, TransformComponent transform){
        Entity entity = new Entity();

        int FRAME_COLS = 3;
        int FRAME_ROWS = 3;
        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(explosionSheet,
                explosionSheet.getWidth() / FRAME_COLS,
                explosionSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        float duration = 1/15f;
        entity.add(new AnimationComponent(new Animation<TextureRegion>(duration, frames)));
        entity.add(new DespawnComponent(duration * 4));
        transform.width = 32;
        transform.height = 32;
        entity.add(transform);
        entity.add(new SpawnComponent(SoundId.ASTEROID_EXPLOSION));
        return entity;

    }
}
