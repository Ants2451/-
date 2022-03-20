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

import java.util.List;
import java.util.Map;

/**
 * Favorites表示收藏夹类
 * @author cy
 * @since 2022/3/19
 * @history 2022/3/19 20:51 created by【cy】
 */
public class Favorites {

    /**
     * id
     */
    private String id;

    /**
     * 收藏夹名称
     */
    private String title;

    /**
     * 收藏数量
     */
    private String item_count;

    /**
     * 文章列表
     */
    private List<Map<String, Object>> articles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItem_count() {
        return item_count;
    }

    public void setItem_count(String item_count) {
        this.item_count = item_count;
    }

    public List<Map<String, Object>> getArticles() {
        return articles;
    }

    public void setArticles(List<Map<String, Object>> articles) {
        this.articles = articles;
    }
}
