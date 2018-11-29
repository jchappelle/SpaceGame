package com.jchappelle.sg.systems.level;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class LevelComponent implements Component {
    private static ComponentMapper<LevelComponent> mapper = ComponentMapper.getFor(LevelComponent.class);
    public static LevelComponent get(Entity entity){
        return mapper.get(entity);
    }

    public int level = 1;
    public int totalXp;
}
