package com.noiprocs.core;

import com.noiprocs.core.model.BaseModel;
import com.noiprocs.core.model.MovableObjectModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WorldContainer implements Serializable {
    private List<BaseModel> modelList = new ArrayList<>();

    public WorldContainer() {
        MovableObjectModel movableObjectModel = new MovableObjectModel();
        movableObjectModel.setX(20);
        movableObjectModel.setY(40);
        modelList.add(movableObjectModel);
    }
}
