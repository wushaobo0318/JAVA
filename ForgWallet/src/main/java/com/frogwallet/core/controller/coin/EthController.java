package com.frogwallet.core.controller.coin;

import com.frogwallet.base.BaseData;
import com.frogwallet.common.ResultGenerator;
import com.frogwallet.core.manager.EthManager;
import com.frogwallet.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;


/**
 * Created by: Yumira.
 * Created on: 2018/12/8-下午23:33.
 * Description:
 */
@RestController
@RequestMapping("/eth")
public class EthController {

    @Autowired
    private EthManager ethManager;

    /**
     * 查询ETH余额，返回单位为 wei
     */
    @PostMapping("/balance")
    public BaseData getBalance(@RequestParam String address) throws ApiException {
        return ResultGenerator.genSuccessResult(ethManager.getBalance(address));
    }

    /**
     * 查询ERC20余额
     */
    @PostMapping("/erc20/balance")
    public BaseData getErc20Balance(@RequestParam String address,String contract) throws ApiException {
        return ResultGenerator.genSuccessResult(ethManager.getBalance(address,contract));
    }

    /**
     * 交易转账，包括erc20代币
     * @return
     * @throws Exception
     */
    @PostMapping("/transfer")
    public BaseData transfer(@RequestParam String transactionData) throws ApiException {
        return ResultGenerator.genSuccessResult(ethManager.sendRawTransaction(transactionData));
    }

    /**
     * 获取交易使用的nonce
     * @return
     * @throws Exception
     */
    @PostMapping("/nonce")
    public BaseData getNonce(@RequestParam String address) throws ApiException {
        return ResultGenerator.genSuccessResult(ethManager.getTransactionNonce(address));
    }

    /**
     * 获取交易的gaslimit
     * @return
     * @throws Exception
     */
    @PostMapping("/gaslimit")
    public BaseData getGaslimit(@RequestParam String fromAddress, String toAddress,
                                 BigInteger value, String data) throws ApiException {
        return ResultGenerator.genSuccessResult(ethManager.getTransactionGasLimit(
                ethManager.makeTransaction(fromAddress,toAddress,null,null,null,value,data)));
    }

    /**
     * 查询交易记录
     * @return
     * @throws Exception
     */
    @PostMapping("/transactionList")
    public BaseData transactionList(@RequestParam String address, byte type, int page, int size) throws Exception {
        // TODO: 2018/12/8 交易记录
        return ResultGenerator.genSuccessResult(null);
    }

    /**
     * 查询单条交易
     * @param txid
     * @return
     */
    @PostMapping("/getTransaction")
    public BaseData gettTransaction(@RequestParam String txid){
        // TODO: 2018/12/8 查询单条交易
        return ResultGenerator.genSuccessResult(null);
    }


}
