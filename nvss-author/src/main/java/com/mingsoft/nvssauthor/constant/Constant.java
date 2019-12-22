package com.mingsoft.nvssauthor.constant;

/**
 * @author xq
 */
public class Constant {

    public static String USER_PTZ_LEVEL = "1";
    public static String USER_AV_LEVEL = "2";
    public static String PRESET_FLAG = "3";
    public static String GROUPS_TYPE = "4";
    public static String USER_GROUPS_RIGHT_TYPE = "5";
    public static String ACTION_TYPE = "6";
    public static String ALARM_TYPE = "7";
    public static String SYSTEM_PARAM = "8";
    public static String TB_DEPARTMENT = "9";
    public static String TB_USER_TYPE = "10";
    public static String ALARM_LEVEL = "11";
    /**
     * 相机类型
     */
    public static String CAMERA_TYPE = "12";

    public static String OPERATION_TYPE = "13";

    public static String OPERATION_LOG = "14";
    public final static String SERVER_TYPE_CMS = "cms";
    public final static String SERVER_TYPE_DMS = "dms";
    public final static String SERVER_TYPE_MSS = "mss";
    public final static String CACHE_TYPE = "cacheType";
    /**
     * 固定摄像机
     */
    public final static String FIXED_CAMERA = "1201";

    /**
     * 云台摄像机
     */
    public final static String PTZ_CAMERA = "1202";
    /**
     * 固定IP摄像机
     */
    public final static String FIXED_IP_CAMERA = "1203";
    /**
     * 云台IP摄像机
     */
    public final static String PTZ_IP_CAMERA = "1204";


    /**
     *============== 浩瀚云存储=======================
     */

    /**
     * 总容量
     */
     public static final String DISK_MEMORY_ALL = "1.3.6.1.4.1.3470.12.1.2.1.0";

     public static final String DISK_MEMORY_USED = "1.3.6.1.4.1.3470.12.1.2.2.0";

     public static final String DISK_COUNT = "1.3.6.1.4.1.3470.12.1.3.2.0";

    /**
     * CPU温度
     */
    public static final String CPU_TEMPERATURE = "1.3.6.1.4.1.3470.12.1.4.1.0";

    /**
     * CPU使用率
     */
    public static final String CPU_USAGE_RATE = "1.3.6.1.4.1.3470.12.1.4.2.0";

    /**
     * 风扇低于阈值
     */

    public static final String  FS_BELOW_THRESHOLD = "1.3.6.1.4.1.3470.12.2.2.2.0";

    /**
     * 拔盘警告
     */
    public static final String  DISK_PULL_ALARM = "1.3.6.1.4.1.3470.12.2.2.4.0";

    /**
     * ============== 告警类型 ===============================================
     */

    /**
     * 节点进程掉线
     */
    public static final String ALARM_TYPE_PD = "pd";
    /**
     * 磁盘损坏
     */
    public static final String ALARM_TYPE_DB = "db";

    /**
     * 拔盘告警
     */
    public static final String ALARM_TYPE_DD = "dd";
    /**
     * CPU温度告警
     */
    public static final String ALARM_TYPE_CT = "ct";

    /**
     * 风扇转速
     */
    public static final String CPU_TYPE_FS = "fs";


}
