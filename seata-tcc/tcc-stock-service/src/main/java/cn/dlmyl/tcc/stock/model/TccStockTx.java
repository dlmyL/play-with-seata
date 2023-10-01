package cn.dlmyl.tcc.stock.model;

import cn.dlmyl.tcc.stock.common.Constants;
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

    @TableId
    private Integer id;
    private String txId;
    private int count;
    private int state = Constants.TxState.TRY.getState();

}
