package uk.co.sentinelweb.igttest.model.dummy;

import android.text.format.DateUtils;

import java.util.Date;

import uk.co.sentinelweb.igttest.model.Game;
import uk.co.sentinelweb.igttest.model.GameList;

/**
 * Created by robert on 28/11/15.
 */
public class DummyGameContent {
    public static GameList generate() {
        GameList games = new GameList("XBT");
        for (int game = 0; game < 20; game++) {
            games.getGames().add(new Game(new Date(System.currentTimeMillis() - game * DateUtils.HOUR_IN_MILLIS), game * 1000, "Game:" + game));
        }
        return games;
    }
}
