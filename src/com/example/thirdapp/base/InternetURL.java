package com.example.thirdapp.base;

/**
 * Created by Administrator on 2015/9/2.
 */
public class InternetURL {
    public static final String INTERNAL = "http://shop.apptech.space/api/";
    public static final String INTERNAL_PIC = "http://shop.apptech.space/";
    //小区
    public static final String COMMUNITY_URL = INTERNAL + "community/lists";
    //轮播图
    public static final String ADV_URL = INTERNAL + "adv/lists";
    //旅游
    public static final String TRIP_URL = INTERNAL + "trip/lists";
    //交友 列表
    public static final String FRIENDS_URL = INTERNAL + "friend/lists";
    //交友 关键词 搜索
    public static final String FRIENDS_SEARCH_URL = INTERNAL + "friend/search";
    //活动 列表
    public static final String ACTIVE_LIST_URL = INTERNAL + "activity/lists";
    //学习 列表
    public static final String STUDY_LIST_URL = INTERNAL + "study/lists";
    //学习
    public static final String STUDY_DETAIL_URL = INTERNAL + "study/studyDetail";
    //学习 回复 详细
    public static final String STUDY_REPLY_DETAIL_URL = INTERNAL + "study/replyDetail";
    //学习 搜索
    public static final String STUDY_SEARCH_URL = INTERNAL + "study/search";
    //交友前三位
    public static final String FRIEND_POPULAR_URL = INTERNAL + "friend/popular";
    //产品列表
    public static final String PRODUCT_URL = INTERNAL + "product/lists";
    //发送验证码
    public static final String SEND_CODE_URL = INTERNAL + "user/sendCode";
    //校验验证码
    public static final String VERITY_CODE_URL = INTERNAL + "user/verityCode";
    //注册
    public static final String REG_URL = INTERNAL + "user/register";
    // 、登陆 (login)
    public static final String LOGIN_URL = INTERNAL + "user/login";
    //修改密码 发送验证码
    public static final String SEND_CODE_CHECK_URL = INTERNAL + "user/sendCheckCode";
    //2)修改密码
    public static final String UPDATE_PWR_URL = INTERNAL + "user/updatePassword";
    // 、 上传地理位置( (
    public static final String SAVE_LNG_LAT_URL = INTERNAL + "user/saveLngLat";
    // 、 设置 允许 交友
    public static final String SET_OPEN_FRIEND_URL = INTERNAL + "friend/setOpenFriend";
    // 、 产品 分类 列表 ( (c ca at t) )
    public static final String PRODUCT_TYPE_URL = INTERNAL + "product/cat";
    //、 根据 产品 分类 查看 产品 列表 ( ( getB By yC Ca at t) )
    public static final String PRODUCT_TYPE_LISTS_URL = INTERNAL + "product/getByCat";
    //、 产品 详情 ( (d de et ta ai il l) )
    public static final String PRODUCT_DETAIL_URL = INTERNAL + "product/detail";
    // 、 便民 列表 ( ( lists) )
    public static final String BIANMIN_LISTS_URL = INTERNAL + "bianmin/lists";
    // 、 便民 房产 分类 ( (
    public static final String BIANMIN_TYPE_URL = INTERNAL + "bianmin/cat";

    public static final String MINE_ORDERS_SJ_URL = INTERNAL + "order/get";
    // 、 论坛 发布( (a a
    public static final String TALK_ADD_URL = INTERNAL + "talk/add";
    // 、 论坛 列表( (
    public static final String TALK_LISTS_URL = INTERNAL + "talk/lists";
    // 、 论坛 分类( ( ca
    public static final String TALK_TYPE_URL = INTERNAL + "talk/cat";
    // 、 订单 列表( ( list
    public static final String ORDER_LISTS_URL = INTERNAL + "order/lists";
    // 、 订单 详细( ( detail) )
    public static final String ORDER_DETAIL_URL = INTERNAL + "order/detail";
    //订单状态改变 (
    public static final String ORDER_STATUS_URL = INTERNAL + "order/status";
    // 、 学习 热门 回复 列表( ( hot tR Re ep pl ly y) )
    public static final String REPLYS_STUDY_URL = INTERNAL + "study/hotReply";
    // 、 学习 回复( (r re ep pl ly y) )
    public static final String REPLY_STUDY_URL = INTERNAL + "study/reply";
    // 、 学习 赞( ( support) )
    public static final String SUPPORT_STUDY_URL = INTERNAL + "study/support";
    // 、 学习 收藏( ( collec ct t) )
    public static final String COLLECT_STUDY_URL = INTERNAL + "study/collect";
    // 、 旅游 收藏( ( collec c
    public static final String COLLECT_TRIP_URL = INTERNAL + "trip/collect";
    //、 旅游 赞( ( support)
    public static final String SUPPORT_TRIP_URL = INTERNAL + "trip/support";
    //、 旅游 评论( ( comment)
    public static final String COMMENT_TRIP_URL = INTERNAL + "trip/comment";
    // 、 产品 评论( (
    public static final String COMMENT_PRODUCT_URL = INTERNAL + "product/comment";
    //新增/ / 编辑收货地址
    public static final String ADDRESS_SET_URL = INTERNAL + "address/set";
    //收货地址列表
    public static final String ADDRESS_LISTS_URL = INTERNAL + "address/lists";
    //删除地址
    public static final String ADDRESS_DEL_URL = INTERNAL + "address/del";
    //充值
    public static final String CHONGZHI_URL = INTERNAL + "alipay/recharge";
    // 、 个人 信息( (g ge et tM Me em mb be er rI In nf fo o) )
    public static final String GET_MEMBER_INFO_URL = INTERNAL + "user/getMemberInfo";
    // 、 下单( ( set)
    public static final String SET_ORDER_LIST = INTERNAL + "order/set";
    //3 、反馈 (feedback)
    public static final String FEED_BACK_URL = INTERNAL + "user/feedback";

    //论坛详细
    public static final String TALK_DETAIL_URL = INTERNAL + "talk/detail";
    //5 、论坛
    public static final String TALK_SEARCH_URL = INTERNAL + "talk/search";
    //论坛评论
    public static final String TALK_ADD_COMMENT_URL = INTERNAL + "talk/comment";

    //商品收藏接口
    public static final String PRODUCT_COLLECT_URL = INTERNAL + "product/collect";
    //编辑个人信息
    public static final String UPDATE_MEMBER_URL = INTERNAL + "user/updateMemberInfo";

    //1 、 添加 晒 范( ( add) )
    public static final String SHOW_ADD_URL = INTERNAL + "show/add";
    // 晒 范 列表( ( lists) )
    public static final String SHOW_LISTS_URL = INTERNAL + "show/lists";
    // 、 晒 范 前三( ( get
    public static final String SHOW_LISTS_TOP_URL = INTERNAL + "show/getTopList";

}
