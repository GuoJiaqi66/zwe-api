package top.zwsave.zweapi.db.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * article
 * @author 
 */
@Data
public class Article implements Serializable {
    private Long id;

    private Long userId;

    private Date createTime;

    /**
     * 公开/私有
     */
    private Object visible;

    private String imgPath;

    private Integer likeCounts;

    private Object status;

    private Object delete;

    private String text;

    private Integer star;

    private Integer lookCounts;

    private static final long serialVersionUID = 1L;
}