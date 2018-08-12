package jp.ac.osaka_u.ty_v1.subject_management.plan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Schedule {

    private final static int SIZE = 31;

    private final Calendar start = Calendar.getInstance();
    private final Calendar end = Calendar.getInstance();

    private final List<PlanListener> listenerList = new ArrayList<>();

    private final Plan[] planList = new Plan[SIZE];

    public Schedule(final int year, final int month){
        start.set(year, month, 1, 0, 0, 0);
        start.add(Calendar.DATE, -1);

        end.set(year, month, 1, 0, 0, 0);
        end.add(Calendar.MONTH, 1);
    }

    public boolean addPlan(){
        return true;
    }

    public boolean addListener(PlanListener listener){
        return listenerList.add(listener);
    }

    private void firePlanAdded(){

    }

    private void firePlanDeleted(){

    }

    private void firePlanChanged(){

    }

}
