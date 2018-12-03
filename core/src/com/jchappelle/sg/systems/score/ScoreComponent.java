package com.jchappelle.sg.systems.score;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class ScoreComponent implements Component {
    private static ComponentMapper<ScoreComponent> mapper = ComponentMapper.getFor(ScoreComponent.class);
    public static ScoreComponent get(Entity entity){
        return mapper.get(entity);
    }

    public int score;

    public ScoreComponent(int score){
        this.score = score;
    }
}
