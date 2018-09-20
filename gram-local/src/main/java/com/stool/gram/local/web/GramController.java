package com.stool.gram.local.web;


import com.stool.gram.common.util.response.JsonResult;
import com.stool.gram.local.service.GramService;
import com.stool.gram.pojo.GramResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("modelCompute")
public class GramController {

    @Autowired
    private GramService gramService;

    @PostMapping("compute")
    public JsonResult<GramResult> compute(@RequestBody Map<Integer, Float> timeData) {
        GramResult gramResult = gramService.compute(timeData);
        return JsonResult.success(gramResult);
    }

    @PostMapping("computeList")
    public JsonResult<List<GramResult>> computeList(@RequestBody List<Map<Integer, Float>> timeDataList) {
        List<GramResult> gramResultList = timeDataList.stream()
                .map(integerFloatMap -> gramService.compute(integerFloatMap))
                .collect(Collectors.toList());
        return JsonResult.success(gramResultList);
    }


}
