package jp.ac.osaka_u.ty_v1.subject_management.database;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Setter;
import com.github.gfx.android.orma.annotation.Table;

import io.reactivex.annotations.NonNull;

@Table
public class Subject {

    private final int FIRST_SEMESTER = 0;
    private final int SECOND_SEMESTER = 1;

    @PrimaryKey(autoincrement = true)
    final int id;

    @Column(indexed = true)
    @NonNull
    final Category category;

    @Column(indexed = true)
    @NonNull
    final String name;

    @Column
    @NonNull
    final int credit;

    @Column
    final int grade;

    @Setter
    public Subject(final int id, final Category category, final String name, final int credit, final int grade) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.credit = credit;
        this.grade = grade;
    }

    public Start_Relation getStarts(OrmaDatabase orma){
        return orma.relationOfStart().subjectEq(this);
    }

    public int getScore(){
        return grade;
    }

    public int getCredit() {
        return credit;
    }
}
