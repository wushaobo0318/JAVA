package com.frogwallet.config;

import com.frogwallet.common.App;
import com.frogwallet.geth.Geth;
import com.frogwallet.geth.RpcRequester;
import com.frogwallet.utils.LogUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

/**
 * Created by: Yumira.
 * Created on: 2018/8/15-下午1:22.
 * Description:
 */
@Configuration
@DependsOn("app")
public class Web3jConfig {

    /**
     * 加载web3j对象
     */
    @Bean
    public RpcRequester getWeb3j() throws IOException {
        String url = App.RPC_URL;
        RpcRequester web3j = (RpcRequester) Geth.build(new HttpService(url));
        Web3ClientVersion send = web3j.web3ClientVersion().send();
        LogUtil.info(Web3jConfig.class,"加载Web3j成功，以太坊版本号："+send.getWeb3ClientVersion());
        return web3j;
    }

    /**
     * 通过keystore和密码获取证书
     */
//    @Bean
//    public Credentials getCredentials() throws IOException, CipherException {
//        Credentials credentials = WalletUtils.loadCredentials(App.COINBASE_PASSOWRD,App.KEYSTORE_PATH);
////        Credentials credentials = Credentials.create("8fef2bc888b9d45f2629227005451f9b34ff41c3e95bcfe9b86577a2c5977869");
//        return credentials;
//    }

}
