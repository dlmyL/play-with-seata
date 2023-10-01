package cn.dlmyl.tcc.stock.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tcc_stock")
public class TccStock {

    @TableId
    private Integer id;

    private String commodityCode;

    private Integer count;

}
