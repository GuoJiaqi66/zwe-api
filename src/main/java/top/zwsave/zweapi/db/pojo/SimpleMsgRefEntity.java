package top.zwsave.zweapi.db.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @Author: Ja7
 * @Date: 2022-03-11 12:06
 */
@Data
@Document("simpleMsgRefEntity")
public class SimpleMsgRefEntity implements Serializable {

    @Id
    private String _id;

    @Indexed(unique = true)
    private String messageId; // 是messageEntity中的_id, 产生表连接，做多表查询

    @Indexed
    private Long receiverId;

    @Indexed
    private Boolean readFlag;

    @Indexed
    private Boolean lastFlag;
}
