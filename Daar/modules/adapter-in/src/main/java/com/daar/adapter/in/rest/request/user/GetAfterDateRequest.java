package com.daar.adapter.in.rest.request.user;

import java.util.Date;

public class GetAfterDateRequest {

    private Date date;

    public GetAfterDateRequest(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
