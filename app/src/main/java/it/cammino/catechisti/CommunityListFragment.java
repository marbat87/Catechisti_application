package it.cammino.catechisti;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.itemanimators.SlideLeftAlphaAnimator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import it.cammino.catechisti.data.CatechistiDB;
import it.cammino.catechisti.items.SimpleCommunityItem;
import it.cammino.catechisti.utils.Utility;

/**
 * An activity representing a list of Communities. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link CommunityDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class CommunityListFragment extends Fragment {

    @BindView(R.id.community_list)
    RecyclerView mRecyclerView;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private long mLastClickTime = 0;

//    private FastItemAdapter<SimpleCommunityItem> mCommunitiesAdapter;
    private Unbinder mUnbinder;
    private CatechistiDB mDb;

    @OnClick(R.id.fab)
    public void fabAction(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //crea un istanza dell'oggetto DatabaseCanti
        mDb = new CatechistiDB(getActivity());
    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_community_list);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle(getTitle());

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        View recyclerView = findViewById(R.id.community_list);
//        assert recyclerView != null;
//        setupRecyclerView((RecyclerView) recyclerView);

//        if (findViewById(R.id.community_detail_container) != null) {
//            // The detail container view will be present only in the
//            // large-screen layouts (res/values-w900dp).
//            // If this view is present, then the
//            // activity should be in two-pane mode.
//            mTwoPane = true;
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_community_list, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);

        assert mRecyclerView != null;
        setupRecyclerView(mRecyclerView);

        if (rootView.findViewById(R.id.community_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        return rootView;

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
//        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));

        // crea un manipolatore per il Database in modalità READ
        SQLiteDatabase db = mDb.getReadableDatabase();

        //recupero lista comunità
        String query = "SELECT A._id, A.DIOCESI, A.ORDINE, A.ANNI_CAMMINO, A.PARROCCHIA, C.COD_PASSAGGIO, C.DESCRIZIONE" +
                "		FROM " + CatechistiDB.COMUNITA_TABLE + " A, " + CatechistiDB.PASSAGGI_TABLE + " B, " + CatechistiDB.DZ_PASSAGGI_TABLE + " C" +
                "       WHERE A._id = B.ID_COMUNITA" +
                "         AND B.DATA_PASSAGGIO = (SELECT MAX(X.DATA_PASSAGGIO)" +
                "                                   FROM " + CatechistiDB.PASSAGGI_TABLE + " Z" +
                "                                  WHERE A._id = Z.ID_COMUNITA)" +
                "         AND B.COD_PASSAGGIO = C.COD_PASSAGGIO" +
                "		ORDER BY PARROCCHIA ASC, ORDINE ASC";
        Cursor lista = db.rawQuery(query, null);

        // crea un array e ci memorizza le comunità estratti
        List<SimpleCommunityItem> mCommunities = new ArrayList<>();
        lista.moveToFirst();
        for (int i = 0; i < lista.getCount(); i++) {
            SimpleCommunityItem sampleItem = new SimpleCommunityItem();
            sampleItem
                    .withId(lista.getInt(0))
                    .withDiocesi(lista.getString(1))
                    .withOrdine(lista.getInt(2))
                    .withAnni(lista.getInt(3))
                    .withParrocchia(lista.getString(4))
                    .withCodiceTappa(lista.getInt(5))
                    .withDescTappa(lista.getString(6));
            mCommunities.add(sampleItem);
            lista.moveToNext();
        }
        // chiude il cursore
        lista.close();
        db.close();

        FastAdapter.OnClickListener<SimpleCommunityItem> mOnClickListener = new FastAdapter.OnClickListener<SimpleCommunityItem>() {
            @Override
            public boolean onClick(View view, IAdapter<SimpleCommunityItem> iAdapter, SimpleCommunityItem item, int i) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < Utility.CLICK_DELAY)
                    return true;
                mLastClickTime = SystemClock.elapsedRealtime();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(CommunityDetailFragment.ARG_ITEM_ID, item.getId());
                    CommunityDetailFragment fragment = new CommunityDetailFragment();
                    fragment.setArguments(arguments);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.community_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, CommunityDetailActivity.class);
                    intent.putExtra(CommunityDetailFragment.ARG_ITEM_ID, item.getId());

                    context.startActivity(intent);
                }
                return true;
            }
        };

        FastItemAdapter<SimpleCommunityItem> mCommunitiesAdapter = new FastItemAdapter<>();
        mCommunitiesAdapter.withSelectable(true)
                .withMultiSelect(true)
                .withSelectOnLongClick(true)
//                .withOnPreClickListener(mOnPreClickListener)
                .withOnClickListener(mOnClickListener)
//                .withOnPreLongClickListener(mOnPreLongClickListener)
                .setHasStableIds(true);
        mCommunitiesAdapter.add(mCommunities);

        recyclerView.setAdapter(mCommunitiesAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new SlideLeftAlphaAnimator());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

//    public class SimpleItemRecyclerViewAdapter
//            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {
//
//        private final List<DummyContent.DummyItem> mValues;
//
//        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
//            mValues = items;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.community_list_content, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, int position) {
//            holder.mItem = mValues.get(position);
//            holder.mIdView.setText(mValues.get(position).id);
//            holder.mContentView.setText(mValues.get(position).content);
//
//            holder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mTwoPane) {
//                        Bundle arguments = new Bundle();
//                        arguments.putString(CommunityDetailFragment.ARG_ITEM_ID, holder.mItem.id);
//                        CommunityDetailFragment fragment = new CommunityDetailFragment();
//                        fragment.setArguments(arguments);
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.community_detail_container, fragment)
//                                .commit();
//                    } else {
//                        Context context = v.getContext();
//                        Intent intent = new Intent(context, CommunityDetailActivity.class);
//                        intent.putExtra(CommunityDetailFragment.ARG_ITEM_ID, holder.mItem.id);
//
//                        context.startActivity(intent);
//                    }
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return mValues.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            public final View mView;
//            public final TextView mIdView;
//            public final TextView mContentView;
//            public DummyContent.DummyItem mItem;
//
//            public ViewHolder(View view) {
//                super(view);
//                mView = view;
//                mIdView = (TextView) view.findViewById(R.id.id);
//                mContentView = (TextView) view.findViewById(R.id.content);
//            }
//
//            @Override
//            public String toString() {
//                return super.toString() + " '" + mContentView.getText() + "'";
//            }
//        }
//    }
}
