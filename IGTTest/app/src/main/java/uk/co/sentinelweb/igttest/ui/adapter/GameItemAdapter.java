package uk.co.sentinelweb.igttest.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.sentinelweb.igttest.R;
import uk.co.sentinelweb.igttest.databinding.ListItemGameBinding;
import uk.co.sentinelweb.igttest.model.Game;
import uk.co.sentinelweb.igttest.model.GameList;

/**
 * The adapter fort he list of items.
 */
public class GameItemAdapter extends Adapter {

    private GameList gameList;
    private OnItemClickListener listener;
    private int selectedItemPosition = -1;

    public GameItemAdapter(final GameList gameList) {
        this.gameList = gameList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ListItemGameBinding listItemGameBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_game, parent, false);
        return new GameItemViewHolder(listItemGameBinding);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((GameItemViewHolder) holder).setCurrentPosition(position);
    }

    @Override
    public int getItemCount() {
        if (gameList != null) {
            return gameList.getGames().size();
        }
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }

    public void setSelectedItemPosition(final int selectedItemPosition) {
        int oldSelectedPosition = selectedItemPosition;
        this.selectedItemPosition = selectedItemPosition;
        if (oldSelectedPosition > -1) {
            notifyItemChanged(selectedItemPosition);
        }
        notifyItemChanged(selectedItemPosition);
    }

    public void setItems(final GameList items) {
        gameList = items;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, Game game);
    }

    /**
     * This view holder is public to use for databinding onClick.
     *
     * We use the currentPosition to handle click calls.
     */

    public class GameItemViewHolder extends RecyclerView.ViewHolder {
        ListItemGameBinding listItemGameBinding;
        int currentPosition;
        Game game;

        public GameItemViewHolder(final ListItemGameBinding listItemGameBinding) {
            super(listItemGameBinding.getRoot());
            this.listItemGameBinding = listItemGameBinding;
            listItemGameBinding.setHandlers(this);
        }

         public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(currentPosition, gameList.getGames().get(currentPosition));
                setSelectedItemPosition(currentPosition);
            }
        }

        public void setCurrentPosition(int position) {
            this.currentPosition = position;
            final Game game = gameList.getGames().get(position);
            listItemGameBinding.setGame(game);
            listItemGameBinding.getRoot().setSelected(selectedItemPosition == position);
        }
    }

}
