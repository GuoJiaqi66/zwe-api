package top.zwsave.zweapi.db.dao;

import top.zwsave.zweapi.db.pojo.ArticleLikeUser;
import top.zwsave.zweapi.db.pojo.ArticleStarUser;

import java.util.HashMap;

public interface ArticleStarUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleStarUser record);

    int insertSelective(ArticleStarUser record);

    ArticleStarUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleStarUser record);

    int updateByPrimaryKey(ArticleStarUser record);

    ArticleLikeUser selectFromArticleStar(HashMap<String, Long> stringLongHashMap);

    Integer updateByUserIdAndArticleId(HashMap<String, Object> stringObjectHashMap);
}