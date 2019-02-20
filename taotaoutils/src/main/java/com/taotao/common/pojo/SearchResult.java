package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
    private long totalPages;
    private long recordCount;
    private List<TbItemSearch> itemList;

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public List<TbItemSearch> getItemList() {
        return itemList;
    }

    public void setItemList(List<TbItemSearch> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "totalPages=" + totalPages +
                ", recordCount=" + recordCount +
                ", itemList=" + itemList +
                '}';
    }
}
