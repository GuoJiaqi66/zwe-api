package top.zwsave.zweapi.db.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * system_msg
 * @author 
 */
@Data
public class SystemMsg implements Serializable {
    private Long id;

    private Long userid;

    private String content;

    private Date createtime;

    private Object delete;

    private String url;

    private static final long serialVersionUID = 1L;
}