package top.zwsave.zweapi.db.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Ja7
 * @Date: 2022-03-06 19:23
 */
@Data
@ApiModel
@Document("videoComment")
public class MongoVideoComment implements Serializable {

    /**
     * 有一个大的前提
     * 然后--评论/回复
     * father 也许会更好
     * */

    @Id
    private String _id;

    @Indexed
    private String uuid;

    @Indexed
    private String videoId;

    @Indexed
    private String from;

    @Indexed
    private String to;

    private String content;

    private Date createTime;

    private int del;
}
