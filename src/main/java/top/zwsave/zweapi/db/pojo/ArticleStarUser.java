package top.zwsave.zweapi.db.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * article_star_user
 * @author 
 */
@Data
public class ArticleStarUser implements Serializable {
    private Long id;

    private Long articleId;

    private Long userId;

    private Date createTime;

    private Object delete;

    private static final long serialVersionUID = 1L;
}