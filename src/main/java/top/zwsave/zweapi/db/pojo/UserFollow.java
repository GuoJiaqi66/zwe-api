package top.zwsave.zweapi.db.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user_follow
 * @author 
 */
@Data
public class UserFollow implements Serializable {
    private Long id;

    private Long userId;

    private Object delete;

    private Long useredId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}