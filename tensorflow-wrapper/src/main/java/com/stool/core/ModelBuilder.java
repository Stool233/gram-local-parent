package com.stool.core;

import com.stool.pojo.Model;
import com.stool.util.FileUtil;
import org.tensorflow.Graph;
import org.tensorflow.Session;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import static com.stool.util.FileUtil.readAllBytes;

public class ModelBuilder {

    private Model model;

    public ModelBuilder createNewModel() {
        model = new Model();
        return this;
    }

    public ModelBuilder buildPath(String modelDir, String modelFileName, String labelFileName) {
        model.setModelDir(modelDir);
        model.setModelFileName(modelFileName);
        model.setLabelFileName(labelFileName);
        return this;
    }

    public ModelBuilder buildTensorName(String inputTensorName, String outputTensorName) {
        model.setInputTensorName(inputTensorName);
        model.setOutputTensorName(outputTensorName);
        return this;
    }

    public ModelBuilder buildNumberLabel(Integer numberLabel) {
        model.setNumberLabel(numberLabel);
        return this;
    }

    public ModelBuilder buildLabels() {
        List<String> labels = FileUtil.readAllLines(Paths.get(model.getModelDir()+
                File.separator+
                model.getLabelFileName()));
        model.setLabels(labels);
        return this;
    }

    public ModelBuilder buildGraphDef() {
        byte[] graphDef = readAllBytes(Paths.get(model.getModelDir()+
                File.separator+
                model.getModelFileName()));
        model.setGraphDef(graphDef);
        return this;
    }

    public ModelBuilder buildComputeGraph() {
        Graph computeGraph = new Graph();
        computeGraph.importGraphDef(model.getGraphDef());
        model.setComputeGraph(computeGraph);
        return this;
    }


    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
