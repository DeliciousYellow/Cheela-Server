package com.delicious.pojo.entity.workflow;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.delicious.pojo.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 黄灿
 * @since 2023-10-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_step")
public class Step extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 步骤id
     */
    @TableId(value = "step_id", type = IdType.AUTO)
    private Integer stepId;

    /**
     * 工作流id
     */
    private String workflowCode;

    /**
     * 步骤次序
     */
    private Integer stepOrder;

    /**
     * 步骤名称
     */
    private String stepName;

    /**
     * 步骤是否完成
     */
    private Integer stepState;

    /**
     * 步骤完成时间
     */
    private LocalDateTime finishTime;

    /**
     * 步骤提交人id
     */
    private String submitterId;

    /**
     * 提交人备注
     */
    private String submitterNotes;


}
