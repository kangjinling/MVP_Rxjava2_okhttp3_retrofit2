package com.kang.ui.model;

import com.google.gson.Gson;

/**
 * @author ChayChan
 * @description: TODO
 * @date 2017/7/6  15:03
 */

public class NewsResponse {


    public int login_status;
    public int total_number;
    public boolean has_more;
    public String post_content_hint;
    public int show_et_status;
    public int feed_flag;
    public int action_to_last_stick;
    public String message;
    public boolean has_more_to_refresh;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
