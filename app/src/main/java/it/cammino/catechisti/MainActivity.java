package it.cammino.catechisti;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.Window;

import com.mikepenz.materialize.MaterializeBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

//    @BindView(R.id.message) TextView mTextMessage;
    @BindView(R.id.navigation) BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    changeFragment(0);
                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    changeFragment(1);
                    return true;
                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
                    changeFragment(2);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            //set the transition
            Transition ts = new Explode();
            ts.setDuration(5000);
            getWindow().setEnterTransition(ts);
            getWindow().setExitTransition(ts);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        new MaterializeBuilder().withActivity(this).build();

        changeFragment(0);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * To load fragments for sample
     * @param position menu index
     */
    private void changeFragment(int position) {

        Fragment newFragment;

        if (position == 0) {
            newFragment = new CommunityListFragment();
        } else if (position % 2 != 0) {
            newFragment = new CommunityListFragment();
        } else {
            newFragment = new CommunityListFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(
                R.id.content, newFragment)
                .commit();
    }

//    /**
//     * To change bottom bar color on the basis of index
//     * @param bottomNavigationView bottom bar object
//     * @param index menu index
//     */
//    private void changeBottomBarColor(BottomNavigationView bottomNavigationView, int index) {
//        if (bottomBarColors != null) {
//            int colorCode = 0;
//
//            if (index == 0) {
//                colorCode = bottomBarColors[index];
//            } else {
//                colorCode = ContextCompat.getColor(BottomBarDemo.this, bottomBarColors[index]);
//            }
//
//            DrawableCompat.setTint(ContextCompat.getDrawable(BottomBarDemo.this,
//                    R.drawable.drawable_bottombar_color),
//                    colorCode);
//
//            bottomNavigationView.setItemBackgroundResource(R.drawable.drawable_bottombar_color);
//
//            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                // If you want to change status bar color
//                //getWindow().setStatusBarColor(ContextCompat.getColor(BottomBarDemo.this, colorCode));
//
//                // If you want to change bottom device navigation key background color
//                getWindow().setNavigationBarColor(colorCode);
//            }
//        }
//    }

}
