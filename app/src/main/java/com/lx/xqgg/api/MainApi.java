package com.lx.xqgg.api;

import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.face_ui.home.bean.FaceProductBean;
import com.lx.xqgg.ui.city.bean.CityBean;
import com.lx.xqgg.ui.company_auth.bean.ImgBean;
import com.lx.xqgg.ui.home.bean.AdvertBean;
import com.lx.xqgg.ui.home.bean.AppVersionBean;
import com.lx.xqgg.ui.home.bean.BannerBean;
import com.lx.xqgg.ui.home.bean.HotMsgBean;
import com.lx.xqgg.ui.home.bean.ResultBean;
import com.lx.xqgg.ui.home.bean.UserServiceBean;
import com.lx.xqgg.ui.login.bean.LoginBean;
import com.lx.xqgg.ui.login.bean.MsgBean;
import com.lx.xqgg.ui.login.bean.UserInfoBean;
import com.lx.xqgg.ui.login.bean.crmLoginBean;
import com.lx.xqgg.ui.match.bean.MatchResultBean;
import com.lx.xqgg.ui.match.bean.MatchSaveFirstBean;
import com.lx.xqgg.ui.message.bean.MessageBean;
import com.lx.xqgg.ui.my_client.bean.ServiceCustomerBean;
import com.lx.xqgg.ui.order.bean.OrderBean;
import com.lx.xqgg.ui.order.bean.OrderUserBean;
import com.lx.xqgg.ui.order.bean.TjBean;
import com.lx.xqgg.ui.person.bean.HelperBean;
import com.lx.xqgg.ui.product.bean.ApplyHistoryBean;
import com.lx.xqgg.ui.product.bean.AuthBean;
import com.lx.xqgg.ui.product.bean.CateBean;
import com.lx.xqgg.ui.product.bean.ProductBean;
import com.lx.xqgg.ui.vip.bean.PayListBean;
import com.lx.xqgg.ui.xq_data.bean.ProductNameBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface MainApi {
    /**
     * 获取短信验证码
     *
     * @return
     */
    @GET("sms/send")
    Flowable<MsgBean> getMsg(@Query("mobile") String mobile, @Query("event") String event,@Query("token") String token,@Query("name") String name);

    /**
     * 登录
     *
     * @return
     */
    @POST("user/mobilelogin")
    Flowable<LoginBean> login(@Body RequestBody body);
    /**
     * crm登录
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST()
    Flowable<BaseData<crmLoginBean>> crmlogin(@Url String url, @Body RequestBody body);
    /**
     * 获取服务商信息
     *
     * @param body
     * @return
     */
    @POST("user/getUserServiceInfo")
    Flowable<BaseData<UserServiceBean>> getUserServiceInfo(@Body RequestBody body);

    /**
     * 获取轮播图片
     *
     * @param appId
     * @return
     */
    @GET("index/getBanner")
    Flowable<BaseData<List<BannerBean>>> getBanner(@Query("appId") int appId, @Query("type") String type);

    /**
     * 获取热点信息
     *
     * @param body
     * @return
     */
    @POST("index/getNotice")
    Flowable<HotMsgBean> getHotNotice(@Body RequestBody body);

    /**
     * 获取热点详情
     *
     * @return
     */
    @GET("index/getNoticeById")
    Flowable<BaseData<HotMsgBean.RecordsBean>> getNoticeById(@Query("id") int id);

    /**
     * 获取产品分类
     *
     * @param appId
     * @return
     */
    @GET("index/getCateList")
    Flowable<BaseData<List<CateBean>>> getCateList(@Query("appId") int appId, @Query("type") String type);

    /**
     * @param level 层级 1 2 3 省市区县
     * @return
     */
    @GET("index/getCity")
    Flowable<BaseData<List<CityBean>>> getCity(@Query("level") String level);

    /**
     * 获取产品列表
     *
     * @param body
     * @return
     */
    @POST("product/getProductList")
    Flowable<ProductBean> getProductList(@Body RequestBody body);

    /**
     * 智能匹配接口
     *
     * @param body
     * @return
     */
    @POST("product/autoProduct")
    Flowable<BaseData<List<MatchResultBean>>> getMatchResult(@Body RequestBody body);


    /**
     * 获取智能匹配保存结果明细
     * @param level
     * @return
     */
    @GET("product/getAutoProductDetail")
    Flowable<BaseData<List<MatchResultBean>>> getAutoDetail(@Query("id") Integer level);

    /**
     * 保存智能匹配结果
     *
     * @param body
     * @return
     */
    @POST("product/saveAutoProduct")
    Flowable<BaseData<Object>> saveAutoProduct(@Body RequestBody body);

    /**
     * 申请产品接口
     *
     * @param body
     * @return
     */
    @POST("product/applyProduct")
    Flowable<BaseData<String>> applyProduct(@Body RequestBody body);

    /**
     * 三证合一接口
     *
     * @param body
     * @return
     */
    @POST("common/getIdAuth")
    Flowable<BaseData<AuthBean>> getIdAuth(@Body RequestBody body);

    /**
     * 企查查接口
     *
     * @param keyWord
     * @return
     */
    @GET("common/getQiChacha")
    Flowable<BaseData<String>> getQiCc(@Query("keyWord") String keyWord);
    /**
     * 企查查接口详情
     *
     * @param keyWord
     * @return
     */
    @GET("common/getQiChachaInfo")
    Flowable<BaseData<String>> getQiCcInfo(@Query("keyWord") String keyWord);

    /**
     * 更具id查找产品详情
     *
     * @return
     */
    @GET("product/getLoanProductById")
    Flowable<BaseData<ProductBean.RecordsBean>> getLoanProductById(@Query("id") int id, @Query("cityName") String cityName);

    /**
     * 获取订单
     *
     * @param body
     * @return
     */
    @POST("order/getMyLoanOrder")
    Flowable<OrderBean> getMyLoanOrder(@Body RequestBody body);

    /**
     * 我的客户的订单
     *
     * @param body
     * @return
     */
    @POST("order/getCustomerOrder")
    Flowable<OrderBean> getCustomerOrder(@Body RequestBody body);

    /**
     * 获取服务商的用户
     *
     * @param token
     * @return
     */
    @GET("user/getUsers")
    Flowable<BaseData<List<OrderUserBean>>> getUsers(@Query("token") String token);

    /**
     * 根据名字获取服务商信息
     *
     * @return
     */
    @POST("user/getServiceByName")
    Flowable<BaseData<UserServiceBean>> getSericeByName(@Body RequestBody body);

    /**
     * 上传文件
     *
     * @param map
     * @return
     */
    @Multipart
    @POST("common/fileUpload")
    Flowable<BaseData<List<ImgBean>>> upload(@PartMap Map<String, RequestBody> map);

    /**
     * 企业认证
     *
     * @param body
     * @return
     */
    @POST("product/serviceProviderGetAuth")
    Flowable<BaseData<Object>> serviceAuth(@Body RequestBody body);

    /**
     * 搜索接口
     *
     * @param body
     * @return
     */
    @POST("index/search")
    Flowable<BaseData<List<ResultBean>>> search(@Body RequestBody body);

    /**
     * 获取订单统计
     *
     * @param body
     * @return
     */
    @POST("statistics/businessStatistics")
    Flowable<BaseData<List<TjBean>>> getTj(@Body RequestBody body);

    /**
     * 我的客户
     *
     * @param body
     * @return
     */
    @POST("user/getServiceCustomer")
    Flowable<ServiceCustomerBean> getServiceCustomer(@Body RequestBody body);

    /**
     * 标星客户功能
     *
     * @param body
     * @return
     */
    @POST("user/topCustomer")
    Flowable<BaseData<Object>> starCustomer(@Body RequestBody body);

    /**
     * 生成订单参数
     *
     * @param body
     * @return
     */
    @POST("pay/vipOrder")
    Flowable<BaseData> getVipOrderParam(@Body RequestBody body);

    /**
     * 获取价格配置
     *
     * @return
     */
    @POST("index/getConfigList")
    Flowable<BaseData<List<PayListBean>>> getPayList(@Body RequestBody body);

    /**
     * 获取用户信息
     *
     * @return
     */
    @GET("user/getUserInfo")
    Flowable<BaseData<UserInfoBean>> getUserInfo(@Query("token") String token);

    /**
     * 获收藏的匹配结果
     *
     * @return
     */
    @POST("product/getAutoProduct")
    Flowable<BaseData<List<MatchSaveFirstBean>>> getAutoProduct(@Body RequestBody body);

    /**
     * 获取帮助列表
     *
     * @param body
     * @return
     */
    @POST("index/getHelpList")
    Flowable<HelperBean> getHelpList(@Body RequestBody body);

    /**
     * 查找帮助详情
     *
     * @param id
     * @return
     */
    @GET("index/getHelpById")
    Flowable<BaseData<HelperBean.RecordsBean>> getHelpById(@Query("id") int id);

    /**
     * 查询开庭公告
     *
     * @param keyWord 公司名称
     * @return
     */
    @GET("common/getCaseInfo")
    Flowable<BaseData<String>> getCaseInfo(@Query("keyWord") String keyWord);

    /**
     * 更新订单接口
     *
     * @param body
     * @return
     */
    @POST("order/updateOrder")
    Flowable<BaseData<Object>> updateOrder(@Body RequestBody body);

    /**
     * 获取所有产品接口
     *
     * @return
     */
    @GET("product/getProductForXiQi")
    Flowable<BaseData<List<ProductNameBean>>> getProductForXq();

    /**
     * 获取表层产品
     *
     * @return
     */
    @GET("face/getFaceProduct")
    Flowable<BaseData<List<FaceProductBean>>> getFaceProduct();

    /**
     * @param body
     * @return
     */
    @POST("face/faceOrder")
    Flowable<BaseData> getFaceOrder(@Body RequestBody body);

    /**
     * 更新用戶头像
     *
     * @return
     */
    @POST("user/updateUser")
    Flowable<BaseData> updateUser(@Body RequestBody body);

    /**
     * 获取税务局
     *
     * @return
     */
    @GET("index/getTaxbureau")
    Flowable<BaseData<String>> getTaxUrl(@Query("province") String province);


    /**
     * 检查更新
     * @param version
     * @return
     */
    @GET("index/getVersion")
    Flowable<BaseData<AppVersionBean>> checkUpdate(@Query("version") int version);

    /**
     * 获取消息列表
     * @param body
     * @return
     */
    @POST("msg/getMsgByPhone")
    Flowable<MessageBean> getMsgByPhone(@Body RequestBody body);

    /**
     * 删除消息根据id
     * @param id
     * @return
     */
    @GET("msg/deleteMsgById")
    Flowable<BaseData> delMsgById(@Query("id") int id);

    /**
     * 根据手机号获取未读消息
     * @param token
     * @return
     */
    @GET("msg/getUnReadNum")
    Flowable<BaseData<Integer>> getUnReadNum(@Query("token") String token);

    /**
     * 通过用户和公司名或客户名获取客户信息
     * @param body
     * @return
     */
    @POST("user/getCustomerInfo")
    Flowable<BaseData<ApplyHistoryBean>> getCustomerInfo(@Body RequestBody body);

    /**
     * 企查查人员相关企业查询
     * @param keyWord
     * @param personName
     * @return
     */
    @GET("common/getSeniorPerson")
    Flowable<BaseData<String>> getSeniorPerson(@Query("keyWord") String keyWord,@Query("personName") String personName);

    /**
     * 订单接口
     * @param body
     * @return
     */
    @POST("order/discardOrder")
    Flowable<BaseData> disCardOrder(@Body RequestBody body);

    /**
     * 模糊匹配公司
     * @param body
     * @return
     */
    @POST("user/getCustomeCompany")
    Flowable<BaseData<List<String>>> getCustomerCompany(@Body RequestBody body);

    /**
     * 获取广告
     * @param token
     * @return
     */
    @GET("index/getAdvert")
    Flowable<BaseData<List<AdvertBean>>> getAdvert(@Query("token") String token);

    /**
     * 获取广告
     * @param
     * @return
     */
    @GET("crm/view/login.html")
    Flowable<BaseData<String>>getCrmlogin();

    /**
     * 仿检查
     * @param
     * @return
     */
    @GET("common/getProperties")
    Flowable<BaseData<ImitationexaminationBean>>getImitationexamination();
}
