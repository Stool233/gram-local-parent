package com.stool.gram.local.service;



import com.stool.gram.pojo.database.GramModel;

import java.util.List;

public interface ModelService {

    /**
     * 查询所有支持的模型
     * @return
     */
    public List<GramModel> listGramModels();


    /**
     * 通过模型名称查询模型
     * @param modelName
     * @return
     */
    public GramModel getGramModelByName(String modelName);

}
