package com.blockchain.backweb3jex01.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.web3j.protocol.core.methods.response.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Slf4j
@SpringBootTest
class Web3jServiceTest {

    @Resource(name = "web3jService")
    private Web3jService web3jService;

    @Test
    public void getBlockNumber() throws ExecutionException, InterruptedException {
        EthBlockNumber ethBlockNumber = web3jService.getBlockNumber();
        long id = ethBlockNumber.getId();
        BigInteger blockNumber = ethBlockNumber.getBlockNumber();

        log.info("id : {}", id);
        log.info("blockNumber : {}", blockNumber);
    }

    @Test
    public void getEthAccounts() throws ExecutionException, InterruptedException {
        EthAccounts ethAccounts = web3jService.getEthAccounts();
        List<String> accounts = ethAccounts.getAccounts();

        log.info("accounts : {}", accounts);
    }

    @Test
    public void getTransactionCount() throws ExecutionException, InterruptedException {
        EthGetTransactionCount ethGetTransactionCount = web3jService.getTransactionCount();
        BigInteger transactionCount = ethGetTransactionCount.getTransactionCount();

        log.info("transactionCount : {}", transactionCount);
    }

    @Test
    public void getEthBalance() throws ExecutionException, InterruptedException {
        EthGetBalance ethGetBalance = web3jService.getEthBalance();
        BigInteger balance = ethGetBalance.getBalance();

        log.info("balance : {}", balance);
    }

    @Test
    public void getContractName() throws Exception {
        String contractName = web3jService.getContractName();

        log.info("contractName : {}", contractName);
    }

    @Test
    public void currentCount() throws Exception {
        BigInteger currentCount = web3jService.currentCount();

        log.info("currentCount : {}", currentCount);
    }

    @Test
    public void nftCreate() throws Exception {
        log.info("start time : {}", LocalDateTime.now());

        TransactionReceipt transactionReceipt = web3jService.nftCreate();
        log.info("transactionReceipt : {}", transactionReceipt);

        log.info("end time : {}", LocalDateTime.now());
    }

    @Test
    public void transferEventFlowable() throws Exception {
        web3jService.transferEventFlowable();
    }

}