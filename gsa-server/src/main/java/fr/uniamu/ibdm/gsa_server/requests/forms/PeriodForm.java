package fr.uniamu.ibdm.gsa_server.requests.forms;

import java.time.LocalDate;

public class PeriodForm {

    private String userName;
    private LocalDate begin;
    private LocalDate end;

    public PeriodForm() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
