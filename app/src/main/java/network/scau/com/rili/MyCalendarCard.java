package network.scau.com.rili;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/10/1 0001.
 */
public class MyCalendarCard extends View {

    private Context context;

    //以下是真实世界的年月日
    private int NowDay;
    private int NowMonth;
    private int NowYear;

    //view的宽度
    private int mViewWidth;

    //view的高度
    private int mViewHeight;

    //日历背景颜色
    private int mBackground = 0xffffffff;


    //普通字体大小
    private int mNormalTextSize;

    //普通字体颜色
    private int mNormalTextColor1 = 0xff5da18f;
    private int mNormalTextColor2 = 0xffcc767e;

    private int mNormalButtonColor1 = 0xff5da18f;
    private int mClickButtonColor2 = 0xffcc767e;

    //日历数字的画笔
    private Paint mNumPaint;

    //第一天星期几
    private int weekOfFirstDay = 1;

    //今天
    private int today = 0;


    float xInterval;
    float yInterval;
    float radius;


    //所有天数
    private int[] allDays;

    private String TAG = "MyCalendarCard";

    private String[] weekName = new String[]{"日", "一", "二", "三", "四", "五", "六"};

    private OnChooseListener chooseListener;

    private OnTurnPageListener turnPageListener;

    private boolean firstClick = false;
    private boolean secondClick = false;

    private int firstCheckDay = -2;
    private int secondCheckDay = -2;

    private int year = 2000;
    private int month = 3;


    private boolean canClick = false;

    private boolean clickLeft = false;
    private boolean clickRight = false;
    //---------------------------------------------------------------------------------

    public MyCalendarCard(Context context, int numOfDay, int weekOfFirstDay, int today) {
        super(context);
        this.weekOfFirstDay = weekOfFirstDay;
        this.today = today;
        allDays = getAllDays(numOfDay);

        init(context);
    }

    public MyCalendarCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCalendarCard);
        today = typedArray.getInt(R.styleable.MyCalendarCard_today, 1);
        weekOfFirstDay = typedArray.getInt(R.styleable.MyCalendarCard_weekOfFirstDay, 1);
        int num = typedArray.getInt(R.styleable.MyCalendarCard_numOfDays, 31);
        allDays = getAllDays(num);
        init(context);
    }

    public MyCalendarCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCalendarCard);
        today = typedArray.getInt(R.styleable.MyCalendarCard_today, 1);
        weekOfFirstDay = typedArray.getInt(R.styleable.MyCalendarCard_weekOfFirstDay, 1);
        int num = typedArray.getInt(R.styleable.MyCalendarCard_numOfDays, 31);
        allDays = getAllDays(num);
        init(context);
    }


    /**
     * 得到天数数组
     *
     * @param numOfDay
     * @return
     */
    private int[] getAllDays(int numOfDay) {
        int[] allDays = new int[numOfDay];
        for (int i = 0; i < numOfDay; i++) {
            allDays[i] = i + 1;
        }
        return allDays;
    }


    /**
     * 设置是否相应点击事件
     *
     * @param canClick
     */
    public void setCanClick(boolean canClick) {
        this.canClick = canClick;
    }

    /**
     * 、
     * 设置日历一个月的天数
     *
     * @param num
     */
    public void setAllDays(int num) {
        this.allDays = getAllDays(num);
    }

    /**
     * 设置日历的背景颜色
     *
     * @param mBackground
     */
    public void setmBackground(int mBackground) {
        this.mBackground = mBackground;
    }

    /**
     * 设置月份
     *
     * @param month
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * 设置年份
     *
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * 设置工作日的字体颜色
     *
     * @param mNormalTextColor1
     */
    public void setmNormalTextColor1(int mNormalTextColor1) {
        this.mNormalTextColor1 = mNormalTextColor1;
    }

    /**
     * 设置箭头按钮点击颜色
     *
     * @param clickButtonColor
     */
    public void setClickButtonColor(int clickButtonColor) {
        this.mClickButtonColor2 = clickButtonColor;
    }

    /**
     * 设置箭头按钮普通颜色
     *
     * @param normalButtonColor
     */
    public void setNormalButtonColor1(int normalButtonColor) {
        this.mNormalButtonColor1 = normalButtonColor;
    }

    /**
     * 设置周末的字体颜色
     *
     * @param mNormalTextColor2
     */
    public void setmNormalTextColor2(int mNormalTextColor2) {
        this.mNormalTextColor2 = mNormalTextColor2;
    }


    /**
     * 设置字体大小,一般会根据控件大小自动设置
     *
     * @param mNormalTextSize
     */
    public void setmNormalTextSize(int mNormalTextSize) {
        this.mNormalTextSize = mNormalTextSize;
    }


    /**
     * 设置今天日期，这个月的任意一天
     *
     * @param today
     */
    public void setToday(int today) {
        this.today = today;
    }

    /**
     * 设置这个月第一天是星期几
     *
     * @param weekOfFirstDay
     */
    public void setWeekOfFirstDay(int weekOfFirstDay) {
        this.weekOfFirstDay = weekOfFirstDay;
    }


    private void init(Context context) {
        this.context = context;
        this.year = CalendarUtils.getCurrentYear();
        this.month = CalendarUtils.getCurrentMonth();
        this.NowDay = CalendarUtils.getCurrentDate();
        this.NowMonth = CalendarUtils.getCurrentMonth();
        this.NowYear = CalendarUtils.getCurrentYear();
        this.weekOfFirstDay = CalendarUtils.getCurrentFirstWeekdayOfMoth();
        mNumPaint = new Paint();
    }

    public void setOnChooseListener(OnChooseListener listener) {
        this.chooseListener = listener;
    }

    public void setOnTurnPageListener(OnTurnPageListener turnPageListener) {
        this.turnPageListener = turnPageListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(mBackground);

        //绘制左箭头
        mNumPaint.setColor(clickLeft ? mNormalButtonColor1 : mClickButtonColor2);
        mNumPaint.setStrokeWidth(6);
        mNumPaint.setAntiAlias(true);
        canvas.drawLine(mViewWidth / 8, mViewHeight / 16, mViewWidth * 3 / 16, mViewHeight / 32, mNumPaint);
        canvas.drawLine(mViewWidth / 8, mViewHeight / 16, mViewWidth * 3 / 16, mViewHeight * 3 / 32, mNumPaint);
        mNumPaint.reset();


        //绘制右箭头
        mNumPaint.setColor(clickRight ? mNormalButtonColor1 : mClickButtonColor2);
        mNumPaint.setStrokeWidth(6);
        mNumPaint.setAntiAlias(true);
        canvas.drawLine(mViewWidth * 7 / 8, mViewHeight / 16, mViewWidth * 13 / 16, mViewHeight / 32, mNumPaint);
        canvas.drawLine(mViewWidth * 7 / 8, mViewHeight / 16, mViewWidth * 13 / 16, mViewHeight * 3 / 32, mNumPaint);
        mNumPaint.reset();

        //绘制年，月份
        mNumPaint.setTextSize(mViewHeight / 16);
        mNumPaint.setColor(mNormalTextColor1);
        mNumPaint.setAntiAlias(true);
        String theYear = year + "";
        String theMonth = month + "";
        canvas.drawText(theYear, mViewWidth / 2 - getTextWidth(mNumPaint, theYear) / 2, mViewHeight / 16, mNumPaint);
        mNumPaint.setTextSize(mViewHeight / 18);
        mNumPaint.setColor(mNormalTextColor2);
        canvas.drawText(theMonth, mViewWidth / 2 - getTextWidth(mNumPaint, theMonth) / 2, mViewHeight / 8, mNumPaint);
        mNumPaint.reset();


        //绘制日历
        xInterval = mViewWidth / 7;
        yInterval = mViewHeight / 8;
        int day = 0;
        float x;
        float y;
        int theday;
        boolean isToday = false;
        boolean isCheckDay = false;
        float offset = 0;
        radius = mViewWidth / 19;

        for (int i = 0; i < weekName.length; i++) {
            x = i * xInterval + mNormalTextSize / 2;
            y = 1 * yInterval + yInterval / 2;
            if (i == 0 || i == weekName.length - 1) {
                drawNum(weekName[i], mNormalTextSize, mNormalTextColor2, x, y, canvas, isToday, offset);
            } else {
                drawNum(weekName[i], mNormalTextSize, mNormalTextColor1, x, y, canvas, isToday, offset);
            }
        }

        mNumPaint.reset();

        String str;

        for (int i = 2; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 2 && j == 0) {
                    j = weekOfFirstDay;
                }

                if (day > allDays.length - 1) {
                    theday = -1;
                } else {
                    theday = allDays[day];
                }

                str = "" + theday;
                if (theday == -1) {
                    str = "";
                }

                //单个数字的偏移量
                if (theday < 10 && theday > 0) {
                    offset = mNormalTextSize / 4;
                }


                //计算数字的位置
                y = i * yInterval + yInterval / 2;
                x = j * xInterval + mNormalTextSize / 2 - getTextWidth(mNumPaint, str) + offset;


                //判断是否为今天
                isToday = theday == today;

                if (isToday) {
                    drawACircle(x, y, Color.argb(255, 254, 140, 26), radius, canvas, offset);
                }

                //如果数字是checkDay
                isCheckDay = theday == firstCheckDay;
                if (isCheckDay) {
                    drawACircle(x, y, 0xffa0c8c8, radius, canvas, offset);
                }

                if (secondCheckDay != -2) {
                    if (theday > firstCheckDay && theday <= secondCheckDay) {
                        drawACircle(x, y, 0xffa0c8c8, radius, canvas, offset);
                        isCheckDay = true;
                    }
                }

                if (j == 0 || j == 6) {
                    drawNum(str, mNormalTextSize, mNormalTextColor2, x, y, canvas, isToday || isCheckDay, offset);
                } else {
                    drawNum(str, mNormalTextSize, mNormalTextColor1, x, y, canvas, isToday || isCheckDay, offset);

                }
                offset = 0;
                day++;
                mNumPaint.reset();
            }
        }

    }

    /**
     * 画出数字
     *
     * @param num
     * @param textSize
     * @param color
     * @param x
     * @param y
     * @param canvas
     * @param isTheDoday
     * @param offset
     */
    private void drawNum(String num, int textSize, int color, float x, float y, Canvas canvas, boolean isTheDoday, float offset) {
        if (isTheDoday) {
            color = 0xffffffff;
        }
        mNumPaint.setTextSize(textSize);
        mNumPaint.setColor(color);
        canvas.drawText(num, x, y, mNumPaint);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //获取事件的位置
        float touchX = event.getX();
        float touchY = event.getY();


        if (!canClick) {
            return true;
        }


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                if (touchY < 3 * mViewHeight / 32 && touchY > mViewHeight / 32) {
                    if (touchX < 3 * mViewWidth / 16 && touchX > mViewWidth / 8) {
                        clickLeft = true;
                        //左箭头事件
                        turnPageListener.OnLeftDown(today, month, year);
                    }

                    if (touchX < 7 * mViewWidth / 8 && touchX > 13 * mViewWidth / 16) {
                        clickRight = true;
                        //右箭头事件
                        turnPageListener.OnRightDown(today, month, year);
                    }
                }

                //以下是对日历的事件处理
                int theX = (int) ((touchX + 0.1 * xInterval) / xInterval);//获取第几列
                int theY = (int) ((touchY + 0.2 * yInterval) / yInterval);//获取第几行

                if (theY < 2) {
                    theY = 2;
                }
                //得到是哪一天
                int num = (theY - 2) * 7 + theX - weekOfFirstDay;
                int day;
                if (num < 0 || num > allDays.length - 1) {
                    num = -2;
                    day = 0;
                } else {
                    day = allDays[num];
                }
                float x = theX * xInterval + mNormalTextSize / 2 - mNumPaint.measureText("" + day);
                float y = theY * yInterval + yInterval / 2;

                //判断是否点击在每个数字为中心的圆内
                boolean isclick = isClick(x, y, num, touchX, touchY);


                //有三种状态 初始状态（00），第一次点击（10），第二次点击（11）
                if (!firstClick) {
                    firstClick = true;
                } else if (!secondClick) {
                    secondClick = true;
                } else {
                    firstClick = false;
                    secondClick = false;
                    firstCheckDay = -2;
                    secondCheckDay = -2;
                }

                //处理点击在月份天数外所引起的数值问题
                if (isclick && num != -2 && firstClick && !secondClick) {
                    firstCheckDay = allDays[num];
                }
                if (firstClick && firstCheckDay == -2) {
                    firstClick = false;
                }
                if (isclick && num != -2 && secondClick) {
                    if (allDays[num] < firstCheckDay) {
                        firstCheckDay = allDays[num];
                        secondClick = false;
                    } else {
                        secondCheckDay = allDays[num];
                    }
                }
                if (secondClick && secondCheckDay == -2) {
                    secondClick = false;
                }
                //


                //调用接口
                if (firstClick && !secondClick) {
                    chooseListener.onSingleChoose(firstCheckDay);
                } else if (firstClick && secondClick) {
                    int numO = secondCheckDay - firstCheckDay + 1;
                    int[] days = new int[numO];
                    int tday = firstCheckDay;
                    for (int j = 0; j < numO; j++) {
                        days[j] = tday++;
                    }
                    chooseListener.onDoubleChoose(days);
                }

                break;

            case MotionEvent.ACTION_UP:

                //左箭头事件
                if (clickLeft) {
                    turnPageListener.OnLeftUp(today, month, year);
                    clickLeft = !clickLeft;
                    preMonth();
                }

                //右箭头事件
                if (clickRight) {
                    turnPageListener.OnRightUp(today, month, year);
                    clickRight = !clickRight;
                    nextMonth();
                }
                break;
        }

        invalidate();

        return true;
    }

    private void nextMonth() {
        resetClick();
        CalendarUtils.nextMonth();
        int tyear = CalendarUtils.getCurrentYear();
        int tmonth = CalendarUtils.getCurrentMonth();
        int tday = CalendarUtils.getCurrentDate();
        int tdayOfWeek = CalendarUtils.getCurrentFirstWeekdayOfMoth();
        int tmaxDayNum = CalendarUtils.getCurrentMaxNumOfMonth();
        setYear(tyear);
        setMonth(tmonth);
        setAllDays(tmaxDayNum);
        setWeekOfFirstDay(tdayOfWeek);
        if (NowDay == tday && NowMonth == tmonth && NowYear == tyear) {
            setToday(tday);
        } else {
            setToday(0);
        }

    }

    private void preMonth() {
        resetClick();
        CalendarUtils.preMonth();
        int tyear = CalendarUtils.getCurrentYear();
        int tmonth = CalendarUtils.getCurrentMonth();
        int tday = CalendarUtils.getCurrentDate();
        int tdayOfWeek = CalendarUtils.getCurrentFirstWeekdayOfMoth();
        int tmaxDayNum = CalendarUtils.getCurrentMaxNumOfMonth();
        setYear(tyear);
        setMonth(tmonth);
        setAllDays(tmaxDayNum);
        setWeekOfFirstDay(tdayOfWeek);
        if (NowDay == tday && NowMonth == tmonth && NowYear == tyear) {
            setToday(tday);
        } else {
            setToday(0);
        }


    }


    private boolean isClick(float theX, float theY, int num, float touchX, float touchY) {

        boolean isContain = isContain(theX + radius * 3 / 4, theY - (radius / 2), touchX, touchY, radius);
        return isContain;
    }

    /**
     * 获取文本宽度
     *
     * @param paint
     * @param str
     * @return
     */
    private float getTextWidth(Paint paint, String str) {
        float iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += Math.ceil(widths[j]);
            }
        }

        return iRet;
    }

    public void resetClick() {
        firstClick = false;
        secondClick = false;

        firstCheckDay = -2;
        secondCheckDay = -2;
    }

    /**
     * 判断是否在圆内
     *
     * @param x      落点x
     * @param y      落点y
     * @param touchX 圆心X
     * @param touchY 圆心Y
     * @param radius 半径
     * @return
     */
    private boolean isContain(float x, float y, float touchX, float touchY, float radius) {
        double xPow = Math.pow(x - touchX, 2);
        double yPow = Math.pow(y - touchY, 2);
        double rPow = xPow + yPow;
        double RPow = Math.pow(radius, 2);
        return rPow <= RPow;
    }


    private void drawACircle(float x, float y, int color, float radius, Canvas canvas, float offset) {
        mNumPaint.setColor(color);
        canvas.drawCircle(x - offset + radius * 3 / 4, y - (radius / 2), radius, mNumPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }


    private int measureWidth(int widthMeasureSpec) {
        int width;

        int mode = MeasureSpec.getMode(widthMeasureSpec);

        int size = MeasureSpec.getSize(widthMeasureSpec);


        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        } else {
            //不是精确模式的话得自己结合paddin
            int desire = size + getPaddingLeft() + getPaddingRight();
            if (mode == MeasureSpec.AT_MOST) {
                width = Math.min(desire, size);
            } else {
                width = desire;
            }
        }
        mViewWidth = width;
        mNormalTextSize = width / (7 * 2);
        return width;
    }

    private int measureHeight(int heightMeasureSpec) {
        int height;

        int mode = MeasureSpec.getMode(heightMeasureSpec);

        int size = MeasureSpec.getSize(heightMeasureSpec);


        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        } else {
            //不是精确模式的话得自己结合paddin
            int desire = size + getPaddingTop() + getPaddingBottom();
            if (mode == MeasureSpec.AT_MOST) {
                height = Math.min(desire, size);
            } else {
                height = desire;
            }
        }
        mViewHeight = height;
        return height;
    }


}
