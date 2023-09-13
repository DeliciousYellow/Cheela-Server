package com.delicious.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.delicious.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 黄灿
 * @since 2023-09-07
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
