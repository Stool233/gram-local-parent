package com.stool.gram.local.service.impl;

import com.stool.gram.dao.GramModelMapper;
import com.stool.gram.local.service.ModelService;
import com.stool.gram.pojo.database.GramModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private GramModelMapper gramModelMapper;

    @Override
    public List<GramModel> listGramModels() {
        return gramModelMapper.selectAll();
    }

    @Override
    public GramModel getGramModelByName(String modelName) {
        return gramModelMapper.selectByModelName(modelName);
    }
}
