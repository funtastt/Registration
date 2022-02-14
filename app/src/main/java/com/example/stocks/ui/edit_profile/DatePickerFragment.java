package com.example.stocks.ui.edit_profile;

import static android.app.Activity.RESULT_OK;

import static com.example.stocks.Constants.*;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import com.example.stocks.CurrentUser;
import com.example.stocks.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DatePickerFragment extends DialogFragment{
    DatePicker mDatePicker;
    long birthdayDate = CurrentUser.getUser().getBirthdayDate();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = this.getArguments();
        if (args != null) {
            birthdayDate = args.getLong(INITIAL_BIRTHDAY_DATE, birthdayDate);
        }


        Calendar currentUserBirthday = new GregorianCalendar();
        currentUserBirthday.setTime(new Date(birthdayDate));
        int year = currentUserBirthday.get(Calendar.YEAR);
        int month = currentUserBirthday.get(Calendar.MONTH);
        int day = currentUserBirthday.get(Calendar.DAY_OF_MONTH);

        View view = getLayoutInflater().inflate(R.layout.dialog_date, null);
        mDatePicker = view.findViewById(R.id.dialog_date_picker);
        mDatePicker.init(year, month, day, null);

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Choose birthday date:")
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    Intent intent = new Intent();
                    long birthdayDate = new Date(mDatePicker.getYear() - 1900, mDatePicker.getMonth(), mDatePicker.getDayOfMonth()).getTime();
                    intent.putExtra(CHOOSE_BIRTHDAY_DATE, birthdayDate);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
                })
                .create();
    }
}
