package com.stool.gram.local.web;

import com.stool.gram.common.util.response.JsonResult;
import com.stool.gram.local.service.ModelService;
import com.stool.gram.pojo.database.GramModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @GetMapping("")
    public JsonResult<List<GramModel>> getGramModels() {
        List<GramModel> gramModels = modelService.listGramModels();
        return JsonResult.success(gramModels);
    }

}
