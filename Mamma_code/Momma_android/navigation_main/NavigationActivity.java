package momma_beta.momma_bv.navigation_main;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

import momma_beta.momma_bv.Brand.BrandFragment;
import momma_beta.momma_bv.Event.EventFragment;
import momma_beta.momma_bv.Model.FilterReviewInfo;
import momma_beta.momma_bv.MyPage.F_BrandFragment;
import momma_beta.momma_bv.MyPage.F_ProductFragment;
import momma_beta.momma_bv.MyPage.F_ReviewFragment;
import momma_beta.momma_bv.MyPage.MyPageEditFragment;
import momma_beta.momma_bv.MyPage.MyPageFragment;
import momma_beta.momma_bv.MyPage.My_ReviewFragment;
import momma_beta.momma_bv.Parenting.ParentingFragment;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.Review.ReviewDetailFragment;
import momma_beta.momma_bv.Review.ReviewFilteringFragment;
import momma_beta.momma_bv.Review.ReviewFragment;
import momma_beta.momma_bv.Review.ReviewWriteFragment;
import momma_beta.momma_bv.Search.SearchFragment;
import momma_beta.momma_bv.Search.Search_Result_Fragment;
import momma_beta.momma_bv.Splash_Login_SignUp.LoginActivity;
import momma_beta.momma_bv.home.HomeFragment;
import momma_beta.momma_bv.product.ProductDetailFragment;
import momma_beta.momma_bv.product.ProductFilteringFragment;
import momma_beta.momma_bv.product.ProductFragment;

import static momma_beta.momma_bv.R.id.profile;
import static momma_beta.momma_bv.R.id.search_btn;
import static momma_beta.momma_bv.R.id.searchbar_layout;


@SuppressLint({"NewApi", "RtlHardcoded"})
public class NavigationActivity extends MasterActivity {

    private ListView mDrawerList;
    private ImageButton menu;
    public ImageButton back;
    private String[] navMenuTitles;
    private DrawerListAdapter adapter;
    private View mHeader;
    public TextView title_text;
    public ImageView title_image;
    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    public static int flag = 1;
    private EditText search_txt;
    SharedPreferences auto;
    SharedPreferences.Editor autoLogin;
    UserManagement userManagement;
    private ArrayList<NavDrawerItem> navDrawerItems;
    DrawerLayout mDrawerLayout;
    public RelativeLayout titlerl,searchbar;
    ActionBarDrawerToggle drawerToggle;
    ImageButton search1;
    CircularImageView profile2;
    ImageButton search_back_btn;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        back = (ImageButton) findViewById(R.id.back_btn);
        menu = (ImageButton) findViewById(R.id.navigation_btn);
        title_text = (TextView) findViewById(R.id.title_text);
        title_image = (ImageView) findViewById(R.id.title_image);
        search1 = (ImageButton)findViewById(search_btn);
        mDrawerList = (ListView) findViewById(R.id.drawer_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.END);
        mDrawerList.setItemChecked(0, true);
        titlerl = (RelativeLayout)findViewById(R.id.titlebar_layout);
        searchbar = (RelativeLayout)findViewById(searchbar_layout);
        search_txt = (EditText)findViewById(R.id.search_text);
        search_back_btn = (ImageButton)findViewById(R.id.search_back_btn);

        menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // toggleMenu(v);
                if (mDrawerLayout.isDrawerVisible(Gravity.RIGHT))
                {
                    title_image.setVisibility(View.VISIBLE);
                    return;
                } else
                {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerVisible(Gravity.RIGHT))
                {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);

                }
                else
                {
                    Log.i("mytag","backpressed");
                    NavigationActivity.this.onBackPressed();
                }

            }
        });

        search_back_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                changeFragment(1,null);
            }
        });
        // Custom Header ...
        mHeader = getLayoutInflater().inflate(R.layout.navigation_list_header,
                mDrawerList, false);

        ImageView headerline = (ImageView) mHeader.findViewById(R.id.headerline);
        profile2 = (CircularImageView)mHeader.findViewById(profile);

        auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        String profileImg = auto.getString("profile",null);
        Glide.with(getApplicationContext())
                .load(profileImg)
                .into(profile2);

        final ImageView search = (ImageView) mHeader.findViewById(R.id.search);

        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(18,null);
            }
        });

        search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment search_result_fragment = new Search_Result_Fragment();
                ReplaceFragement(search_result_fragment);
                Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
                bundle.putString("milkname", search_txt.getText().toString()); // key , value
                search_result_fragment.setArguments(bundle);
                search_txt.setText("");
            }
        });

        mDrawerList.addHeaderView(mHeader);
        // Custom Header End...

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_title);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6]));



        mDrawerList.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                Log.i("mytag","ddd");
//                titlerl.setVisibility(View.VISIBLE);
                searchbar.setVisibility(View.GONE);
                title_text.setText("");
                title_image.setVisibility(View.VISIBLE);
                changeFragment(position,null);
                mDrawerLayout.closeDrawers();

            }
        });

        adapter = new DrawerListAdapter(NavigationActivity.this, navDrawerItems);
        mDrawerList.setAdapter(adapter);

        if (savedInstanceState == null)
        {
            Fragment landingFragment = new HomeFragment();
            ReplaceFragement(landingFragment);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String profileImg = auto.getString("profile",null);
        Glide.with(getApplicationContext())
                .load(profileImg)
                .into(profile2);
    }

    public void changeFragment(int index, ArrayList<FilterReviewInfo> data) {
        switch (index)
        {
            // MAIN ...
            case 1:
                Fragment homeFramgnet = new HomeFragment();
                showActionbar();
                title_text.setText("");
                titlerl.setVisibility(View.VISIBLE);
                title_image.setVisibility(View.VISIBLE);
                this.ReplaceFragement(homeFramgnet);
                flag =1;
                Log.i("mytag","indicator flag"+flag);
                break;
            case 2:
                Fragment mypagefragment = new MyPageFragment();
                this.ReplaceFragement(mypagefragment);
                showActionbar();
                title_text.setText("마이페이지");
                flag =100;
                Log.i("mytag","indicator flag"+flag);
                break;
            case 3:
                Fragment productFragment = new ProductFragment();
                Bundle bundle1 = new Bundle(1); // 파라미터는 전달할 데이터 개수
                bundle1.putSerializable("productinfo", data); // key , value
                productFragment.setArguments(bundle1);
                this.ReplaceFragement(productFragment);
                showActionbar();
                title_text.setText("제품");
                flag =100;
                Log.i("mytag","indicator flag"+flag);
                break;
            case 4:
                Fragment reviewFragment = new ReviewFragment();
                showActionbar();
                title_text.setText("리뷰");
                Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
                bundle.putSerializable("reviewinfo", data); // key , value
                reviewFragment.setArguments(bundle);
                this.ReplaceFragement(reviewFragment);
                flag =100;
                Log.i("mytag","indicator flag"+flag);
                break;
            case 5:
                Fragment parentingFragment = new ParentingFragment();
                this.ReplaceFragement(parentingFragment);
                showActionbar();
                title_text.setText("육아 정보");
                flag =100;
                Log.i("mytag","indicator flag"+flag);
                break;
            case 6:
                Fragment eventFragment = new EventFragment();
                this.ReplaceFragement(eventFragment);
                showActionbar();
                title_text.setText("이벤트");
                flag =100;
                Log.i("mytag","indicator flag"+flag);
                break;
            case 7:
                userManagement.requestLogout(new LogoutResponseCallback()
                {
                    @Override
                    public void onCompleteLogout()
                    {}
                });
                LoginManager.getInstance().logOut();
                auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                autoLogin = auto.edit();
                autoLogin.clear();
                autoLogin.commit();
                Intent mainpage = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(mainpage);
                Log.i("mytag","indicator flag"+flag);
                break;
            case 8:
                Fragment f_productFragement = new F_ProductFragment();
                this.ReplaceFragement(f_productFragement);
                showActionbar();
                title_text.setText("즐겨찾기-제품");
                flag =2;
                Log.i("mytag","indicator flag"+flag);
                break;
            case 9:
                Fragment f_reviewFragement = new F_ReviewFragment();
                this.ReplaceFragement(f_reviewFragement);
                showActionbar();
                title_text.setText("즐겨찾기-리뷰");
                flag =2;
                Log.i("mytag","indicator flag"+flag);
                break;
            case 10:
                Fragment my_reviewFragment = new My_ReviewFragment();
                this.ReplaceFragement(my_reviewFragment);
                showActionbar();
                title_text.setText("내 리뷰");
                flag =2;
                Log.i("mytag","indicator flag"+flag);
                break;
            case 11:
                Fragment myPageEditFragment = new MyPageEditFragment();
                this.ReplaceFragement(myPageEditFragment);
                showActionbar();
                title_text.setText("마이페이지 수정");
                flag =2;
                Log.i("mytag","indicator flag"+flag);
                break;
            case 12:
                Fragment filtering_review = new ReviewFilteringFragment();
                this.ReplaceFragement(filtering_review);
                showActionbar();
                title_text.setText("필터링");
                flag =5;
                Log.i("mytag","indicator flag"+flag);
                break;
            case 13:
                Fragment filtering_product = new ProductFilteringFragment();
                this.ReplaceFragement(filtering_product);
                showActionbar();
                title_text.setText("필터링");
                flag =3;
                Log.i("mytag","indicator flag"+flag);
                break;
            case 14:
                Fragment WriteResult = new ReviewWriteFragment();
                this.ReplaceFragement(WriteResult);
                showActionbar();
                title_text.setText("필터링");
                flag =11;
                Log.i("mytag","indicator flag"+flag);
                break;
            case 15:
                Fragment ReviewResult = new ReviewDetailFragment();
                this.ReplaceFragement(ReviewResult);
                showActionbar();
                title_text.setText("리뷰 상세보기");
                flag =5;
                Log.i("mytag","indicator flag"+flag);
                break;
            case 16:
                Fragment F_Brand = new F_BrandFragment();
                this.ReplaceFragement(F_Brand);
                showActionbar();
                title_text.setText("즐겨찾기-브랜드");
                flag =2;
                Log.i("mytag","indicator flag"+flag);
                break;
            case 17:
                Fragment search = new BrandFragment();
                this.ReplaceFragement(search);
                showActionbar();
                title_text.setText("회사 정보");
                flag =100;
                Log.i("mytag","indicator flag"+flag);
                break;
            case 18:
                Fragment searchFragment = new SearchFragment();
                this.ReplaceFragement(searchFragment);
                showSearchbar();
                flag = 100;
                Log.i("mytag","indicator flag"+flag);
                break;
            case 19:
                Fragment prodetail = new ProductDetailFragment();
                this.ReplaceFragement(prodetail);
                showSearchbar();
                title_text.setText("제품");
                flag = 11;
            default:
                Fragment homeFramgnet2 = new HomeFragment();
                ReplaceFragement(homeFramgnet2);
                showActionbar();
                title_image.setVisibility(View.INVISIBLE);
                flag =1;
                Log.i("mytag","indicator flag"+flag);
                break;
        }
    }
    @Override
    public void onBackPressed()
    {
        long tempTime        = System.currentTimeMillis();
        long intervalTime    = tempTime - backPressedTime;
        Log.i("mytag","indicator flag"+flag);
        if (mDrawerLayout.isDrawerVisible(Gravity.RIGHT))
        {
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
        }
        else
        {
            if (flag == 1) {

                if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime)
                {
                    finish();
                }
                else
                {
                    backPressedTime = tempTime;
                    Toast.makeText(getApplicationContext(), "뒤로 가기 키을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
                }
            }
            else if(flag ==100){
                changeFragment(1,null);
            }

        }
    }

    public void ReplaceFragement(Fragment fragment)
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (mDrawerLayout.isDrawerVisible(Gravity.RIGHT)) {
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
        }
    }
    private void showActionbar()
    {
        title_image.setVisibility(View.INVISIBLE);
        searchbar.setVisibility(View.GONE);
    }
    private void showSearchbar()
    {
        titlerl.setVisibility(View.GONE);
        searchbar.setVisibility(View.VISIBLE);
        mDrawerLayout.closeDrawer(Gravity.RIGHT);
    }
}