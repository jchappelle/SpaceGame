package com.jchappelle.sg.systems.physics;

import com.badlogic.ashley.core.Entity;

public interface CollisionListener {
    void onCollision(Entity entityA, Entity entityB);
}
