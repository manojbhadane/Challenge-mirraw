package com.manojbhadane.challenge.model;

import java.util.ArrayList;

/**
 * Created by manoj.bhadane on 16-12-2017.
 */
public class ResponceModel
{
    private int page;
    private int total;
    private int per_page;
    private int total_pages;
    private ArrayList<UserModel> data;

    public ResponceModel()
    {
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public int getPer_page()
    {
        return per_page;
    }

    public void setPer_page(int per_page)
    {
        this.per_page = per_page;
    }

    public int getTotal_pages()
    {
        return total_pages;
    }

    public void setTotal_pages(int total_pages)
    {
        this.total_pages = total_pages;
    }

    public ArrayList<UserModel> getData()
    {
        return data;
    }

    public void setData(ArrayList<UserModel> data)
    {
        this.data = data;
    }
}
