package com.jchappelle.sg.entities;

import com.badlogic.ashley.core.Entity;
import com.jchappelle.sg.components.TransformComponent;

public interface EntityFactory {

    Entity make(String prefabId);

    Entity make(String prefabId, TransformComponent transform);

}
