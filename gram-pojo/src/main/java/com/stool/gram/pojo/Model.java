package com.stool.gram.pojo;

public class Model {

    private Long id;

    // 模型名字，将展示到前端的名字
//    @NotNull
    private String modelName;

    // 模型所在文件夹
    private String modelDir;
    // 模型文件名字
    private String modelFileName;
    // 类别文件名字
    private String labelFileName;

    // 模型中输入和输出的节点名字，模型运行时需要用到
//    @NotNull
    private String inputTensorName;
//    @NotNull
    private String outputTensorName;

    // 类别个数
//    @NotNull
    private Integer numberLabel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getNumberLabel() {
        return numberLabel;
    }

    public void setNumberLabel(Integer numberLabel) {
        this.numberLabel = numberLabel;
    }
}
