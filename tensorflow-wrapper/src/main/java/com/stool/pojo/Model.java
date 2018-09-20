package com.stool.pojo;

import org.tensorflow.Graph;
import org.tensorflow.Session;

import java.util.List;

public class Model {
    private String modelName;
    private String modelDir;
    private String modelFileName;
    private String labelFileName;
    private Integer numberLabel;

    // 保存从模型文件中提取出的模型
    private byte[] graphDef;
    // 类别数组，保存从类别文件中提取出的类别
    private List<String> labels;
    // 模型预测时的计算图
    private Graph computeGraph;

    private String inputTensorName;
    private String outputTensorName;


    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelDir() {
        return modelDir;
    }

    public void setModelDir(String modelDir) {
        this.modelDir = modelDir;
    }

    public String getModelFileName() {
        return modelFileName;
    }

    public void setModelFileName(String modelFileName) {
        this.modelFileName = modelFileName;
    }

    public String getLabelFileName() {
        return labelFileName;
    }

    public void setLabelFileName(String labelFileName) {
        this.labelFileName = labelFileName;
    }

    public Integer getNumberLabel() {
        return numberLabel;
    }

    public void setNumberLabel(Integer numberLabel) {
        this.numberLabel = numberLabel;
    }

    public byte[] getGraphDef() {
        return graphDef;
    }

    public void setGraphDef(byte[] graphDef) {
        this.graphDef = graphDef;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public Graph getComputeGraph() {
        return computeGraph;
    }

    public void setComputeGraph(Graph computeGraph) {
        this.computeGraph = computeGraph;
    }


    public String getInputTensorName() {
        return inputTensorName;
    }

    public void setInputTensorName(String inputTensorName) {
        this.inputTensorName = inputTensorName;
    }

    public String getOutputTensorName() {
        return outputTensorName;
    }

    public void setOutputTensorName(String outputTensorName) {
        this.outputTensorName = outputTensorName;
    }
}
