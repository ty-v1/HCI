package jp.ac.osaka_u.ty_v1.subject_management.subject;

import java.util.ArrayList;
import java.util.List;

import jp.ac.osaka_u.ty_v1.subject_management.database.Attendance;
import jp.ac.osaka_u.ty_v1.subject_management.database.OrmaDatabase;
import jp.ac.osaka_u.ty_v1.subject_management.database.Start;
import jp.ac.osaka_u.ty_v1.subject_management.database.Start_Selector;
import jp.ac.osaka_u.ty_v1.subject_management.database.Subject;

public class TableModel {

    private final boolean[][] emptyTable = new boolean[5][7];

    private final List<Integer> startIDList = new ArrayList<>();
    private final OrmaDatabase orma;

    private final List<TableListener> listenerList = new ArrayList<>();

    public TableModel(final int year, final int semester, final OrmaDatabase _orma){
        orma = _orma;
        initialize();

        final Start_Selector startSelector = orma.selectFromStart()
                .yearEq(year)
                .and()
                .semesterEq(semester);

        for(Start start : startSelector){
            if(start.getAttendances(orma).isEmpty())
                continue;

            for(Attendance attendance : start.getAttendances(orma)){
                startIDList.add(attendance.getStartID());
            }

            final int week = start.getWeek();
            for(int i=start.getStart();i<=start.getEnd();i++){
                emptyTable[week][i] = false;
            }
        }
    }

    private void initialize(){
        for(int j=0;j<5;j++){
            for(int i=0;i<7;i++){
                emptyTable[j][i] = true;
            }
        }
    }

    public boolean isAddable(final int week, final int start, final int end){

        if(start < 0 || 6 < start || end < 0 || 6 < end)
            return false;

        if(end < start)
            return false;

        for(int i=start;i<end;i++) {
            if(!emptyTable[week][i])
                return false;
        }
        return true;
    }

    public boolean add(final String subjectName, final int year, final int semester){
        final Subject subject = orma.selectFromSubject().nameEq(subjectName).getOrNull(0);
        final Start start = orma.selectFromStart().yearEq(year).and().semesterEq(semester).and().subjectEq(subject).getOrNull(0);

        return add(start.getId());
    }

    public boolean add(final int startID){
        final Start start = orma.selectFromStart().idEq(startID).get(0);

        if(!isAddable(start.getWeek(), start.getStart(), start.getEnd()))
            return false;

        startIDList.add(startID);

        final Attendance attendance = new Attendance(start);
        orma.insertIntoAttendance(attendance);

        updateTable(start.getWeek(), start.getStart(), start.getEnd(), false);
        fireAdded(start.getWeek(), start.getStart(), start.getEnd(), attendance.getName());
        return true;
    }

    public boolean delete(final int year, final int semester, final int week, final int time){

        final Start_Selector startSelector = orma.selectFromStart()
                .yearEq(year)
                .and()
                .semesterEq(semester)
                .and()
                .weekEq(week)
                .startLe(time)
                .and()
                .endGe(time);

        if(startSelector.isEmpty())
            return false;

        final Start start = startSelector.get(0);
        final int startID = start.getId();

        startIDList.remove(Integer.valueOf(startID));
        orma.deleteFromAttendance().startEq(startID).execute();

        updateTable(start.getWeek(), start.getStart(), start.getEnd(), true);
        fireDeleted(start.getWeek(), start.getStart(), start.getEnd());
        return true;
    }

    private void updateTable(final int week, final int start, final int end, final boolean isEmpty){
        for(int i=start;i<end;i++)
            emptyTable[week][i] = isEmpty;
    }

    public void addListener(final TableListener listener){
        listenerList.add(listener);

        listener.registered();
    }

    private void fireAdded(final int week, final int start, final int end, final String name){
       for(TableListener listener : listenerList)
           listener.attendanceAdded(week, start, end, name);
    }

    private void fireDeleted(final int week, final int start, final int end){
        for(TableListener listener : listenerList)
            listener.attendanceDeleted(week, start, end);
    }

    public String getName(int year, int semester, int week, int time){
        final Start_Selector startSelector = orma.selectFromStart()
                .yearEq(year)
                .and()
                .semesterEq(semester)
                .and()
                .weekEq(week)
                .startLe(time)
                .and()
                .endGe(time);
        if(startSelector.isEmpty())
            return "";

        final Start start = startSelector.get(0);
        if(orma.selectFromAttendance().startEq(start).isEmpty())
            return "";
        else
            return start.getName();
    }
}
