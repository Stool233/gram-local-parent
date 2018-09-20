package com.stool.core;

import com.stool.pojo.Model;
import com.stool.pojo.ModelConfiguration;

public class ModelDirector {
    private ModelBuilder modelBuilder;

    public ModelDirector(ModelBuilder modelBuilder) {
        this.modelBuilder = modelBuilder;
    }

    public Model getModel() {
        return modelBuilder.getModel();
    }

    public void createModel(ModelConfiguration configuration) {
        modelBuilder.createNewModel()
            .buildPath(configuration.getModelDir(), configuration.getModelFileName(), configuration.getLabelFileName())
            .buildTensorName(configuration.getInputTensorName(), configuration.getOutputTensorName())
            .buildNumberLabel(configuration.getNumberLabel())
            .buildLabels()
            .buildGraphDef()
            .buildComputeGraph();
    }

}
