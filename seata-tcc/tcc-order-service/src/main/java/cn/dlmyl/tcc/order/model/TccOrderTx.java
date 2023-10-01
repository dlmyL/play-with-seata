package cn.dlmyl.tcc.order.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 订单事务实体类
 *
 * @author dlmyL
 */
@Data
@TableName("tcc_order_tx")
public class TccOrderTx {

    public static final int STATE_TRY = 0;
    public static final int STATE_CONFIRM = 1;
    public static final int STATE_CANCEL = 2;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String txId;

    private int state = STATE_TRY;

}
