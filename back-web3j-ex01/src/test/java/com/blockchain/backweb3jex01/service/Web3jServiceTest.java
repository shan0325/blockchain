package com.blockchain.backweb3jex01.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;


@SpringBootTest
class Web3jServiceTest {

    @Resource(name = "web3jService")
    private Web3jService web3jService;

    @Test
    public void balanceOf() {
        web3jService.balanceOf();
    }

    @Test
    public void getBlockNumber() throws ExecutionException, InterruptedException {
        EthBlockNumber ethBlockNumber = web3jService.getBlockNumber();
        long id = ethBlockNumber.getId();
        BigInteger blockNumber = ethBlockNumber.getBlockNumber();

        System.out.println("id = " + id);
        System.out.println("blockNumber = " + blockNumber);
    }

    @Test
    public void getEthAccounts() throws ExecutionException, InterruptedException {
        EthAccounts ethAccounts = web3jService.getEthAccounts();
        List<String> accounts = ethAccounts.getAccounts();
        System.out.println("accounts = " + accounts);

        Assertions.assertThat(accounts.get(0)).isEqualTo("0x296e4e429d90ce3aa25de36df23c005902996add");
    }

    @Test
    public void getTransactionCount() throws ExecutionException, InterruptedException {
        EthGetTransactionCount ethGetTransactionCount = web3jService.getTransactionCount();
        BigInteger transactionCount = ethGetTransactionCount.getTransactionCount();
        System.out.println("transactionCount = " + transactionCount);
    }



}