package top.zwsave.zweapi.db.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author: Ja7
 * @Date: 2022-03-11 15:57
 */
@Data
@Document("systemMsgRefEntity")
public class SystemMsgRefEntity {

    @Id
    private String _id;

    @Indexed
    private String messageId; // 是messageEntity中的_id, 产生表连接，做多表查询

    @Indexed
    private Integer receiverId;

    @Indexed
    private Boolean readFlag;

    @Indexed
    private Boolean lastFlag;

    private String msg;

    private String with;
}
