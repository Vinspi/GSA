package fr.uniamu.ibdm.gsa_server.requests.forms;

import java.time.LocalDate;

public class PeriodForm {

    private LocalDate begin;
    private LocalDate end;

    public PeriodForm() {
    }

    public LocalDate getBegin() {
        return begin;
    }

    public void setBegin(LocalDate begin) {
        this.begin = begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

}
