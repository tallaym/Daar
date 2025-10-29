package com.daar.adapter.in.rest.request;

import java.util.Date;

public class GetBetweenDateRequest {
    private Date start, end;

    public GetBetweenDateRequest(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
