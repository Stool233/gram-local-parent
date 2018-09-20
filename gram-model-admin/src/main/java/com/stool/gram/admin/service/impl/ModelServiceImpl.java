package com.stool.gram.admin.service.impl;


import com.stool.gram.admin.service.ModelService;

import com.stool.gram.dao.GramModelMapper;
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
        return gramModelMapper.list();
    }

    @Override
    public void insertGramModel(GramModel gramModel) {
        gramModelMapper.insert(gramModel);
    }

    @Override
    public void updateGramModelByName(GramModel gramModel) {
        gramModelMapper.updateByName(gramModel);
    }
}
