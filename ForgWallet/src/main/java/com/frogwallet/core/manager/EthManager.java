package com.frogwallet.core.manager;

import com.frogwallet.common.ResultCode;
import com.frogwallet.exception.ApiException;
import com.frogwallet.geth.RpcRequester;
import com.frogwallet.utils.JsonUtil;
import com.frogwallet.utils.LogUtil;
import com.frogwallet.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by: Yumira.
 * Created on: 2018/12/8-下午11:51.
 * Description:
 */
@Component
public class EthManager {

    @Autowired
    private RpcRequester admin;
    @Autowired
    private CallManager callManager;

    /**
     * 发送一笔离线交易，解锁和签名过程在客户端完成
     * @return
     * @throws ApiException
     */
    public String sendRawTransaction(String signedData) throws ApiException{
        String txHash = "";
        try {
            if (!StringUtil.isEmpty(signedData)) {
                CompletableFuture<EthSendTransaction> ethSendTransactionCompletableFuture = admin.ethSendRawTransaction(signedData).sendAsync();
                EthSendTransaction sendTransaction = ethSendTransactionCompletableFuture.get();
                LogUtil.info(EthManager.class, "signed:"+signedData+" \n==> "+ JsonUtil.toJSONString(sendTransaction));
                if (sendTransaction.getError() != null){
                    //常见错误：invalid sender  检查ChainId和链上networkId是否匹配
                    throw new ApiException(ResultCode.FAIL,sendTransaction.getError().getMessage());
                }
                txHash = sendTransaction.getTransactionHash();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return txHash;
    }


    /**
     * 获取ETH余额
     * @param address
     * @return
     * @throws ApiException
     */
    public String getBalance(String address) throws ApiException {
        if (StringUtil.isEmpty(address)){
            throw new ApiException(ResultCode.FAIL,"查询地址不能为空");
        }
        BigInteger balance = callManager.getBalance(address);
        return balance.toString();
    }


    /**
     * 获取ERC20余额
     * @param address
     * @return
     * @throws ApiException
     */
    public String getBalance(String address,String contract) throws ApiException {
        if (StringUtil.isEmpty(address)){
            throw new ApiException(ResultCode.FAIL,"查询地址不能为空");
        }
        BigInteger balance = callManager.getTokenBalance(address,contract);
        return balance.toString();
    }


    /**
     * 获取普通交易的gas上限
     *
     * @param transaction 交易对象
     * @return gas 上限
     */
    public BigInteger getTransactionGasLimit(Transaction transaction) throws ApiException {
        BigInteger gasLimit = BigInteger.ZERO;
        try {
            EthEstimateGas ethEstimateGas = admin.ethEstimateGas(transaction).send();
            gasLimit = ethEstimateGas.getAmountUsed();
        } catch (Exception e) {
            throw new ApiException(ResultCode.FAIL,e.getMessage());
        }
        return gasLimit;
    }

    /**
     * 获取账号交易次数 nonce
     *
     * @param address 钱包地址
     * @return nonce
     */
    public BigInteger getTransactionNonce(String address) throws ApiException {
        BigInteger nonce = BigInteger.ZERO;
        try {
            EthGetTransactionCount ethGetTransactionCount = admin.ethGetTransactionCount(address, DefaultBlockParameterName.PENDING).send();
            nonce = ethGetTransactionCount.getTransactionCount();
        } catch (Exception e) {
            throw new ApiException(ResultCode.FAIL,e.getMessage());
        }
        return nonce;
    }

    /**
     * 生成一个普通交易对象
     *
     * @param fromAddress 放款方
     * @param toAddress   收款方
     * @param nonce       交易序号
     * @param gasPrice    gas 价格
     * @param gasLimit    gas 数量
     * @param value       金额
     * @return 交易对象
     */
    public static Transaction makeTransaction(String fromAddress, String toAddress,
                                               BigInteger nonce, BigInteger gasPrice,
                                               BigInteger gasLimit, BigInteger value,String data) {
        Transaction transaction;
        transaction = Transaction.createFunctionCallTransaction(fromAddress, nonce, gasPrice, gasLimit, toAddress, value,data);
        return transaction;
    }

}
