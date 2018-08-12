package jp.ac.osaka_u.ty_v1.subject_management.database;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Setter;
import com.github.gfx.android.orma.annotation.Table;

import io.reactivex.annotations.NonNull;

@Table
public class Start {

    @PrimaryKey(autoincrement = true)
    final int id;

    @Column(indexed = true)
    @NonNull
    final Subject subject;

    @Column(indexed = true)
    @NonNull
    final int year;

    @Column(indexed = true)
    @NonNull
    final int semester;

    @Column(indexed = true)
    @NonNull
    final int week;

    @Column(indexed = true)
    @NonNull
    final int start;

    @Column(indexed = true)
    @NonNull
    final int end;

    @Setter
    public Start(final int id, final Subject subject,
                 final int year, final int semester, final int week,  final int start, final int end){
        this.id = id;
        this.subject = subject;
        this.year = year;
        this.semester = semester;
        this.week = week;
        this.start = start;
        this.end = end;
    }

    public Attendance_Relation getAttendances(OrmaDatabase orma){
        return orma.relationOfAttendance().startEq(this);
    }

    public int getId(){
        return id;
    }

    public int getYear(){
        return year;
    }

    public int getSemester(){
        return semester;
    }

    public int getWeek(){
        return week;
    }

    public int getStart(){
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getName(){
        return subject.name;
    }
}
