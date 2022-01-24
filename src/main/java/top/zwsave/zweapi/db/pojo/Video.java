package top.zwsave.zweapi.db.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * video
 * @author 
 */
@Data
public class Video implements Serializable {
    private Long id;

    private String videoPath;

    /**
     * 禁播？
     */
    private Object status;

    /**
     * 公开/私有
     */
    private Object visible;

    private Date createTime;

    private Object delete;

    private String text;

    private Integer likeCounts;

    private Integer star;

    private Long userId;

    private static final long serialVersionUID = 1L;
}