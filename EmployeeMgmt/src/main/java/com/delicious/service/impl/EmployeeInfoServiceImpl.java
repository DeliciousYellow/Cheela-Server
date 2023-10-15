package com.delicious.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.delicious.mapper.EmployeeInfoMapper;
import com.delicious.pojo.entity.EmployeeInfo;
import com.delicious.service.EmployeeInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 黄灿
 * @since 2023-10-15
 */
@Service
public class EmployeeInfoServiceImpl extends ServiceImpl<EmployeeInfoMapper, EmployeeInfo> implements EmployeeInfoService {

}
