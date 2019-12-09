package com.community.community.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Page<T> {

    private static final int SIZE = 5;

    private List<T> list;

    private boolean hasPrevious;

    private boolean hasNext;

    private int currentPage;

    private int pageSize;

    public List<Integer> getPages() {
        return pages;
    }

    private List<Integer> pages = new ArrayList<>();


    void setPages() {
        int temp =totalPage;
        int p =currentPage;
        if (temp <= SIZE) {
            while ( temp > 0) {
                pages.add(temp--);
            }
            Collections.reverse(pages);
        }
        else if (temp > SIZE && p < (SIZE/2 + 1)) {
            for (int i = 1; i <= 5; i++) {
                pages.add(i);
            }
        }
        else if (temp> SIZE && totalPage - currentPage < (SIZE/2 + 1)) {
            for (int i = 0; i < 5; i++) {
                pages.add(temp--);
            }
            Collections.reverse(pages);
        }
        else {
            pages.add(p-2);
            pages.add(p - 1);
            pages.add(p);
            pages.add(p + 1);
            pages.add(p+2);
        }
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }
    private int totalRow;

    private int totalPage;


    public Page (int currentPage, int pageSize, int total){
        if (currentPage == 0) {
            this.currentPage = 1;
        } else {
            this.currentPage = currentPage;
        }
        this.pageSize =  pageSize;
        this.totalRow = total;
        this.totalPage = totalRow % pageSize == 0 ? totalRow / pageSize : totalRow / pageSize + 1;
        hasPrevious();
        hasNext();
        setPages();
    }
    void hasPrevious() {
        if (currentPage == 1) {
            hasPrevious = false;
        } else
            hasPrevious = true;
    }
    void hasNext() {
        if (currentPage == totalPage)
            hasNext = false;
        else
            hasNext = true;
    }

}
