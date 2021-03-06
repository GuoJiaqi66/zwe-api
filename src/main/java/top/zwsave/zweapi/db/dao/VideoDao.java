package top.zwsave.zweapi.db.dao;

import top.zwsave.zweapi.db.pojo.Video;

import java.util.ArrayList;
import java.util.HashMap;

public interface VideoDao {
    int deleteByPrimaryKey(Long id);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);

    ArrayList<HashMap> selectByPage();

    Integer deleteVideoById(Long id);

    String selectVisibleById(Long id);

    Integer likeCountAdd(Long id);

    Integer likeCountRemove(Long id);

    Integer starCountAdd(Long id);

    Integer starCountRemove(Long id);

    Integer videoLookCountAdd(Long id);

    ArrayList<HashMap> selectVideoAllLooker(Long videoId);

    ArrayList<Video> selectMyVideo(Long userId);

    ArrayList<HashMap> selectAllLike(Long userId);

    ArrayList<HashMap> selectAllStar(Long userId);

    ArrayList<HashMap> selectVideoLiker(Long id);

    ArrayList<HashMap> selectVideoStarer(Long id);

    ArrayList selectAllLikeId(Long userId);

    ArrayList selectAllStarId(Long userId);

    String selectInfoByVideo(String id);

    String selectVideoImgPathByArticleId(String id);

    ArrayList selectByUserIdAllVideo(String id);
}
