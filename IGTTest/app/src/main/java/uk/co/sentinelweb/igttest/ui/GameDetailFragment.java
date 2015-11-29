package uk.co.sentinelweb.igttest.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import uk.co.sentinelweb.igttest.LoaderId;
import uk.co.sentinelweb.igttest.R;
import uk.co.sentinelweb.igttest.databinding.FragmentGameDetailBinding;
import uk.co.sentinelweb.igttest.loader.GameListLoader;
import uk.co.sentinelweb.igttest.model.Game;
import uk.co.sentinelweb.igttest.model.GameList;

/**
 * A fragment representing a single Game detail screen.
 */
public class GameDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<GameList> {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    Game mItem;
    int gameIndex;
    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
    DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, Locale.getDefault());
    FragmentGameDetailBinding viewDataBound;

    String currency = "?";
    private CollapsingToolbarLayout appBarLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GameDetailFragment() {
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            gameIndex = getArguments().getInt(ARG_ITEM_ID);

            Activity activity = this.getActivity();
            appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null && mItem != null) {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewDataBound = FragmentGameDetailBinding.inflate(inflater, container, false);
        viewDataBound.setNumberFormatter(numberFormat);
        viewDataBound.setTimeFormatter(dateFormat);
        viewDataBound.setCurrency(currency);
        return viewDataBound.getRoot();
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        getLoaderManager().initLoader(LoaderId.GAME_LOADER, null, this);
    }

    @Override
    public Loader<GameList> onCreateLoader(final int id, final Bundle args) {
        if (id == LoaderId.GAME_LOADER) {
            return new GameListLoader(getActivity());
        }
        return null;
    }

    @Override
    public void onLoaderReset(final Loader<GameList> loader) {

    }

    @Override
    public void onLoadFinished(final Loader<GameList> loader, final GameList data) {
        if (data != null) {
            currency = data.getCurrency();
            mItem = data.getGames().get(gameIndex);
        } else {
            mItem = null;
        }
        Game displayGame = mItem;
        if (displayGame == null) {
            displayGame = new Game(new Date(), 0, "No Game");
        }
        viewDataBound.setCurrency(currency);
        viewDataBound.setGame(displayGame);
        if (appBarLayout != null) {
            appBarLayout.setTitle(displayGame.getName());
        }

    }

}
