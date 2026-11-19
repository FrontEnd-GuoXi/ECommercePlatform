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
     * 根据登录账号查询用户（登录认证使用）
     * <p>
     * 账号可以是用户名或手机号，两列均为唯一索引，最多匹配一条记录
     *
     * @param account 登录账号（用户名或手机号）
     * @return 用户信息，不存在时返回 null
     */
    default User selectByAccount(String account) {
        return selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, account)
                .or()
                .eq(User::getPhone, account));
    }
}
