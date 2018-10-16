package com.sangxiaonian.xcalendar.inter;

import com.sangxiaonian.xcalendar.entity.DateBean;

import java.util.List;

/**
 * 作者： ${PING} on 2018/10/15.
 */
public class CalendarControl  {

    public interface OnCalendarClickListener{

        /**
         * 当日历被点击的时候
         * @param selects 当前选中的全部日期
         */
        void onSelectChange(List<DateBean> selects);

    }



}
