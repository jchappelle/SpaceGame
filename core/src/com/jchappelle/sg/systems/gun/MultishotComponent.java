package com.jchappelle.sg.systems.gun;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class MultishotComponent implements Component {
    private static ComponentMapper<MultishotComponent> mapper = ComponentMapper.getFor(MultishotComponent.class);
    public static MultishotComponent get(Entity entity){
        return mapper.get(entity);
    }

    public int bulletCount = 7;

    public MultishotComponent(){

    }

    public MultishotComponent(int bulletCount){
        this.bulletCount = bulletCount;
    }

}
