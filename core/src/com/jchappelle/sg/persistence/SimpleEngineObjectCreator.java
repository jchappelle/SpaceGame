package com.jchappelle.sg.persistence;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class SimpleEngineObjectCreator implements EngineObjectCreator {

    @Override
    public Entity createEntity() {
        return new Entity();
    }

    @Override
    public <T extends Component> T createComponent (Class<T> type) {
        try{
            return ClassReflection.newInstance(type);
        }
        catch(ReflectionException e){
            throw new RuntimeException(e);
        }

    }

}
