package com.sangxiaonian.xcalendar.calendar;

import android.content.Context;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.sangxiaonian.xcalendar.adapter.SequenceCalenderAdapter;
import com.sangxiaonian.xcalendar.entity.DateBean;

/**
 * 作者： ${PING} on 2018/10/15.
 * 两次选择之间的数据是连续的
 */
public class SequenceCalendarView extends BaseCalendarView {


    private int clickDay;

    public SequenceCalendarView(Context context) {
        super(context);
    }

    public SequenceCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SequenceCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        super.initView(context, attrs, defStyleAttr);
        adapter=new SequenceCalenderAdapter(context);
    }

    private DateBean startDatebean;
    private DateBean endDateBean;


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                final float x = event.getX();
                final float y = event.getY();
                for (int i = 0; i < rectFS.size(); i++) {
                    RectF rectF = rectFS.get(i);
                    if (rectF.contains(x, y)) {
                        clickDay = getDayByIndex(i);
                        return true;
                    }
                }
            }
            break;
            case MotionEvent.ACTION_MOVE:
                return false;
            case MotionEvent.ACTION_UP:
                final float x = event.getX();
                final float y = event.getY();
                for (int i = 0; i < rectFS.size(); i++) {
                    RectF rectF = rectFS.get(i);
                    if (rectF.contains(x, y)&&clickDay==getDayByIndex(i)) {
                         if (endDateBean==null){
                             //没有没有初始日期，加入初始日期
                             if (startDatebean==null){
                                 setStart(i);

                                 //初始日期已经存在，此时为确认当前日期
                             }else {
                                 setEnd(i);
                                 int startIndex = showDates.indexOf(startDatebean);
                                 selectDates.clear();
                                 int start=Math.min(startIndex,i);
                                 int end=Math.max(startIndex,i);
                                 for (int j = start; j <= end; j++) {
                                     selectDates.add(showDates.get(j));
                                 }
                             }
                         }else {
                             endDateBean=null;
                             setStart(i);
                         }
                         if (calendarClickListener!=null){
                             calendarClickListener.onSelectChange(selectDates);
                         }
                        postInvalidate();
                        return true;
                    }
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    private void setEnd(int i) {
        endDateBean=showDates.get(i);
        if (adapter instanceof SequenceCalenderAdapter){
            ((SequenceCalenderAdapter) adapter).setEndIndx(endDateBean);
        }
    }

    private void setStart(int i) {
        startDatebean=showDates.get(i);
        if (adapter instanceof SequenceCalenderAdapter){
            ((SequenceCalenderAdapter) adapter).setStartIndex(startDatebean);
            ((SequenceCalenderAdapter) adapter).setEndIndx(null);
        }
        selectDates.clear();
        selectDates.add(startDatebean);
    }


    /**
     * 选中所有日期
     */
    public void setSelectedAll() {
        selectDates.clear();
        selectDates.addAll(showDates);
        postInvalidate();
    }
}
