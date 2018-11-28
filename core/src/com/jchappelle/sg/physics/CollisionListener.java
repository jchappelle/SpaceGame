package com.jchappelle.sg.physics;

import com.badlogic.ashley.core.Entity;

public interface CollisionListener {
    void onCollision(Entity entityA, Entity entityB);
}
