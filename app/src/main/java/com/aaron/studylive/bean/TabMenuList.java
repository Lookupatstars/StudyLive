package com.aaron.studylive.bean;

import com.aaron.studylive.R;
import com.aaron.studylive.utils.L;

import java.security.acl.Acl;
import java.util.ArrayList;

/**
 * Created by Aaron on 2020/4/9
 * The current project is StudyLive
 *
 * @Describe: String 类型的tabMenulist
 */
public class TabMenuList {

    public static ArrayList<String> getDefault() {
        ClassMenu classMenu = new ClassMenu();
        L.d("ArrayList<String> getDefault()  中的classMenu.content.size()：："+classMenu.content.size());
        ArrayList<String> tabMenuList = new ArrayList<String>();
        for (int i=0;i< classMenu.content.size();i++){
            tabMenuList.add(classMenu.content.get(i).name);
        }
        return tabMenuList;
    }

}
