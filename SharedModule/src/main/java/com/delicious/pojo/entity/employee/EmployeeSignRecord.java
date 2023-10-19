package com.delicious.pojo.entity.employee;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.delicious.pojo.entity.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 黄灿
 * @since 2023-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
public class EmployeeSignRecord extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 签到_id
     */
    @TableId(value = "sign_id", type = IdType.AUTO)
    private Integer signId;

    /**
     * 签到人id
     */
    private Integer signUserId;

    /**
     * 签到时间
     */
    private LocalDateTime signInTime;

    /**
     * 签退时间
     */
    private LocalDateTime signOutTime;


}
