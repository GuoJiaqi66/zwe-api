package top.zwsave.zweapi.db.dao;

import top.zwsave.zweapi.db.pojo.Article;

import java.util.ArrayList;
import java.util.HashMap;

public interface ArticleDao {
    int deleteByPrimaryKey(Long id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    ArrayList<HashMap> selectByPage();
}