package top.zwsave.zweapi.db.dao;

import top.zwsave.zweapi.db.pojo.RabbitmqtestOrder;

public interface RabbitmqtestOrderDao {
    int deleteByPrimaryKey(String id);

    int insert(RabbitmqtestOrder record);

    int insertSelective(RabbitmqtestOrder record);

    RabbitmqtestOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RabbitmqtestOrder record);

    int updateByPrimaryKey(RabbitmqtestOrder record);
}