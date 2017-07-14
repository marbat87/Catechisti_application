package it.cammino.catechisti.items;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.commons.utils.FastAdapterUIUtils;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.materialize.holder.StringHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.cammino.catechisti.R;

public class SimpleCommunityItem extends AbstractItem<SimpleCommunityItem, SimpleCommunityItem.ViewHolder> {

    private StringHolder diocesi;
    private int ordine;
    private int anni;
    private StringHolder parrocchia;
    private int codiceTappa;
    private StringHolder descTappa;
    private int id;

    public SimpleCommunityItem withDiocesi(String diocesi) {
        this.diocesi = new StringHolder(diocesi);
        return this;
    }

    public SimpleCommunityItem withOrdine(int ordine) {
        this.ordine = ordine;
        return this;
    }

    public SimpleCommunityItem withAnni(int anni) {
        this.anni = anni;
        return this;
    }

    public SimpleCommunityItem withParrocchia(String parrocchia) {
        this.parrocchia = new StringHolder(parrocchia);
        return this;
    }

    public SimpleCommunityItem withCodiceTappa(int codiceTappa) {
        this.codiceTappa = codiceTappa;
        return this;
    }

    public SimpleCommunityItem withDescTappa(String descTappa) {
        this.descTappa = new StringHolder(descTappa);
        return this;
    }

    public SimpleCommunityItem withId(int id) {
        this.id = id;
        super.withIdentifier(id);
        return this;
    }

    public StringHolder getDiocesi() {
        return diocesi;
    }

    public int getOrdine() {
        return ordine;
    }

    public int getAnni() {
        return anni;
    }

    public StringHolder getParrocchia() {
        return parrocchia;
    }

    public int getCodiceTappa() {
        return codiceTappa;
    }

    public StringHolder getDescTappa() {
        return descTappa;
    }

    public int getId() {
        return id;
    }

    /**
     * defines the type defining this item. must be unique. preferably an id
     *
     * @return the type
     */
    @Override
    public int getType() {
        return R.id.fastadapter_simple_community_item_id;
    }

    /**
     * defines the layout which will be used for this item in the list
     *
     * @return the layout for this item
     */
    @Override
    public int getLayoutRes() {
        return R.layout.community_item;
    }

    /**
     * binds the data of this item onto the viewHolder
     *
     * @param viewHolder the viewHolder of this item
     */
    @Override
    public void bindView(ViewHolder viewHolder, List<Object> payloads) {
        super.bindView(viewHolder, payloads);

        //get the context
        Context ctx = viewHolder.itemView.getContext();

        StringHolder nameHolder = new StringHolder(ordine + "a - " + parrocchia);
        StringHolder.applyTo(nameHolder, viewHolder.mCommunityName);
        ViewCompat.setBackground(viewHolder.view, FastAdapterUIUtils.getSelectableBackground(ctx, ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.ripple_color), true));

    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.mCommunityName.setText(null);
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    /**
     * our ViewHolder
     */
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        protected View view;
        @BindView(R.id.community_name) TextView mCommunityName;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
        }
    }
}