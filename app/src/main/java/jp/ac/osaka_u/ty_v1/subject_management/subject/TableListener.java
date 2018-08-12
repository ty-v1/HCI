package jp.ac.osaka_u.ty_v1.subject_management.subject;

public interface TableListener {

    public void attendanceAdded(final int week, final int start, final int end, final String name);

    public void attendanceDeleted(final int week, final int start, final int end);

    public void registered();
}
