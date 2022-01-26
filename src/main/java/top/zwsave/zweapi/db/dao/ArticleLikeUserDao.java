package top.zwsave.zweapi.db.dao;

import top.zwsave.zweapi.db.pojo.ArticleLikeUser;

import java.util.HashMap;

public interface ArticleLikeUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleLikeUser record);

    int insertSelective(ArticleLikeUser record);

    ArticleLikeUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleLikeUser record);

    int updateByPrimaryKey(ArticleLikeUser record);

    ArticleLikeUser selectFromArticleLike(HashMap map);

    Integer updateByUserIdAndArticleId(HashMap map);
}