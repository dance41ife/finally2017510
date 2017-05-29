package com.shh.test.Share;

import java.util.UUID;

/**
 * Created by Administrator on 2017/5/29.
 */

public class Share {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShareMessage() {
        return ShareMessage;
    }

    public void setShareMessage(String shareMessage) {
        ShareMessage = shareMessage;
    }

    private String ShareMessage;
}
