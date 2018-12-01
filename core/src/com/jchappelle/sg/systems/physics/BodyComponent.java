package com.jchappelle.sg.systems.physics;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.jchappelle.sg.Constants;

public class BodyComponent implements Component {
    private static ComponentMapper<BodyComponent> mapper = ComponentMapper.getFor(BodyComponent.class);
    public static BodyComponent get(Entity entity){
        return mapper.get(entity);
    }

    public Body body;
    public BodyDef bodyDef;
    public FixtureDef fixtureDef;
    public PolygonShape shape;

    private float width;
    private float height;

    public BodyComponent(float x, float y, float width, float height, float density, short collisionCategory, short collisionMask){
        this.width = width;
        this.height = height;

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        setPosition(x, y);

        shape = new PolygonShape();
        shape.setAsBox(width/2 / Constants.PIXELS_TO_METERS, height /2 / Constants.PIXELS_TO_METERS, new Vector2(width/2/ Constants.PIXELS_TO_METERS,height/2/ Constants.PIXELS_TO_METERS),0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.filter.categoryBits = collisionCategory;
        fixtureDef.filter.maskBits = collisionMask;
    }

    public void setPosition(float x, float y){
        bodyDef.position.set((x + width/2) / Constants.PIXELS_TO_METERS, (y + height/2) / Constants.PIXELS_TO_METERS);
    }
}
