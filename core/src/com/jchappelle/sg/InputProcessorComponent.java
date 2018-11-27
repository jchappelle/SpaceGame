package com.jchappelle.sg;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.InputProcessor;

public class InputProcessorComponent implements Component {
    private static ComponentMapper<InputProcessorComponent> mapper = ComponentMapper.getFor(InputProcessorComponent.class);
    public static InputProcessorComponent get(Entity entity){
        return mapper.get(entity);
    }

    public InputProcessor inputProcessor;

    public InputProcessorComponent(InputProcessor inputProcessor){
        this.inputProcessor = inputProcessor;
    }
}
