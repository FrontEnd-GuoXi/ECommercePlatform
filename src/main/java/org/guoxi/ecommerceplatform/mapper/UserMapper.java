package org.guoxi.ecommerceplatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;
import org.guoxi.ecommerceplatform.entity.User;

/**
 * 用户表 DAO
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询用户（登录认证使用）
     *
     * @param username 用户名
     * @return 用户信息，不存在时返回 null
     */
    default User selectByUsername(String username) {
        return selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, username));
    }
}
