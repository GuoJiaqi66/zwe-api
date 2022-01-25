package top.zwsave.zweapi.db.dao;

import top.zwsave.zweapi.db.pojo.UserFollow;

public interface UserFollowDao {
    int deleteByPrimaryKey(Long id);

    int insert(UserFollow record);

    int insertSelective(UserFollow record);

    UserFollow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserFollow record);

    int updateByPrimaryKey(UserFollow record);

    Long selectNoteByUseredId(Long useredId);
}