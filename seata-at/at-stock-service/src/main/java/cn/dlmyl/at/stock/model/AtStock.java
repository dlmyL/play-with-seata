package cn.dlmyl.at.stock.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("at_stock")
public class AtStock {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String commodityCode;

    private Integer count;

}
