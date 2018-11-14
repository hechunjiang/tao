package com.application.sven.huinews.view.read.cache;

import com.application.sven.huinews.AppConfig;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.main.read.test.Chapter;
import com.application.sven.huinews.utils.FileUtils;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.StringUtils;
import com.application.sven.huinews.view.read.ThemeManager;

import java.io.File;

/**
 * auther: sunfuyi
 * data: 2018/7/6
 * effect: 书籍章节缓存
 */
public class CacheBook {

    private static CacheBook manager;

    public static CacheBook getInstance() {
        return manager == null ? (manager = new CacheBook()) : manager;
    }


    /**
     * 缓存书籍章节
     *
     * @param bookId
     * @param chapter
     * @param data
     */
    public void saveChapterFile(String bookId, int chapter, Chapter data) {
        File file = FileUtils.getChapterFile(bookId, chapter);
        FileUtils.writeFile(file.getAbsolutePath(), StringUtils.formatContent(data.getBody()), false);
    }



}
