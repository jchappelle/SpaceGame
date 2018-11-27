package com.jchappelle.sg.render;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.utils.Disposable;
import com.jchappelle.sg.TransformComponent;

public class RenderSystem extends EntitySystem implements Disposable {
    private ImmutableArray<Entity> entities;

    SpriteBatch batch;
    private OrthographicCamera camera;

    public RenderSystem(){
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth * .5f, camera.viewportHeight * .5f, 0f);
    }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(SpriteComponent.class, TransformComponent.class).get());
    }

    public void update(float deltaTime) {
        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        for (int i = 0; i < entities.size(); ++i) {
            renderEntity(entities.get(i));
        }
        batch.end();

    }

    private void renderEntity(Entity entity){
        Sprite sprite = SpriteComponent.get(entity).sprite;
        sprite.setOriginCenter();
        TransformComponent tc = TransformComponent.get(entity);
        batch.draw(sprite, tc.x, tc.y, tc.originX, tc.originY, tc.width, tc.height, tc.scaleX, tc.scaleY, tc.rotation);
    }

    public void dispose(){
        batch.dispose();
    }
}