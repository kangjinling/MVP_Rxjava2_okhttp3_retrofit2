package com.kang.net;

import com.yanzhenjie.permission.ApLog;

import java.util.HashMap;

import static com.kang.utils.UIUtils.showToast;

/**
* 作者 ：kangjinling
* 邮箱 ：401205099@qq.com
* 功能描述 ： 网络状态码
*
*/

public class HttpState {

    // 网络请求接口返回
    public static final HashMap<String, String> getHttpStateMap(){
        HashMap<String, String> map = new HashMap<String, String>();
        //自定义状态码
        map.put("timeout","请求超时");
        map.put("time out","请求超时");
        map.put("connect timed out","请求超时");
        map.put("HTTP 404","网络好像不给力...");
        map.put("HTTP 404 Not Found","网络好像不给力...");
        map.put("No route to host","网络好像不给力...");
//        map.put("HTTP 413 Request Entity Too Large","网络好像不给力...");

        map.put("100","客户端应当继续发送完整的请求。");
        map.put("101","请采用不同的协议来完成请求。");
        map.put("102","处理将被继续执行。");
        map.put("200","请求已成功。");
        map.put("201","需要的资源无法及时建立的话。");
        map.put("202","服务器已接受请求，但尚未处理。");
        map.put("203","服务器已成功处理了请求，但返回的实体头部元信息不是在原始服务器上有效的确定集合。");
        map.put("204","服务器成功处理了请求，但不需要返回任何实体内容，并且希望返回更新了的元信息。");
        map.put("205","服务器成功处理了请求，且没有返回任何内容。");
        map.put("206","服务器已经成功处理了部分 GET 请求。请确保HTTP消息头已经正确设置。");
        map.put("207","207");
        map.put("300","请求被重定向");
        map.put("301","资源（网页等）被永久转移到其它URL");
        map.put("302","302");
        map.put("303","对应当前请求的响应可以在另一个 URI 上被找到，而且客户端应当采用 GET 的方式访问那个资源。");
        map.put("304","304");
        map.put("305","被请求的资源必须通过指定的代理才能被访问。");
        map.put("306","306");
        map.put("307","307");
        map.put("400","请求参数有误。");
        map.put("401","当前请求需要用户验证。");
        map.put("402","402");
        map.put("403","服务器已经理解请求，但是拒绝执行它。");
        map.put("404","请求失败，请求所希望得到的资源未被在服务器上发现。");
        map.put("405","请求行中指定的请求方法不能被用于请求相应的资源。");
        map.put("406","请求的资源的内容特性无法满足请求头中的条件，因而无法生成响应实体。");
        map.put("407","当前请求需要用户验证。");
        map.put("408","请求超时。");
        map.put("409","由于和被请求的资源的当前状态之间存在冲突，请求无法完成。");
        map.put("410","被请求的资源在服务器上已经不再可用，而且没有任何已知的转发地址。");
        map.put("411","服务器拒绝在没有定义 Content-Length 头的情况下接受请求。");
        map.put("412","服务器在验证在请求的头字段中给出先决条件时，没能满足其中的一个或多个。");
        map.put("413","服务器拒绝处理当前请求，因为该请求提交的实体数据大小超过了服务器愿意或者能够处理的范围。");
        map.put("414","请求的URI 长度超过了服务器能够解释的长度，因此服务器拒绝对该请求提供服务。");
        map.put("415","对于当前请求的方法和所请求的资源，请求中提交的实体并不是服务器中所支持的格式，因此请求被拒绝。");
        map.put("416","请求指定的所有数据范围的首字节位置超过了当前资源的长度。");
        map.put("417","在请求头 Expect 中指定的预期内容无法被服务器满足。");
        map.put("421","从当前客户端所在的IP地址到服务器的连接数超过了服务器许可的最大范围。");
        map.put("422","从当前客户端所在的IP地址到服务器的连接数超过了服务器许可的最大范围。");
        map.put("422","请求格式正确，但是由于含有语义错误，无法响应。");
        map.put("424","由于之前的某个请求发生的错误，导致当前请求失败。");
        map.put("425","425");
        map.put("426","客户端应当切换到TLS/1.0。");
        map.put("449","449");
        map.put("500","服务器的程序出错。");
        map.put("501","服务器不支持当前请求所需要的某个功能。");
        map.put("502","作为网关或者代理工作的服务器尝试执行请求时，从上游服务器接收到无效的响应。");
        map.put("503","服务器维护或者过载。");
        map.put("504","未能从上游服务器收到响应。");
        map.put("505","服务器不支持，或者拒绝支持在请求中使用的 HTTP 版本。");
        map.put("506","506");
        map.put("507","服务器无法存储完成请求所必须的内容。");
        map.put("509","服务器达到带宽限制。");
        map.put("510","获取资源所需要的策略并没有没满足。");

        return map;
    }


    public static  String  getStateErr(String str ){
        ApLog.e("err===="+str);
        String state = "网络好像不给力...";
        for (String key :  HttpState.getHttpStateMap().keySet()) {
            if (str.contains(key)){
                ApLog.e(str+"========="+key+ "====="+HttpState.getHttpStateMap().get(key));
                state = HttpState.getHttpStateMap().get(key);
                showToast(state);
            }
        }
        return state;
    }
}
