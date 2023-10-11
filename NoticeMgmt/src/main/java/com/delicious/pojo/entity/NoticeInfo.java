package com.delicious.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 黄灿
 * @since 2023-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class NoticeInfo extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 公告id
     */
    @TableId(value = "notice_id", type = IdType.AUTO)
    private Integer noticeId;

    /**
     * 公告主题
     */
    private String noticeSubject;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 公告创建用户id
     */
    private Integer noticeCreateUserId;

    /**
     * 公告创建时间
     */
    private LocalDateTime noticeCreateTime;

    /**
     * 逻辑删除
     */
    private Integer logicallyDelete;


}
