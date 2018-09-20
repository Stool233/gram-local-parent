package com.stool.client;

import com.stool.core.Classifier;
import com.stool.core.ModelClassifier;
import com.stool.pojo.ModelConfiguration;
import com.stool.pojo.Result;

import java.net.URL;

public class Client {
    public static void main(String[] args) {

        URL url5 = Client.class.getClassLoader().getResource("lstm_0-5");
        String modelDir5 = url5.getPath();

        ModelConfiguration configuration = new ModelConfiguration()
                .setInputTensorName("x-input")
                .setOutputTensorName("output")
                .setModelFileName("model_inference.pb")
                .setLabelFileName("label.txt")
                .setNumberLabel(2)
                .setModelDir(modelDir5);

        Classifier classifier5 = ModelClassifier.getClassifier(configuration);

        float[][][] data = {{{1}, {1}}};
        Result result = classifier5.predict(data);
        System.out.println(result);
        //classifier5.close();

        URL url10 = Client.class.getClassLoader().getResource("lstm_0-15");
        String modelDir10 = url10.getPath();
        configuration.setModelDir(modelDir10);
        Classifier classifier10 = ModelClassifier.getClassifier(configuration);

        Result result2 = classifier10.predict(data);
        System.out.println(result2);
        //classifier10.close();
    }
}
