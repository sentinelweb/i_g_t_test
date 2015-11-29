package uk.co.sentinelweb.igttest.model;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Game list object
 */
public class GameList  {
    @Expose
    @SerializedName("currency")
    private String currency;

    @Expose
    @SerializedName("response")
    private String status;

    @Expose
    @SerializedName("data")
    private ArrayList<Game> games;

    public GameList(final String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(final ArrayList<Game> games) {
        this.games = games;
    }
}
