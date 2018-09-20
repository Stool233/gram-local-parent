package com.stool.pojo;

public class ModelConfiguration {

    public static final String DEFAULT_MODEL_NAME = "model_inference.pb";
    public static final String DEFAULT_LABEL_FILE_NAME = "label.txt";
    public static final String DEFAULT_INPUT_TENSOR_NAME = "x-input";
    public static final String DEFAULT_OUTPUT_TENSOR_NAME = "output";

    private String modelName;
    private String modelDir;
    private String modelFileName = DEFAULT_MODEL_NAME;
    private String labelFileName = DEFAULT_LABEL_FILE_NAME;
    private Integer numberLabel;
    private String inputTensorName = DEFAULT_INPUT_TENSOR_NAME;
    private String outputTensorName = DEFAULT_OUTPUT_TENSOR_NAME;

    public String getModelDir() {
        return modelDir;
    }

    public ModelConfiguration setModelDir(String modelDir) {
        this.modelDir = modelDir;
        return this;
    }

    public String getModelFileName() {
        return modelFileName;
    }

    public ModelConfiguration setModelFileName(String modelFileName) {
        this.modelFileName = modelFileName;
        return this;
    }

    public String getLabelFileName() {
        return labelFileName;
    }

    public ModelConfiguration setLabelFileName(String labelFileName) {
        this.labelFileName = labelFileName;
        return this;
    }

    public Integer getNumberLabel() {
        return numberLabel;
    }

    public ModelConfiguration setNumberLabel(Integer numberLabel) {
        this.numberLabel = numberLabel;
        return this;
    }

    public String getInputTensorName() {
        return inputTensorName;
    }

    public ModelConfiguration setInputTensorName(String inputTensorName) {
        this.inputTensorName = inputTensorName;
        return this;
    }

    public String getOutputTensorName() {
        return outputTensorName;
    }

    public ModelConfiguration setOutputTensorName(String outputTensorName) {
        this.outputTensorName = outputTensorName;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public ModelConfiguration setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }
}
