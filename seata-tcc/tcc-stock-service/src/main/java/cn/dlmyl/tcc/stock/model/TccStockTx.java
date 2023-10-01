package cn.dlmyl.tcc.stock.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * TccStockTx
 *
 * @author dlmyL
 */
@Data
@TableName("tcc_stock_tx")
public class TccStockTx {

    public static final int STATE_TRY = 0;
    public static final int STATE_CONFIRM = 1;
    public static final int STATE_CANCEL = 2;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String txId;
    private int count;
    private int state = STATE_TRY;

}
