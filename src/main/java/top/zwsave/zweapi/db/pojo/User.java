package top.zwsave.zweapi.db.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user
 * @author 
 */
@Data
public class User implements Serializable {
    private Long id;

    private String nickname;

    private String mail;

    private String loginName;

    private String password;

    private String imgPath;

    private Date createTime;

    private Integer fansCount;

    private Integer followCount;

    private Integer ranking;

    private static final long serialVersionUID = 1L;
}