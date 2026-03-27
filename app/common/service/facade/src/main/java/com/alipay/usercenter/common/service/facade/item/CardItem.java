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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CardIssuer getCardNetwork() {
        return cardNetwork;
    }

    public void setCardNetwork(CardIssuer cardNetwork) {
        this.cardNetwork = cardNetwork;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getProviderToken() {
        return providerToken;
    }

    public void setProviderToken(String providerToken) {
        this.providerToken = providerToken;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }
}