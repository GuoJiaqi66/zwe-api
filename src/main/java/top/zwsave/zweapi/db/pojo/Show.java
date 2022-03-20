package top.zwsave.zweapi.db.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * show
 * @author 
 */
@Data
public class Show implements Serializable {
    private Long id;

    private Long userId;

    private Long useredId;

    private String tag;

    private String flag;

    private Date createTime;

    private Object delete;

    private Integer count;

    private static final long serialVersionUID = 1L;
}