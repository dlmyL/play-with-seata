package cn.dlmyl.xa.account.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 账户实体类
 *
 * @author dlmyL
 */
@Data
@TableName("xa_account")
public class XaAccount {

    /**
     * 主键 ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 账户余额
     */
    private int money;

}
