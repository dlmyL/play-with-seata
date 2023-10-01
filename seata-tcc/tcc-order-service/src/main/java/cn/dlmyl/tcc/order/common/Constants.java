package cn.dlmyl.tcc.order.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Constants.
 *
 * @author dlmyL
 */
public interface Constants {

    String SUCCESS = "SUCCESS";
    String FAIL = "FAIL";

    @Getter
    @AllArgsConstructor
    enum TxState {
        TRY(0),
        CONFIRM(1),
        CANCEL(2);
        private final int state;
    }

}