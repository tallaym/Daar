package com.daar.core.port.in.dto.user;

import java.time.Instant;
import java.util.Date;

public class GetAfterDateQuery {

    private Date date;

    public GetAfterDateQuery(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
