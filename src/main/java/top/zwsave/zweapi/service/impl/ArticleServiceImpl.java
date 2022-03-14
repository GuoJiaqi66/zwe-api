package top.zwsave.zweapi.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.zwsave.zweapi.config.shiro.JwtUtil;
import top.zwsave.zweapi.controller.form.AddArticleForm;
import top.zwsave.zweapi.controller.form.PageReq;
import top.zwsave.zweapi.db.dao.ArticleDao;
import top.zwsave.zweapi.db.dao.ArticleLikeUserDao;
import top.zwsave.zweapi.db.dao.ArticleLookUserDao;
import top.zwsave.zweapi.db.dao.ArticleStarUserDao;
import top.zwsave.zweapi.db.pojo.Article;
import top.zwsave.zweapi.db.pojo.ArticleLikeUser;
import top.zwsave.zweapi.db.pojo.ArticleLookUser;
import top.zwsave.zweapi.db.pojo.ArticleStarUser;
import top.zwsave.zweapi.exception.ZweApiException;
import top.zwsave.zweapi.service.ArticleService;
import top.zwsave.zweapi.service.COSService;
import top.zwsave.zweapi.task.SimpleMessageTask;
import top.zwsave.zweapi.utils.CopyUtil;
import top.zwsave.zweapi.utils.Tool;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: Ja7
 * @Date: 2022-01-21 11:59
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    ArticleDao articleDao;

    @Resource
    JwtUtil jwtUtil;

    @Resource
    COSService cosService;

    @Resource
    ArticleLikeUserDao articleLikeUserDao;

    @Resource
    ArticleStarUserDao articleStarUserDao;

    @Resource
    ArticleLookUserDao articleLookUserDao;

    @Resource
    SimpleMessageTask simpleMessageTask;

    @Resource
    Tool tool;


    @Override
    public String insert(MultipartFile file, AddArticleForm article, String token) {
        if (article.getText() == null) {
            throw new ZweApiException("文案不能为空");
        }
        Long userId = jwtUtil.getUserId(token);
        String s = RandomUtil.randomNumbers(15).trim();
        long id = Long.parseLong(s);
        Object visible = article.getVisible();
        String s1 = visible + "";
        article.setVisible(s1);
        Article art = CopyUtil.copy(article, Article.class);
        art.setId(id);
        art.setCreateTime(new Date());
        art.setLikeCounts(0);
        art.setStatus("0");
        art.setDelete("0");
        art.setUserId(userId);
        art.setImgPath("string");
        art.setStar(0);
        art.setLookCounts(0);
        int insert = articleDao.insertArticle(art);

        String s2 = cosService.insertArticleImg(file, token, id);

        return s2;
    }

    @Override
    public List selectByPage(int pageNum, int pageSize) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(pageNum, pageSize);
        /*PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(2, 3);*/
        ArrayList<HashMap> articles = articleDao.selectByPage();
        PageInfo<Article> pageInfo = new PageInfo(articles);
        List<Article> list = pageInfo.getList();

        return list;
    }

    @Override
    public Integer deleteArticle(String token, Long id) {
        jwtUtil.verifierToken(token);
        selectVisibleById(id);
        Integer integer = articleDao.deleteArticleById(id);
        return integer;
    }

    @Override
    public String selectVisibleById(Long id) {
        String s = articleDao.selectVisibleById(id);
        if (s == null) {
            throw new ZweApiException("博客不存在");
        }
        return s;
    }

    @Override
    public Integer likeArticle(String token, Long id) {
        Long userId = jwtUtil.getUserId(token);
        ArticleLikeUser articleLikeUser = selectFromArticleLike(userId, id);
        if (articleLikeUser == null) {
            ArticleLikeUser articleLikeUser1 = new ArticleLikeUser();
            articleLikeUser1.setArticleId(id);
            articleLikeUser1.setCreateTime(new Date());
            articleLikeUser1.setUserId(userId);
            String s = RandomUtil.randomNumbers(15).trim();
            long l = Long.parseLong(s);
            articleLikeUser1.setId(l);
            articleLikeUser1.setDelete("0");
            articleLikeUserDao.insert(articleLikeUser1);

            // 根据articleId 查出所属userid
            HashMap hashMap1 = selectInfoByArticle(id);
            String  userId1 = hashMap1.get("userId").toString();

            HashMap hashMap = new HashMap();
            hashMap.put("uuid", tool.uuidString());
            hashMap.put("sender", userId);
            hashMap.put("targetId", id);
            hashMap.put("type", "article");
            hashMap.put("clazz", "like");
            hashMap.put("targetUserId", userId1);
            simpleMessageTask.send(userId1, hashMap);
        } else if (articleLikeUser.getDelete().equals("0")) {
            throw new ZweApiException("已喜欢");
        }else if (articleLikeUser.getDelete().equals("1")) {
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("createTime", new Date());
            stringObjectHashMap.put("userId", userId);
            stringObjectHashMap.put("articleId", id);
            articleLikeUserDao.updateByUserIdAndArticleId(stringObjectHashMap);
        }

        Integer integer = articleDao.likeCountAdd(id);
        return integer;
    }

    @Override
    public Integer removeLikeArticle(String token, Long id) {
        Long userId = jwtUtil.getUserId(token);
        ArticleLikeUser articleLikeUser = selectFromArticleLike(userId, id);
        if (articleLikeUser.getDelete().equals("1")) {
            throw new ZweApiException("已取消夏欢");
        }
        Long id1 = articleLikeUser.getId();
        ArticleLikeUser articleLikeUser1 = new ArticleLikeUser();
        articleLikeUser1.setCreateTime(new Date());
        articleLikeUser1.setId(id1);
        articleLikeUser1.setDelete("1");
        articleLikeUserDao.updateByPrimaryKeySelective(articleLikeUser1);
        Integer integer = articleDao.likeCountRemove(id);
        return integer;
    }

    @Override
    public Integer starArticle(String token, Long id) {
        Long userId = jwtUtil.getUserId(token);
        ArticleStarUser articleLikeUser = selectFromArticleStar(userId, id);
        if (articleLikeUser == null) {
            ArticleStarUser articleLikeUser1 = new ArticleStarUser();
            articleLikeUser1.setArticleId(id);
            articleLikeUser1.setCreateTime(new Date());
            articleLikeUser1.setUserId(userId);
            String s = RandomUtil.randomNumbers(15).trim();
            long l = Long.parseLong(s);
            articleLikeUser1.setId(l);
            articleLikeUser1.setDelete("0");
            articleStarUserDao.insert(articleLikeUser1);

            // 根据articleId 查出所属userId
            HashMap hashMap1 = selectInfoByArticle(id);
            String  userId1 = hashMap1.get("userId").toString();

            HashMap hashMap = new HashMap();
            hashMap.put("uuid", tool.uuidString());
            hashMap.put("sender", userId);
            hashMap.put("targetId", id);
            hashMap.put("type", "article");
            hashMap.put("clazz", "star");
            hashMap.put("targetUserId", userId1);
            simpleMessageTask.send(userId1, hashMap);

        } else if (articleLikeUser.getDelete().equals("0")) {
            throw new ZweApiException("已喜欢");
        }else if (articleLikeUser.getDelete().equals("1")) {
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("createTime", new Date());
            stringObjectHashMap.put("userId", userId);
            stringObjectHashMap.put("articleId", id);
            articleStarUserDao.updateByUserIdAndArticleId(stringObjectHashMap);
        }

        Integer integer = articleDao.starCountAdd(id);
        return integer;
    }

    @Override
    public Integer removeStarArticle(String token, Long id) {
        Long userId = jwtUtil.getUserId(token);
        ArticleStarUser articleLikeUser = selectFromArticleStar(userId, id);
        if (articleLikeUser.getDelete().equals("1")) {
            throw new ZweApiException("已取消夏欢");
        }
        Long id1 = articleLikeUser.getId();
        ArticleStarUser articleLikeUser1 = new ArticleStarUser();
        articleLikeUser1.setCreateTime(new Date());
        articleLikeUser1.setId(id1);
        articleLikeUser1.setDelete("1");
        articleStarUserDao.updateByPrimaryKeySelective(articleLikeUser1);
        Integer integer = articleDao.starCountRemove(id);
        return integer;
    }

    @Override
    public Integer articleLook(String token, Long id) {

        Long userId = jwtUtil.getUserId(token);
        ArticleLookUser articleLookUser = new ArticleLookUser();
        articleLookUser.setArticleId(id);
        articleLookUser.setCreateTime(new Date());
        articleLookUser.setId(Long.parseLong(RandomUtil.randomNumbers(15).trim()));
        articleLookUser.setUserId(userId);
        int insert = articleLookUserDao.insert(articleLookUser);
        articleDao.lookCountAdd(id);
        return insert;
    }

    @Override
    public ArrayList<HashMap> selectAllLooker(String token, Long id) {
        ArrayList<HashMap> hashMaps = articleDao.selectAllLooker(id);
        return hashMaps;
    }

    @Override
    public List selectMyArticle(String token, PageReq pageReq) {
        Long userId = jwtUtil.getUserId(token);
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(pageReq.getPageNum(), pageReq.getPageSize());
        ArrayList<Article> articles = articleDao.selectMyArticle(userId);
        PageInfo<Article> articlePageInfo = new PageInfo(articles);
        List<Article> list = articlePageInfo.getList();
        return list;
    }

    @Override
    public List selectAllLike(String token, PageReq pageReq) {
        Long userId = jwtUtil.getUserId(token);
        ArrayList<HashMap> articles = articleDao.selectAllLike(userId);
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(pageReq.getPageNum(), pageReq.getPageSize());
        PageInfo<HashMap> articlePageInfo = new PageInfo<>(articles);
        List<HashMap> list = articlePageInfo.getList();
        return list;
    }

    @Override
    public List selectAllStar(String token, PageReq pageReq) {
        Long userId = jwtUtil.getUserId(token);
        ArrayList<HashMap> articles = articleDao.selectAllStar(userId);
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(pageReq.getPageNum(), pageReq.getPageSize());
        PageInfo<HashMap> articlePageInfo = new PageInfo<>(articles);
        List<HashMap> list = articlePageInfo.getList();
        return list;
    }

    @Override
    public HashMap selectInfoByArticle(Long id) {
        HashMap hashMap = articleDao.selectInfoByArticle(id);
        if (hashMap == null) {
            throw new ZweApiException("article不存在");
        }
        return hashMap;
    }

    @Override
    public List selectArticleLiker(/*String token,*/ Long id, PageReq pageReq) {
        /*jwtUtil.verifierToken(token);*/
        ArrayList<HashMap> hashMaps = articleDao.selectArticleLiker(id);
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(pageReq.getPageNum(), pageReq.getPageSize());
        PageInfo pageInfo = new PageInfo(hashMaps);
        List list = pageInfo.getList();
        return list;
    }
    @Override
    public List selectArticleStarer(/*String token,*/ Long id, PageReq pageReq) {
        /*jwtUtil.verifierToken(token);*/
        ArrayList<HashMap> hashMaps = articleDao.selectArticleStarer(id);
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(pageReq.getPageNum(), pageReq.getPageSize());
        PageInfo pageInfo = new PageInfo(hashMaps);
        List list = pageInfo.getList();
        return list;
    }

    @Override
    public HashMap selectAllLikeStarId(String token) {
        Long userId = jwtUtil.getUserId(token);
        ArrayList arrayList = articleDao.selectAllLikeId(userId);
        ArrayList arrayList1 = articleDao.selectAllStarId(userId);
        HashMap hashMap = new HashMap();
        hashMap.put("articleLike", arrayList);
        hashMap.put("articleStar", arrayList1);
        return hashMap;
    }

    ArticleLikeUser selectFromArticleLike(Long userId, Long id) {
        HashMap<String, Long> stringLongHashMap = new HashMap<>();
        stringLongHashMap.put("userId", userId);
        stringLongHashMap.put("id", id);
        ArticleLikeUser articleLikeUser = articleLikeUserDao.selectFromArticleLike(stringLongHashMap);
        if (articleLikeUser == null) {
            return null;
        }

        return articleLikeUser;
    }

    ArticleStarUser selectFromArticleStar(Long userId, Long id) {
        HashMap<String, Long> stringLongHashMap = new HashMap<>();
        stringLongHashMap.put("userId", userId);
        stringLongHashMap.put("id", id);
        ArticleStarUser articleLikeUser = articleStarUserDao.selectFromArticleStar(stringLongHashMap);
        if (articleLikeUser == null) {
            return null;
        }

        return articleLikeUser;
    }
}
