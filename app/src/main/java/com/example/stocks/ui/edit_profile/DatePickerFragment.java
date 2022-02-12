package com.example.stocks.ui.edit_profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import com.example.stocks.CurrentUser;
import com.example.stocks.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DatePickerFragment extends DialogFragment {
    DatePicker mDatePicker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar currentUserBirthday = new GregorianCalendar();
        currentUserBirthday.setTime(CurrentUser.getUser().getBirthdayDate());
        int year = currentUserBirthday.get(Calendar.YEAR);
        int month = currentUserBirthday.get(Calendar.MONTH) + 1;
        int day = currentUserBirthday.get(Calendar.DAY_OF_MONTH);

        View view = getLayoutInflater().inflate(R.layout.dialog_date, null);
        mDatePicker = view.findViewById(R.id.dialog_date_picker);
//        mDatePicker.init(year, month, day, null);
        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Choose birthday date:")
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
