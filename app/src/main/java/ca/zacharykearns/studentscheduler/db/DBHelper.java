package ca.zacharykearns.studentscheduler.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ca.zacharykearns.studentscheduler.model.Term;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "student_scheduler.db";
    private static final String TERM_TABLE_NAME = "term";
    private static final String TERM_COLUMN_ID = "term_id";
    private static final String TERM_COLUMN_TITLE = "title";
    private static final String TERM_COLUMN_START = "start_date";
    private static final String TERM_COLUMN_END = "end_date";
    private static final String MENTOR_TABLE_NAME = "mentor";
    private static final String MENTOR_COLUMN_ID = "mentor_id";
    private static final String MENTOR_COLUMN_NAME = "name";
    private static final String MENTOR_COLUMN_PHONE = "phone";
    private static final String MENTOR_COLUMN_EMAIL = "email";
    private static final String COURSE_TABLE_NAME = "course";
    private static final String COURSE_COLUMN_ID = "course_id";
    private static final String COURSE_COLUMN_TITLE = "title";
    private static final String COURSE_COLUMN_STATUS = "status";
    private static final String COURSE_COLUMN_START = "start_date";
    private static final String COURSE_COLUMN_END = "end_date";
    private static final String TERM_COURSE_TABLE_NAME = "term_course";
    private static final String TERM_COURSE_COLUMN_ID = "term_course_id";
    private static final String TERM_COURSE_COLUMN_TERM_ID = "term_id";
    private static final String TERM_COURSE_COLUMN_COURSE_ID = "course_id";
    private static final String MENTOR_COURSE_TABLE_NAME = "mentor_course";
    private static final String MENTOR_COURSE_COLUMN_ID = "mentor_course_id";
    private static final String MENTOR_COURSE_COLUMN_MENTOR_ID = "mentor_id";
    private static final String MENTOR_COURSE_COLUMN_COURSE_ID = "course_id";
    private static final String NOTE_TABLE_NAME = "note";
    private static final String NOTE_COLUMN_ID = "note_id";
    private static final String NOTE_COLUMN_NOTE = "note";
    private static final String NOTE_COLUMN_COURSE_ID= "course_id";
    private static final String ASSESSMENT_TABLE_NAME = "assessment";
    private static final String ASSESSMENT_COLUMN_ID = "assessment_id";
    private static final String ASSESSMENT_COLUMN_TITLE = "title";
    private static final String ASSESSMENT_COLUMN_TYPE = "type";
    private static final String ASSESSMENT_COLUMN_DUE = "due_date";
    private static final String ASSESSMENT_COLUMN_COURSE_ID = "course_id";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(

                "CREATE TABLE " + TERM_TABLE_NAME + "(" +
                TERM_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TERM_COLUMN_TITLE + " TEXT NOT NULL," +
                TERM_COLUMN_START + " TEXT NOT NULL," +
                TERM_COLUMN_END + " TEXT NOT NULL);" +

                "CREATE TABLE " + MENTOR_TABLE_NAME + "(" +
                MENTOR_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MENTOR_COLUMN_NAME + " TEXT NOT NULL," +
                MENTOR_COLUMN_PHONE + " TEXT NOT NULL," +
                MENTOR_COLUMN_EMAIL + " TEXT NOT NULL);" +

                "CREATE TABLE " + COURSE_TABLE_NAME + "(" +
                COURSE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COURSE_COLUMN_TITLE + " TEXT NOT NULL," +
                COURSE_COLUMN_STATUS + " TEXT NOT NULL," +
                COURSE_COLUMN_START + " TEXT NOT NULL," +
                COURSE_COLUMN_END + " TEXT NOT NULL);" +

                "CREATE TABLE " + TERM_COURSE_TABLE_NAME + "(" +
                TERM_COURSE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FOREIGN KEY(" + TERM_COURSE_COLUMN_TERM_ID + ") " +
                "REFERENCES " + TERM_TABLE_NAME + "(" + TERM_COLUMN_ID + ")," +
                "FOREIGN KEY(" + TERM_COURSE_COLUMN_COURSE_ID + ") " +
                "REFERENCES " + COURSE_TABLE_NAME + "(" + COURSE_COLUMN_ID + "));" +

                "CREATE TABLE " + MENTOR_COURSE_TABLE_NAME + "(" +
                MENTOR_COURSE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FOREIGN KEY(" + MENTOR_COURSE_COLUMN_MENTOR_ID + ") " +
                "REFERENCES " + MENTOR_TABLE_NAME + "(" + MENTOR_COLUMN_ID + ")," +
                "FOREIGN KEY(" + MENTOR_COURSE_COLUMN_COURSE_ID + ") " +
                "REFERENCES " + COURSE_TABLE_NAME + "(" + COURSE_COLUMN_ID + "));" +

                "CREATE TABLE " + NOTE_TABLE_NAME + "(" +
                NOTE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOTE_COLUMN_NOTE + " TEXT NOT NULL," +
                "FOREIGN KEY(" + NOTE_COLUMN_COURSE_ID + ") " +
                "REFERENCES " + COURSE_TABLE_NAME + "(" + COURSE_COLUMN_ID + "));" +

                "CREATE TABLE " + ASSESSMENT_TABLE_NAME + "(" +
                ASSESSMENT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ASSESSMENT_COLUMN_TITLE + " TEXT NOT NULL," +
                ASSESSMENT_COLUMN_TYPE + " TEXT NOT NULL," +
                ASSESSMENT_COLUMN_DUE + " TEXT NOT NULL," +
                "FOREIGN KEY(" + ASSESSMENT_COLUMN_COURSE_ID + ") " +
                "REFERENCES " + COURSE_TABLE_NAME + "(" + COURSE_COLUMN_ID + "));"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(
                "DROP TABLE IF EXISTS " + TERM_TABLE_NAME + ";" +
                "DROP TABLE IF EXISTS " + MENTOR_TABLE_NAME + ";" +
                "DROP TABLE IF EXISTS " + COURSE_TABLE_NAME + ";" +
                "DROP TABLE IF EXISTS " + TERM_COURSE_TABLE_NAME + ";" +
                "DROP TABLE IF EXISTS " + MENTOR_COURSE_TABLE_NAME + ";" +
                "DROP TABLE IF EXISTS " + NOTE_TABLE_NAME + ";" +
                "DROP TABLE IF EXISTS " + ASSESSMENT_TABLE_NAME + ";"
        );
        onCreate(sqLiteDatabase);
    }

    public boolean insertTerm(String title, String start, String end) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TERM_COLUMN_TITLE, title);
        cv.put(TERM_COLUMN_START, start);
        cv.put(TERM_COLUMN_END, end);
        db.insert(TERM_TABLE_NAME, null, cv);
        return true;
    }

    public boolean updateTerm(int id, String title, String start, String end) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TERM_COLUMN_TITLE, title);
        cv.put(TERM_COLUMN_START, start);
        cv.put(TERM_COLUMN_END, end);
        db.update(
                TERM_TABLE_NAME,
                cv,
                TERM_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) }
                );
        return true;
    }

    public ArrayList<Term> getTerms() {
        ArrayList<Term> a = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TERM_TABLE_NAME, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            int mId = res.getInt(0);
            String mTitle = res.getString(1);
            String mStart = res.getString(2);
            String mEnd = res.getString(3);
            a.add(new Term(mId, mTitle, mStart, mEnd));
            res.moveToNext();
        }
        res.close();
        return a;
    }
}
