package com.blockchain.backweb3jex01.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.http.HttpService;

import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service("web3jService")
public class Web3jService {

    private final Web3j web3j;

    public void balanceOf() {
        String ERC20_CONTRACT_ADDRESS = "0x.....";
        String USER_ADDRESS = "0x....";
        String USER_PRIVATE_KEY = "0x...."; // 사용자의 개인키
        long TX_END_CHECK_DURATION = 3000;
        int TX_END_CHECK_RETRY = 3;

        // 로컬 환경에서 프라이빗 네트워크를 띄운 상태에서 진행한다.
        Web3j web3j = Web3j.build(new HttpService("http://localhost:8545"));
        Credentials credential = Credentials.create(USER_PRIVATE_KEY);
    }

    // 현재 블록 번호
    public EthBlockNumber getBlockNumber() throws ExecutionException, InterruptedException {
        return web3j.ethBlockNumber().sendAsync().get();
    }

    // 계정
    public EthAccounts getEthAccounts() throws ExecutionException, InterruptedException {
        return web3j.ethAccounts().sendAsync().get();
    }

    // 계좌 거래 건수
    public EthGetTransactionCount getTransactionCount() throws ExecutionException, InterruptedException {
        EthGetTransactionCount result = new EthGetTransactionCount();
        result = web3j.ethGetTransactionCount("0x296e4e429d90ce3aa25de36df23c005902996add",
                        DefaultBlockParameter.valueOf("latest"))
                .sendAsync()
                .get();
        return result;
    }

    // 계정 잔액
    public EthGetBalance getEthBalance() throws ExecutionException, InterruptedException {
        EthGetBalance result = new EthGetBalance();
        this.web3j.ethGetBalance("0x296e4e429d90ce3aa25de36df23c005902996add",
                        DefaultBlockParameter.valueOf("latest"))
                .sendAsync()
                .get();
        return result;
    }
}
