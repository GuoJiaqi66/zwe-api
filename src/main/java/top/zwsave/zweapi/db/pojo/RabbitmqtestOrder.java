package top.zwsave.zweapi.db.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * rabbitmqtest_order
 * @author 
 */
@Data
public class RabbitmqtestOrder implements Serializable { // Serializable 必填：因为要进行网络传输，扔到对应mq上


    private String id;

    private String name;

    private String messageId; // 消息的唯一标识

    private static final long serialVersionUID = 1L;
}