package com.stool.gram.local.service.impl;

import com.stool.gram.common.exception.GlobalException;
import com.stool.gram.common.util.response.CodeMsg;
import com.stool.gram.local.dlmodel.GramClassifierMap;

import com.stool.gram.local.service.GramService;
import com.stool.gram.local.dlmodel.GramClassifier;
import com.stool.gram.pojo.GramResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GramServiceImpl implements GramService{


    @Autowired
    private GramClassifierMap gramClassifierMap;


    @Override
    public GramResult compute(Map<Integer, Float> timeData) {
        GramClassifier classifier = gramClassifierMap.get(timeData.keySet());
        if (classifier == null) {
            throw new GlobalException(CodeMsg.MODEL_NOT_FOUND_ERROR);
        }
        return classifier.compute(timeData.values());
    }




}
