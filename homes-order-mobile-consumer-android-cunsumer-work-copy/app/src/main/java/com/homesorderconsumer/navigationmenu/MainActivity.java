package com.homesorderconsumer.navigationmenu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.homesorderconsumer.R;
import com.homesorderconsumer.category.CategoryActivity;
import com.homesorderconsumer.databinding.ActivityMainBinding;
import com.homesorderconsumer.home.HomeFragment;
import com.homesorderconsumer.home.viewmodel.CartAndSearchVM;
import com.homesorderconsumer.initialscreen.languageselection.LanguageSelectionActivity;
import com.homesorderconsumer.navigationmenu.adapter.MenuAdapter;
import com.homesorderconsumer.navigationmenu.model.AppMenu;
import com.homesorderconsumer.navigationmenu.viewmodel.MainVM;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.support.SupportFragment;
import com.homesorderconsumer.trackorder.TrackOrderFragment;
import com.homesorderconsumer.user.login.LoginActivity;
import com.homesorderconsumer.user.myaccount.MyAccountActivity;
import com.homesorderconsumer.user.myorder.MyOrderFragment;
import com.homesorderconsumer.user.staticcontent.StaticContentActivity;
import com.homesorderconsumer.user.wishlist.WishListFragment;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.Util;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.yarolegovich.slidingrootnav.callback.DragListener;
import com.yarolegovich.slidingrootnav.callback.DragStateListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnNavigationMenuListener,
        DragListener, DragStateListener {

    ActivityMainBinding binding;
    MainVM mainVM;
    private SlidingRootNav slidingRootNav;

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        setupNavigationView(savedInstanceState);
        setMenuRecyclerView(menuList());
        showFragment(new HomeFragment());
    }

    @Override
    public void onBackPressed() {
        if (slidingRootNav.isMenuOpened()) {
            slidingRootNav.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.setCartCount(String.valueOf(MySession.getInstance(this).getCartCount()));
    }

    @Override
    public void onClickNavigationItem(AppMenu appMenu) {
        if (MySession.getInstance(this).isLogin()) {
            onClickLoggedUser(appMenu);
        } else {
            onClickGuestUser(appMenu);
        }
    }

    @Override
    public void onDrag(float progress) {
        if (progress > 0.001) {
            Util.getInstance().changeStatusBarColor(this, getResources().getColor(R.color
                    .colorAccent), true);
        } else {
            Util.getInstance().changeStatusBarColor(this, getResources().getColor(R.color
                    .colorPrimary), false);
        }
    }

    @Override
    public void onDragStart() {
    }

    @Override
    public void onDragEnd(boolean isMenuOpened) {
    }

    private void bindView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMainVM(mainVM);
        binding.setCartAndSearchVM(new CartAndSearchVM(this));
        setSupportActionBar(binding.toolbar);
    }

    private void setupNavigationView(Bundle savedInstanceState) {
        if (MySession.getInstance(this).getLanguageKey().equals(getString(R.string.ar))){
            slidingRootNav = new SlidingRootNavBuilder(this)
                    .withMenuOpened(false)
                    .withContentClickableWhenMenuOpened(true)
                    .withSavedState(savedInstanceState)
                    .withMenuLayout(R.layout.menu_layout)
                    .withRootViewScale(0.9f)
                    .withGravity(SlideGravity.RIGHT)
                    .addDragListener(this)
                    .addDragStateListener(this)
                    .inject();
        }else {
            slidingRootNav = new SlidingRootNavBuilder(this)
                    .withMenuOpened(false)
                    .withContentClickableWhenMenuOpened(true)
                    .withSavedState(savedInstanceState)
                    .withMenuLayout(R.layout.menu_layout)
                    .withRootViewScale(0.9f)
                    .addDragListener(this)
                    .addDragStateListener(this)
                    .inject();
        }

        binding.toggleAction.setOnClickListener(v -> {
            if (slidingRootNav.isMenuOpened()) {
                slidingRootNav.closeMenu();
                Util.getInstance().changeStatusBarColor(this, getResources().getColor(R.color
                        .colorPrimary), false);
            } else {
                slidingRootNav.openMenu();
                Util.getInstance().changeStatusBarColor(this, getResources().getColor(R.color
                        .colorAccent), true);
            }
        });
    }

    private void setMenuRecyclerView(List<AppMenu> list) {
        MenuAdapter adapter = new MenuAdapter(this, list, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private List<AppMenu> menuList() {
        List<AppMenu> list = new ArrayList<>();
        String[] menuArray;
        if (MySession.getInstance(this).isLogin()) {
            menuArray = getResources().getStringArray(R.array.menu_array_user);
        } else {
            menuArray = getResources().getStringArray(R.array.menu_array_guest);
        }
        for (int i = 0; i < menuArray.length; i++) {
            AppMenu menu = new AppMenu();
            menu.setId(i);
            menu.setName(menuArray[i]);
            list.add(menu);
        }
        return list;
    }

    private void onClickLoggedUser(AppMenu appMenu){
        if (appMenu.getId()==0){//Home
            showFragment(new HomeFragment());
        }else if (appMenu.getId()==1) {//Food
            Intent intent=new Intent(this, CategoryActivity.class);
            intent.putExtra("productType", CategoryActivity.ProductType.FOOD);
            startActivity(intent);
        }else if (appMenu.getId()==2) {//Fashion
            Intent intent=new Intent(this, CategoryActivity.class);
            intent.putExtra("productType", CategoryActivity.ProductType.FASHION);
            startActivity(intent);
        }else if (appMenu.getId()==3) {//My Account
            startActivity(new Intent(this, MyAccountActivity.class));
        }else if (appMenu.getId()==4) {//My Order
            showFragment(new MyOrderFragment());
        }else if (appMenu.getId()==5) {//Wishlist
            showFragment(new WishListFragment());
        }else if (appMenu.getId()==6){//Support
            showFragment(new SupportFragment());
        }else if (appMenu.getId()==7) {//About us
            aboutUs();
        }else if (appMenu.getId()==8) {//Privacy Policy
            privacyPolicy();
        }else if (appMenu.getId()==9) {//Logout
            logout();
        }
        if (slidingRootNav.isMenuOpened()){
            slidingRootNav.closeMenu();
        }
    }

    private void onClickGuestUser(AppMenu appMenu){
        if (appMenu.getId()==0){//Home
            showFragment(new HomeFragment());
        }else if (appMenu.getId()==1) {//Food
            Intent intent=new Intent(this, CategoryActivity.class);
            intent.putExtra("productType", CategoryActivity.ProductType.FOOD);
            startActivity(intent);
        }else if (appMenu.getId()==2) {//Fashion
            Intent intent=new Intent(this, CategoryActivity.class);
            intent.putExtra("productType", CategoryActivity.ProductType.FASHION);
            startActivity(intent);
        }else if (appMenu.getId()==3){//Support
            showFragment(new SupportFragment());
        }else if (appMenu.getId()==4) {//About us
            aboutUs();
        }else if (appMenu.getId()==5) {//Privacy Policy
            privacyPolicy();
        }else if (appMenu.getId()==6) {//Track Order
            showFragment(new TrackOrderFragment());
        }else if(appMenu.getId()==7){//Change Language
            Intent intent=new Intent(this, LanguageSelectionActivity.class);
            intent.putExtra("changelanguagevia", LanguageSelectionActivity.ChangeLanguageVia.PROFILE);
            startActivity(intent);
        }else if (appMenu.getId()==8) {//Login
            startActivity(new Intent(this, LoginActivity.class));
        }
        if (slidingRootNav.isMenuOpened()){
            slidingRootNav.closeMenu();
        }
    }

    public void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_layout, fragment)
                .commit();
    }

    private void aboutUs(){
        Intent intent=new Intent(this, StaticContentActivity.class);
        intent.putExtra("title",getString(R.string.about_us));
        if (MySession.getInstance(this).getLanguageKey().equals(getString(R.string
                .ar))) {
            intent.putExtra("url",getString(R.string.about_us_url_ar));
        }else{
            intent.putExtra("url",getString(R.string.about_us_url_en));
        }
        startActivity(intent);
    }

    private void privacyPolicy(){
        Intent intent=new Intent(this, StaticContentActivity.class);
        intent.putExtra("title",getString(R.string.privacy_policy));
        if (MySession.getInstance(this).getLanguageKey().equals(getString(R.string
                .ar))) {
            intent.putExtra("url",getString(R.string.privacy_policy_url_ar));
        }else{
            intent.putExtra("url",getString(R.string.privacy_policy_url_en));
        }
        startActivity(intent);
    }

    private void logout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getString(R.string.logout_alert));
        alertDialogBuilder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        MySession.getInstance(MainActivity.this).clearLoginSession();
                        setMenuRecyclerView(menuList());
                        showFragment(new HomeFragment());
                        binding.setCartCount(String.valueOf(MySession.getInstance(MainActivity.this)
                                .getCartCount
                                ()));
                    }
                });

        alertDialogBuilder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
