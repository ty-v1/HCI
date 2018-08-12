package jp.ac.osaka_u.ty_v1.subject_management.plan;

import java.util.Calendar;
import java.util.Date;

public class Plan {

    private final Calendar calendar;
    private final String activity;

    public Plan(Calendar _calendar, String _activity){
        calendar = _calendar;
        activity = _activity;
    }

}
