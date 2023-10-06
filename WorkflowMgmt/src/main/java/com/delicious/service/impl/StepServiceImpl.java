package com.delicious.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.delicious.mapper.StepMapper;
import com.delicious.pojo.entity.Step;
import com.delicious.service.StepService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 黄灿
 * @since 2023-10-06
 */
@Service
public class StepServiceImpl extends ServiceImpl<StepMapper, Step> implements StepService {

}
