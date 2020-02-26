package com.aaron.studylive;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aaron.studylive.Fragment.TabFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mVpMain;
    private List<String> mTitles = new ArrayList<>(Arrays.asList("首页","问答","我的课程","我的"));

    //底部导航栏按钮声明
    private Button mBtnHome;
    private Button mBtnDiscuss;
    private Button mBtnCourse;
    private Button mBtnMine;

    private List<Button> mTabs = new ArrayList<>();

    private SparseArray<TabFragment> mFragment = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initPagerAdapter();


    }

    private void initPagerAdapter() {

        mVpMain.setOffscreenPageLimit(mTitles.size());
        mVpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {//这里的getItem函数实际上还是一个create的作用(fragment调用activity
                TabFragment fragment = TabFragment.newInstance(mTitles.get(i));//获得List中的每个值
                if(i==0){
                    fragment.setOnTitleClickListener(new TabFragment.OnTitleClickListener() {
                        @Override
                        public void OnClick(String title) {
                            changeHomeTab(title);//调用activity的方法
                        }
                    });
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return mTitles.size();//List中的数量
            }

            //界面上有几个fragment，就能接收到几个
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                TabFragment fragment = (TabFragment) super.instantiateItem(container, position);
                mFragment.put(position,fragment);
                return fragment;
            }
            //当fragment被销毁时，及时remove
            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                mFragment.remove(position);
                super.destroyItem(container, position, object);
            }
        });

        mVpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //左边->右边 0—>1, left pos ,right pos + 1, posotionOffset 0~1
                //left progress:1~0 (1-positionOffset); right progress: 0~1 positionOffset；

                //右→左 1→0， left pos ， right pos +1， positionOffset 1~0
                //left progress:0~1 (1 - positionOffset); right progress: 1~0. positionOffset；

                //left tab → (1 - positionOffset)    right tab→positionOffset
                if(positionOffset>0){
                    Button left = mTabs.get(position);
                    Button right = mTabs.get(position+1);
                    left.setText((1-positionOffset)+"");
                    right.setText(positionOffset+"");
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initView() {
        mVpMain = findViewById(R.id.vp_main);
        mBtnHome = findViewById(R.id.btn_home);
        mBtnDiscuss = findViewById(R.id.btn_discuss);
        mBtnCourse = findViewById(R.id.btn_course);
        mBtnMine = findViewById(R.id.btn_mine);

        mTabs.add(mBtnHome);
        mTabs.add(mBtnDiscuss);
        mTabs.add(mBtnCourse);
        mTabs.add(mBtnMine);


        //activity 调用Fragment的changeTitle的方法
        mBtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取第一个Fragment
                TabFragment tabFragment = mFragment.get(0);
                if(tabFragment!=null){
                    tabFragment.changeTitle("改变了");//改变当前点击的button的相对应的内容
                }
            }
        });
    }

    //Fragment调用Activity的方法
    //通过这种方法，只需要在每个activity中添加对fragment的事件的响应就可以了，而TabFragment的方法 不用改变
    public void changeHomeTab(String title){
        mBtnHome.setText(title);
    }

}
