package top.zwsave.zweapi.db.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @Author: Ja7
 * @Date: 2022-01-10 23:05
 */
@Data
@Document(collection = "test")
@ApiModel
public class MongoDBTest {

    @Id
    private String _id;

    @Indexed(unique = true)
    private String uuid;

    @Indexed
    private String senderId;

    private String senderName;

    @Indexed
    private Date sendTime;
}
