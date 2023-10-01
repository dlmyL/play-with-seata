package cn.dlmyl.tcc.account.model;

import com.baomidou.mybatisplus.annotation.IdType;
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

    public static final int STATE_TRY = 0;
    public static final int STATE_CONFIRM = 1;
    public static final int STATE_CANCEL = 2;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String txId;

    private int freezeMoney;

    private int state = STATE_TRY;

}
