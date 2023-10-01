package cn.dlmyl.tcc.account.model;

import cn.dlmyl.tcc.account.common.Constants;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 账户事务状态实体类
 *
 * @author dlmyL
 */
@Data
@TableName("tcc_account_tx")
public class TccAccountTx {

    @TableId
    private Integer id;

    private String txId;

    private int freezeMoney;

    private int state = Constants.TxState.TRY.getState();

}
