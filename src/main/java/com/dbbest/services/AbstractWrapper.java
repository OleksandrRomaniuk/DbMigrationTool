package com.dbbest.services;

import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

public abstract class AbstractWrapper {

    protected Container getWrapper(Container root) throws ContainerException {
        Container wrapper = new Container();
        wrapper.setLabel("Schemas");
        wrapper.setValue("/Schemas");
        wrapper.setChecked(false);
        wrapper.setExpanded(true);
        wrapper.addChild(root);
        return wrapper;
    }
}
