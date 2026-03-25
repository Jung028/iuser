package com.alipay.usercenter.common.service.facade.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * @author adam
 * @date 19/3/2026 8:42 PM
 */
public class ContactConfig {
    @JsonProperty("userContactList")
    List<Contact> contactList;

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }
}