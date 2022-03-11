package top.zwsave.zweapi.db.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Ja7
 * @Date: 2022-03-11 12:04
 */
@Data
@Document("systemMsgEntity")
public class SimpleMsgEntity implements Serializable {

    @Id
    private String _id;

    @Indexed(unique = true) // 唯一性
    private String uuid; // 放置轮询时重复读消息

    @Indexed
    private Integer senderId;

    private String senderPhoto = "https://static-1258386385.cos.ap-beijing.myqcloud.com/img/System.jpg";

    private String senderName;

    private String msg;

    @Indexed
    private Date sendTime;

}
