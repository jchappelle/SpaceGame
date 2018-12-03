package com.jchappelle.sg.entities;
import com.jchappelle.sg.GameManager;

import static com.jchappelle.sg.entities.Prefab.*;

public class EntityFactoryProvider {

    public static EntityFactory getEntityFactory(GameManager gameManager){
        DelegatingEntityFactory factory = new DelegatingEntityFactory();
        factory.registerFactory(PLAYER_SHIP, new PlayerShipEntityFactory(gameManager));
        factory.registerFactory(ASTEROID, new AsteroidEntityFactory());
        factory.registerFactory(BULLET, new BulletEntityFactory(gameManager));
        factory.registerFactory(EXPLOSION, new ExplosionEntityFactory());
        return factory;
    }
}
