package top.zwsave.zweapi.db.dao;

import top.zwsave.zweapi.db.pojo.ArticleLookUser;

public interface ArticleLookUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleLookUser record);

    int insertSelective(ArticleLookUser record);

    ArticleLookUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleLookUser record);

    int updateByPrimaryKey(ArticleLookUser record);
}