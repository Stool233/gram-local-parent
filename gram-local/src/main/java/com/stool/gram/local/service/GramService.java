package com.stool.gram.local.service;


import com.stool.gram.pojo.GramResult;

import java.util.List;
import java.util.Map;

public interface GramService {

    /**
     * 使用模型进行计算
     * @param timeData 必须是TreeMap等有顺序的Map
     * @return
     */
    public GramResult compute(Map<Integer, Float> timeData);
}
