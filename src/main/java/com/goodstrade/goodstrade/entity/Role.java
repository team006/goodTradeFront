package com.goodstrade.goodstrade.entity;

public enum Role {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_SHOP("ROLE_SHOP"),
    ROLE_CLIENT("ROLE_CLIENT");

    private String val;

    public String getVal(){
        return val;
    }

    private Role(String val){
        this.val = val;
    }
}
