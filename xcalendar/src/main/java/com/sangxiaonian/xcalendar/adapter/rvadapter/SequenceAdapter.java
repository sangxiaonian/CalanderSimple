package com.sangxiaonian.xcalendar.adapter.rvadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sangxiaonian.xcalendar.R;
import com.sangxiaonian.xcalendar.adapter.SequenceCalenderAdapter;
import com.sangxiaonian.xcalendar.calendar.SequenceCalendarView;
import com.sangxiaonian.xcalendar.entity.DateBean;
import com.sangxiaonian.xcalendar.inter.CalendarControl;
import com.sangxiaonian.xcalendar.utils.JLog;

import java.util.Calendar;
import java.util.List;

/**
 * 作者： ${PING} on 2018/10/15.
 */
public class SequenceAdapter extends RecyclerView.Adapter implements CalendarControl.OnCalendarClickListener {

    Context context;
    List<DateBean> dateBeans;

    DateBean startDatebean,endDateBean;





    public SequenceAdapter(Context context, List<DateBean> dateBeans) {
        this.context = context;
        this.dateBeans = dateBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SequenceCalendarHolder(LayoutInflater.from(context).inflate(R.layout.item_calendar, viewGroup, false));
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        SequenceCalendarHolder holder = (SequenceCalendarHolder) viewHolder;
        DateBean dateBean = dateBeans.get(i);
        holder.initView(i, dateBean);
        holder.setListener(this);

        holder.calendarView.setSequenceSelect(startDatebean,endDateBean);

//
//        if (startDatebean==null&&endDateBean==null){
//            holder.calendarView.clearSelects();
//        }else if (startDatebean!=null&&endDateBean==null){
//            if (startDatebean.equals(dateBean)){//如果是初始月
//                holder.calendarView.
//            }
//        }else
//
//        if (startDatebean!=null&&endDateBean!=null) {
//            if (startDatebean.compareWithNoDayTo(dateBean) > 0 || endDateBean.compareWithNoDayTo(dateBean) < 0) {//不再日期范围之内
//                holder.calendarView.clearSelects();
//            } else if (startDatebean.compareWithNoDayTo(dateBean)<0&&endDateBean.compareWithNoDayTo(dateBean)>0){//在选择的日期范围之内
//                holder.calendarView.setSelectedAll();
//            }else if (startDatebean.compareWithNoDayTo(endDateBean)==0){//如果同月
//                holder.calendarView.setSelectedAll();
//            }else {
//                holder.calendarView.clearSelects();
//            }
//        }


    }

    @Override
    public int getItemCount() {
        return dateBeans.size();
    }

    /**
     * 当日历被点击的时候
     *
     * @param selects 当前选中的全部日期
     */
    @Override
    public void onSelectChange(List<DateBean> selects) {
        if (selects!=null&&!selects.isEmpty()) {
            JLog.i(selects.toString());
            if (endDateBean==null){
                //没有没有初始日期，加入初始日期
                if (startDatebean==null){
                    setStart(selects.get(0));
                    //初始日期已经存在，此时为确认当前日期
                }else {
                    setEnd(selects.get(selects.size()-1));

                }
            }else {
                endDateBean=null;
                setStart(selects.get(0));
            }
            notifyDataSetChanged();
        }
    }
    private void setEnd(DateBean i) {
        if (startDatebean.compareTo(i)>0){
            endDateBean=startDatebean;
            startDatebean=i;
        }else if (startDatebean.compareTo(i)<0){
            endDateBean=i;
        }

    }

    private void setStart(DateBean bean) {
        startDatebean=bean;
        endDateBean=null;
    }


    public static class SequenceCalendarHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public   SequenceCalendarView calendarView;

        public SequenceCalendarHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            calendarView = itemView.findViewById(R.id.calendar);

        }

        public void initView(int position, DateBean dateBean) {
            tvTitle.setText(dateBean.getYear() + "年" + (dateBean.getMonth() + 1) + "月");
            calendarView.setCurrentDate(dateBean.getYear(),dateBean.getMonth());



        }

        public void setListener(CalendarControl.OnCalendarClickListener lisener){
            calendarView.setCalendarClickListener(lisener);
        }

    }


}
