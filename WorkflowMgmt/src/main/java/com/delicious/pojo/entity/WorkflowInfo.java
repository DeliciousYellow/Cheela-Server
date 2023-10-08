package com.delicious.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2023-09-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@TableName("workflow_info")
public class WorkflowInfo extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 流程id
     */
    @TableId(value = "workflow_id", type = IdType.AUTO)
    private Integer workflowId;

    /**
     * 流程编号
     */
    private String workflowCode;

    /**
     * 流程状态
     */
    private String workflowState;

    /**
     * 流程所属部门
     */
    private String workflowDepart;

    /**
     * 流程紧急程度
     */
    private String workflowEmergencyDegree;

    /**
     * 流程内容
     */
    private String workflowDetails;

    /**
     * 流程发布者id
     */
    @TableField("workflow_creator_id")
    private Integer workflowCreatorId;

    /**
     * 流程创建时间
     */
    private LocalDateTime workflowCreatTime;

    /**
     * 流程结束时间
     */
    private LocalDateTime workflowEndTime;

    /**
     * 流程附件地址
     */
    private String workflowAttachment;

}
