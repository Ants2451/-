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

import jcsf.Persistence;
import jcsf.json.JsonFactory;
import jcsf.json.JsonPersistence;
import jcsf.util.DateUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;

/**
 * Crawler表示爬虫类
 * @author cy
 * @since 2022/3/18
 * @history 2022/3/18 21:27 created by【cy】
 */
public class Crawler {

    /**
     * 获取目标Json
     * @param uri 目标地址
     * @return Json 数据
     * @author cy
     * @history 1.0.0.0 2022/3/19 17:00 create [cy]
     */
    public static Json getJson(String uri){
        Json json = new Json();

        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        //2.创建get请求，相当于在浏览器地址栏输入 网址
        HttpGet request = new HttpGet(uri);
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36");
        try {
            //3.执行get请求，相当于在输入地址栏后敲回车键
            response = httpClient.execute(request);

            //4.判断响应状态为200，进行处理
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                String html = EntityUtils.toString(httpEntity, "utf-8");
                JsonPersistence persistence = JsonFactory.instance();
                String result = persistence.rectifyJson(html);
                json = persistence.bean(result, Json.class);
                return json;
            } else {
                //如果返回状态不是200，比如404（页面不存在）等，根据情况做处理，这里略
                System.out.println("返回状态不是200");
                System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
                return json;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6.关闭
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }

        return json;
    }

    /**
     * 菜单
     * @param map Json内的data数据
     * @author cy
     * @history 1.0.0.0 2022/3/18 23:18 create [cy]
     */
    public static void menu(Map<String, Object> map){
        double price = Double.parseDouble(map.get("price").toString())/100.00 ;
        double market_price = Double.parseDouble(map.get("market_price").toString())/100.00 ;
        double discount_rate = Double.parseDouble(map.get("discount_rate").toString());
        double discount_price = market_price * discount_rate;
        System.out.println("------------商品：" + map.get("name") + "------------");
        System.out.println("规格：" + map.get("spec"));
        System.out.println("价格：" + price);
        System.out.println("原价/折扣价：" + market_price + "/" + discount_price);
        System.out.println("详细内容：" + map.get("share_content"));

    }

    /**
     * 监控价格
     * @param json Json数据
     * @param period 间隔时间
     * @author cy
     * @history 1.0.0.0 2022/3/18 23:23 create [cy]
     */
    public static void monitoring(Json json, int period){
        final List<Map<String,Object>> map = json.getData();
        //生成定时器
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //对Json数据进行转换处理
                double price = Double.parseDouble(map.get(0).get("price").toString())/100.00;
                System.out.println("------------商品：" + map.get(0).get("name") + "------------");
                System.out.println("当前时间为" + DateUtil.getSystemDate() + ",价格为" + price);
            }
        }, 0,period);
    }

    /**
     * 获取收藏夹列表
     * @param json Json数据
     * @return 收藏夹列表
     * @author cy
     * @history 1.0.0.0 2022/3/19 21:23 create [cy]
     */
    public static List<Favorites> getFavorites(Json json){
        //对Json数据进行持久化转换
        List<Favorites> favoritesList = Persistence.beans(Favorites.class, json.getData());

        return favoritesList;
    }

    /**
     * 获取文章列表
     * @param json Json数据
     * @return 文章列表
     * @author cy
     * @history 1.0.0.0 2022/3/19 22:06 create [cy]
     */
    public static List<Map<String, Object>> getArticles(Json json){
        //对Json数据进行持久化转换
        List<Map<String, Object>> articleList = json.getData();

        return articleList;
    }

    /**
     * 展示收藏夹
     * @param json Json数据
     * @param uri 收藏夹地址
     * @author cy
     * @history 1.0.0.0 2022/3/19 22:31 create [cy]
     */
    public static void showFavorites(Json json, String uri){
        List<Favorites> favoritesList = Crawler.getFavorites(json);
        System.out.println("----------------收藏夹内容----------------");
        
        for (Favorites item: favoritesList) {

            System.out.println("收藏夹名称：" + item.getTitle());
            String favoriteUri = uri.replace("<1>", item.getId());
            Json articlesJson = Crawler.getJson(favoriteUri);
            item.setArticles(Crawler.getArticles(articlesJson));

            int i = 0;
            for (Map<String,Object> article:item.getArticles()) {
                System.out.println("--" + ++i);

                Map<String,Object> map = Persistence.map(Persistence.json(article.get("content")));

                try {
                    Map<String, Object> articleList = Persistence.map(Persistence.json(map.get("question")));
                    System.out.println("--文章标题：" + articleList.get("title"));
                    System.out.println("--文章地址：" + articleList.get("url"));
                    System.out.println();

                }catch (Exception e){
                    System.out.println("--文章标题：" + map.get("title"));
                    System.out.println("--文章地址：" + map.get("url"));
                    System.out.println();
                }

            }
        }

    }
}
