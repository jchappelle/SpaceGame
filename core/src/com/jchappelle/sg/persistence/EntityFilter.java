package com.jchappelle.sg.persistence;

import com.badlogic.ashley.core.Entity;

/**
 * Interface that can be used to select the entities that should be
 * serialized by {@link EngineSerializer}.
 *
 * @author David Saltares
 */
public interface EntityFilter {
    /**
     * @return true if the entity should be serialized.
     */
    boolean filter(Entity entity);
}