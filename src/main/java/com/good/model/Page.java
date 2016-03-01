package com.good.model;

import java.io.Serializable;
import java.util.List;

/**
 * Page
 *
 * @Description: uuuuuuuuuuuuuuuuu
 * @Author: tretert
 * @Time:1
 */
public class Page<T> implements Serializable{
    private static final long serialVersionUID = 2565271160913574119L;

    //当前页数
    private int pageNo = 1;

    //每页个数
    private int pageSize;

    //总数目
    private int dataCount;

    //一页的数据
    private List<T> pageResult;

    public Page(){}

    public Page(int pageSize){
        this.pageSize = pageSize;
    }

    //获取offset
    public int getOffset(){
        return (pageNo-1) * pageSize;
    }

    //获取limit
    public int getLimit(){
        return pageSize;
    }

    //获取最大的页数
    public int getMaxPage(){

        int maxPage = dataCount/pageSize + 1;

        return maxPage;
    }

    public List<T> getPageResult() {
        return pageResult;
    }

    public void setPageResult(List<T> pageResult) {
        this.pageResult = pageResult;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }
}
