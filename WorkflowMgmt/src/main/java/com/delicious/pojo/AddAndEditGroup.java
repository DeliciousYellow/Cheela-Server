package com.delicious.pojo;


/**
 * @作者： 王炸
 * 在实体类中标明了每个属性的校验方式，但不是任何情况下都需要校验
 * 比如当需要根据传入的实体类中不为空的字段来查询记录时。这个实体类并不需要满足上述校验规则，这种情形下任何属性都可能为空
 * 所以把需要校验的字段设立一个或多个分组，需要校验属性的地方显式的声明@Validated(Group.class)
 */
public interface AddAndEditGroup {
    /**
     * 添加和修改时需要校验字段
     */
}
