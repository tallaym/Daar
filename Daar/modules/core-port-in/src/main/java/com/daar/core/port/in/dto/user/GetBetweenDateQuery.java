package com.daar.core.port.in.dto.user;

import java.time.Instant;
import java.util.Date;

public class GetBetweenDateQuery {

    private Date start, end;

    public GetBetweenDateQuery(Date start, Date end) {
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
