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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GramClassifier{

    private Classifier classifier;

    private GramModel gramModel;

    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private Lock readLock = rwl.readLock();
    private Lock writeLock = rwl.writeLock();

    public GramClassifier(GramModel gramModel) {
        this.gramModel = gramModel;
        buildClassifier();
    }

    /**
     * 读写锁，防止重建一半即被访问
     */
    public GramResult compute(Collection<Float> data) {
        readLock.lock();
        try {
            return compute(new ArrayList<>(data));
        } finally {
            readLock.unlock();
        }
    }

    private GramResult compute(List<Float> data) {
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
     * 读写锁，防止重建一半即被访问
     */
    public void buildClassifier() {
        writeLock.lock();
        try {
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
        } finally {
            writeLock.unlock();
        }

    }

    public void close() {
        writeLock.lock();
        try {
            classifier.close();
        } finally {
            writeLock.unlock();
        }

    }

    public GramModel getGramModel() {
        readLock.lock();
        try {
            return gramModel;
        } finally {
            readLock.unlock();
        }
    }

}
