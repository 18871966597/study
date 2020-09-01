package com.ll.eurakclient.model;

import java.io.Serializable;

/**
 * <p>
 * 服务注册传输实体类
 * </p>
 *
 * @author ll Create on 20/8/23 16:57
 * @version 1.0
 */
public class EurakClient implements Serializable {

    private static final long serialVersionUID = -6985468585867781246L;

    /*
     * ServiseId
     */
    private String serverName;

    /*
     * ip
     */
    private String host;

    /*
     * 端口
     */
    private String port;

    /*
     * 服务状态（用来知道服务服务上线和下线）
     */
    private String status;

    /*
     * 最后一次心跳时间（实现超时后将服务踢出）
     */
    private Long isDirtWithTime;

    /*
     * 注册中心的注册地址
     */
    private String adress;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getIsDirtWithTime() {
        return isDirtWithTime;
    }

    public void setIsDirtWithTime(Long isDirtWithTime) {
        this.isDirtWithTime = isDirtWithTime;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
