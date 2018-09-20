package com.stool.gram.local.config;

import com.stool.gram.local.dlmodel.GramClassifierMap;
import com.stool.gram.local.service.ModelService;
import com.stool.gram.local.dlmodel.GramClassifier;
import com.stool.gram.pojo.database.GramModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ModelConfig{

    Logger logger = LoggerFactory.getLogger(ModelConfig.class);

    @Autowired
    private ModelService modelService;

    @Bean
    public GramClassifierMap initGramClassifierMap() {
        GramClassifierMap gramClassifierMap = new GramClassifierMap();

        List<GramModel> gramModels = modelService.listGramModels();
        gramModels.forEach(gramModel -> gramClassifierMap.put(new GramClassifier(gramModel)));

        return gramClassifierMap;
    }


}
