package com.delicious.pojo.entity.workflow;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.delicious.pojo.entity.BaseEntity;
import jakarta.validation.constraints.NotNull;
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
 * @since 2023-09-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("workflow_info")
public class WorkflowInfo extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 流程id
     */
    @TableId(value = "workflow_id", type = IdType.AUTO)
    @NotNull
    private Integer workflowId;

    /**
     * 流程编号
     */
    @NotNull
    private String workflowCode;

    /**
     * 流程状态
     */
    @NotNull
    private String workflowState;

    /**
     * 流程所属部门
     */
    @NotNull
    private String workflowDepart;

    /**
     * 流程紧急程度
     */
    @NotNull
    private String workflowEmergencyDegree;

    /**
     * 流程内容
     */
    @NotNull
    private String workflowDetails;

    /**
     * 流程发布者id
     */
    @TableField("workflow_creator_id")
    @NotNull
    private Integer workflowCreatorId;

    /**
     * 流程创建时间
     */
    @NotNull
    private LocalDateTime workflowStartTime;

    /**
     * 流程结束时间
     */
    @NotNull
    private LocalDateTime workflowEndTime;

    /**
     * 流程附件地址
     */
    private String workflowAttachment;

}
