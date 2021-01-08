package com.qgfun.go.util;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.orhanobut.logger.Logger;
import com.qgfun.go.entity.AppInfo;
import com.qgfun.go.entity.DataCache;
import com.qgfun.go.entity.DataCache_Table;
import com.qgfun.go.entity.DouBanVideoInfo;
import com.qgfun.go.entity.TvInfo;
import com.qgfun.go.entity.TvPlayInfo;
import com.qgfun.go.entity.UrlResources;
import com.qgfun.go.entity.VideoDetail;
import com.qgfun.go.entity.VideoIndex;
import com.qgfun.go.entity.VideoUrl;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.apache.common.codec.digest.DigestUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import ren.yale.android.cachewebviewlib.utils.AppUtils;

/**
 * @author LLY
 */
public class ResourceUtils {
    public static String changeUrl(String url) {
        return "u" + DigestUtils.md5Hex(url);
    }

    public static AppInfo getInfo(Context context) {
        AppInfo appInfo;
        String url = "https://gitee.com/colalp/config/raw/master/go/info";
        DataCache dataCache = SQLite.select()
                //查询第一个
                .from(DataCache.class)
                .where(DataCache_Table.url.eq(changeUrl(url)))
                .querySingle();
        boolean isEmpty = dataCache == null;
        if (isEmpty || System.currentTimeMillis() / 1000 - dataCache.getLast() > 3600) {
            try {
                String result = OkGo.<ResponseBody>get(url)
                        .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                        .cacheTime(3600)
                        .retryCount(3)
                        .execute().body().string();
                appInfo = JSON.parseObject(result, AppInfo.class);
            } catch (IOException e) {
                e.printStackTrace();
                if (!isEmpty) {
                    appInfo = JSON.parseObject(dataCache.getData(), AppInfo.class);
                } else {
                     appInfo=new AppInfo("","",AppUtils.getVersionCode(context),"https://cdn.jsdelivr.net/gh/colally/app/go/app/new_year_one.png","http://fun.qgfun.com",false,1);
                }
            }
        } else {
            appInfo = JSON.parseObject(dataCache.getData(), AppInfo.class);
        }

        return appInfo;
    }

    public static List<DouBanVideoInfo.DataBean> getDouBanVideoIndex(String category, int page) {
        String url = "https://movie.douban.com/j/new_search_subjects?sort=U&range=0,10&tags=" + category + "&start=" + page;
        DataCache dataCache = SQLite.select()
                //查询第一个
                .from(DataCache.class)
                .where(DataCache_Table.url.eq(changeUrl(url)))
                .querySingle();
        boolean isEmpty = dataCache == null;
        List<DouBanVideoInfo.DataBean> list = new ArrayList<>();
        if (isEmpty || System.currentTimeMillis() / 1000 - dataCache.getLast() > 1800) {
            try {
                String result = OkGo.<ResponseBody>get(url)
                        .cacheMode(CacheMode.DEFAULT)
                        .cacheTime(-1)
                        .retryCount(3)
                        .headers("Referer", "https://movie.douban.com/tag/")
                        .headers("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36")
                        .execute().body().string();
                DouBanVideoInfo videoInfo = JSON.parseObject(result, DouBanVideoInfo.class);
                if (videoInfo != null) {
                    list = videoInfo.getData();
                    if (list != null && list.size() > 0) {
                        if (isEmpty) {
                            new DataCache.Builder().url(changeUrl(url)).data(JSON.toJSONString(list)).last(System.currentTimeMillis() / 1000).build().save();
                        } else {
                            dataCache.setData(JSON.toJSONString(list));
                            dataCache.setLast(System.currentTimeMillis() / 1000);
                            dataCache.update();
                        }
                    } else {
                        Logger.e("DouBanVideoInfo.DataBean is null");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (!isEmpty) {
                    list = JSON.parseArray(dataCache.getData(), DouBanVideoInfo.DataBean.class);
                }
            }
        } else {
            list = JSON.parseArray(dataCache.getData(), DouBanVideoInfo.DataBean.class);
        }
        return list;
    }


    public static List<DouBanVideoInfo.DataBean> getDouBanVideoCategoryIndex(String category, String type, String area, String year, int page) {
        String url = "https://movie.douban.com/j/new_search_subjects?sort=U&range=0,10&tags=" + category + "&start=" + page + "&genres=" + type + "&countries=" + area + "&year_range=" + year;
        DataCache dataCache = SQLite.select()
                //查询第一个
                .from(DataCache.class)
                .where(DataCache_Table.url.eq(changeUrl(url)))
                .querySingle();
        boolean isEmpty = dataCache == null;
        List<DouBanVideoInfo.DataBean> list = new ArrayList<>();
        if (isEmpty || System.currentTimeMillis() / 1000 - dataCache.getLast() > 1800) {
            try {
                String result = OkGo.<ResponseBody>get(url)
                        .cacheMode(CacheMode.DEFAULT)
                        .cacheTime(-1)
                        .retryCount(3)
                        .headers("Referer", "https://movie.douban.com/tag/")
                        .headers("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36")
                        .execute().body().string();
                DouBanVideoInfo videoInfo = JSON.parseObject(result, DouBanVideoInfo.class);
                if (videoInfo != null) {
                    list = videoInfo.getData();
                    if (list != null && list.size() > 0) {
                        if (isEmpty) {
                            new DataCache.Builder().url(changeUrl(url)).data(JSON.toJSONString(list)).last(System.currentTimeMillis() / 1000).build().save();
                        } else {
                            dataCache.setData(JSON.toJSONString(list));
                            dataCache.setLast(System.currentTimeMillis() / 1000);
                            dataCache.update();
                        }
                    } else {
                        Logger.e("DouBanVideoInfo.DataBean is null");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (!isEmpty) {
                    list = JSON.parseArray(dataCache.getData(), DouBanVideoInfo.DataBean.class);
                }
            }
        } else {
            list = JSON.parseArray(dataCache.getData(), DouBanVideoInfo.DataBean.class);
        }
        return list;
    }


    public static Map<String, UrlResources> getUrlSource() {
        Map<String, UrlResources> yuan = new HashMap<>();
        yuan.put("ok", new UrlResources("ok","http://cj.okzy.tv/inc/apickm3u8s.php", "OK云"));
        yuan.put("kk", new UrlResources("kk","http://caiji.kuyun98.com/inc/s_ldg_kkm3u8.php", "酷云"));
        yuan.put("zuida",new UrlResources("zuida","http://www.zdziyuan.com/inc/s_api_zuidam3u8.php", "最大云"));
        yuan.put("605", new UrlResources("605","http://www.605zy.co/inc/605m3u8.php", "65云"));
        yuan.put("yj", new UrlResources("yj","http://cj.yongjiuzyw.com/inc/yjm3u8.php", "永久云"));
        yuan.put("xin", new UrlResources("xin","http://api.zuixinapi.com/inc/apixinm3u8.php", "最新云"));
        yuan.put("ali", new UrlResources("ali","http://zy.haodanxia.com/api.php/provide/vod/at/xml/", "阿里云"));
        yuan.put("mahua", new UrlResources("mahua","https://www.mhapi123.com/inc/api.php", "麻花云"));
        yuan.put("bj", new UrlResources("bj", "http://cj.bajiecaiji.com/inc/seabjm3u8.php", "背景云"));
        yuan.put("88", new UrlResources("88","http://www.88zyw.net/inc/m3u8.php", "88云"));
        return yuan;
    }

    /*public static String getFrom(String key) {
        Map<String, String> yuan = new HashMap<>();
        yuan.put("OK云", "ok");
        yuan.put("酷云", "kk");
        yuan.put("最大云", "zuida");
        yuan.put("65云", "605");
        yuan.put("永久云", "yj");
        yuan.put("最新云", "xin");
        yuan.put("阿里云", "ali");
        yuan.put("麻花云", "mahua");
        yuan.put("背景云", "bj");
        yuan.put("88云", "88");
        return yuan.get(key);
    }*/

    /*public static String[] getFrom() {
        return new String[]{"ok", "kk", "zuida", "605", "yj", "605",  "xin", "ali", "mahua", "bj", "88"};
    }*/

    public static List<VideoIndex> getVideoIndex(String key, String tid, String page, UrlResources sources) {

        List<VideoIndex> list = new ArrayList<>();
        try {
            Document document = HtmlUtils.getHtml(String.format("%s?ac=list&t=%s&pg=%s&h=&ids=&wd=%s", sources.getUrl(), tid, page, key), false, "http://baidu.com");

            for (Element element : document.getElementsByTag("video")) {
                try {
                    String id = element.getElementsByTag("id").first().text();
                    String name = element.getElementsByTag("name").first().text();
                    String type = element.getElementsByTag("type").first().text();
                    //String pic = element.getElementsByTag("pic").first().text();
                    //String actor = element.getElementsByTag("actor").first().text();
                    //String director = element.getElementsByTag("director").first().text();
                    //String des = element.getElementsByTag("des").first().text();
                    String last = element.getElementsByTag("last").first().text();
                    Log.i("getVideoIndex:%s", "id:" + id + ",name:" + name + ",type:" + type + ",last:" + last);
                    list.add(new VideoIndex(id, name, type, sources.getFrom(), last));
                } catch (NullPointerException e) {
                    Logger.e(e.getMessage());
                }
            }
        } catch (Exception e) {
            Logger.e(e.toString());
        }
        return list;
    }

    public static List<VideoDetail> getVideoDetail(String id, UrlResources sources) {

        List<VideoDetail> list = new ArrayList<>();
        try {

            Document document = HtmlUtils.getHtml(String.format("%s?ac=videolist&ids=%s", sources.getUrl(), id), false, "http://baidu.com");

            for (Element element : document.getElementsByTag("video")) {
                try {
                    String ids = element.getElementsByTag("id").first().text();
                    String name = element.getElementsByTag("name").first().text();
                    String type = element.getElementsByTag("type").first().text();
                    String pic = element.getElementsByTag("pic").first().text();
                    String note = element.getElementsByTag("note").first().text();
                    String actor = element.getElementsByTag("actor").first().text();
                    String director = element.getElementsByTag("director").first().text();
                    String des = element.getElementsByTag("des").first().text();
                    String videoUrls = element.getElementsByTag("dd").first().text();

                    List<VideoUrl> videoUrlList = new ArrayList<>();
                    for (String s : videoUrls.split("#")) {
                        String[] vu = s.split("\\$");
                        videoUrlList.add(new VideoUrl(vu[0], vu[1]));
                    }
                    String last = element.getElementsByTag("last").first().text();
                    list.add(new VideoDetail(ids, name, type, pic, note, actor, director, des, videoUrlList, sources, last));
                } catch (NullPointerException e) {
                    Logger.e(e.getMessage());
                }
            }

        } catch (Exception e) {
            Logger.e(e.toString());
        }
        return list;
    }

    public static List<VideoDetail> newVideo(UrlResources sources, String tid, String pg) {
        List<VideoDetail> list = new ArrayList<>();
        //影视数据
        DataCache dataCache = SQLite.select()
                //查询第一个
                .from(DataCache.class)
                .where(DataCache_Table.url.eq(changeUrl(sources.getUrl() + tid+pg)))
                .querySingle();
        boolean isEmpty = dataCache == null;
        if (isEmpty || System.currentTimeMillis() / 1000 - dataCache.getLast() > 10 * 60) {
            try {

                Document document = HtmlUtils.getHtml(String.format("%s?ac=videolist&t=%s&pg=%s", sources.getUrl(), tid, pg), false, "http://baidu.com");
                String pagecount = document.getElementsByTag("list").first().attr("pagecount");
                //当前分类页码数据
                DataCache dataCache1 = SQLite.select()
                        //查询第一个
                        .from(DataCache.class)
                        .where(DataCache_Table.url.eq(changeUrl(sources.getUrl() + tid)))
                        .querySingle();
                if (dataCache1 == null) {
                    new DataCache.Builder().url(changeUrl(sources.getUrl() + tid)).data(pagecount).last(System.currentTimeMillis() / 1000).build().save();
                } else {
                    dataCache1.setData(pagecount);
                    dataCache1.setLast(System.currentTimeMillis() / 1000);
                    dataCache1.update();
                }
                Logger.e("pagecount：%s", pagecount);
                for (Element element : document.getElementsByTag("video")) {
                    try {
                        String ids = element.getElementsByTag("id").first().text();
                        String name = element.getElementsByTag("name").first().text();
                        String type = element.getElementsByTag("type").first().text();
                        String pic = element.getElementsByTag("pic").first().text();
                        String note = element.getElementsByTag("note").first().text();
                        String actor = element.getElementsByTag("actor").first().text();
                        String director = element.getElementsByTag("director").first().text();
                        String des = element.getElementsByTag("des").first().text();
                        String videoUrls = element.getElementsByTag("dd").first().text();

                        List<VideoUrl> videoUrlList = new ArrayList<>();
                        for (String s : videoUrls.split("#")) {
                            String[] vu = s.split("\\$");
                            videoUrlList.add(new VideoUrl(vu[0], vu[1]));
                        }
                        String last = element.getElementsByTag("last").first().text();
                        list.add(new VideoDetail(ids, name, type, pic, note, actor, director, des, videoUrlList, sources, last));
                    } catch (NullPointerException e) {
                        Log.e("NullPointerException", e.getMessage());
                    }
                }
                //保存数据到数据库
                if (list.size() > 0) {
                    if (isEmpty) {
                        new DataCache.Builder().url(changeUrl(sources.getUrl() + tid+pg)).data(JSON.toJSONString(list)).last(System.currentTimeMillis() / 1000).build().save();
                    } else {
                        dataCache.setData(JSON.toJSONString(list));
                        dataCache.setLast(System.currentTimeMillis() / 1000);
                        dataCache.update();
                    }
                } else {
                    Logger.e("VideoDetail is null");
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (!isEmpty) {
                    list = JSON.parseArray(dataCache.getData(), VideoDetail.class);
                }
            }

        } else {
            list = JSON.parseArray(dataCache.getData(), VideoDetail.class);
        }

        return list;
    }

    public static List<VideoDetail> search(String key, UrlResources sources) {
        if (sources==null) {
            sources=getUrlSource().get("zuida");
        }

        StringBuilder ids = new StringBuilder();
        for (VideoIndex videoIndex : getVideoIndex(key, "", "", sources)) {
            ids.append(videoIndex.getId()).append(",");
        }
        return getVideoDetail(ids.deleteCharAt(ids.length() - 1).toString(), sources);
    }

    public static List<TvInfo.DataBean> getTvIndex(String id) {
        String url = "http://api.qgfun.com/v1/tv/iptv/" + id;
        DataCache dataCache = SQLite.select()
                //查询第一个
                .from(DataCache.class)
                .where(DataCache_Table.url.eq(changeUrl(url)))
                .querySingle();
        boolean isEmpty = dataCache == null;
        List<TvInfo.DataBean> list = new ArrayList<>();
        if (isEmpty || System.currentTimeMillis() / 1000 - dataCache.getLast() > 30 * 24 * 3600) {
            try {
                String result = OkGo.<ResponseBody>get(url)
                        .cacheMode(CacheMode.DEFAULT)
                        .cacheTime(-1)
                        .retryCount(3)
                        .headers("Referer", "https://go.qgfun.com")
                        .headers("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36")
                        .execute().body().string();
                TvInfo tvInfo = JSON.parseObject(result, TvInfo.class);
                if (tvInfo != null) {
                    list = tvInfo.getData();
                    if (list != null && list.size() > 0) {
                        if (isEmpty) {
                            new DataCache.Builder().url(changeUrl(url)).data(JSON.toJSONString(list)).last(System.currentTimeMillis() / 1000).build().save();
                        } else {
                            dataCache.setData(JSON.toJSONString(list));
                            dataCache.setLast(System.currentTimeMillis() / 1000);
                            dataCache.update();
                        }
                    } else {
                        Logger.e("DouBanVideoInfo.DataBean is null");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (!isEmpty) {
                    list = JSON.parseArray(dataCache.getData(), TvInfo.DataBean.class);
                }
            }
        } else {
            list = JSON.parseArray(dataCache.getData(), TvInfo.DataBean.class);
        }
        return list;
    }

    public static TvPlayInfo.DataBean getTvPlayUrl(String tid, String id) {
        String url = "http://api.qgfun.com/v1/tv/iptv/" +tid+"/"+ id;
        DataCache dataCache = SQLite.select()
                //查询第一个
                .from(DataCache.class)
                .where(DataCache_Table.url.eq(changeUrl(url)))
                .querySingle();
        boolean isEmpty = dataCache == null;
        TvPlayInfo.DataBean bean = null;
        if (isEmpty || System.currentTimeMillis() / 1000 - dataCache.getLast() > 15*60) {
            try {
                String result = OkGo.<ResponseBody>get(url)
                        .cacheMode(CacheMode.DEFAULT)
                        .cacheTime(-1)
                        .retryCount(3)
                        .headers("Referer", "https://go.qgfun.com")
                        .headers("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36")
                        .execute().body().string();
                TvPlayInfo tvInfo = JSON.parseObject(result, TvPlayInfo.class);
                if (tvInfo != null) {
                    bean= tvInfo.getData();
                    if (bean != null && bean.getUrls()!=null&&bean.getUrls().size()>0) {
                        if (isEmpty) {
                            new DataCache.Builder().url(changeUrl(url)).data(JSON.toJSONString(bean)).last(System.currentTimeMillis() / 1000).build().save();
                        } else {
                            dataCache.setData(JSON.toJSONString(bean));
                            dataCache.setLast(System.currentTimeMillis() / 1000);
                            dataCache.update();
                        }
                    } else {
                        Logger.e("DouBanVideoInfo.DataBean is null");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (!isEmpty) {
                    bean = JSON.parseObject(dataCache.getData(), TvPlayInfo.DataBean.class);
                }
            }
        } else {
            bean = JSON.parseObject(dataCache.getData(), TvPlayInfo.DataBean.class);
        }
        return bean;

    }
}
