package com.jchappelle.sg.systems.level;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class XpComponent implements Component {
    private static ComponentMapper<XpComponent> mapper = ComponentMapper.getFor(XpComponent.class);
    public static XpComponent get(Entity entity){
        return mapper.get(entity);
    }

    public int xp;

    public XpComponent(int xp){
        this.xp = xp;
    }
}
