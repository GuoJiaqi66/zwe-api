package top.zwsave.zweapi.db.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @Author: Ja7
 * @Date: 2022-03-06 19:23
 */
@Data
@ApiModel
@Document("articleComment")
public class MongoArticleComment implements Serializable {

    /**
     * 有一个大的前提
     * 然后--评论/回复
     * */

    @Id
    private String _id;

    @Indexed
    private String uuid;

    @Indexed
    private String articleId;

    @Indexed
    private String from;

    @Indexed
    private String to;

    private String content;
}