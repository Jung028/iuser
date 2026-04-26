package com.alipay.usercenter.common.service.facade.result;

import com.alipay.usercenter.common.service.facade.baseresult.UserBaseResult;
import com.alipay.usercenter.common.service.facade.item.CardItem;
import com.alipay.usercenter.common.service.facade.item.UserCardDetailItem;

import java.util.List;

/**
 * @author adam
 * @date 23/3/2026 3:18 PM
 */
public class QueryCardDetailsResult {
    private List<UserCardDetailItem> cardDetailsList;

    public List<UserCardDetailItem> getCardDetailsList() {
        return cardDetailsList;
    }

    public void setCardDetailsList(List<UserCardDetailItem> cardDetailsList) {
        this.cardDetailsList = cardDetailsList;
    }
}