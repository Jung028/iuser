package com.alipay.usercenter.common.service.facade.item;

import com.alipay.usercenter.common.service.facade.enums.CardIssuer;
import com.alipay.usercenter.common.service.facade.enums.CardStatus;
import com.alipay.usercenter.common.service.facade.enums.CardType;
import com.alipay.usercenter.common.service.facade.enums.Provider;

/**
 * @author adam
 * @date 23/3/2026 3:21 PM
 */
public class CardItem {
    // CardItem
    private String userId;
    private CardType cardType; // DEBIT/CREDIT
    private CardIssuer cardNetwork; // VISA/MASTERCARD
    private Provider provider; // STRIPE/PBE
    private String providerToken;
    private String cardHolderName;
    private CardStatus cardStatus; // ACTIVE/REMOVED
    private String gmtCreate;
    private String gmtModified;
}