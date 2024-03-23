package com.clickpick.dto.image;

import lombok.Data;

@Data
public class UrlRes {
    private String url;

    public UrlRes(String url) {
        this.url = url;
    }
}
