package com.qgfun.go.entity;

/**
 * @author LLY
 * date: 2020/7/18 16:34
 * package name: com.qgfun.go.entity
 * description：本地asset与拦截url资源对应的信息
 */
public class ResourceInfo {
    /**
     * 本地asset文件路径
     */
   private String path;
    /**
     * 资源mine-type
     */
   private String type;

    public ResourceInfo(String path, String type) {
        this.path = path;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }


}
