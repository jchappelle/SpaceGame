package com.jchappelle.sg.persistence;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

/**
 * Handles serialization and deserialization of entities and their
 * components to and from Json.
 *
 * @author David Saltares
 */
public class EntitySerializer implements Json.Serializer<Entity> {
    private EngineObjectCreator engineObjectCreator;

    public void setEngineObjectCreator(EngineObjectCreator engineObjectCreator) {
        this.engineObjectCreator = engineObjectCreator;
    }

    @Override
    public void write(Json json, Entity entity, Class knownType) {
        json.writeObjectStart();
        json.writeObjectStart("components");
        for (Component component : entity.getComponents()) {
            if (isTransient(component)) {
                continue;
            }
            write(json, component);
        }
        json.writeObjectEnd();
        json.writeObjectEnd();
    }

    @Override
    public Entity read(Json json, JsonValue jsonData, Class type) {
        try {
            Entity entity = createEntity();

            JsonValue components = jsonData.get("components");
            for (JsonValue componentValue : components) {
                Component component = read(json, componentValue);
                entity.add(component);
            }

            return entity;
        } catch (ReflectionException e) {
            return null;
        }
    }

    private boolean isTransient(Component component) {
        Class componentType = component.getClass();
        return ClassReflection.getAnnotation(componentType, Transient.class) != null;
    }

    private Component read(Json json, JsonValue componentValue) throws ReflectionException {
        String className = componentValue.name();
        Class componentType = TagResolver.getClass(json, className);
        Component component = createComponent(componentType);
        json.readFields(component, componentValue);
        return component;
    }

    private void write(Json json, Component component) {
        Class type = component.getClass();
        String tag = TagResolver.getTag(json, type);
        json.writeObjectStart(tag);
        json.writeFields(component);
        json.writeObjectEnd();
    }

    private Entity createEntity() {
        if (engineObjectCreator != null) {
            return engineObjectCreator.createEntity();
        }
        else {
            return new Entity();
        }
    }

    public <T extends Component> T createComponent (Class<T> type) throws ReflectionException {
        if (engineObjectCreator != null) {
            return engineObjectCreator.createComponent(type);
        }
        else {
            return ClassReflection.newInstance(type);
        }
    }
}