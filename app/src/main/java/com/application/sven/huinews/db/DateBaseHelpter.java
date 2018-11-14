package com.application.sven.huinews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.application.sven.huinews.utils.LogUtil;

/**
 * auther: sunfuyi
 * data: 2018/5/28
 * effect:
 */
public class DateBaseHelpter extends SQLiteOpenHelper {
    public static final String DB_NAME = "taoVideo";
    public static final String HISTORY_SEARCH_TABLE = "historySearchTable";
    public static final String HISTORY_SEARCH_BOOK_TABLE = "historySearchBookTable";
    public static final String HISTORY_SEARCH_KEYWORD = "search_id";

    public static final String VIDEO_TABLE = "readNewsTable";
    public static final String COLUMN_VIDEO_ID_NAME = "newsId";

    public static final String DOWNLOAD_MOVIE_TABLE = "download_movie_table";
    public static final String DOWNLOAD_MOVIE_KEYWORD = "movie_id";

    public static final String BOOK_TABLE = "readBookTable";


    String stu_table = "create table " + VIDEO_TABLE + "(_id integer primary key autoincrement," + COLUMN_VIDEO_ID_NAME + " text)";
    String history_tab = "create table " + HISTORY_SEARCH_TABLE + "(_id integer primary key autoincrement," + HISTORY_SEARCH_KEYWORD + " text)";
    String history_book_tab = "create table " + HISTORY_SEARCH_BOOK_TABLE + "(_id integer primary key autoincrement," + HISTORY_SEARCH_KEYWORD + " text)";
    /**
     * download_info表建表语句
     */
    String movie_tab = "create table " + DOWNLOAD_MOVIE_TABLE + " (id integer primary key autoincrement, "
            + "video_id integer, "
            + "url text, "
            /* + "path text, "*/
            + "image_path text, "
            + "name text)";
           /* + "child_task_count integer, "
            + "current_length integer, "
            + "total_length integer, "
            + "percentage real, "
            + "status integer, "
            + "last_modify text, "
            + "date text)"*/

    /**
     * 小说阅读记录保存
     *
     * @param context
     */
    String book_tab = "create table " + BOOK_TABLE + "(book_id integer primary key autoincrement," + "book_name text,latestReadChapter integer,latestReadChapterId integer,lastestReadPage integer)";


    private static final int version = 2;

    public DateBaseHelpter(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(stu_table);
        db.execSQL(movie_tab);
        db.execSQL(history_tab);
        db.execSQL(book_tab);
        db.execSQL(history_book_tab);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < version) {
            db.execSQL(book_tab);
            db.execSQL(history_book_tab);
        }
    }

}
