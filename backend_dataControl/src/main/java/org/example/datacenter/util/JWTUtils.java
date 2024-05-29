package org.example.datacenter.util;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.algorithms.Algorithm;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.example.datacenter.model.DataCenterAdmin;
import org.example.datacenter.service.DataCenterAdminService;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

@Component
public class JWTUtils {

    private static DataCenterAdminService staticDataCenterAdminService;

    @Resource
    private DataCenterAdminService dataCenterAdminService;

    @PostConstruct
    public void setUserService() {
        staticDataCenterAdminService = dataCenterAdminService;
    }
    /**
     * 生成token
     *
     * @return
     */
    public static String getToken(String userId, String password) {
        return JWT.create().withAudience(userId) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(password)); // 以 password 作为 token 的密钥
    }


}

