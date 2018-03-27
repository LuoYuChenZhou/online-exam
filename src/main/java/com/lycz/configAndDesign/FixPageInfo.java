package com.lycz.configAndDesign;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 分页插件返回值修改版，针对LayUI
 * @author lizhenqing
 * @version 1.0
 * @data 2018/2/24 10:45
 */
public class FixPageInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    //当前页
    private int page;
    //每页的数量
    private int limit;
    //总记录数
    private long count;
    //结果集
    private List<T> data;
    //状态(默认0为成功)
    private int code;
    //提示信息
    private String msg;
    //日志记录信息
    private String logMsg;

    public FixPageInfo() {
    }

    /**
     * 包装Page对象
     *
     * @param list          page结果
     */
    public FixPageInfo(List<T> list) {
        this.logMsg = "";
        if (list instanceof Page) {
            Page page = (Page) list;
            this.page = page.getPageNum();
            this.limit = page.getPageSize();
            this.data = page;
            this.count = page.getTotal();
        } else if (list instanceof Collection) {
            this.page = 1;
            this.limit = list.size();
            this.data = list;
            this.count = list.size();
        }
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageInfo{");
        sb.append("page=").append(page);
        sb.append(", limit=").append(limit);
        sb.append(", count=").append(count);
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
