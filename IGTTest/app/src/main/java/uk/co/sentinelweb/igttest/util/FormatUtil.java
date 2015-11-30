package uk.co.sentinelweb.igttest.util;

import android.content.Context;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import uk.co.sentinelweb.igttest.R;

/**
 * Formatting utilities.
 * Created by robert on 29/11/15.
 */
public class FormatUtil {
    private Context context;
    private final DateFormat timeFormat;
    private final DateFormat dateFormat;
    private final NumberFormat numberFormat;

    public FormatUtil(Context context) {
        this.context = context;
        timeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, Locale.getDefault());
        dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getDefault());
        timeFormat.setTimeZone(TimeZone.getDefault());
        numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
    }

    @NonNull
    public String formatDateString(final Date date) {
        if (date != null) {
            return context.getString(R.string.date_format, dateFormat.format(date), timeFormat.format(date));
        }
        return context.getString(R.string.invalid_date);
    }

    @NonNull
    public String formatNumberString(final Integer number) {
        if (number != null) {
            return numberFormat.format(number);
        }
        return context.getString(R.string.invalid_number);
    }

}
