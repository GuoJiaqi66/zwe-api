package top.zwsave.zweapi.db.pojo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * user
 * @author 
 */
@Data
@ApiModel
public class User implements Serializable {
    private String id;

    @ApiParam("用户名")
    private String username;

    private String password;

    private String faceImage;

    private String nickname;

    private Integer fansCounts;

    private Integer followCounts;

    /**
     * 获赞数
     */
    private Integer receiveLikeCounts;

    private static final long serialVersionUID = 1L;
}