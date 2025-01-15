package com.crazylychee.result;

import java.util.ArrayList;
import java.util.List;

/**
* 分页对象
*/
public class PageList<T> {

    private long total;
    private List<T> rows = new ArrayList<>();

    @Override
    public String toString() {
        return "PageList{" +
        "total=" + total +
        ", rows=" + rows +
        '}';
    }


    public PageList(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageList() {

    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}