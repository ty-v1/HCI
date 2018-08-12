package jp.ac.osaka_u.ty_v1.subject_management.database;

import android.content.Context;

import com.github.gfx.android.orma.AccessThreadConstraint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OrmaDataBaseHelper {

    private static OrmaDatabase orma = null;

    public static OrmaDatabase createOrmaDatabase() {
        return orma;
    }

    public static OrmaDatabase createOrmaDatabase(Context context) {
        if (orma != null)
            return orma;
        orma = OrmaDatabase.builder(context)
                .name("subject_management")
                .readOnMainThread(AccessThreadConstraint.NONE)
                .writeOnMainThread(AccessThreadConstraint.NONE)
                .build();

        try {
            if(orma.selectFromCategory().isEmpty())
                initializeCategory(context, orma);

            if(orma.selectFromSubject().isEmpty())
                initializeSubject(context, orma);

            if(orma.selectFromStart().isEmpty())
              initializeStart(context, orma);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orma;
    }

    private static void initializeCategory(Context context, OrmaDatabase orma) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("category")));

        String line;
        while((line = reader.readLine()) != null){
            String[] rows = line.split(",");
            Category category = new Category(Integer.parseInt(rows[0]), rows[1], Boolean.parseBoolean(rows[2]));
            orma.insertIntoCategory(category);
        }
        reader.close();
    }

    private static void initializeSubject(Context context, OrmaDatabase orma) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("subject")));

        String line;
        while((line = reader.readLine()) != null){
            final String[] rows = line.split(",");
            final Category category = orma.selectFromCategory().idEq(Integer.parseInt(rows[1])).get(0);
            final Subject subject = new Subject(
                    Integer.parseInt(rows[0]),
                    category,
                    rows[2],
                    Integer.parseInt(rows[3]),
                    Integer.parseInt(rows[4]));
            orma.insertIntoSubject(subject);
        }
        reader.close();
    }

    private static void initializeStart(Context context, OrmaDatabase orma) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("start")));

        String line;
        while((line = reader.readLine()) != null){
            final String[] rows = line.split(",");
            final Subject subject = orma.selectFromSubject().idEq(Integer.parseInt(rows[1])).get(0);
            final Start start = new Start(
                    Integer.parseInt(rows[0]),
                    subject,
                    Integer.parseInt(rows[2]),
                    Integer.parseInt(rows[3]),
                    Integer.parseInt(rows[4]),
                    Integer.parseInt(rows[5]),
                    Integer.parseInt(rows[6]));
            orma.insertIntoStart(start);
        }
        reader.close();
    }


    /**
     *
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
     *
     * */
}
