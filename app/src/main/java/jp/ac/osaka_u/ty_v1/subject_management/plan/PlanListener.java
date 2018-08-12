package jp.ac.osaka_u.ty_v1.subject_management.plan;

public interface PlanListener {

    public void planAdded();

    public void planChanged();

    public void planDeleted();
}
