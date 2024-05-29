package org.example.datacenter.model;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class UserVo {
    private String username;

    /**
     * token令牌
     */
    private String token;
}
