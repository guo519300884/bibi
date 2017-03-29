package gjw.bibi.utils;

/**
 * Created by 皇上 on 2017/3/20.
 */

public class AppNetConfig {

    // 直播页
    public static final String LIVE_STREAMING = "http://live.bilibili.com/AppNewIndex/common?_device=android&appkey=1d8b6e7d45233436&build=501000&mobi_app=android&platform=android&scale=hdpi&ts=1490013188000&sign=92541a11ed62841120e786e637b9db3b";
    //推荐页
    public static final String RECOMMEND = "http://app.bilibili.com/x/feed/index?appkey=1d8b6e7d45233436&build=501000&idx=1490013250&mobi_app=android&network=wifi&platform=android&pull=true&style=2&ts=1490014766000&sign=843ebd23f67bd67d755d5c000f68e054";
    //追番页
    public static final String TO_THEM = "http://bangumi.bilibili.com/api/app_index_page_v4?build=3940&device=phone&mobi_app=iphone&platform=ios";
    //分区页頭部
    public static final String ZONE_HEAD = "http://app.bilibili.com/x/v2/region?appkey=1d8b6e7d45233436&build=501000&mobi_app=android&platform=android&ts=1490170066000&sign=88793834edd7dd2977bd2de07b93a9b4";
    //分区页下类型
    public static final String ZONE_TYPE = "http://app.bilibili.com/x/v2/show/region?appkey=1d8b6e7d45233436&build=501000&mobi_app=android&platform=android&ts=1490014674000&sign=93edb7634f38498a60e5c3ad0b8b0974";

    //发现页
    public static final String FIND = "common?_device=android&appkey=1d8b6e7d45233436&build=501000&mobi_app=android&platform=android&scale=hdpi&ts=1490013188000&sign=92541a11ed62841120e786e637b9db3b";
    //发现热门TAG
    public static final String TAG = "http://app.bilibili.com/x/v2/search/hot?appkey=1d8b6e7d45233436&build=501000&limit=50&mobi_app=android&platform=android&ts=1490014710000&sign=e5ddf94fa9a0d6876cb85756c37c4adc";
    //发现TAG点击跳转的搜索页
    public static final String TAGSEARCH = "http://app.bilibili.com/x/v2/search?appkey=1d8b6e7d45233436&build=501000&duration=0&keyword=极乐净土&mobi_app=android&platform=android&pn=1&ps=20";
    //话题中心
    public static final String TOPIC = "http://api.bilibili.com/topic/getlist?appkey=1d8b6e7d45233436&build=501000&mobi_app=android&page=1&pageSize=20&platform=android&ts=1490015740000&sign=be68382cdc99c168ef87f2fa423dd280";
    //原创排行榜----全站
    public static final String ALLSTATION = "http://app.bilibili.com/x/v2/rank?appkey=1d8b6e7d45233436&build=501000&mobi_app=android&order=all&platform=android&pn=1&ps=20&ts=1490015891000&sign=8e7dfaa1c2fb779943430b46e734b422";

    //根据关键字搜索
    public static final String SEARCH_HEAD = "http://app.bilibili.com/x/v2/search?appkey=1d8b6e7d45233436&build=501000&duration=0&keyword=";

    public static String KEYWORD_SEARCH(String kw) {
        return SEARCH_HEAD + kw + SEARCH_TAIL;
    }

    public static final String SEARCH_TAIL = "&mobi_app=android&platform=android&pn=1&ps=20";

    //商品
    public static final String GOODS = "http://bmall.bilibili.com/api/product/list.do?pn=1&ps=6";

}
