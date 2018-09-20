package com.stool.gram.admin.service;



import com.stool.gram.pojo.database.GramModel;

import java.util.List;

public interface ModelService {

    /**
     * 查询所有支持的模型
     * @return
     */
    public List<GramModel> listGramModels();

    /**
     * 插入新的模型
     * @param gramModel
     */
    public void insertGramModel(GramModel gramModel);


    /**
     * 更新模型
     * @param gramModel
     */
    public void updateGramModelByName(GramModel gramModel);

}
