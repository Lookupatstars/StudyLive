package com.aaron.studylive.fragment;

import android.app.ProgressDialog;
import androidx.fragment.app.Fragment;

/**
 * Created by Aaron on 2020/4/6
 * The current project is StudyLive
 *
 * @Describe:  请求数据的碎片类
 */
public class ClassContentFragment extends Fragment {
    private ProgressDialog progressDialog;


    //从服务器获取数据
    private void getDataFromServer(String address , final String type){
        ShowProgressDialog();
//        HttpUtil.sendOkHttpRequest(address, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//            }
//        });
    }

    //    显示进度对话框
    private void ShowProgressDialog(){
        if (progressDialog ==null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    //    显示进度对话框
    private void CloseProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }

}
