package com.jchappelle.sg.systems.render;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.jchappelle.sg.Entities;
import com.jchappelle.sg.GameManager;
import com.jchappelle.sg.systems.level.LevelComponent;
import com.jchappelle.sg.components.TransformComponent;
import com.jchappelle.sg.systems.damage.HealthComponent;
import com.jchappelle.sg.systems.player.PlayerComponent;

public class RenderSystem extends EntitySystem implements Disposable {
    private ImmutableArray<Entity> entities;

    SpriteBatch batch;
    private OrthographicCamera camera;
    private BitmapFont font;
    private GameManager gameManager;

    public RenderSystem(GameManager gameManager){
        this.gameManager = gameManager;

        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth * .5f, camera.viewportHeight * .5f, 0f);
        font = gameManager.newFont(12);

    }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class).one(SpriteComponent.class, AnimationComponent.class).get());
    }

    public void update(float deltaTime) {
        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        gameManager.getBackground().updateAndRender(deltaTime, batch);
        for (int i = 0; i < entities.size(); ++i) {
            renderEntity(entities.get(i), deltaTime);
        }
        drawScore();
        batch.end();

    }

    private void drawScore(){
        Entity player = gameManager.getPlayer();
        if(player != null){
            PlayerComponent pc = PlayerComponent.get(player);
            font.draw(batch, "Score: " + pc.score, 10, Gdx.graphics.getHeight() - 10);

            HealthComponent hc = HealthComponent.get(player);
            font.draw(batch, "Health: " + hc.health, 10, Gdx.graphics.getHeight() - 30);

            LevelComponent lc = LevelComponent.get(Entities.get().getGame());
            font.draw(batch, "Level: " + lc.level, 10, Gdx.graphics.getHeight() - 50);
        }
    }

    private void renderEntity(Entity entity, float deltaTime){
        TransformComponent tc = TransformComponent.get(entity);

        SpriteComponent sc = SpriteComponent.get(entity);
        if(sc != null){
            float alpha = 1;
            BlinkingComponent bc = BlinkingComponent.get(entity);
            if(bc != null){
                bc.timer += deltaTime;
                bc.blinkTimer += bc.speed * deltaTime;
                if(bc.timer > bc.timeToLive){
                    entity.remove(BlinkingComponent.class);
                }
                else{
                    alpha = (float)Math.abs(Math.sin(bc.blinkTimer));
                }
            }

            Sprite sprite = sc.sprite;
            sprite.setOriginCenter();
            sprite.setPosition(tc.x, tc.y);
            sprite.setOrigin(tc.originX, tc.originY);
            sprite.setSize(tc.width, tc.height);
            sprite.setScale(tc.scaleX, tc.scaleY);
            sprite.setRotation(tc.rotation);
            sprite.draw(batch, alpha);
        }

        AnimationComponent ac = AnimationComponent.get(entity);
        if(ac != null){
            ac.stateTime += deltaTime;
            if(!ac.animation.isAnimationFinished(ac.stateTime)){
                batch.draw(ac.animation.getKeyFrame(ac.stateTime), tc.x, tc.y, tc.originX, tc.originY, tc.width, tc.height, tc.scaleX, tc.scaleY, tc.rotation);
            }
        }
    }

    public void dispose(){
        batch.dispose();
        font.dispose();
    }
}
