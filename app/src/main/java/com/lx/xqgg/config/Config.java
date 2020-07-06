package com.lx.xqgg.config;

import com.lx.xqgg.base.BaseApplication;

import java.io.File;

public class Config {
    public static final String PATH_DATA = BaseApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String HTTP = "https";

    public static final String HOST = "fwpt.hnga.gov.cn";

    public static final int PORT = 443;

//    public static final String URL = HTTP + "://" + HOST + ":" + PORT + "/";

//       // 本地
   public static final String URL = "http://192.168.1.144:8081/xiaoqiguaiguai-mobile/";
//
    public static final String IMGURL = "http://192.168.1.144:8081/xiaoqiguaiguai-mobile/";
    public static final String URLS = "https://192.168.1.144:8081/xiaoqiguaiguai-mobile/";

        //正式
   // public static final String URL = "http://xq.xhsqy.com/xiaoqiguaiguai-mobile/";

    //public static final String IMGURL = "http://xq.xhsqy.com/xiaoqiguaiguai-mobile/";

//       // 测试
//    public static final String URL = "http://app.xhsqy.com/xiaoqiguaiguai-mobile/";
//
//    public static final String IMGURL = "http://app.xhsqy.com/xiaoqiguaiguai-mobile/";

    public static final String ZXWDURL = URL + "view/credit.html";

    public static final String RFWURL = "http://zxgk.court.gov.cn/zhzxgk/";

    public static final String NORMALURL = URL+"view/invloancuts.html";
        public static final String CRMURL = URL+"crm/view/serviceHome.html";

}
