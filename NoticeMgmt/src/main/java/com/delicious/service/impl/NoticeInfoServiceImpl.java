package com.delicious.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.delicious.mapper.NoticeInfoMapper;
import com.delicious.pojo.entity.notice.NoticeInfo;
import com.delicious.service.NoticeInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 黄灿
 * @since 2023-10-09
 */
@Service
public class NoticeInfoServiceImpl extends ServiceImpl<NoticeInfoMapper, NoticeInfo> implements NoticeInfoService {

}
