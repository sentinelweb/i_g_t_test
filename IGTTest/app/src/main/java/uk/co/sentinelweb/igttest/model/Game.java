package uk.co.sentinelweb.igttest.model;

import com.google.gson.annotations.Expose;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import uk.co.sentinelweb.igttest.util.FormatUtil;

/**
 * An individual game.
 */
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
//    public String getJackpotDisplay() {
//        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
//        return numberFormat.format(jackpot);
//    }
//
//    // should be able to format using databinding but doesn't seem to work
//    public String getDateDisplay() {
//        final Date date = this.date;
//        return new FormatUtil().formatDateString(date);
//    }

}
