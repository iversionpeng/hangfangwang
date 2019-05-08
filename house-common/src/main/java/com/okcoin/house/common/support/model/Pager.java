package com.okcoin.house.common.support.model;

import lombok.Data;

import java.util.List;

/**
 * @author: liupeng
 * @date: 2019/4/17 18:20
 * @description(功能描述): 返回列表结果封装
 */
@Data
public class Pager<T> {
    /**
     * 当前页
     */
    private Integer currentPage;
    /**
     * 每页数量
     */
    private Integer pageSize;
    /**
     * 结果总数
     */
    private Long total;
    /**
     * 结果集
     */
    private List<T> result;
    /**
     * 总页数
     */
    private Integer pages;
    /**
     * 是否第一页
     */
    private boolean isFirstPage;
    /**
     * 是否最后一页
     */
    private boolean isLastPage;

    public Pager(Integer currentPage, Integer pageSize, Long total, List<T> result) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.total = total;
        this.result = result;
    }

    public Pager(Integer currentPage, Integer pageSize, Long total, List<T> result, Integer pages, boolean isFirstPage, boolean isLastPage) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.total = total;
        this.result = result;
        this.pages = pages;
        this.isFirstPage = isFirstPage;
        this.isLastPage = isLastPage;
    }
}
