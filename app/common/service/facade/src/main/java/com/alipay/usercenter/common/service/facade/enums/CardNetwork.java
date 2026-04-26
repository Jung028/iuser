package com.alipay.usercenter.common.service.facade.enums;

/**
 * @author adam
 * @date 24/3/2026 7:56 PM
 */
public enum CardNetwork {
    VISA, MASTERCARD, AMEX;

    /**
     * convert to enum values from stripe
     * @param brand
     * @return
     */
    public static CardNetwork fromStripe(String brand) {
        if (brand == null) {
            throw new IllegalArgumentException("Stripe card brand is null");
        }

        switch (brand.toLowerCase()) {
            case "visa":
                return VISA;
            case "mastercard":
                return MASTERCARD;
            case "amex":
            case "american_express":
                return AMEX;
            default:
                throw new IllegalArgumentException("Unsupported card brand: " + brand);
        }
    }
}