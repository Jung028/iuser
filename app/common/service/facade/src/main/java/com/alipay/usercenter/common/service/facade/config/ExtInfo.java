package com.alipay.usercenter.common.service.facade.config;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author adam
 * @date 24/3/2026 8:37 PM
 */
public class ExtInfo {
    private String stripeCustomerId;
    private List<JsonNode> extraItems = new ArrayList<>();

    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    public void setStripeCustomerId(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }

    public List<JsonNode> getExtraItems() {
        return extraItems;
    }

    public void setExtraItems(List<JsonNode> extraItems) {
        this.extraItems = extraItems;
    }

    public void append(JsonNode newNode) {
        this.extraItems.add(newNode);
    }
}