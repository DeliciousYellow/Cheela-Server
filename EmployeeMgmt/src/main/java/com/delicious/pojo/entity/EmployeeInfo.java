package com.delicious.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
public class EmployeeInfo extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 雇员主键
     */
    @TableId(value = "employee_id", type = IdType.AUTO)
    private Integer employeeId;

    /**
     * 雇员名称
     */
    private String employeeName;

    /**
     * 雇员性别
     */
    private String employeeSex;

    /**
     * 雇员电话
     */
    private String employeePhone;

    /**
     * 雇员身份证号
     */
    private String employeeIdNumber;

    /**
     * 雇员薪资
     */
    private Integer employeeSalary;

    /**
     * 入职时间
     */
    private LocalDateTime employeeJoinTime;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer logicallyDelete;

    /**
     * 离职时间
     */
    private LocalDateTime employeeLeaveTime;

    /**
     * 对应用户id
     */
    private Integer employeeUserId;
    /**
     * 部门id
     */
    private Integer employeeDeptId;
    /**
     * 对应部门名
     */
    private String employeeDeptName;

}
