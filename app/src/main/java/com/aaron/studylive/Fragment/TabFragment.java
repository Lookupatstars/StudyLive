package com.aaron.studylive.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aaron.studylive.MainActivity;
import com.aaron.studylive.R;
import com.aaron.studylive.utils.L;

public class TabFragment extends Fragment {

    private static final String BUNDLE_KEY_TITLE = "key_title";

    private TextView mTvTitle;
    //需要一个外部传入的title变量,即本TabFragment可能显示的内容不同，比如课程，问答，我的等
    private String mTitle;

    //把Fragment的操作暴露给Activity，并回调
    //回调函数
    public static interface OnTitleClickListener{
        void OnClick(String title);
    }
    //回调函数成员变量
    private OnTitleClickListener mListener;
    //公开的成员函数
    public void setOnTitleClickListener(OnTitleClickListener listener){
        mListener = listener;
    }


    //接收从外部传进来的key，完成本Fragment中title的创建
    //把获得的值传递给mTitle
    //合理使用newInstance，要用Argument保存数据
    public static TabFragment newInstance(String title){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_TITLE,title);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);//使用Argument传递参数，Argument在应用恢复布局过程中要优于直接设置tabFragment.mTitle="xxx"
        return tabFragment;
    }
    //从newInstance的Argument里边拿到参数
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments != null){
            mTitle = arguments.getString(BUNDLE_KEY_TITLE,"");
        }
    }//end 完成了从外部传入一个title，内部获取的功能

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab,container,false); //返回所在的xml文件
    }

    //创建完成之后的方法重写
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //完成本view的一些操作
        super.onViewCreated(view, savedInstanceState);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText(mTitle);//把获得title值赋给xml显示

        mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取activity的对象
                //强转，直接调用，容易崩溃
//                MainActivity activity = (MainActivity)getActivity();
//                activity.changeHomeTab("主页改变了");
                //先判断，解决了崩溃的问题但是不适合多个Activity，要去预测是哪个Activity调用了
//                Activity activity = getActivity();
//                if(activity instanceof MainActivity){
//                    ((MainActivity)activity).changeHomeTab("主页改变了");
//                }
                //考虑问题在于：Fragment会触发一些事件，我们应该让activity去响应这些事件。
                //提供一个发消息的机制给activity
                //Fragment调用Activity，换个思路：不是Fragment调用activity的方法，而是fragment自己对外
                // 提供自己的核心事件回调，activity自己决定是否要监听
                if(mListener!=null){
                    mListener.OnClick("主页改变了");
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //activity调用fragment的函数方法：拿到Fragment的.xxx方法
    public void changeTitle(String title){
        if (!isAdded()){//约束，防止被修改私有变量
            return;
        }
        mTvTitle.setText(title);
    }
}
