package com.qgfun.go.entity;

import com.alibaba.fastjson.JSONObject;
import com.qgfun.go.util.Log;

import java.io.Serializable;
import java.util.List;

/**
 * @author LLY
 * date: 2020/4/11 18:20
 * package name: com.qgfun.beauty.entity
 */

public class AppInfo implements Serializable {

    /**
     * note : 1、新增下载功能，播放页面长按播放地址即可下载<br>2、修复TV播放的Bug<br>3、如有问题请卸载重新安装最新版
     * tip : 通知：测试测试测试测试
     * version : 23
     * splash : https://cdn.jsdelivr.net/gh/colally/app/go/app/new_year_one.png
     * download : http://fun.qgfun.com
     * force : false
     * session : 2
     * show : ok
     * resources : [{"code":"ok","url":"https://www.apiokzy.com/inc/apickm3u8s_subname.php","name":"OK云","status":true,"category":[{"show":true,"id":3,"name":"综艺片"},{"show":true,"id":4,"name":"动漫片"},{"show":true,"id":5,"name":"动作片"},{"show":true,"id":6,"name":"喜剧片"},{"show":true,"id":7,"name":"爱情片"},{"show":true,"id":8,"name":"科幻片"},{"show":true,"id":9,"name":"恐怖片"},{"show":true,"id":10,"name":"剧情片"},{"show":true,"id":11,"name":"战争片"},{"show":true,"id":12,"name":"国产剧"},{"show":true,"id":13,"name":"香港剧"},{"show":true,"id":14,"name":"韩国剧"},{"show":true,"id":15,"name":"欧美剧"},{"show":true,"id":16,"name":"台湾剧"},{"show":true,"id":17,"name":"日本剧"},{"show":true,"id":18,"name":"海外剧"},{"show":true,"id":19,"name":"纪录片"},{"show":true,"id":20,"name":"微电影"},{"show":false,"id":21,"name":"伦理片"},{"show":false,"id":22,"name":"福利片"},{"show":true,"id":23,"name":"国产动漫"},{"show":true,"id":24,"name":"日韩动漫"},{"show":true,"id":25,"name":"欧美动漫"},{"show":true,"id":26,"name":"内地综艺"},{"show":true,"id":27,"name":"港台综艺"},{"show":true,"id":28,"name":"日韩综艺"},{"show":true,"id":29,"name":"欧美综艺"},{"show":true,"id":31,"name":"港台动漫"},{"show":true,"id":32,"name":"海外动漫"},{"show":true,"id":33,"name":"解说"},{"show":true,"id":34,"name":"电影解说"},{"show":true,"id":35,"name":"泰国剧"},{"show":true,"id":36,"name":"动漫电影"}]},{"code":"kk","url":"http://caiji.kuyun98.com/inc/s_ldg_kkm3u8.php","name":"酷云","status":true,"category":[{"show":true,"id":3,"name":"综艺节目"},{"show":true,"id":4,"name":"动漫剧场"},{"show":true,"id":5,"name":"动作片"},{"show":true,"id":6,"name":"喜剧片"},{"show":true,"id":7,"name":"爱情片"},{"show":true,"id":8,"name":"科幻片"},{"show":true,"id":9,"name":"恐怖片"},{"show":true,"id":10,"name":"剧情片"},{"show":true,"id":11,"name":"战争片"},{"show":true,"id":12,"name":"国产剧"},{"show":true,"id":13,"name":"香港剧"},{"show":true,"id":14,"name":"台湾剧"},{"show":true,"id":15,"name":"日本剧"},{"show":true,"id":16,"name":"动画片"},{"show":true,"id":17,"name":"微电影"},{"show":true,"id":18,"name":"记录片"},{"show":true,"id":19,"name":"韩国剧"},{"show":true,"id":20,"name":"欧美剧"},{"show":true,"id":21,"name":"海外剧"},{"show":false,"id":22,"name":"伦理片"}]},{"code":"zuida","url":"http://www.zdziyuan.com/inc/s_api_zuidam3u8.php","name":"最大云","status":true,"category":[{"show":true,"id":10,"name":"剧情片"},{"show":true,"id":11,"name":"战争片"},{"show":true,"id":12,"name":"国产剧"},{"show":true,"id":13,"name":"香港剧"},{"show":true,"id":14,"name":"韩国剧"},{"show":true,"id":15,"name":"欧美剧"},{"show":false,"id":16,"name":"福利片"},{"show":false,"id":17,"name":"伦理片"},{"show":true,"id":18,"name":"音乐片"},{"show":true,"id":19,"name":"台湾剧"},{"show":true,"id":20,"name":"日本剧"},{"show":true,"id":21,"name":"海外剧"},{"show":true,"id":22,"name":"记录片"},{"show":true,"id":3,"name":"综艺片"},{"show":true,"id":4,"name":"动漫片"},{"show":true,"id":5,"name":"动作片"},{"show":true,"id":6,"name":"喜剧片"},{"show":true,"id":7,"name":"爱情片"},{"show":true,"id":8,"name":"科幻片"},{"show":true,"id":9,"name":"恐怖片"}]},{"code":"yj","url":"http://cj.yongjiuzyw.com/inc/yjm3u8.php","name":"永久云","status":true,"category":[{"show":true,"id":3,"name":"综艺"},{"show":true,"id":4,"name":"动漫"},{"show":true,"id":5,"name":"动作片"},{"show":true,"id":6,"name":"喜剧片"},{"show":true,"id":7,"name":"爱情片"},{"show":true,"id":8,"name":"科幻片"},{"show":true,"id":9,"name":"恐怖片"},{"show":true,"id":10,"name":"剧情片"},{"show":true,"id":11,"name":"战争片"},{"show":true,"id":12,"name":"国产剧"},{"show":true,"id":13,"name":"港剧"},{"show":true,"id":14,"name":"日剧"},{"show":true,"id":15,"name":"欧美剧"},{"show":true,"id":16,"name":"韩剧"},{"show":true,"id":17,"name":"台剧"},{"show":false,"id":18,"name":"伦理片"},{"show":false,"id":19,"name":"美女写真"},{"show":false,"id":20,"name":"嫩妹写真"},{"show":false,"id":21,"name":"美女视频秀"},{"show":true,"id":22,"name":"泰剧"},{"show":false,"id":23,"name":"街拍系列"},{"show":false,"id":24,"name":"高跟赤足视频"},{"show":false,"id":25,"name":"VIP视频秀"},{"show":true,"id":26,"name":"纪录片"}]},{"code":"xin","url":"http://api.zuixinapi.com/inc/apixinm3u8.php","name":"最新云","status":true,"category":[{"show":true,"id":3,"name":"综艺片"},{"show":true,"id":4,"name":"动漫片"},{"show":true,"id":5,"name":"动作片"},{"show":true,"id":6,"name":"喜剧片"},{"show":true,"id":7,"name":"爱情片"},{"show":true,"id":8,"name":"科幻片"},{"show":true,"id":9,"name":"恐怖片"},{"show":true,"id":10,"name":"剧情片"},{"show":true,"id":11,"name":"战争片"},{"show":true,"id":12,"name":"国产剧"},{"show":true,"id":13,"name":"香港剧"},{"show":true,"id":14,"name":"韩国剧"},{"show":true,"id":15,"name":"欧美剧"},{"show":true,"id":16,"name":"台湾剧"},{"show":true,"id":17,"name":"日本剧"},{"show":true,"id":18,"name":"海外剧"},{"show":true,"id":19,"name":"纪录片"},{"show":false,"id":21,"name":"伦理片"},{"show":true,"id":22,"name":"犯罪片"},{"show":false,"id":23,"name":"情色片"},{"show":true,"id":24,"name":"奇幻片"},{"show":true,"id":25,"name":"惊悚片"},{"show":true,"id":27,"name":"传记片"},{"show":true,"id":28,"name":"悬疑片"},{"show":true,"id":34,"name":"解说"},{"show":true,"id":35,"name":"电影说"}]},{"code":"ali","url":"http://zy.haodanxia.com/api.php/provide/vod/at/xml/","name":"阿里云","status":true,"category":[{"show":true,"id":3,"name":"综艺"},{"show":true,"id":4,"name":"动漫"},{"show":true,"id":6,"name":"动作片"},{"show":true,"id":7,"name":"喜剧片"},{"show":true,"id":8,"name":"爱情片"},{"show":true,"id":9,"name":"科幻片"},{"show":true,"id":10,"name":"恐怖片"},{"show":true,"id":11,"name":"剧情片"},{"show":true,"id":12,"name":"战争片"},{"show":true,"id":13,"name":"国产剧"},{"show":true,"id":14,"name":"港台剧"},{"show":true,"id":15,"name":"日韩剧"},{"show":true,"id":16,"name":"欧美剧"},{"show":true,"id":20,"name":"动漫电影"},{"show":true,"id":21,"name":"纪录片"},{"show":true,"id":35,"name":"海外剧"},{"show":true,"id":36,"name":"国产动漫"},{"show":true,"id":37,"name":"日韩动漫"}]},{"code":"mahua","url":"https://www.mhapi123.com/inc/api.php","name":"麻花云","status":true,"category":[{"show":true,"id":3,"name":"综艺"},{"show":true,"id":4,"name":"动漫"},{"show":true,"id":5,"name":"动作片"},{"show":true,"id":6,"name":"喜剧片"},{"show":true,"id":7,"name":"爱情片"},{"show":true,"id":8,"name":"科幻片"},{"show":true,"id":9,"name":"恐怖片"},{"show":true,"id":10,"name":"剧情片"},{"show":true,"id":11,"name":"战争片"},{"show":true,"id":12,"name":"国产剧"},{"show":true,"id":13,"name":"台湾剧"},{"show":true,"id":14,"name":"韩剧"},{"show":true,"id":15,"name":"欧美剧"},{"show":true,"id":16,"name":"香港剧"},{"show":true,"id":17,"name":"泰剧"},{"show":true,"id":18,"name":"日剧"},{"show":false,"id":19,"name":"福利片"},{"show":true,"id":20,"name":"记录片"},{"show":false,"id":49,"name":"倫理"},{"show":true,"id":48,"name":"国产短剧"},{"show":true,"id":42,"name":"少儿频道"},{"show":true,"id":43,"name":"音乐MV"},{"show":true,"id":44,"name":"预告片"},{"show":true,"id":47,"name":"欧美动漫"},{"show":true,"id":46,"name":"搞笑片"},{"show":true,"id":45,"name":"游戏解说"},{"show":true,"id":41,"name":"动画片"},{"show":true,"id":38,"name":"短视频"},{"show":true,"id":39,"name":"日韩动漫"},{"show":true,"id":40,"name":"国产动漫"},{"show":false,"id":51,"name":"港台倫理"},{"show":false,"id":52,"name":"日韩倫理"},{"show":false,"id":53,"name":"欧美倫理"},{"show":true,"id":54,"name":"海外剧"},{"show":false,"id":55,"name":"动漫伦理"}]},{"code":"bj","url":"http://cj.bajiecaiji.com/inc/seabjm3u8.php","name":"背景云","status":true,"category":[{"show":true,"id":3,"name":"综艺"},{"show":true,"id":4,"name":"动漫"},{"show":true,"id":5,"name":"动作片"},{"show":true,"id":6,"name":"喜剧片"},{"show":true,"id":7,"name":"爱情片"},{"show":true,"id":8,"name":"科幻片"},{"show":true,"id":9,"name":"恐怖片"},{"show":true,"id":10,"name":"奇幻片"},{"show":true,"id":11,"name":"剧情片"},{"show":true,"id":12,"name":"国产剧"},{"show":true,"id":13,"name":"香港剧"},{"show":true,"id":14,"name":"台湾剧"},{"show":true,"id":15,"name":"韩国剧"},{"show":true,"id":16,"name":"日本剧"},{"show":true,"id":17,"name":"欧美剧"},{"show":true,"id":19,"name":"微电影"},{"show":true,"id":42,"name":"海外剧"},{"show":false,"id":46,"name":"VIP福利"},{"show":true,"id":47,"name":"写真美女"},{"show":true,"id":48,"name":"展会美女"},{"show":true,"id":98,"name":"港台综艺"},{"show":true,"id":97,"name":"国外综艺"},{"show":true,"id":96,"name":"大陆综艺"},{"show":true,"id":53,"name":"韩国美女 "},{"show":true,"id":91,"name":"国产动漫"},{"show":true,"id":92,"name":"日本动漫"},{"show":true,"id":93,"name":"欧美动漫"},{"show":true,"id":94,"name":"其他动漫"},{"show":false,"id":95,"name":"主播美女"},{"show":true,"id":99,"name":"纪录片"},{"show":true,"id":100,"name":"动画片"},{"show":true,"id":101,"name":"战争片"}]},{"code":"88","url":"http://www.88zyw.net/inc/m3u8.php","name":"88云","status":true,"category":[{"show":true,"id":3,"name":"综艺"},{"show":true,"id":4,"name":"动漫"},{"show":true,"id":5,"name":"动作片"},{"show":true,"id":6,"name":"喜剧片"},{"show":true,"id":7,"name":"爱情片"},{"show":true,"id":8,"name":"科幻片"},{"show":true,"id":9,"name":"恐怖片"},{"show":true,"id":10,"name":"剧情片"},{"show":true,"id":11,"name":"战争片"},{"show":true,"id":12,"name":"国产剧"},{"show":true,"id":13,"name":"港台剧"},{"show":true,"id":14,"name":"日韩剧"},{"show":true,"id":15,"name":"欧美剧"},{"show":true,"id":16,"name":"东南亚剧"},{"show":false,"id":17,"name":"伦理"},{"show":false,"id":18,"name":"性感"},{"show":true,"id":21,"name":"日本动漫"},{"show":true,"id":20,"name":"纪录片"},{"show":true,"id":22,"name":"国产动漫"},{"show":true,"id":23,"name":"欧美动漫"},{"show":true,"id":24,"name":"里番"},{"show":true,"id":25,"name":"欧美片"}]}]
     */


    private String note;

    private String tip;

    private Integer version;

    private String splash;

    private String download;

    private Boolean force;

    private Integer session;

    private Integer index;

    private List<Resources> resources;

    public AppInfo() {
    }

    public AppInfo(String note, String tip, Integer version, String splash, String download, Boolean force, Integer session, Integer index, List<Resources> resources) {
        this.note = note;
        this.tip = tip;
        this.version = version;
        this.splash = splash;
        this.download = download;
        this.force = force;
        this.session = session;
        this.index = index;
        this.resources = resources;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getSplash() {
        return splash;
    }

    public void setSplash(String splash) {
        this.splash = splash;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public Boolean getForce() {
        return force;
    }

    public void setForce(Boolean force) {
        this.force = force;
    }

    public Integer getSession() {
        return session;
    }

    public void setSession(Integer session) {
        this.session = session;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public List<Resources> getResources() {
        return resources;
    }

    public void setResources(List<Resources> resources) {
        this.resources = resources;
    }

    public static class Resources implements Serializable{
        /**
         * code : ok
         * url : https://www.apiokzy.com/inc/apickm3u8s_subname.php
         * name : OK云
         * status : true
         * category : [{"show":true,"id":3,"name":"综艺片"},{"show":true,"id":4,"name":"动漫片"},{"show":true,"id":5,"name":"动作片"},{"show":true,"id":6,"name":"喜剧片"},{"show":true,"id":7,"name":"爱情片"},{"show":true,"id":8,"name":"科幻片"},{"show":true,"id":9,"name":"恐怖片"},{"show":true,"id":10,"name":"剧情片"},{"show":true,"id":11,"name":"战争片"},{"show":true,"id":12,"name":"国产剧"},{"show":true,"id":13,"name":"香港剧"},{"show":true,"id":14,"name":"韩国剧"},{"show":true,"id":15,"name":"欧美剧"},{"show":true,"id":16,"name":"台湾剧"},{"show":true,"id":17,"name":"日本剧"},{"show":true,"id":18,"name":"海外剧"},{"show":true,"id":19,"name":"纪录片"},{"show":true,"id":20,"name":"微电影"},{"show":false,"id":21,"name":"伦理片"},{"show":false,"id":22,"name":"福利片"},{"show":true,"id":23,"name":"国产动漫"},{"show":true,"id":24,"name":"日韩动漫"},{"show":true,"id":25,"name":"欧美动漫"},{"show":true,"id":26,"name":"内地综艺"},{"show":true,"id":27,"name":"港台综艺"},{"show":true,"id":28,"name":"日韩综艺"},{"show":true,"id":29,"name":"欧美综艺"},{"show":true,"id":31,"name":"港台动漫"},{"show":true,"id":32,"name":"海外动漫"},{"show":true,"id":33,"name":"解说"},{"show":true,"id":34,"name":"电影解说"},{"show":true,"id":35,"name":"泰国剧"},{"show":true,"id":36,"name":"动漫电影"}]
         */


        private String code;

        private String url;

        private String name;

        private Boolean status;

        private List<Category> category;
        public static Resources jsonToObject(String json){
            Log.i("jsonToObject:%s",json);
            Resources urlResources = JSONObject.parseObject(json, Resources.class);
            Log.i(urlResources.toString());
            return urlResources;
        }

        @Override
        public String toString() {
            return JSONObject.toJSON(this).toString();
        }
        public Resources(String code, String url, String name, Boolean status, List<Category> category) {
            this.code = code;
            this.url = url;
            this.name = name;
            this.status = status;
            this.category = category;
        }

        public Resources() {
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public List<Category> getCategory() {
            return category;
        }

        public void setCategory(List<Category> category) {
            this.category = category;
        }

        public static class Category implements Serializable{
            /**
             * show : true
             * id : 3
             * name : 综艺片
             */


            private Boolean show;

            private Integer id;

            private String name;

            public Category() {
            }

            public Category(Boolean show, Integer id, String name) {
                this.show = show;
                this.id = id;
                this.name = name;
            }

            public Boolean getShow() {
                return show;
            }

            public void setShow(Boolean show) {
                this.show = show;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
