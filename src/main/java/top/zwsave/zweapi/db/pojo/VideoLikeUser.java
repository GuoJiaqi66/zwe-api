package top.zwsave.zweapi.db.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * video_like_user
 * @author 
 */
@Data
public class VideoLikeUser implements Serializable {
    private Long id;

    private Long videoId;

    private Long userId;

    private Date createTime;

    private Object delete;

    private static final long serialVersionUID = 1L;
}