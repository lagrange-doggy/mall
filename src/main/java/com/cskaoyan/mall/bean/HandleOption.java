package com.cskaoyan.mall.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HandleOption {

    private boolean cancel;

    private boolean comment;

    private boolean confirm;

    private boolean delete;

    private boolean pay;

    private boolean rebuy;

    private boolean refund;

    public static HandleOption getInstance(Short status) {
        if (status == 101) {
            return unPaid();
        } else if (status == 102) {
            return cancel();
        } else if (status == 201) {
            return paid();
        } else if (status == 202) {
            return needRefund();
        } else if (status == 203) {
            return cancel();
        } else if (status == 301) {
            return delivery();
        } else if (status == 401 || status == 402) {
            return received();
        } else if (status == 103) {
            return evaluated();
        }   return null;
    }

    private static HandleOption needRefund() {
        HandleOption handleOption = new HandleOption();
        return handleOption;
    }

    public static HandleOption unPaid() {
        HandleOption handleOption = new HandleOption();
        handleOption.setCancel(true);
        handleOption.setPay(true);
        return handleOption;
    }

    public static HandleOption cancel() {
        HandleOption handleOption = new HandleOption();
        handleOption.setDelete(true);
        return handleOption;
    }

    private static HandleOption paid() {
        HandleOption handleOption = new HandleOption();
        handleOption.setRefund(true);
        return handleOption;
    }

    private static HandleOption delivery() {
        HandleOption handleOption = new HandleOption();
        handleOption.setConfirm(true);
        return handleOption;
    }

    private static HandleOption received() {
        HandleOption handleOption = new HandleOption();
        handleOption.setDelete(true);
        handleOption.setRebuy(true);
        handleOption.setComment(true);
        return handleOption;
    }

    private static HandleOption evaluated() {
        HandleOption handleOption = new HandleOption();
        handleOption.setDelete(true);
        handleOption.setRebuy(true);
        return handleOption;
    }
}