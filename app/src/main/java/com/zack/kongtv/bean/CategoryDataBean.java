package com.zack.kongtv.bean;

import java.util.List;

public class CategoryDataBean {
    private List<Cms_movie> movieItemBeans;
    private List<TagItemBean> tag1;
    private List<TagItemBean> tag2;
    private List<TagItemBean> tag3;


    public List<TagItemBean> getTag1() {
        return tag1;
    }

    public void setTag1(List<TagItemBean> tag1) {
        this.tag1 = tag1;
    }

    public List<TagItemBean> getTag2() {
        return tag2;
    }

    public void setTag2(List<TagItemBean> tag2) {
        this.tag2 = tag2;
    }

    public List<TagItemBean> getTag3() {
        return tag3;
    }

    public void setTag3(List<TagItemBean> tag3) {
        this.tag3 = tag3;
    }

    public List<Cms_movie> getMovieItemBeans() {
        return movieItemBeans;
    }

    public void setMovieItemBeans(List<Cms_movie> movieItemBeans) {
        this.movieItemBeans = movieItemBeans;
    }
}
