package com.homesorderconsumer.product.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by innoppl on 27/03/18.
 */

public class ProductRequest {

    @SerializedName("categoryID")
    @Expose
    private String categoryID;

    @SerializedName("page")
    @Expose
    private String page;

    @SerializedName("sortby")
    @Expose
    private List<Sortby> sortby;

    @SerializedName("searchCriteria")
    @Expose
    private List<SearchCriteria> searchCriteria;

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<Sortby> getSortby() {
        return sortby;
    }

    public void setSortby(List<Sortby> sortby) {
        this.sortby = sortby;
    }

    public List<SearchCriteria> getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(List<SearchCriteria> searchCriteria) {
        this.searchCriteria = searchCriteria;
    }
}
