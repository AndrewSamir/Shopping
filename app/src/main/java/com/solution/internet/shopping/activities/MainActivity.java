
package com.solution.internet.shopping.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.view.MenuItem;
import android.view.View;

import com.solution.internet.shopping.R;
import com.solution.internet.shopping.fragments.BaseFragment;
import com.solution.internet.shopping.fragments.HomeFragment;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;


/**
 * This will mainly be used to show most of the app. <br></br>
 * It has a header and a BottomNavigationView, and can easily be shown from the functions required from each Fragment.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, HandleRetrofitResp {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        appHeader.setBackgroundResource(R.color.colorPrimary);
        initBottomBar();

        Intent intent = getIntent();

      /*  if (SharedPrefHelper.getInstance(this).getAccessToken() == null)
            addContentFragment(new IntroFragment(), false);
        else if (intent.hasExtra(DataEnum.extraNotificationType.name()))
        {

            switch (intent.getStringExtra(DataEnum.extraNotificationType.name()))
            {
                case "newChallengeReplyCreated":

                    break;

                case "newChallengeCreated":

                    break;

                case "ChallengeAccepted":

                    break;

                case "ConversationCreated":

                    addContentFragment(MainConversations.init(intent.getStringExtra(DataEnum.extraNotificationConversationID.name())), false);
                    break;
                case "Conversation":
                    addContentFragment(MainConversations.init(), false);
                    break;

                case "User":
                    if (SharedPrefHelper.getInstance(this).getUserid().equals(intent.getStringExtra(DataEnum.extraNotificationUser.name())))
                        addContentFragment(MoreFragment.init(), false);
                    else
                        addContentFragment(ProfileFragment.init(intent.getStringExtra(DataEnum.extraNotificationUser.name())), false);
                    break;
            }
        } else*/

        addContentFragment(new HomeFragment(), false);

//            addContentFragment(ReservationsFragments.init(intent.getStringExtra(DataEnum.extraReservationID.name())), true);

    }

    //region parent
    @Override
    public int getFragmentContainerID() {
        return R.id.mainActivity_container;
    }

    @Override
    protected int getBaseLayoutID() {
        return R.layout.main_activity;
    }

    //endregion

    //region bottom bar

    private void initBottomBar() {
//        adjustMenuItemFont();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {//open correct view when clicked

                if (item.getItemId() == R.id.bottomItem_main) {
                    addContentFragment(new HomeFragment(), true);
                } else if (item.getItemId() == R.id.bottomItem_messages) {
                    addContentFragment(new HomeFragment(), true);
                } else if (item.getItemId() == R.id.bottomItem_messages) {
                    addContentFragment(new HomeFragment(), true);
                } else if (item.getItemId() == R.id.bottomItem_more) {
                    addContentFragment(new HomeFragment(), true);
                }

                return true;
            }
        });


    }

    private void adjustMenuItemFont() {
        int menuSize = bottomNavigationView.getMenu().size();
        for (int j = 0; j < menuSize; j++) {//change the type face for the menu font
            MenuItem mi = bottomNavigationView.getMenu().getItem(j);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/TheSansArabic-Plain.otf");
            if (mi.getTitle() != null) {
                SpannableString mNewTitle = new SpannableString(mi.getTitle());
//                mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                mi.setTitle(mNewTitle);
            }
        }
    }


    //endregion

    //region helpers
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void updateNavigationBarState() {
        //TODO adjust bottom navigation view buttons to select the correct one
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(getFragmentContainerID());
        int actionId = fragment.getSelectedMenuId();
        if (actionId != 0) {
            //            Menu menu = bottomNavigationView.getMenu();

            //            for (int i = 0, size = menu.size(); i < size; i++)
            //            {
            //                MenuItem item = menu.getItem(i);
            //                item.setChecked(item.getItemId() == actionId);
            //            }

            bottomNavigationView.getMenu().findItem(actionId).setChecked(true);
        }
    }

    @Override
    public void addContentFragment(Fragment fragment, boolean addToBackStack, @AnimRes int enterAnim, @AnimRes int exitAnim) {
        super.addContentFragment(fragment, addToBackStack, enterAnim, exitAnim);
    }

    //endregion

    //region initiate the activity
    public static void init(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        } else {
//            Logger.INSTANCE.e("MainActivity", "Context is null during init() function");
        }
    }

    //endregion

    @Override
    public void onClick(View v) {
       /* Category category = new Category();

        switch (v.getId())
        {
            case R.id.floatingAction_homeMade:
                category.setCategoryId(Category.Companion.getType_homeMade());
                category.setName(getString(R.string.homeMade));
                closeFloatingActionMenu();
                break;
            case R.id.floatingAction_sweets:
                category.setCategoryId(Category.Companion.getType_sweets());
                category.setName(getString(R.string.sweets));
                closeFloatingActionMenu();
                break;
            case R.id.floatingAction_coffee:
                category.setCategoryId(Category.Companion.getType_coffee());
                category.setName(getString(R.string.coffee));
                closeFloatingActionMenu();
                break;
            case R.id.floatingAction_restaurant:
                category.setCategoryId(Category.Companion.getType_restaurant());
                category.setName(getString(R.string.restaurants));
                closeFloatingActionMenu();
                break;

        }

        addContentFragment(FilterFragment.init(category), true);*/
    }

    @Override
    public void onResponseSuccess(String flag, Object o) {

    }

    @Override
    public void onNoContent(String flag, int code) {

    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position) {

    }
}
