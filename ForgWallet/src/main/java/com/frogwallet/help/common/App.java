package com.frogwallet.help.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;

/**
 * created by yumira 2018/7/27.
 */
@Configuration
@Order(1)
public class App {

    /**
     * 当前版本号
     */
    @Value("${version}")
    private int version;
    /**
     * 测试环境
     */
    @Value("${test}")
    private Boolean test;
    /**
     * 节点服务器地址
     */
    @Value("${rpcUrl}")
    private String rpc_url;
    /**
     * 当前区块链网络id
     */
    @Value("${chainId}")
    private byte chainId;
    /**
     * 请求白名单
     */
    @Value("${writeIp}")
    private String[] writeIp;



    public static int VERSION;
    public static boolean TEST;
    public static String RPC_URL;
    public static byte CHAIN_ID;
    public static String[] WRITE_IP;

    @PostConstruct
    public void init() {
        VERSION = version;
        TEST = test;
        RPC_URL = rpc_url;
        CHAIN_ID = chainId;
        WRITE_IP = writeIp;
    }

}
