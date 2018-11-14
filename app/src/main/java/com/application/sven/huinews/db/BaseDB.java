package com.application.sven.huinews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.db.Dao.Book;
import com.application.sven.huinews.entity.response.MovieDetailResponse;
import com.application.sven.huinews.utils.download.DownloadData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/28
 * effect:
 */
public class BaseDB {
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;

    private static BaseDB mInstance;

    private BaseDB(Context context) {
        mContext = context;
    }

    public static BaseDB getInstance(Context context) {
        if (mInstance == null) {
            synchronized (BaseDB.class) {
                if (mInstance == null) {
                    mInstance = new BaseDB(context);
                }
            }
        }
        return mInstance;
    }


    /**
     * 插入已经领取过红包或者金币的视频
     *
     * @param newsId
     */
    public void insertInVideoId(String newsId) {
        mSQLiteDatabase = new DateBaseHelpter(mContext).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DateBaseHelpter.COLUMN_VIDEO_ID_NAME, newsId);
        mSQLiteDatabase.insert(DateBaseHelpter.VIDEO_TABLE, null, contentValues);
        mSQLiteDatabase.close();
    }

    /**
     * 读取领取过红包或者金币的视频
     *
     * @return
     */
    public ArrayList<String> getAllVideoList() {
        mSQLiteDatabase = new DateBaseHelpter(mContext).getWritableDatabase();
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = mSQLiteDatabase.query(DateBaseHelpter.VIDEO_TABLE, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String newsId = cursor.getString(cursor.getColumnIndex(DateBaseHelpter.COLUMN_VIDEO_ID_NAME));
            list.add(newsId);
        }
        mSQLiteDatabase.close();
        return list;

    }

    /**
     * 插入历史搜索
     *
     * @param keyword
     */
    public void insertInSearchKeyWord(String keyword) {
        mSQLiteDatabase = new DateBaseHelpter(mContext).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DateBaseHelpter.HISTORY_SEARCH_KEYWORD, keyword);
        mSQLiteDatabase.insert(DateBaseHelpter.HISTORY_SEARCH_TABLE, null, contentValues);
        mSQLiteDatabase.close();
    }

    /**
     * 删除历史搜索
     *
     * @param keyword
     */
    public void delInSearchKeyWord(String keyword) {
        mSQLiteDatabase = new DateBaseHelpter(mContext).getWritableDatabase();
        mSQLiteDatabase.delete(DateBaseHelpter.HISTORY_SEARCH_TABLE, DateBaseHelpter.HISTORY_SEARCH_KEYWORD + "=?", new String[]{keyword});
        mSQLiteDatabase.close();
    }

    /**
     * 获取历史搜索
     *
     * @return
     */
    public ArrayList<String> getAllHistroySearchList() {
        mSQLiteDatabase = new DateBaseHelpter(mContext).getWritableDatabase();
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = mSQLiteDatabase.query(DateBaseHelpter.HISTORY_SEARCH_TABLE, null, null, null, null, null, null, "21");
        while (cursor.moveToNext()) {
            String keyword = cursor.getString(cursor.getColumnIndex(DateBaseHelpter.HISTORY_SEARCH_KEYWORD));
            if (list.contains(keyword)) {
                continue;
            }
            list.add(keyword);
        }
        mSQLiteDatabase.close();
        Collections.reverse(list);
        return list;
    }

    /**
     * 清空历史搜索
     */
    public void emptySearchDb() {
        mSQLiteDatabase = new DateBaseHelpter(mContext).getWritableDatabase();
        mSQLiteDatabase.execSQL("delete from " + DateBaseHelpter.HISTORY_SEARCH_TABLE);
        mSQLiteDatabase.close();
    }

    /******************书籍历史搜索***********/

    /**
     * 插入历史搜索
     *
     * @param keyword
     */
    public void insertInBookSearchKeyWord(String keyword) {
        mSQLiteDatabase = new DateBaseHelpter(mContext).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DateBaseHelpter.HISTORY_SEARCH_KEYWORD, keyword);
        mSQLiteDatabase.insert(DateBaseHelpter.HISTORY_SEARCH_BOOK_TABLE, null, contentValues);
        mSQLiteDatabase.close();
    }

    /**
     * 删除历史搜索
     *
     * @param keyword
     */
    public void delInBookSearchKeyWord(String keyword) {
        mSQLiteDatabase = new DateBaseHelpter(mContext).getWritableDatabase();
        mSQLiteDatabase.delete(DateBaseHelpter.HISTORY_SEARCH_BOOK_TABLE, DateBaseHelpter.HISTORY_SEARCH_KEYWORD + "=?", new String[]{keyword});
        mSQLiteDatabase.close();
    }

    /**
     * 获取历史搜索
     *
     * @return
     */
    public ArrayList<String> getAllBookHistroySearchList() {
        mSQLiteDatabase = new DateBaseHelpter(mContext).getWritableDatabase();
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = mSQLiteDatabase.query(DateBaseHelpter.HISTORY_SEARCH_BOOK_TABLE, null, null, null, null, null, null, "21");
        while (cursor.moveToNext()) {
            String keyword = cursor.getString(cursor.getColumnIndex(DateBaseHelpter.HISTORY_SEARCH_KEYWORD));
            if (list.contains(keyword)) {
                continue;
            }
            list.add(keyword);
        }
        mSQLiteDatabase.close();
        Collections.reverse(list);
        return list;
    }

    /**
     * 清空历史搜索
     */
    public void emptyBookSearchDb() {
        mSQLiteDatabase = new DateBaseHelpter(mContext).getWritableDatabase();
        mSQLiteDatabase.execSQL("delete from " + DateBaseHelpter.HISTORY_SEARCH_BOOK_TABLE);
        mSQLiteDatabase.close();
    }

    /*******************影视下载*******************/


    /**
     * 保存下载信息
     *
     * @param mData
     */
    public void insertMovie(DownloadData mData) {
        mSQLiteDatabase = new DateBaseHelpter(mContext).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("video_id", mData.getVideo_id());
        values.put("url", mData.getUrl());
        //  values.put("path", mData.getPath());
        values.put("image_path", mData.getImagePath());
        values.put("name", mData.getName());
      /*  values.put("child_task_count", mData.getChildTaskCount());
        values.put("current_length", mData.getCurrentLength());
        values.put("total_length", mData.getTotalLength());
        values.put("percentage", mData.getPercentage());
        values.put("status", mData.getStatus());
        values.put("last_modify", mData.getLastModify());
        values.put("date", mData.getData());*/
        mSQLiteDatabase.insert(DateBaseHelpter.DOWNLOAD_MOVIE_TABLE, null, values);
        mSQLiteDatabase.close();
    }

    /**
     * 获取全部下载信息
     *
     * @return
     */
    public List<DownloadData> getAllDownloadMovie() {
        mSQLiteDatabase = new DateBaseHelpter(mContext).getWritableDatabase();
        List<DownloadData> list = new ArrayList<>();
        Cursor cursor = mSQLiteDatabase.query(DateBaseHelpter.DOWNLOAD_MOVIE_TABLE, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            DownloadData data = new DownloadData();
            data.setVideo_id(cursor.getInt(cursor.getColumnIndex("video_id")));
            data.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            data.setImagePath(cursor.getString(cursor.getColumnIndex("image_path")));
            data.setName(cursor.getString(cursor.getColumnIndex("name")));

            if (list.contains(data)) {
                continue;
            }
            list.add(data);
        }
        cursor.close();
        return list;
    }


    /**
     * 清空所有
     */
    public void deleteMovie() {
        mSQLiteDatabase = new DateBaseHelpter(mContext).getWritableDatabase();
        mSQLiteDatabase.execSQL("delete from " + DateBaseHelpter.DOWNLOAD_MOVIE_TABLE);
        mSQLiteDatabase.close();
    }

    /**
     * 删除数据库小视频
     */
    public void deleteVideo() {
        mSQLiteDatabase = new DateBaseHelpter(mContext).getWritableDatabase();
        mSQLiteDatabase.execSQL("delete from " + DateBaseHelpter.VIDEO_TABLE);
        mSQLiteDatabase.close();
    }


    /**
     * 是否包含
     *
     * @param movieId
     * @return
     */
    public boolean isContainsMovie(int movieId) {
        boolean b = false;
        mSQLiteDatabase = new DateBaseHelpter(mContext).getWritableDatabase();
        List<DownloadData> mDatas = getAllDownloadMovie();
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).getVideo_id() == movieId) {
                b = true;
                break;
            }
        }
        return b;
    }


    /**************书籍数据库处理***********/

    /**
     * 保存下载信息
     *
     * @param mData
     */
    public void updateBook(Book mData) {
        mSQLiteDatabase = new DateBaseHelpter(mContext).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("book_id", mData.getBookId());
        values.put("book_name", mData.getBookName());
        values.put("latestReadChapter", mData.getLatestReadChapter());
        values.put("latestReadChapterId", mData.getLatestReadChapterId());
        values.put("lastestReadPage", mData.getLastestReadPage());
        mSQLiteDatabase.replace(DateBaseHelpter.BOOK_TABLE, null, values);
        mSQLiteDatabase.close();
    }

    /**
     * 获取书籍
     *
     * @return
     */
    public ArrayList<Book> getDbBooks() {
        mSQLiteDatabase = new DateBaseHelpter(mContext).getWritableDatabase();
        ArrayList<Book> list = new ArrayList<>();
        Cursor cursor = mSQLiteDatabase.query(DateBaseHelpter.BOOK_TABLE, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int book_id = cursor.getInt(cursor.getColumnIndex("book_id"));
            String book_name = cursor.getString(cursor.getColumnIndex("book_name"));
            int latestReadChapter = cursor.getInt(cursor.getColumnIndex("latestReadChapter"));
            int latestReadChapterId = cursor.getInt(cursor.getColumnIndex("latestReadChapterId"));
            int lastestReadPage = cursor.getInt(cursor.getColumnIndex("lastestReadPage"));
            Book book = new Book();
            book.setBookId(book_id);
            book.setBookName(book_name);
            book.setLastestReadPage(lastestReadPage);
            book.setLatestReadChapterId(latestReadChapterId);
            book.setLatestReadChapter(latestReadChapter);
            list.add(book);

        }
        mSQLiteDatabase.close();
        return list;
    }


    /**
     * 根据id查找书籍
     *
     * @param bookId
     * @return
     */
    public Book getBook(int bookId) {
        Book book = new Book();
        ArrayList<Book> books = getDbBooks();
        for (int i = 0; i < books.size(); i++) {
            if (bookId == books.get(i).getBookId()) {
                book.setLastestReadPage(books.get(i).getLastestReadPage());
                book.setLatestReadChapterId(books.get(i).getLatestReadChapterId());
                book.setLatestReadChapter(books.get(i).getLatestReadChapter());
                book.setBookId(books.get(i).getBookId());
                book.setBookName(books.get(i).getBookName());
                return book;
            }
        }
        return null;
    }
}
