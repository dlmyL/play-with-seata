package cn.dlmyl.tcc.order.model;

import cn.dlmyl.tcc.order.common.Constants;
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

    @TableId
    private Integer id;

    private String txId;

    private int state = Constants.TxState.TRY.getState();

}
