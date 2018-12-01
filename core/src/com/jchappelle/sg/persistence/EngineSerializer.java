package com.jchappelle.sg.persistence;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.reflect.Annotation;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

/**
 * Handles serialization and deserialization to and from Json of
 * a whole {@link Engine}. It can also be used for
 * {@link PooledEngine}.
 *
 * @author David Saltares
 */
public class EngineSerializer<T extends Engine> implements Json.Serializer<T> {
    private final EntityFilter entityFilter;
    private final EngineObjectCreator engineObjectCreator;

    public EngineSerializer() {
        this(null);
    }

    /**
     * An {@link EntityFilter} can be passed to select which entities
     * are serialized. If no filter is present, all entities will be
     * serialized.
     *
     * @param entityFilter
     */
    public EngineSerializer(EntityFilter entityFilter) {
        this.entityFilter = entityFilter;
        this.engineObjectCreator = new SimpleEngineObjectCreator();
    }

    @Override
    public void write(Json json, T engine, Class knownType) {
        prepareEntitySerializer(json);
        json.writeObjectStart();
        writeEntities(json, engine.getEntities());
        writeSystems(json, engine.getSystems());
        json.writeObjectEnd();
    }

    @Override
    public T read(Json json, JsonValue jsonData, Class type) {
        try {
            T engine = (T)ClassReflection.newInstance(type);

            prepareEntitySerializer(json);

            JsonValue systems = jsonData.get("systems");
            readSystems(json, engine, systems);

            JsonValue entities = jsonData.get("entities");
            readEntities(json, engine, entities);

            return engine;
        }
        catch(ReflectionException e) {
            return null;
        }
    }

    private void writeEntities(Json json, ImmutableArray<Entity> entities) {
        json.writeArrayStart("entities");
        for (Entity entity : entities) {
            if (shouldWrite(entity)) {
                json.writeValue(entity);
            }
        }
        json.writeArrayEnd();
    }

    private void writeSystems(Json json, ImmutableArray<EntitySystem> systems) {
        json.writeObjectStart("systems");
        for (EntitySystem system : systems) {
            if (!isTransient(system.getClass())) {
                writeSystem(json, system);
            }
        }
        json.writeObjectEnd();
    }

    private void writeSystem(Json json, EntitySystem system) {
        Class systemType = system.getClass();
        String tag = TagResolver.getTag(json, systemType);
        json.writeObjectStart(tag);
        json.writeValue(system);
        json.writeObjectEnd();
    }

    private void readSystems(Json json, T engine, JsonValue systems) throws ReflectionException {
        for (JsonValue systemValue : systems) {
            EntitySystem system = readSystem(json, systemValue);
            engine.addSystem(system);
        }
    }

    private void readEntities(Json json, T engine, JsonValue entities) {
        for (JsonValue entityValue : entities) {
            Entity entity = json.readValue(Entity.class, entityValue);
            engine.addEntity(entity);
        }
    }

    private EntitySystem readSystem(Json json, JsonValue value) throws ReflectionException{
        String name = value.name();
        Class type = TagResolver.getClass(json, name);
        return (EntitySystem) json.readValue(type, value);
    }

    private void prepareEntitySerializer(Json json) {
        EntitySerializer entitySerializer;
        Json.Serializer serializer = json.getSerializer(Entity.class);

        if (serializer instanceof EntitySerializer) {
            entitySerializer = (EntitySerializer)serializer;
        }
        else {
            entitySerializer = new EntitySerializer();
            json.setSerializer(Entity.class, entitySerializer);
        }

        entitySerializer.setEngineObjectCreator(engineObjectCreator);
    }

    private boolean isTransient(Class type) {
        Annotation annotation = ClassReflection.getAnnotation(type, Transient.class);
        return annotation != null;
    }

    private boolean shouldWrite(Entity entity) {
        return entityFilter == null || entityFilter.filter(entity);
    }
}