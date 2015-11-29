package uk.co.sentinelweb.igttest.model;

import com.google.gson.annotations.Expose;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class Game {

    @Expose
    private String name;

    @Expose
    private Date date;

    @Expose
    private Integer jackpot;

    public Game(final Date date, final Integer jackpot, final String name) {
        this.date = date;
        this.jackpot = jackpot;
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public Integer getJackpot() {
        return jackpot;
    }

    public void setJackpot(final Integer jackpot) {
        this.jackpot = jackpot;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    // should be able to format using databinding but doesn't seem to work
    public String getJackpotDisplay() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        return numberFormat.format(jackpot);
    }

    // should be able to format using databinding but doesn't seem to work
    public String getDateDisplay() {
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, Locale.getDefault());
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(date) + " @ " + timeFormat.format(date);
    }

}
