package top.zwsave.zweapi.db.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author: Ja7
 * @Date: 2022-03-11 15:31
 */
@Data
@Document("systemMsgEntity")
public class SystemMsgEntity {

    @Id
    private String _id;

    @Indexed(unique = true)
    private String uuid;

    private String senderPhoto = "https://static-1258386385.cos.ap-beijing.myqcloud.com/img/System.jpg";

    private String msg;

    private String sender = "system";

    /**
     * 附赠
     * */
    private String with;
}
