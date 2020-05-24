package com.aaron.studylive.activitys;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aaron.studylive.R;
import com.aaron.studylive.bean.MediaData;
import com.aaron.studylive.fragments.CourseCommentFragment;
import com.aaron.studylive.fragments.CourseIntroFragment;
import com.aaron.studylive.fragments.CpFragment;
import com.aaron.studylive.utils.ActivityCollector;
import com.aaron.studylive.utils.HttpRequest;
import com.aaron.studylive.utils.HttpUrl;
import com.aaron.studylive.utils.L;
import com.aaron.studylive.video.LandLayoutVideo;
import com.aaron.studylive.views.NoScrollViewPager;
import com.google.android.exoplayer2.SeekParameters;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import butterknife.Bind;
import butterknife.ButterKnife;
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;

/**
 * Created by Aaron on 2020/4/27
 * The current project is StudyLive
 *
 * @Describe:   详情页播放  有一个章节界面 cpfragment
 */

public class DetailPlayerActivity extends AppCompatActivity implements View.OnClickListener,
        CpFragment.OnFragmentInteractionListener {
    //视频外的外层布局
    @Bind(R.id.post_detail_nested_scroll)
    NestedScrollView postDetailNestedScroll;

    //内容布局
    @Bind(R.id.vp_cp_content)
    NoScrollViewPager vpCpContent;

    //三个Relativity的Tab标签
    @Bind(R.id.tab_cp)
    RelativeLayout tabCp;

    @Bind(R.id.tab_intro)
    RelativeLayout tabIntro;

    @Bind(R.id.tab_comment)
    RelativeLayout tabComment;

    //三个TextView的title字体标签
    @Bind(R.id.tv_cp)
    TextView tvCp;

    @Bind(R.id.tv_intro)
    TextView tvIntro;

    @Bind(R.id.tv_comment)
    TextView tvComment;

    //推荐使用StandardGSYVideoPlayer，功能一致
    //CustomGSYVideoPlayer部分功能处于试验阶段
    @Bind(R.id.detail_player)
    LandLayoutVideo detailPlayer;

    //整个Activity最外层的布局
    @Bind(R.id.activity_detail_player)
    RelativeLayout activityDetailPlayer;

    //接收传来的数据
    private int mCourseId;
    private int classType;
    private String mTitle;  //课程名
    private String summary; //课程简介
    private int numberOfLession;
    private List<MediaData> mMediaDatas;

    private boolean isPlay;
    private boolean isPause;
    private int position = 0;
    OrientationUtils orientationUtils;

    private static final String homeUrl = "http://course-api.zzu.gdatacloud.com:890/";

    private List<Fragment> mFragments; //三个Tab的初始化fragment
    private CpFragment mCpFragment;
    private CourseCommentFragment mCommentFragmet;
    private CourseIntroFragment mIntroFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_player);
        ButterKnife.bind(this);

        ActivityCollector.addActivity(this);

        //获取上层传下来的数据
        mCourseId = getIntent().getIntExtra("id",0);
        mTitle = getIntent().getStringExtra("title");
        summary = getIntent().getStringExtra("summary");
        classType = getIntent().getIntExtra("type",1);
        numberOfLession = getIntent().getIntExtra("lessionNum",1);


        L.d("summaryDetail =  "+summary);
        //1.创建Fragment的管理对象
        FragmentManager fragmentManager = getSupportFragmentManager();
        //2.获取Fragment的事务对象并且开启事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //3.调用事务中动态操作fragment的方法执行 add(添加到哪里，需要添加的fragment对象);
        fragmentTransaction.add(R.id.vp_cp_content, new CpFragment());
        //4.提交事务
        fragmentTransaction.commit();

        mMediaDatas = new ArrayList<>();
        new MediaAsyncTask().execute();
        setupViewPager(); //初始化章节、评论、详情Tab

    }

    @Override
    public void onFragmentInteraction(int s) {
//        tvMain.setText(s);
        position = s;

        L.d("回调之后的执行："+position);
        new MediaAsyncTask().execute();
    }


    private class MediaAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String url = HttpUrl.getInstance().getMediaInfo(mCourseId);
            L.d("DetailPlayerActivity ->  MediaAsyncTask + url = "+url);
            return HttpRequest.getInstance().GET(DetailPlayerActivity.this,url,null);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            analysisJsonData(s);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void analysisJsonData(String s) {

        L.d("DetailPlayerActivity ->  analysisJsonData + s = "+s);

        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("code");
            if (errorCode == 0) {
                JSONArray array =object.getJSONObject("content").getJSONArray("records");
                L.d("DetailPlayerActivity ->  analysisJsonData + array.length = "+array.length());
                for (int i = 0; i< array.length(); i++){
                    MediaData mMediaData = new MediaData();
                    object = array.getJSONObject(i);

                    L.d("start:::: DetailPlayerActivity ->  analysisJsonData  =  "+i);
                    L.d("看看课程 ID是多少 "+object.getInt("id"));
                    mMediaData.setCourseId(object.getInt("courseId"));
                    mMediaData.setCourseTime(object.getString("courseTime"));
                    mMediaData.setCreateTime(object.getString("createTime"));
                    mMediaData.setDownload(object.getInt("download"));
                    mMediaData.setDownloadCount(object.getInt("downloadCount"));
                    mMediaData.setId(object.getInt("id"));
                    mMediaData.setName(object.getString("name"));
                    mMediaData.setResourceAddress(homeUrl+object.getString("resourceAddress")); //下载资源地址
                    mMediaData.setResourceAddress2(homeUrl+object.getString("resourceAddress2"));  //播放资源地址
                    mMediaData.setStatus(object.getInt("status"));
                    mMediaData.setSummary(object.getString("summary"));
                    mMediaData.setUpdateTime(object.getString("updateTime"));
                    mMediaData.setUserId(object.getInt("userId"));
                    mMediaData.setViewCount(object.getInt("viewCount"));
                    mMediaData.setViewPermissions(object.getInt("viewPermissions"));

                    L.d("end:::: DetailPlayerActivity ->  analysisJsonData  =  ");
                    mMediaDatas.add(mMediaData);
                }
                setupPlay();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void setupPlay() {
        try {
            String url = mMediaDatas.get(position).getResourceAddress();

//        //增加封面
//        ImageView imageView = new ImageView(this);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(R.drawable.nothumb);

            resolveNormalVideoUI();

            //外部辅助的旋转，帮助全屏
            orientationUtils = new OrientationUtils(this, detailPlayer);
            //初始化不打开外部的旋转
            orientationUtils.setEnable(false);

            Map<String, String> header = new HashMap<>();
            header.put("ee", "33");
//            header.put("allowCrossProtocolRedirects", "true");
            GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
            gsyVideoOption.setUrl(url)
//                .setThumbImageView(imageView)

                    .setIsTouchWiget(true)
                    .setRotateViewAuto(false)
                    .setRotateWithSystem(true)
                    .setLockLand(false)
                    .setShowFullAnimation(false)
                    .setNeedLockFull(true)
                    .setMapHeadData(header)
                    .setVideoTitle(mTitle)
                    .setVideoAllCallBack(new GSYSampleCallBack() {
                        @Override
                        public void onPrepared(String url, Object... objects) {
                            Debuger.printfError("***** onPrepared **** " + objects[0]);
                            Debuger.printfError("***** onPrepared **** " + objects[1]);
                            super.onPrepared(url, objects);
                            //开始播放了才能旋转和全屏
                            orientationUtils.setEnable(true);
                            isPlay = true;

                            //设置 seek 的临近帧。
                            if(detailPlayer.getGSYVideoManager().getPlayer() instanceof Exo2PlayerManager) {
                                ((Exo2PlayerManager) detailPlayer.getGSYVideoManager().getPlayer()).setSeekParameter(SeekParameters.NEXT_SYNC);
                                Debuger.printfError("***** setSeekParameter **** ");
                            }
                        }

                        @Override
                        public void onEnterFullscreen(String url, Object... objects) {
                            super.onEnterFullscreen(url, objects);
                            Debuger.printfError("***** onEnterFullscreen **** " + objects[0]);//title
                            Debuger.printfError("***** onEnterFullscreen **** " + objects[1]);//当前全屏player
                        }

                        @Override
                        public void onAutoComplete(String url, Object... objects) {
                            super.onAutoComplete(url, objects);
                        }

                        @Override
                        public void onClickStartError(String url, Object... objects) {
                            super.onClickStartError(url, objects);
                        }

                        @Override
                        public void onQuitFullscreen(String url, Object... objects) {
                            super.onQuitFullscreen(url, objects);
                            Debuger.printfError("***** onQuitFullscreen **** " + objects[0]);//title
                            Debuger.printfError("***** onQuitFullscreen **** " + objects[1]);//当前非全屏player
                            if (orientationUtils != null) {
                                orientationUtils.backToProtVideo();
                            }
                        }
                    })
                    .setLockClickListener(new LockClickListener() {
                        @Override
                        public void onClick(View view, boolean lock) {
                            if (orientationUtils != null) {
                                //配合下方的onConfigurationChanged
                                orientationUtils.setEnable(!lock);
                            }
                        }
                    })
                    .setGSYVideoProgressListener(new GSYVideoProgressListener() {
                        @Override
                        public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
                            Debuger.printfLog(" progress " + progress + " secProgress " + secProgress + " currentPosition " + currentPosition + " duration " + duration);
                        }
                    })
                    .build(detailPlayer);

            detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //直接横屏
                    orientationUtils.resolveByClick();

                    //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                    detailPlayer.startWindowFullscreen(DetailPlayerActivity.this, false, true);
                }
            });
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }finally {

        }

    }

    @Override
    public void onBackPressed() {

        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }

        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        getCurPlay().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        getCurPlay().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            getCurPlay().release();
        }
        //GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }

    private void resolveNormalVideoUI() {
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.GONE);
        detailPlayer.getBackButton().setVisibility(View.GONE);
    }

    private GSYVideoPlayer getCurPlay() {
        if (detailPlayer.getFullWindowPlayer() != null) {
            return  detailPlayer.getFullWindowPlayer();
        }
        return detailPlayer;
    }

    /**
     *
     * 章节开始的地方
     */
    //添加Fragment
    private void addFragments() {
        mFragments = new ArrayList<>();

        mCpFragment = new CpFragment();
        mIntroFragment = new CourseIntroFragment();
        mCommentFragmet = new CourseCommentFragment();

        //吧数据传递到fragment
        Bundle bundle = new Bundle();
        bundle.putInt("CourseId",mCourseId);
        bundle.putString("title",mTitle);
        bundle.putString("summary",summary);
        bundle.putInt("type",classType);
        bundle.putInt("lessionNum",numberOfLession);
        mCpFragment.setArguments(bundle);
        mIntroFragment.setArguments(bundle);
        mCommentFragmet.setArguments(bundle);

        mFragments.add(mCpFragment);
        mFragments.add(mIntroFragment);
        mFragments.add(mCommentFragmet);

    }

    //显示fragment
    @SuppressWarnings("deprecation")
    private void setupViewPager() {
        addFragments();

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        vpCpContent.setAdapter(adapter);
        vpCpContent.setOffscreenPageLimit(mFragments.size());
        vpCpContent.setOnPageChangeListener(pagerChangeListener());
        vpCpContent.setCurrentItem(0);
        setupTabClick();

    }

    ////界面监听，当界面改变的时候，进行设置
    private  ViewPager.OnPageChangeListener pagerChangeListener(){

        vpCpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                clearTabBackgroundWithTextColor();  //每次点击之前，清除上次的点击
                switch (position){
                    case 0:
                        tvCp.setTextColor(getResources().getColor(R.color.tab_text_selected));
                        break;
                    case 1:
                        tvIntro.setTextColor(getResources().getColor(R.color.tab_text_selected));
                        break;
                    case 2:
                        tvComment.setTextColor(getResources().getColor(R.color.tab_text_selected));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return null;
    }


    //每次点击之前，清除上次的点击
    private void clearTabBackgroundWithTextColor() {
        tvCp.setTextColor(getResources().getColor(R.color.tab_text_normal));
        tvIntro.setTextColor(getResources().getColor(R.color.tab_text_normal));
        tvComment.setTextColor(getResources().getColor(R.color.tab_text_normal));
    }

    //Tab的每次点击事件
    private void setupTabClick() {
        L.d("setupTabClick   = 被执行了 ！！");
        tabCp.setOnClickListener(this);
        L.d("setupTabClick   = 被执行了1");
        tabIntro.setOnClickListener(this);
        L.d("setupTabClick   = 被执行了2");
        tabComment.setOnClickListener(this);
        L.d("setupTabClick   = 被执行了3");
    }

    //当点击到对应的Tab的时候，显示对应的界面
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_cp:
                L.d("点击了 setCurrentItem(0) ");
                vpCpContent.setCurrentItem(0);
                break;
            case R.id.tab_intro:
                L.d("点击了 setCurrentItem(1) ");
                vpCpContent.setCurrentItem(1);
                break;
            case R.id.tab_comment:
                L.d("点击了 setCurrentItem(2) ");
                vpCpContent.setCurrentItem(2);
                break;
        }
    }


}
