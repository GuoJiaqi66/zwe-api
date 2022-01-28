package top.zwsave.zweapi.db.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * article_look_user
 * @author 
 */
@Data
public class ArticleLookUser implements Serializable {
    private Long id;

    private Long articleId;

    private Long userId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}