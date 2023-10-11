package com.delicious.pojo.entity;

import lombok.*;

import java.io.Serial;

/**
 * <p>
 *
 * </p>
 *
 * @author 黄灿
 * @since 2023-10-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelationUserRole extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;


}
