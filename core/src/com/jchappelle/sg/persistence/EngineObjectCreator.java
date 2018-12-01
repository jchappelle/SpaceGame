package com.jchappelle.sg.persistence;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public interface EngineObjectCreator {

    Entity createEntity();

    <T extends Component> T createComponent(Class<T> type);
}
