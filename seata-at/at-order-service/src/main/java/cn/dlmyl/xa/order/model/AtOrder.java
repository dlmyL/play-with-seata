package cn.dlmyl.xa.order.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 订单实体类
 *
 * @author dlmyL
 */
@Data
@Builder
@AllArgsConstructor
@TableName("at_order")
public class AtOrder {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 商品编号
     */
    private String commodityCode;

    /**
     * 商品数量
     */
    private Integer count;

    /**
     * 商品金额
     */
    private Integer money;

}