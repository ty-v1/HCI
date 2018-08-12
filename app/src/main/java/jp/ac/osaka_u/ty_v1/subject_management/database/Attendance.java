package jp.ac.osaka_u.ty_v1.subject_management.database;

import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Setter;
import com.github.gfx.android.orma.annotation.Table;

@Table
public class Attendance {

    @PrimaryKey
   final Start start;

    @Setter
    public Attendance(final Start start) {
        this.start = start;
    }

    public int getStartID(){
        return start.id;
    }

    public String getName(){
        return start.subject.name;
    }

}
