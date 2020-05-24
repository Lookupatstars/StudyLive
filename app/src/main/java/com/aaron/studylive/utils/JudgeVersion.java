package com.aaron.studylive.utils;

/**
 * Created by Aaron on 2020/5/14
 * The current project is StudyLive
 *
 * @Describe:
 */
public class JudgeVersion {
    /**
     * 大家都知道，版本号一般由以下几部分组成：
     1.  主版本号
     2.  次版本号
     3.  修正版本号
     4.  编译版本号
     * @param version1
     * @param version2
     * @return 0代表相等，1代表version1大于version2，-1代表version1小于version2
     * @throws Exception
     */
    public static int compareVersion(String version1, String version2) throws Exception {

        if (version1.equals(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");

        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小
        while (index < minLen
                && (diff = Integer.parseInt(version1Array[index])
                - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }
}
