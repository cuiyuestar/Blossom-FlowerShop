package com.blossom.constant;

/**
 * redis相关关键字
 */
public class RedisConstants {
    public static final String LOGIN_CODE_KEY = "login:code:";
    public static final Long LOGIN_CODE_TTL = 2L;
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 36000L;
    public static final String COMMENT_KEY = "comment:";
    public static final Long CACHE_NULL_TTL = 2L;

    public static final Long CACHE_SHOP_TTL = 30L;
    public static final String CACHE_SHOP_KEY = "cache:shop:";

    public static final String LOCK_SHOP_KEY = "lock:shop:";
    public static final Long LOCK_SHOP_TTL = 10L;

    public static final String SECKILL_STOCK_KEY = "seckill:stock:";

    public static final String SHOP_GEO_KEY = "shop:geo:";
    public static final String USER_SIGN_KEY = "sign:";

    public static final String ACTIVITY_KEY= "activity:";

    public static final String ACTIVITY_SALE_KEY = "activity:sale:";
    public static final String ACTIVITY_STOCK_KEY = "activity:stock:";

    public static final Long CACHE_ACTIVITY_TTL = 30L;

    public static final int INIT_RETRY_COUNT = 0; // 初始缓存重建重试次数
    public static final int MAX_RETRY_COUNT = 3; // 最大缓存重建重试次数

    public static final String COMMENT_LIKED_KEY = "comment:liked:";


}
