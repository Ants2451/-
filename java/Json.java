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
 * Json表示Json数据类
 * @author cy
 * @since 2022/3/18
 * @history 2022/3/18 22:01 created by【cy】
 */
public class Json {
    /**
     * 代码
     */
    private String errcode;

    /**
     * 信息
     */
    private String errmsg;

    /**
     * 数据
     */
    private List<Map<String, Object>> data;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }
}
