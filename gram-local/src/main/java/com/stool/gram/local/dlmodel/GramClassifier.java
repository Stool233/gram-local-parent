package com.stool.gram.local.dlmodel;

import com.stool.core.Classifier;
import com.stool.core.ModelClassifier;

import com.stool.gram.pojo.GramResult;
import com.stool.gram.pojo.database.GramModel;
import com.stool.pojo.ModelConfiguration;
import com.stool.pojo.Result;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GramClassifier{

    private Classifier classifier;

    private GramModel gramModel;

    public GramClassifier(GramModel gramModel) {
        this.gramModel = gramModel;
        buildClassifier();
    }

    public GramResult compute(Collection<Float> data) {
        return compute(new ArrayList<>(data));
    }

    public GramResult compute(List<Float> data) {
        float[][][] arraysData = convertToArrays(data);
        Result result = classifier.predict(arraysData, Float.class);
        return convertToGramResult(result);
    }

    /**
     * 将Result转成GramResult，减少代码与tensorFlowWrapper包耦合
     * @param result
     * @return
     */
    private GramResult convertToGramResult(Result result) {
        GramResult gramResult = new GramResult();
        BeanUtils.copyProperties(result, gramResult);
        return gramResult;
    }

    /**
     * 将data转成三维数组，因为本项目的模型的输入都是三维数组
     * @param data
     * @return
     */
    private float[][][] convertToArrays(List<Float> data) {
        float[][][] arraysData = new float[1][data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            arraysData[0][i][0] = data.get(i);
        }
        return arraysData;
    }

    /**
     * 加锁，防止重建一半即被访问
     */
    public synchronized void buildClassifier() {

        if (classifier != null) {
            classifier.close();
        }

        ModelConfiguration configuration = new ModelConfiguration()
                .setModelDir(gramModel.getModelDir())
                .setModelFileName(gramModel.getModelFileName())
                .setLabelFileName(gramModel.getLabelFileName())
                .setNumberLabel(gramModel.getNumberLabel())
                .setInputTensorName(gramModel.getInputTensorName())
                .setOutputTensorName(gramModel.getOutputTensorName())
                .setModelName(gramModel.getModelName());

        this.classifier = ModelClassifier.getClassifier(configuration);

    }

    public void close() {
        classifier.close();
    }

    public GramModel getGramModel() {
        return gramModel;
    }

    public void setGramModel(GramModel gramModel) {
        this.gramModel = gramModel;
    }
}
