package com.stool.core;

import com.stool.pojo.Model;
import com.stool.pojo.ModelConfiguration;
import com.stool.pojo.Result;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

public class ModelClassifier implements Classifier{

    // 线程安全，防止执行到一半模型就被关闭
    private volatile boolean computing = false;

    private Model model;

    private ModelClassifier(Model model) {
        this.model = model;
    }

    public static Classifier getClassifier(ModelConfiguration modelConfiguration) {
        ModelDirector modelDirector = new ModelDirector(new ModelBuilder());
        modelDirector.createModel(modelConfiguration);
        Model model = modelDirector.getModel();
        ModelClassifier modelClassifier = new ModelClassifier(model);
        return modelClassifier;
    }

    @Override
    public Result predict(float[][][] data) {

        computing = true;

        try (Session sess = new Session(model.getComputeGraph());
             Tensor<Float> result = sess.runner().feed(model.getInputTensorName(), Tensor.create(data, Float.class))
                .fetch(model.getOutputTensorName()).run().get(0).expect(Float.class)) {

            float[] labelProbabilities = result.copyTo(new float[1][model.getNumberLabel()])[0];

            computing = false;

            //float[] labelProbabilities = result.copyTo(new float[numLabels]);
            int bestLabelIdx = maxIndex(labelProbabilities);
            return new Result(model.getLabels().get(bestLabelIdx), labelProbabilities[bestLabelIdx]);
        }
    }

    /**
     *
     * @param data 必须是原始类型或者String
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> Result predict(T data, Class<?> clazz) {

        computing = true;

        try (Session sess = new Session(model.getComputeGraph());
             Tensor<Float> result = sess.runner().feed(model.getInputTensorName(), Tensor.create(data, clazz))
                     .fetch(model.getOutputTensorName()).run().get(0).expect(Float.class)) {

            float[] labelProbabilities = result.copyTo(new float[1][model.getNumberLabel()])[0];

            computing = false;

            //float[] labelProbabilities = result.copyTo(new float[numLabels]);
            int bestLabelIdx = maxIndex(labelProbabilities);
            return new Result(model.getLabels().get(bestLabelIdx), labelProbabilities[bestLabelIdx]);
        }
    }

    private int maxIndex(float[] probabilities) {
        int best = 0;
        for (int i = 1; i < probabilities.length; ++i) {
            if (probabilities[i] > probabilities[best]) {
                best = i;
            }
        }
        return best;
    }

    @Override
    public void close() {
        while (computing) {
            // 等待执行结束
        }
        model.getComputeGraph().close();
    }
}
