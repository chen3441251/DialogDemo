package com.dialog.demo.dialogdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

interface onDataSetListener {
    void onDateSet(DatePicker datePicker, int year, int month, int day);
}

/**
 * @ Creator     :     chenchao
 * @ CreateDate  :     2018/5/16 0016 9:32
 * @ Description :     DialogDemo
 */

public class CustomDatePicker extends AlertDialog implements DialogInterface.OnClickListener, DatePicker.OnDateChangedListener {
    private final DatePicker        mDatePicker;
    private       onDataSetListener listener;

    public CustomDatePicker(Context context, int themeResId, onDataSetListener listener, int year, int month, int day) {
        super(context, AlertDialog.THEME_TRADITIONAL);
        this.listener = listener;
        Context context1 = getContext();
        setButton(BUTTON_POSITIVE, "确认", this);
        setButton(BUTTON_NEGATIVE, "取消", this);
        setIcon(0);
        View inflate = LayoutInflater.from(context).inflate(R.layout.datepicker_layout, null);
        setView(inflate);
        mDatePicker = inflate.findViewById(R.id.datepick);
        mDatePicker.init(year, month, day, this);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case BUTTON_POSITIVE:
                tryNotifyDateSet();
                break;
            default:
                break;
        }
    }

    private void tryNotifyDateSet() {

        if (listener != null) {
            mDatePicker.clearFocus();
            //月份要加1
            listener.onDateSet(mDatePicker, mDatePicker.getYear(), mDatePicker.getMonth() + 1, mDatePicker.getDayOfMonth());
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (view.getId() == R.id.datepick) {
            mDatePicker.init(year, monthOfYear, dayOfMonth, this);
        }
    }
}
