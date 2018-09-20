package com.stool.core;

import com.stool.pojo.Result;

public interface Classifier {

    public Result predict(float[][][] data);

    public <T> Result predict(T data, Class<?> clazz);

    public void close();
}
