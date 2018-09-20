package com.stool.gram.pojo.database;


import com.stool.gram.pojo.Model;

import java.util.List;

/**
 * 产酶检测模型类，将写入数据库中
 */
public class GramModel extends Model {

    // 需要作为输入的时间点列表 以min为单位
//    @NotNull
    private List<Integer> times;

    public List<Integer> getTimes() {
        return times;
    }

    public void setTimes(List<Integer> times) {
        this.times = times;
    }

}
