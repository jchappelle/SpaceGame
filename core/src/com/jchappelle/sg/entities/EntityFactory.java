package com.jchappelle.sg.entities;

import com.badlogic.ashley.core.Entity;

public interface EntityFactory {

    Entity make(Prefab prefab);

}
