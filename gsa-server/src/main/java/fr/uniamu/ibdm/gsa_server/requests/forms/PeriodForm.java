package fr.uniamu.ibdm.gsa_server.requests.forms;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class PeriodForm implements Form {

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

    @Override
    public boolean validate() {
        Pattern userNameRegexp = Pattern.compile("[^<>/]");
        Pattern dateRegexp = Pattern.compile("^[0-9]{4}(-[0-9]{2}){2}$");

        return (userName == null || userNameRegexp.matcher(userName).matches()) &&
               (begin != null && (begin.toString().equals("") || dateRegexp.matcher(begin.toString()).matches())) &&
               (end != null && (end.toString().equals("") || dateRegexp.matcher(end.toString()).matches()));

    }
}
