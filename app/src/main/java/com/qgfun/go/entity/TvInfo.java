package com.qgfun.go.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author LLY
 * date: 2020/4/19 17:06
 * package name: com.qgfun.go.entity
 * description：TODO
 */
public class TvInfo {

    /**
     * timestamp : 1587286955
     * code : 200
     * msg : ok
     * data : [{"name":"HBO HD","tid":"itv","id":"9"},{"name":"CHC高清电影","tid":"itv","id":"11"},{"name":"劲爆体育","tid":"itv","id":"13"},{"name":"新视觉HD","tid":"itv","id":"14"},{"name":"五星体育高清","tid":"itv","id":"15"},{"name":"广东体育高清","tid":"itv","id":"16"},{"name":"CCTV1综合高清","tid":"itv","id":"17"},{"name":"CCTV2财经高清","tid":"itv","id":"18"},{"name":"CCTV3综艺高清","tid":"itv","id":"19"},{"name":"CCTV4中文国际高清","tid":"itv","id":"20"},{"name":"CCTV5体育高清","tid":"itv","id":"21"},{"name":"CCTV5+体育赛事高清","tid":"itv","id":"22"},{"name":"CCTV6电影高清","tid":"itv","id":"23"},{"name":"CCTV7军事高清","tid":"itv","id":"24"},{"name":"CCTV8电视剧高清","tid":"itv","id":"25"},{"name":"CCTV9纪录高清","tid":"itv","id":"26"},{"name":"CCTV10科教高清","tid":"itv","id":"27"},{"name":"CCTV11戏曲高清","tid":"itv","id":"28"},{"name":"CCTV12社会与法高清","tid":"itv","id":"29"},{"name":"CCTV13新闻","tid":"itv","id":"30"},{"name":"CCTV14少儿高清","tid":"itv","id":"31"},{"name":"CCTV15音乐高清","tid":"itv","id":"32"},{"name":"CCTV17农业农村高清","tid":"itv","id":"33"},{"name":"CGTN","tid":"itv","id":"34"},{"name":"CGTN纪录","tid":"itv","id":"35"},{"name":"CCTV阿拉伯语","tid":"itv","id":"36"},{"name":"CCTV法语","tid":"itv","id":"37"},{"name":"CCTV西班牙语","tid":"itv","id":"38"},{"name":"CCTV俄语","tid":"itv","id":"39"},{"name":"CETV-1高清","tid":"itv","id":"40"},{"name":"CETV-2","tid":"itv","id":"41"},{"name":"CETV-3","tid":"itv","id":"42"},{"name":"CETV-4","tid":"itv","id":"43"},{"name":"湖南卫视高清","tid":"itv","id":"45"},{"name":"江苏卫视高清","tid":"itv","id":"46"},{"name":"浙江卫视高清","tid":"itv","id":"47"},{"name":"东方卫视高清","tid":"itv","id":"48"},{"name":"北京卫视高清","tid":"itv","id":"49"},{"name":"深圳卫视高清","tid":"itv","id":"50"},{"name":"广东卫视高清","tid":"itv","id":"51"},{"name":"安徽卫视高清","tid":"itv","id":"52"},{"name":"东南卫视高清","tid":"itv","id":"53"},{"name":"河北卫视高清","tid":"itv","id":"54"},{"name":"黑龙江卫视高清","tid":"itv","id":"55"},{"name":"湖北卫视高清","tid":"itv","id":"56"},{"name":"江西卫视高清","tid":"itv","id":"57"},{"name":"辽宁卫视高清","tid":"itv","id":"58"},{"name":"海南卫视高清","tid":"itv","id":"59"},{"name":"山东卫视高清","tid":"itv","id":"60"},{"name":"四川卫视高清","tid":"itv","id":"61"},{"name":"天津卫视高清","tid":"itv","id":"62"},{"name":"重庆卫视高清","tid":"itv","id":"63"},{"name":"贵州卫视高清","tid":"itv","id":"64"},{"name":"吉林卫视高清","tid":"itv","id":"65"},{"name":"广西卫视高清","tid":"itv","id":"66"},{"name":"河南卫视高清","tid":"itv","id":"67"},{"name":"甘肃卫视","tid":"itv","id":"68"},{"name":"青海卫视","tid":"itv","id":"69"},{"name":"云南卫视","tid":"itv","id":"70"},{"name":"内蒙古卫视","tid":"itv","id":"71"},{"name":"山西卫视","tid":"itv","id":"72"},{"name":"陕西卫视","tid":"itv","id":"73"},{"name":"兵团卫视","tid":"itv","id":"74"},{"name":"新疆卫视","tid":"itv","id":"75"},{"name":"西藏卫视","tid":"itv","id":"76"},{"name":"宁夏卫视","tid":"itv","id":"77"},{"name":"延边卫视","tid":"itv","id":"78"},{"name":"康巴卫视","tid":"itv","id":"79"},{"name":"北京纪实高清","tid":"itv","id":"81"},{"name":"北京卡酷少儿","tid":"itv","id":"82"},{"name":"北京新闻","tid":"itv","id":"83"},{"name":"北京生活","tid":"itv","id":"84"},{"name":"北京影视","tid":"itv","id":"85"},{"name":"北京财经","tid":"itv","id":"86"},{"name":"北京体育","tid":"itv","id":"87"},{"name":"北京青年","tid":"itv","id":"88"},{"name":"北京科教","tid":"itv","id":"89"},{"name":"北京文艺","tid":"itv","id":"90"},{"name":"珠江频道","tid":"itv","id":"91"},{"name":"广东新闻","tid":"itv","id":"92"},{"name":"广东公共","tid":"itv","id":"93"},{"name":"广东国际","tid":"itv","id":"94"},{"name":"广东房产","tid":"itv","id":"95"},{"name":"广东会展","tid":"itv","id":"96"},{"name":"广东经视 TVS1","tid":"itv","id":"97"},{"name":"南方卫视 TVS2","tid":"itv","id":"98"},{"name":"广东综艺 TVS3","tid":"itv","id":"99"},{"name":"广东影视 TVS4","tid":"itv","id":"100"},{"name":"广东少儿 TVS5","tid":"itv","id":"101"},{"name":"嘉佳卡通","tid":"itv","id":"102"},{"name":"高尔夫频道","tid":"itv","id":"103"},{"name":"广州电视综合","tid":"itv","id":"104"},{"name":"广州新闻","tid":"itv","id":"105"},{"name":"广州经济","tid":"itv","id":"106"},{"name":"广州影视","tid":"itv","id":"107"},{"name":"广州生活","tid":"itv","id":"108"},{"name":"广州竞赛","tid":"itv","id":"109"},{"name":"广州少儿","tid":"itv","id":"110"},{"name":"深圳都市","tid":"itv","id":"112"},{"name":"深圳DV生活","tid":"itv","id":"113"},{"name":"深圳体育健康","tid":"itv","id":"114"},{"name":"深圳电视剧","tid":"itv","id":"115"},{"name":"深圳少儿","tid":"itv","id":"116"},{"name":"深圳娱乐","tid":"itv","id":"117"},{"name":"深圳公共","tid":"itv","id":"118"},{"name":"深圳财经生活","tid":"itv","id":"119"},{"name":"东莞综合","tid":"itv","id":"120"},{"name":"佛山综合","tid":"itv","id":"121"},{"name":"佛山公共","tid":"itv","id":"122"},{"name":"珠海-1","tid":"itv","id":"123"},{"name":"珠海-2","tid":"itv","id":"124"},{"name":"中山综合","tid":"itv","id":"125"},{"name":"肇庆综合","tid":"itv","id":"126"},{"name":"台山新闻","tid":"itv","id":"127"},{"name":"汕头1","tid":"itv","id":"128"},{"name":"惠州-1","tid":"itv","id":"129"},{"name":"惠州-2","tid":"itv","id":"130"},{"name":"上海都市频道高清","tid":"itv","id":"131"},{"name":"上海新闻综合高清","tid":"itv","id":"132"},{"name":"上海电视剧高清","tid":"itv","id":"133"},{"name":"上海新闻综合","tid":"itv","id":"134"},{"name":"上海纪实频道高清","tid":"itv","id":"135"},{"name":"上海外语","tid":"itv","id":"136"},{"name":"上海第一财经","tid":"itv","id":"137"},{"name":"上海艺术人文","tid":"itv","id":"138"},{"name":"游戏风云高清","tid":"itv","id":"139"},{"name":"优漫卡通","tid":"itv","id":"140"},{"name":"法治天地","tid":"itv","id":"141"},{"name":"茶频道","tid":"itv","id":"142"},{"name":"快乐垂钓","tid":"itv","id":"144"},{"name":"先锋乒羽","tid":"itv","id":"145"},{"name":"湖南国际","tid":"itv","id":"146"},{"name":"湖南电视剧","tid":"itv","id":"147"},{"name":"金鹰卡通","tid":"itv","id":"148"},{"name":"金鹰纪实","tid":"itv","id":"149"},{"name":"湖南经视","tid":"itv","id":"150"},{"name":"湖南娱乐","tid":"itv","id":"151"},{"name":"湖南都市","tid":"itv","id":"152"},{"name":"湖南公共","tid":"itv","id":"153"},{"name":"湖南快乐购","tid":"itv","id":"154"},{"name":"长沙政法","tid":"itv","id":"155"},{"name":"长沙女性","tid":"itv","id":"156"},{"name":"长沙新闻","tid":"itv","id":"157"},{"name":"四川峨眉电影高清","tid":"itv","id":"171"},{"name":"四川2文化旅游高清","tid":"itv","id":"172"},{"name":"四川3经济频道高清","tid":"itv","id":"173"},{"name":"四川4新闻综合","tid":"itv","id":"174"},{"name":"四川5影视文艺高清","tid":"itv","id":"175"},{"name":"四川6星空购物","tid":"itv","id":"176"},{"name":"四川7妇女儿童高清","tid":"itv","id":"177"},{"name":"河北经济","tid":"itv","id":"178"},{"name":"河北都市","tid":"itv","id":"179"},{"name":"河北影视","tid":"itv","id":"180"},{"name":"河北少儿","tid":"itv","id":"181"},{"name":"河北公共","tid":"itv","id":"182"},{"name":"河北农民","tid":"itv","id":"183"},{"name":"湖北综合","tid":"itv","id":"184"},{"name":"湖北影视","tid":"itv","id":"185"},{"name":"湖北教育","tid":"itv","id":"186"},{"name":"湖北生活","tid":"itv","id":"187"},{"name":"湖北公共新闻","tid":"itv","id":"188"},{"name":"湖北经视","tid":"itv","id":"189"},{"name":"武汉-1 新闻","tid":"itv","id":"190"},{"name":"武汉-2 电视剧","tid":"itv","id":"191"},{"name":"武汉-3 科技生活","tid":"itv","id":"192"},{"name":"武汉-4 经济","tid":"itv","id":"193"},{"name":"武汉-5 文体","tid":"itv","id":"194"},{"name":"武汉-6 外语","tid":"itv","id":"195"},{"name":"武汉-7 少儿","tid":"itv","id":"196"},{"name":"苏州-1 新闻综合","tid":"itv","id":"197"},{"name":"苏州-2 社会经济","tid":"itv","id":"198"},{"name":"苏州-3 文化生活","tid":"itv","id":"199"},{"name":"苏州-5 生活资讯","tid":"itv","id":"200"},{"name":"苏州太仓新闻","tid":"itv","id":"201"},{"name":"苏州太仓娄东民生","tid":"itv","id":"202"},{"name":"苏州太仓党建","tid":"itv","id":"203"},{"name":"苏州昆山新闻综合","tid":"itv","id":"204"},{"name":"苏州昆山社会生活","tid":"itv","id":"205"},{"name":"徐州-1","tid":"itv","id":"206"},{"name":"徐州-2","tid":"itv","id":"207"},{"name":"徐州-3","tid":"itv","id":"208"},{"name":"徐州-4","tid":"itv","id":"209"},{"name":"徐州邳州综合","tid":"itv","id":"210"},{"name":"徐州邳州综艺","tid":"itv","id":"211"},{"name":"徐州新沂新闻综合","tid":"itv","id":"212"},{"name":"徐州新沂生活频道","tid":"itv","id":"213"},{"name":"海南综合","tid":"itv","id":"214"},{"name":"海南公共","tid":"itv","id":"215"},{"name":"海南影视剧","tid":"itv","id":"216"},{"name":"海南新闻","tid":"itv","id":"217"},{"name":"海南三亚","tid":"itv","id":"218"},{"name":"陕西新闻","tid":"itv","id":"219"},{"name":"陕西都市","tid":"itv","id":"220"},{"name":"陕西生活","tid":"itv","id":"221"},{"name":"陕西公共","tid":"itv","id":"222"},{"name":"西安新闻","tid":"itv","id":"223"},{"name":"西安白鸽","tid":"itv","id":"224"},{"name":"西安资讯","tid":"itv","id":"225"},{"name":"西安影视","tid":"itv","id":"226"},{"name":"西安健康","tid":"itv","id":"227"},{"name":"榆林综合","tid":"itv","id":"228"},{"name":"淮安综合","tid":"itv","id":"229"},{"name":"淮安公共","tid":"itv","id":"230"},{"name":"淮安影视","tid":"itv","id":"231"},{"name":"沉阳新闻","tid":"itv","id":"232"},{"name":"沉阳经济","tid":"itv","id":"233"},{"name":"沉阳公共","tid":"itv","id":"234"},{"name":"大连瓦房店新闻综合","tid":"itv","id":"235"},{"name":"大连瓦房店经济生活","tid":"itv","id":"236"},{"name":"兰州综合","tid":"itv","id":"237"},{"name":"兰州生活","tid":"itv","id":"238"},{"name":"兰州综艺","tid":"itv","id":"239"},{"name":"兰州公共","tid":"itv","id":"240"},{"name":"新疆维语综合","tid":"itv","id":"241"},{"name":"哈萨克语综合","tid":"itv","id":"242"},{"name":"新疆汉语综艺","tid":"itv","id":"243"},{"name":"新疆维语综艺","tid":"itv","id":"244"},{"name":"新疆汉语生活","tid":"itv","id":"245"},{"name":"新疆哈语综艺","tid":"itv","id":"246"},{"name":"新疆维语生活","tid":"itv","id":"247"},{"name":"新疆体育健康","tid":"itv","id":"248"},{"name":"新疆法制信息","tid":"itv","id":"249"},{"name":"新疆少儿","tid":"itv","id":"250"},{"name":"CIBN精品影院","tid":"itv","id":"251"},{"name":"CIBN电影导视","tid":"itv","id":"252"},{"name":"CIBN喜剧影院","tid":"itv","id":"253"},{"name":"CIBN动漫频道","tid":"itv","id":"254"},{"name":"CIBN航美","tid":"itv","id":"255"},{"name":"CIBN综合","tid":"itv","id":"256"},{"name":"CIBN院线大片","tid":"itv","id":"257"},{"name":"CIBN电影频道","tid":"itv","id":"258"},{"name":"CIBN好莱坞","tid":"itv","id":"259"},{"name":"CIBN经典剧场","tid":"itv","id":"260"},{"name":"CIBN真人秀","tid":"itv","id":"261"},{"name":"CIBN微电影","tid":"itv","id":"262"},{"name":"CIBN财经","tid":"itv","id":"263"},{"name":"CIBN旅游","tid":"itv","id":"264"},{"name":"CIBN流金岁月","tid":"itv","id":"265"},{"name":"CIBN禅文化","tid":"itv","id":"266"},{"name":"CIBN热播剧场","tid":"itv","id":"267"},{"name":"CIBNTEA电竞频道","tid":"itv","id":"268"}]
     */

    private String timestamp;
    private int code;
    private String msg;
    private List<DataBean> data;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * name : HBO HD
         * tid : itv
         * id : 9
         */

        private String name;
        private String tid;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.tid);
            dest.writeString(this.id);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.name = in.readString();
            this.tid = in.readString();
            this.id = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }
}
