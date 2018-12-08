package com.frogwallet.core.manager;

import com.frogwallet.common.ResultCode;
import com.frogwallet.exception.ApiException;
import com.frogwallet.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetBalance;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by: Yumira.
 * Created on: 2018/12/8-下午10:53.
 * Description:
 */
@Component
public class CallManager {

    @Autowired
    private Web3j web3j;


    /**
     * 查询ETH余额
     */
    public BigInteger getBalance(String fromAddress) throws ApiException {
        if (StringUtil.isEmpty(fromAddress)){
            throw new ApiException(ResultCode.FAIL,"查询地址不能为空");
        }
        BigInteger balance = null;
        try {
            EthGetBalance ethGetBalance = web3j.ethGetBalance(fromAddress, DefaultBlockParameterName.LATEST).send();
            balance = ethGetBalance.getBalance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return balance;
    }

    /**
     * 查询ERC20余额
     */
    public BigInteger getTokenBalance(String fromAddress, String contractAddress) {

        String methodName = "balanceOf";
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Address address = new Address(fromAddress);
        inputParameters.add(address);

        TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
        };
        outputParameters.add(typeReference);
        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(fromAddress, contractAddress, data);

        EthCall ethCall;
        BigInteger balanceValue = BigInteger.ZERO;
        try {
            ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            balanceValue = (BigInteger) results.get(0).getValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return balanceValue;
    }

}
