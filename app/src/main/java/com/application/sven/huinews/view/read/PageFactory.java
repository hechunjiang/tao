package com.application.sven.huinews.view.read;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.application.sven.huinews.R;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.FileUtils;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ScreensUitls;
import com.yanyusong.y_divideritemdecoration.Dp2Px;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * auther: sunfuyi
 * data: 2018/7/5
 * effect: 书籍页面处理
 */
public class PageFactory {


    private Context mContext;

    /**
     * 屏幕高宽
     */
    private int mHeight, mWidth;

    /**
     * 文字区域高宽
     */
    private int mVisibleHeight, mVisibleWidth;

    /**
     * 间距
     */
    private int marginHeight, marginWidth;

    /**
     * 字体大小
     */
    private int mFontSize, mNumFontSize;

    /**
     * 每页行数
     */
    private int mPageLineCount;

    /**
     * 行间距
     */
    private int mLineSpace;

    /**
     * 字节长度
     */
    private int mbBufferLen;

    /**
     * 高效的文件内存映射
     */
    private MappedByteBuffer mbBuff;

    /**
     * 页首页尾的位置
     */
    private int curEndPos = 0, curBeginPos = 0, tempBeginPos, tempEndPos;
    /**
     * 当前章节，临时章节
     */
    private int currentChapter, tempChapter;

    /**
     * 当前时间，和当前阅读进度半分比
     */
    private int timeLen = 0, percentLen = 0;

    /**
     * 当前时间
     */
    private String mTime;

    private Rect rectF;
    /**
     * 内容画笔
     */
    private Paint mPaint;
    /**
     * 标题画笔
     */
    private Paint mTitlePaint;

    /**
     * 支付信息画笔
     */
    private Paint mPayPaint;
    /**
     * 背景
     */
    private Bitmap mBookPageBg;

    /**
     * 背景颜色
     */
    private int bgColorRes;
    /**
     * 进度百分比格式化
     */
    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    /**
     * 时间格式化
     */
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    /**
     * 每行数据
     */
    private Vector<String> mLines = new Vector<>();


    /**
     * 书籍长度
     */
    private int chapterSize = 0;

    /**
     * 当前页数
     */
    private int currentPage = 1;

    /**
     * 书籍id
     */
    private String bookId;
    /**
     * 编码格式
     */
    private String charset = "UTF-8";
    private OnReadStateChangeListener listener;


    public PageFactory(Context mContext, String bookId) {
        this.mContext = mContext;
        this.bookId = bookId;
        mWidth = ScreensUitls.getScreenWidth(mContext);
        mHeight = ScreensUitls.getScreenHeight(mContext);
        mFontSize = CommonUtils.dip2px(mContext, 20);
        mLineSpace = mFontSize / 5 * 2;
        mNumFontSize = CommonUtils.dip2px(mContext, 16);
        marginWidth = CommonUtils.dip2px(mContext, 15);
        marginHeight = CommonUtils.dip2px(mContext, 15);
        mVisibleHeight = mHeight - marginHeight * 2 - mNumFontSize * 2 - mLineSpace * 2;
        mVisibleWidth = mWidth - marginWidth * 2;
        mPageLineCount = mVisibleHeight / (mFontSize + mLineSpace);

        rectF = new Rect(0, 0, mWidth, mHeight);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mFontSize);
        mPaint.setColor(Color.BLACK);

        mTitlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTitlePaint.setTextSize(mNumFontSize);
        mTitlePaint.setColor(ContextCompat.getColor(mContext, R.color.chapter_title_day));

        mPayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPayPaint.setTextSize(mNumFontSize);
        mPayPaint.setColor(mContext.getResources().getColor(R.color.c_333333));
        mPayPaint.setTextAlign(Paint.Align.CENTER);

        timeLen = (int) mTitlePaint.measureText("00:00");
        percentLen = (int) mTitlePaint.measureText("00.00%");

    }

    public File getBookFile(int chapter) {
        File file = FileUtils.getChapterFile(bookId, chapter);
        if (file != null && file.length() > 10) {
            // 解决空文件造成编码错误的问题
            charset = FileUtils.getCharset(file.getAbsolutePath());
        }
        LogUtil.showLog("charset=" + charset);
        return file;
    }

    /**
     * @param chapter  阅读章节--当前
     * @param position 阅读位置
     * @return 0：文件不存在或打开失败   1：打开成功
     */
    public int openBook(int chapter, int[] position) {
        this.currentChapter = chapter;
        this.chapterSize = 1;
        if (currentChapter > chapterSize) {
            currentChapter = chapterSize;
        }
        String path = getBookFile(currentChapter).getPath();
        try {
            File file = new File(path);
            long length = file.length();
            if (length > 10) {
                mbBufferLen = (int) length;
                // 创建文件通道，映射为MappedByteBuffer
                mbBuff = new RandomAccessFile(file, "r").getChannel().map(FileChannel.MapMode.READ_ONLY, 0, length);
                curBeginPos = position[0];
                curEndPos = position[1];
                // TODO: 2018/7/6
                //onChapterChanged(chapter);
                mLines.clear();
                return 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 绘制支付信息
     *
     * @param canvas
     */
    public synchronized void onDrawPayInfo(Canvas canvas) {
        Paint.FontMetrics fontMetrics = mPayPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int baseLineY = (int) (rectF.centerY() - top / 2 - bottom / 2);
        canvas.drawText("一一一   本章节需要购买后阅读   一一一", rectF.centerX(), baseLineY, mPayPaint);
        canvas.drawText("价格：5金币", rectF.centerX(), baseLineY + 50, mPayPaint);
        canvas.drawText("余额：5金币", rectF.centerX(), baseLineY + 100, mPayPaint);

    }

    boolean isPay = false;

    /**
     * 绘制阅读页面
     *
     * @param canvas
     */
    public synchronized void onDraw(Canvas canvas) {

        if (mLines.size() == 0) {
            curEndPos = curBeginPos;
            mLines = pageDown();
        }

        if (mLines.size() > 0) {
            int y = marginHeight + (mLineSpace << 1);
            //绘制背景
            if (mBookPageBg != null) {
                canvas.drawBitmap(mBookPageBg, null, rectF, null);
            } else {
                canvas.drawColor(bgColorRes);
            }

            //绘制标题
            canvas.drawText("第三十章   恭喜你", marginWidth, y, mTitlePaint);

            y += mLineSpace + mNumFontSize;
            //绘制阅读页面文字
            for (int i = 0; i < mLines.size(); i++) {
                // TODO: 2018/7/7  
                if (isPay && i == 4) {
                    break;
                }
                y += mLineSpace;
                String line = mLines.get(i);
                if (line.endsWith("@")) {
                    canvas.drawText(line.substring(0, line.length() - 1), marginWidth, y, mPaint);
                    y += mLineSpace;
                } else {
                    canvas.drawText(line, marginWidth, y, mPaint);
                    LogUtil.showLog("msg----行数:" + y + "---行间距:" + marginWidth);
                }
                y += mFontSize;
            }
           /* for (String line : mLines) {
                y += mLineSpace;

                if (line.endsWith("@")) {
                    canvas.drawText(line.substring(0, line.length() - 1), marginWidth, y, mPaint);
                    y += mLineSpace;
                } else {
                    canvas.drawText(line, marginWidth, y, mPaint);
                    LogUtil.showLog("msg----行数:" + y + "---行间距:" + marginWidth);
                }
                y += mFontSize;

            }*/

            float percent = (float) currentChapter * 100 / chapterSize;
            canvas.drawText(decimalFormat.format(percent) + "%", (mWidth - percentLen) / 2, mHeight - marginHeight, mTitlePaint);

            mTime = dateFormat.format(new Date());
            canvas.drawText(mTime, mWidth - marginWidth - timeLen, mHeight - marginHeight, mTitlePaint);
            // TODO: 2018/7/6 保存阅读进度

            onDrawPayInfo(canvas);
        }
    }


    /**
     * 指针移到上一页页首
     */
    private void pageUp() {
        String strParagraph = "";
        Vector<String> lines = new Vector<>(); // 页面行
        int paraSpace = 0;
        mPageLineCount = mVisibleHeight / (mFontSize + mLineSpace);
        while ((lines.size() < mPageLineCount) && (curBeginPos > 0)) {
            Vector<String> paraLines = new Vector<>(); // 段落行
            byte[] parabuffer = readParagraphBack(curBeginPos); // 1.读取上一个段落

            curBeginPos -= parabuffer.length; // 2.变换起始位置指针
            try {
                strParagraph = new String(parabuffer, charset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            strParagraph = strParagraph.replaceAll("\r\n", "  ");
            strParagraph = strParagraph.replaceAll("\n", " ");

            while (strParagraph.length() > 0) { // 3.逐行添加到lines
                int paintSize = mPaint.breakText(strParagraph, true, mVisibleWidth, null);
                paraLines.add(strParagraph.substring(0, paintSize));
                strParagraph = strParagraph.substring(paintSize);
            }
            lines.addAll(0, paraLines);

            while (lines.size() > mPageLineCount) { // 4.如果段落添加完，但是超出一页，则超出部分需删减
                try {
                    curBeginPos += lines.get(0).getBytes(charset).length; // 5.删减行数同时起始位置指针也要跟着偏移
                    lines.remove(0);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            curEndPos = curBeginPos; // 6.最后结束指针指向下一段的开始处
            paraSpace += mLineSpace;
            mPageLineCount = (mVisibleHeight - paraSpace) / (mFontSize + mLineSpace); // 添加段落间距，实时更新容纳行数
        }
    }

    private Vector<String> pageDown() {
        String strParagraph = "";
        Vector<String> lines = new Vector<>();
        int paraSpace = 0;
        mPageLineCount = mVisibleHeight / (mFontSize + mLineSpace); //计算出每页的行数
        while ((lines.size() < mPageLineCount) && (curEndPos < mbBufferLen)) {
            byte[] parabuffer = readParagraphForward(curEndPos);
            curEndPos += parabuffer.length;
            try {
                strParagraph = new String(parabuffer, charset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            strParagraph = strParagraph.replaceAll("\r\n", "  ").replaceAll("\n", " "); // 段落中的换行符去掉，绘制的时候再换行

            while (strParagraph.length() > 0) {
                int paintSize = mPaint.breakText(strParagraph, true, mVisibleWidth, null);
                lines.add(strParagraph.substring(0, paintSize));
                strParagraph = strParagraph.substring(paintSize);
                if (lines.size() >= mPageLineCount) {
                    break;
                }
            }
            lines.set(lines.size() - 1, lines.get(lines.size() - 1) + "@");
            if (strParagraph.length() != 0) {
                try {
                    curEndPos -= (strParagraph).getBytes(charset).length;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            paraSpace += mLineSpace;
            mPageLineCount = (mVisibleHeight - paraSpace) / (mFontSize + mLineSpace);
        }
        return lines;
    }

    /**
     * 跳转下一页
     */
    public BookStatus nextPage() {
        if (!hasNextPage()) { // 最后一章的结束页
            return BookStatus.NO_NEXT_PAGE;
        } else {
            tempChapter = currentChapter;
            tempBeginPos = curBeginPos;
            tempEndPos = curEndPos;
            if (curEndPos >= mbBufferLen) { // 中间章节结束页
                currentChapter++;
                int ret = openBook(currentChapter, new int[]{0, 0}); // 打开下一章
                if (ret == 0) {
                    onLoadChapterFailure(currentChapter);
                    currentChapter--;
                    curBeginPos = tempBeginPos;
                    curEndPos = tempEndPos;
                    return BookStatus.NEXT_CHAPTER_LOAD_FAILURE;
                } else {
                    currentPage = 0;
                    onChapterChanged(currentChapter);
                }
            } else {
                curBeginPos = curEndPos; // 起始指针移到结束位置
            }
            mLines.clear();
            mLines = pageDown(); // 读取一页内容
            onPageChanged(currentChapter, ++currentPage);
        }
        return BookStatus.LOAD_SUCCESS;
    }

    /**
     * 跳转上一页
     */
    public BookStatus prePage() {
        if (!hasPrePage()) { // 第一章第一页
            return BookStatus.NO_PRE_PAGE;
        } else {
            // 保存当前页的值
            tempChapter = currentChapter;
            tempBeginPos = curBeginPos;
            tempEndPos = curEndPos;
            if (curBeginPos <= 0) {
                currentChapter--;
                int ret = openBook(currentChapter, new int[]{0, 0});
                if (ret == 0) {
                    onLoadChapterFailure(currentChapter);
                    currentChapter++;
                    return BookStatus.PRE_CHAPTER_LOAD_FAILURE;
                } else { // 跳转到上一章的最后一页
                    mLines.clear();
                    mLines = pageLast();
                    onChapterChanged(currentChapter);
                    onPageChanged(currentChapter, currentPage);
                    return BookStatus.LOAD_SUCCESS;
                }
            }
            mLines.clear();
            pageUp(); // 起始指针移到上一页开始处
            mLines = pageDown(); // 读取一页内容
            onPageChanged(currentChapter, --currentPage);
        }
        return BookStatus.LOAD_SUCCESS;
    }

    /**
     * 读取上一段落
     *
     * @param curBeginPos 当前页起始位置指针
     * @return
     */
    private byte[] readParagraphBack(int curBeginPos) {
        byte b0;
        int i = curBeginPos - 1;
        while (i > 0) {
            b0 = mbBuff.get(i);
            if (b0 == 0x0a && i != curBeginPos - 1) {
                i++;
                break;
            }
            i--;
        }
        int nParaSize = curBeginPos - i;
        byte[] buf = new byte[nParaSize];
        for (int j = 0; j < nParaSize; j++) {
            buf[j] = mbBuff.get(i + j);
        }
        return buf;
    }

    public boolean hasNextPage() {
        return currentChapter < 1 || curEndPos < mbBufferLen;
    }

    public boolean hasPrePage() {
        return currentChapter > 1 || (currentChapter == 1 && curBeginPos > 0);
    }

    /**
     * 获取最后一页的内容。比较繁琐，待优化
     *
     * @return
     */
    public Vector<String> pageLast() {
        String strParagraph = "";
        Vector<String> lines = new Vector<>();
        currentPage = 0;
        while (curEndPos < mbBufferLen) {
            int paraSpace = 0;
            mPageLineCount = mVisibleHeight / (mFontSize + mLineSpace);
            curBeginPos = curEndPos;
            while ((lines.size() < mPageLineCount) && (curEndPos < mbBufferLen)) {
                byte[] parabuffer = readParagraphForward(curEndPos);
                curEndPos += parabuffer.length;
                try {
                    strParagraph = new String(parabuffer, charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                strParagraph = strParagraph.replaceAll("\r\n", "  ");
                strParagraph = strParagraph.replaceAll("\n", " "); // 段落中的换行符去掉，绘制的时候再换行

                while (strParagraph.length() > 0) {
                    int paintSize = mPaint.breakText(strParagraph, true, mVisibleWidth, null);
                    lines.add(strParagraph.substring(0, paintSize));
                    strParagraph = strParagraph.substring(paintSize);
                    if (lines.size() >= mPageLineCount) {
                        break;
                    }
                }
                lines.set(lines.size() - 1, lines.get(lines.size() - 1) + "@");

                if (strParagraph.length() != 0) {
                    try {
                        curEndPos -= (strParagraph).getBytes(charset).length;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                paraSpace += mLineSpace;
                mPageLineCount = (mVisibleHeight - paraSpace) / (mFontSize + mLineSpace);
            }
            if (curEndPos < mbBufferLen) {
                lines.clear();
            }
            currentPage++;
        }
        //SettingManager.getInstance().saveReadProgress(bookId, currentChapter, curBeginPos, curEndPos);
        return lines;
    }

    /**
     * 读取下一段落
     *
     * @param curEndPos 当前页结束位置指针
     * @return
     */
    private byte[] readParagraphForward(int curEndPos) {
        byte b0;
        int i = curEndPos;
        while (i < mbBufferLen) {
            b0 = mbBuff.get(i++);
            if (b0 == 0x0a) {
                break;
            }
        }
        int nParaSize = i - curEndPos;
        byte[] buf = new byte[nParaSize];
        for (i = 0; i < nParaSize; i++) {
            buf[i] = mbBuff.get(curEndPos + i);
        }
        return buf;
    }

    public void cancelPage() {
        currentChapter = tempChapter;
        curBeginPos = tempBeginPos;
        curEndPos = curBeginPos;

        int ret = openBook(currentChapter, new int[]{curBeginPos, curEndPos});
        if (ret == 0) {
            onLoadChapterFailure(currentChapter);
            return;
        }
        mLines.clear();
        mLines = pageDown();
    }

    /**
     * 获取当前阅读位置
     *
     * @return index 0：起始位置 1：结束位置
     */
    public int[] getPosition() {
        return new int[]{currentChapter, curBeginPos, curEndPos};
    }

    public String getHeadLineStr() {
        if (mLines != null && mLines.size() > 1) {
            return mLines.get(0);
        }
        return "";
    }

    /**
     * 设置字体大小
     *
     * @param fontsize 单位：px
     */
    public void setTextFont(int fontsize) {
        LogUtil.showLog("fontSize=" + fontsize);
        mFontSize = fontsize;
        mLineSpace = mFontSize / 5 * 2;
        mPaint.setTextSize(mFontSize);
        mPageLineCount = mVisibleHeight / (mFontSize + mLineSpace);
        curEndPos = curBeginPos;
        nextPage();
    }

    /**
     * 设置字体颜色
     *
     * @param textColor
     * @param titleColor
     */
    public void setTextColor(int textColor, int titleColor) {
        mPaint.setColor(textColor);
        mTitlePaint.setColor(titleColor);
    }

    public int getTextFont() {
        return mFontSize;
    }

    /**
     * 根据百分比，跳到目标位置
     *
     * @param persent
     */
    public void setPercent(int persent) {
        float a = (float) (mbBufferLen * persent) / 100;
        curEndPos = (int) a;
        if (curEndPos == 0) {
            nextPage();
        } else {
            nextPage();
            prePage();
            nextPage();
        }
    }

    public void setBgBitmap(Bitmap BG) {
        mBookPageBg = BG;
    }

    public void setBgColor(int res) {
        this.bgColorRes = res;
    }

    public void setOnReadStateChangeListener(OnReadStateChangeListener listener) {
        this.listener = listener;
    }

    private void onChapterChanged(int chapter) {
        if (listener != null)
            listener.onChapterChanged(chapter);
    }

    private void onPageChanged(int chapter, int page) {
        if (listener != null)
            listener.onPageChanged(chapter, page);
    }

    private void onLoadChapterFailure(int chapter) {
        if (listener != null)
            listener.onLoadChapterFailure(chapter);
    }

   /* public void convertBetteryBitmap() {
        batteryView = (ProgressBar) LayoutInflater.from(mContext).inflate(R.layout.layout_battery_progress, null);
        batteryView.setProgressDrawable(ContextCompat.getDrawable(mContext,
                SettingManager.getInstance().getReadTheme() < 4 ?
                        R.drawable.seekbar_battery_bg : R.drawable.seekbar_battery_night_bg));
        batteryView.setProgress(battery);
        batteryView.setDrawingCacheEnabled(true);
        batteryView.measure(View.MeasureSpec.makeMeasureSpec(ScreenUtils.dpToPxInt(26), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(ScreenUtils.dpToPxInt(14), View.MeasureSpec.EXACTLY));
        batteryView.layout(0, 0, batteryView.getMeasuredWidth(), batteryView.getMeasuredHeight());
        batteryView.buildDrawingCache();
        //batteryBitmap = batteryView.getDrawingCache();
        // tips: @link{https://github.com/JustWayward/BookReader/issues/109}
        batteryBitmap = Bitmap.createBitmap(batteryView.getDrawingCache());
        batteryView.setDrawingCacheEnabled(false);
        batteryView.destroyDrawingCache();
    }*/

   /* public void setBattery(int battery) {
        this.battery = battery;
        convertBetteryBitmap();
    }*/

    public void setTime(String time) {
        this.mTime = time;
    }

    public void recycle() {
        if (mBookPageBg != null && !mBookPageBg.isRecycled()) {
            mBookPageBg.recycle();
            mBookPageBg = null;
            LogUtil.showLog("mBookPageBg recycle");
        }

       /* if (batteryBitmap != null && !batteryBitmap.isRecycled()) {
            batteryBitmap.recycle();
            batteryBitmap = null;
            LogUtils.d("batteryBitmap recycle");
        }*/
    }
}
