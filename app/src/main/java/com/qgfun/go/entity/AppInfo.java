package com.qgfun.go.entity;

/**
 * @author LLY
 * date: 2020/4/11 18:20
 * package name: com.qgfun.beauty.entity
 */

public class AppInfo {

    /**
     * note : 1、新增下载功能，播放页面长按播放地址即可下载<br>2、修复TV播放的Bug<br>3、如有问题请卸载重新安装最新版
     * tip : 通知：测试测试测试测试
     * version : 26
     * splash : https://cdn.jsdelivr.net/gh/colally/app/go/app/new_year_one.png
     * download : https://cdn.jsdelivr.net/gh/colally/app/go/app/new_year_one.png
     * force : true
     */
    private String note;

    private String tip;

    private Integer version;

    private String splash;

    private String download;

    private Boolean force;
    private Integer session;

    public AppInfo() {
    }

    public AppInfo(String note, String tip, Integer version, String splash, String download, Boolean force, Integer session) {
        this.note = note;
        this.tip = tip;
        this.version = version;
        this.splash = splash;
        this.download = download;
        this.force = force;
        this.session = session;
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
}
