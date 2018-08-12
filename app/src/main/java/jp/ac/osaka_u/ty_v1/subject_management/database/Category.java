package jp.ac.osaka_u.ty_v1.subject_management.database;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Setter;
import com.github.gfx.android.orma.annotation.Table;

import io.reactivex.annotations.NonNull;

@Table
public class Category {
//    public Item_Relation getItems(OrmaDatabase orma) {
//        return orma.relationOfItem().categoryEq(this);
//    }
    @PrimaryKey(autoincrement = true)
    final int id;

    @Column(indexed = true)
    @NonNull
    final String name;

    @Column
    @NonNull
    final boolean isMust;

    @Setter
    public Category(final int id, final String name, final boolean isMust){
        this.id = id;
        this.name = name;
        this.isMust = isMust;
    }

    public Subject_Relation getSubjects(OrmaDatabase orma){
        return orma.relationOfSubject().categoryEq(this);
    }


}
