package com.aaron.studylive.fragment;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.aaron.studylive.R;
import com.aaron.studylive.view.NoScrollViewPager;
import com.aaron.studylive.widget.BasePager;
import com.aaron.studylive.widget.implement.CoursePager;
import com.aaron.studylive.widget.implement.ClassPager;
import com.aaron.studylive.widget.implement.HomePager;
import com.aaron.studylive.widget.implement.MinePager;

import java.util.ArrayList;

/**
 * Created by Aaron on 2020/3/29
 * The current project is StudyLive
 *
 * @Describe: 主界面的Fragment
 */
@SuppressWarnings("ALL")
public class ContentFragment extends BaseFragment {

    private NoScrollViewPager mViewPager;
    private RadioGroup rg_group;
    private ArrayList<BasePager> mPagers; //四个标签页的集合

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_content,null);
        mViewPager = view.findViewById(R.id.vp_content);
        rg_group = view.findViewById(R.id.rg_group);

        return view;
    }

    @Override
    public void initData() {
        mPagers = new ArrayList<BasePager>();
        //添加四个tab标签页
        mPagers.add(new HomePager(mActivity));
        mPagers.add(new ClassPager(mActivity));
        mPagers.add(new CoursePager(mActivity));
        mPagers.add(new MinePager(mActivity));

        mViewPager.setAdapter(new ContentAdapter());

        //给RadioGroup类添加监听事件 底部标签栏的切换
        rg_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_sy: //首页
//                        mViewPager.setCurrentItem(0);
                        mViewPager.setCurrentItem(0,false); //不平滑滑动
                        break;
                    case R.id.rb_dic://直播课程
                        mViewPager.setCurrentItem(1,false);
                        break;
                    case R.id.rb_kc://我的课程
                        mViewPager.setCurrentItem(2,false);
                        break;
                    case R.id.rb_wd://我的
                        mViewPager.setCurrentItem(3,false);
                        break;
                        default:
                            break;
                }
            }
        });

        //当页面被点击的时候初始化界面
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) { //当界面被选中时，拿到页面，初始化布局
                BasePager pager = mPagers.get(position);
                pager.initData(); //初始化数据
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //默认加载第一个页面
        mPagers.get(0).initData();
    }

    //给vp_content初始化一个Adapter
    class ContentAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject( View view,  Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem( ViewGroup container, int position) {
            BasePager pager = mPagers.get(position);
            View view = pager.mRootView; //初始化base_page的布局,获取当前页面对象的布局
//            pager.initData();// 初始化中间的布局
// 不在这里调用初始化数据，因为ViewPager会自动加载下一个页面的数据，浪费流量
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem( ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    //从主界面获取到直播课的界面 ClassPager
    public ClassPager getClassPager(){
        ClassPager pager = (ClassPager) mPagers.get(1);
        return pager;
    }


}
