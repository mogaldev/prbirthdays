package com.mogal.prbirthdays.activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.mogal.prbirthdays.R;
import com.mogal.prbirthdays.R.id;
import com.mogal.prbirthdays.R.layout;
import com.mogal.prbirthdays.facebook.RequestBuilder;
import com.mogal.prbirthdays.utils.FontTypeFaceManager;
import com.mogal.prbirthdays.utils.FontTypeFaceManager.CustomFonts;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.OnDateSelectedListener;
import com.squareup.timessquare.CalendarPickerView.SelectionMode;

public class DatesSelectionActivity extends Activity {
	private Date dateFrom, dateTo;
	private TextView tvDateRange;
	private CalendarPickerView calendarView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dates_selection);

		calendarView = (CalendarPickerView) findViewById(R.id.calendarView);
		tvDateRange = (TextView) findViewById(R.id.tvDateRange);
		Button btnImport = (Button) findViewById(R.id.btnImportBirthdayUsers);
		FontTypeFaceManager ftfm = FontTypeFaceManager.getInstance(this);
		ftfm.setFont(tvDateRange, CustomFonts.RobotoThin);
		ftfm.setFont(btnImport, CustomFonts.RobotoLight);

		btnImport.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(),
						BirthdayUsersListActivity.class));
			}
		});

		// Calendar View initialization
		initCalendar();

	}

	@SuppressLint("SimpleDateFormat")
	private void updateRangeLabel() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
		tvDateRange.setText(String.format("%s - %s", sdf.format(dateFrom),
				sdf.format(dateTo)));

		RequestBuilder.dateFrom = dateFrom;
		RequestBuilder.dateTo = dateTo;
	}

	private void initCalendar() {
		Calendar nextYear = Calendar.getInstance();
		nextYear.add(Calendar.YEAR, 1);
		Date today = new Date();
		calendarView.init(today, nextYear.getTime())
				.inMode(SelectionMode.RANGE);
		calendarView.setOnDateSelectedListener(new OnDateSelectedListener() {
			@Override
			public void onDateSelected(Date date) {
				updateDates();
			}
		});

		// set the initial value for today
		calendarView.selectDate(today);
		updateDates();
	}

	private void updateDates() {
		List<Date> selectedDates = calendarView.getSelectedDates();
		int numOfDates = selectedDates.size();
		if (numOfDates > 0) {
			dateFrom = selectedDates.get(0);
			dateTo = selectedDates.get(numOfDates - 1);
			// updates the label that displays the chosen range
			updateRangeLabel();
		}
	}
}
