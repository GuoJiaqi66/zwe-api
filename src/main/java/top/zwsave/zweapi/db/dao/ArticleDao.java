package top.zwsave.zweapi.db.dao;

import top.zwsave.zweapi.db.pojo.Article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ArticleDao {
    int deleteByPrimaryKey(Long id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    ArrayList<HashMap> selectByPage();

    Integer deleteArticleById(Long id);

    String selectVisibleById(Long id);

    Integer likeCountAdd(Long id);

    Integer likeCountRemove(Long id);

    Integer starCountAdd(Long id);

    Integer starCountRemove(Long id);

    Integer lookCountAdd(Long id);

    ArrayList<HashMap> selectAllLooker(Long articleId);

    ArrayList<Article> selectMyArticle(Long userId);

    ArrayList<HashMap> selectAllLike(Long userId);

    ArrayList<HashMap> selectAllStar(Long userId);

    HashMap selectInfoByArticle(Long id);

    ArrayList<HashMap> selectArticleLiker(Long id);

    ArrayList<HashMap> selectArticleStarer(Long id);

    int insertArticle(Article article);

    ArrayList selectAllLikeId(Long userId);

    ArrayList selectAllStarId(Long userId);

    String selectArticleImgPathByArticleId(String id);
}