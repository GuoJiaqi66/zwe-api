package top.zwsave.zweapi.db.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author: Ja7
 * @Date: 2022-03-13 19:49
 *
 * 个人消息实体
 */
@Data
@Document("personalMessage")
public class PersonalMessage {

    @Id
    private String _id;

    @Indexed
    private String uuid;

    @Indexed
    private String sender;

    @Indexed
    private String targetId;

    @Indexed
    private String type; // article/ video

    @Indexed
    private String clazz; // star/ like

    private String content;

    private String targetUserId;

    @Indexed
    private Boolean readFlag;
}
