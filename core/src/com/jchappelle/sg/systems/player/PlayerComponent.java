package com.jchappelle.sg.systems.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class PlayerComponent implements Component {
    private static ComponentMapper<PlayerComponent> mapper = ComponentMapper.getFor(PlayerComponent.class);
    public static PlayerComponent get(Entity entity){
        return mapper.get(entity);
    }

    public int score;

}
