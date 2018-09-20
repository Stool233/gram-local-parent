package com.stool.gram.dao;


import com.stool.gram.pojo.database.GramModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GramModelMapper {

    List<GramModel> list();

    List<GramModel> selectAll();

    int insert(GramModel gramModel);

    int updateByName(GramModel gramModel);

    GramModel selectByModelName(@Param("modelName") String modelName);
}
