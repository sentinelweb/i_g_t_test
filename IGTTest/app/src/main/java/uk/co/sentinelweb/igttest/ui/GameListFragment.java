package uk.co.sentinelweb.igttest.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import uk.co.sentinelweb.igttest.Const;
import uk.co.sentinelweb.igttest.LoaderId;
import uk.co.sentinelweb.igttest.R;
import uk.co.sentinelweb.igttest.adapter.GameItemAdapter;
import uk.co.sentinelweb.igttest.loader.GameListLoader;
import uk.co.sentinelweb.igttest.model.Game;
import uk.co.sentinelweb.igttest.model.GameList;

/**
 * A list fragment representing a list of Games. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link GameDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class GameListFragment extends Fragment implements GameItemAdapter.OnItemClickListener, LoaderManager.LoaderCallbacks<GameList> {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(int id) {
        }
    };
    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;
    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;
    private RecyclerView list;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GameListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        list = (RecyclerView) inflater.inflate(R.layout.fragment_game_list, container);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        return list;
    }

    private void setAdapter(final GameList testGameList) {
        if (list.getAdapter() == null) {
            final GameItemAdapter adapter = new GameItemAdapter(testGameList);
            adapter.setOnItemClickListener(this);
            list.setAdapter(adapter);
        } else {
            ((GameItemAdapter) list.getAdapter()).setItems(testGameList);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }

    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        getLoaderManager().initLoader(LoaderId.LIST_LOADER, null, this);
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    //public void setActivateOnItemClick(boolean activateOnItemClick) {
    // When setting CHOICE_MODE_SINGLE, ListView will automatically
    // give items the 'activated' state when touched.
//        list.setChoiceMode(activateOnItemClick
//                ? ListView.CHOICE_MODE_SINGLE
//                : ListView.CHOICE_MODE_NONE);
    //}

    //
    private void setActivatedPosition(int position) {
        mActivatedPosition = position;
        if (list != null && list.getAdapter() != null) {
            ((GameItemAdapter) list.getAdapter()).setSelectedItemPosition(mActivatedPosition);
        }
    }

    @Override
    public void onItemClick(final int position, final Game game) {
        mCallbacks.onItemSelected(position);
        setActivatedPosition(position);
    }

    @Override
    public Loader<GameList> onCreateLoader(final int id, final Bundle args) {
        if (id == LoaderId.LIST_LOADER) {
            return new GameListLoader(getActivity());
        }
        return null;
    }

    @Override
    public void onLoadFinished(final Loader<GameList> loader, final GameList data) {
        Log.d(Const.LOG, "got list:" + data);
        setAdapter(data);
    }

    @Override
    public void onLoaderReset(final Loader<GameList> loader) {

    }

    public void reload() {
        getLoaderManager().initLoader(LoaderId.LIST_LOADER, null, this);
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        void onItemSelected(int id);
    }

}
