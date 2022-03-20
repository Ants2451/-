/**
 * Copyright (c) 2017 SeaFounder 版权所有
 * SeaFounder Co. Ltd. All rights reserved.
 * <p>
 * This software is the confidential and proprietary
 * information of SeaFounder Co. Ltd.
 * ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only
 * in accordance with the terms of the contract agreement
 * you entered into with SeaFounder Co. Ltd
 */


/**
 * Main表示
 * @author cy
 * @since 2022/3/19
 * @history 2022/3/19 17:00 created by【cy】
 */
public class Main {


    /**
     * 朴朴商品地址
     */
    private static final String PUPU_URI = "https://j1.pupuapi.com/client/product/storeproduct/detail/7c1208da-907a-4391-9901-35a60096a3f9/a63bf415-6db1-49ce-9b3e-feaf53fa3186";

    /**
     * 收藏夹地址
     */
    private static final String FAVORITES_URI = "https://www.zhihu.com/api/v4/people/ants-67-59/collections?include=data%5B*%5D.updated_time%2Canswer_count%2Cfollower_count%2Ccreator%2Cdescription%2Cis_following%2Ccomment_count%2Ccreated_time%3Bdata%5B*%5D.creator.vip_info&offset=0&limit=20";

    /**
     * 收藏夹信息地址
     */
    private static final String FAVORITE_URI = "https://www.zhihu.com/api/v4/collections/<1>/items?offset=0&limit=20";

    public static void main(String[] args) {
//
//        Json json = Crawler.getJson(PUPU_URI);
//        Crawler.menu(json.getData().get(0));
//        Crawler.monitoring(json,5*1000);

        Json json = Crawler.getJson(FAVORITES_URI);
        Crawler.showFavorites(json, FAVORITE_URI);
    }
}
