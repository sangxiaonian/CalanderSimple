package com.sangxiaonian.xcalendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.sangxiaonian.xcalendar.adapter.rvadapter.SequenceAdapter;
import com.sangxiaonian.xcalendar.entity.DateBean;
import com.sangxiaonian.xcalendar.utils.CalendarUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2018/10/15.
 */
public class SequenceCalendar extends RecyclerView {

    private int startYear,endYear,startMonth,endMonth;

    List<DateBean> dates;
    private SequenceAdapter adapter;


    public SequenceCalendar(@NonNull Context context) {
        this(context,null,0);
    }

    public SequenceCalendar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SequenceCalendar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context,attrs,defStyle);
    }

    private void initView(Context context, AttributeSet attrs, int defStyle) {
        startYear= CalendarUtils.getInstance().getCurrentYear();
        startMonth=0;
        endYear=CalendarUtils.getInstance().getCurrentYear()+1;
        endMonth=11;
        dates=new ArrayList<>();
        for (int i = startYear; i <=endYear ; i++) {
            for (int j = 0; j <12 ; j++) {
                dates.add(new DateBean(i,j,1));
            }
        }
        LinearLayoutManager manager=new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(manager);
        adapter=new SequenceAdapter(context,dates);
        setAdapter(adapter);
        new LinearSnapHelper().attachToRecyclerView(this);

    }

    public void setStart(int year,int month){
        this.startMonth=month;
        startYear=year;
    }

    public void setEnd(int year,int month){
        this.endMonth=month;
        endYear=year;
    }













}
