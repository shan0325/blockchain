package com.blockchain.backweb3jex01.service;

import com.blockchain.backweb3jex01.contract.NFT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.tx.Contract;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service("web3jService")
public class Web3jService {

    private final Web3j web3j;
    private final NFT nft;

    @Value("${metamask.WALLET_ADDRESS}")
    private String WALLET_ADDRESS;

    @Value("${metamask.CONTRACT_ADDRESS}")
    private String CONTRACT_ADDRESS;


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
        result = web3j.ethGetTransactionCount(WALLET_ADDRESS,
                        DefaultBlockParameter.valueOf("latest"))
                .sendAsync()
                .get();
        return result;
    }

    // 계정 잔액
    public EthGetBalance getEthBalance() throws ExecutionException, InterruptedException {
        return web3j.ethGetBalance(WALLET_ADDRESS,
                        DefaultBlockParameter.valueOf("latest"))
                .sendAsync()
                .get();
    }

    public String getContractName() throws Exception {
        return nft.name().send();
    }

    public BigInteger currentCount() throws Exception {
        return nft.balanceOf(WALLET_ADDRESS).send();
    }

    public TransactionReceipt nftCreate() throws ExecutionException, InterruptedException {
        System.out.println("nftCreate start : " + LocalDateTime.now());
        TransactionReceipt transactionReceipt = nft.create(WALLET_ADDRESS, "ipfs://QmNZLXLk8nWG4PMdcCWAGpgW12hAhiV375YeFpaCLisfBi").sendAsync().get();
        System.out.println("nftCreate end : " + LocalDateTime.now());

        return transactionReceipt;
    }

    public EthLog web3jEthNewFilter() throws Exception {
        EthBlockNumber blockNumber = getBlockNumber();
        System.out.println("blockNumber.getBlockNumber() = " + blockNumber.getBlockNumber());

        EthFilter filter = new EthFilter(DefaultBlockParameter.valueOf(blockNumber.getBlockNumber()), DefaultBlockParameterName.LATEST, CONTRACT_ADDRESS);

        org.web3j.protocol.core.methods.response.EthFilter send = web3j.ethNewFilter(filter).send();
        System.out.println("getFilterId = " + send.getFilterId());
        System.out.println("getId = " + send.getId());
        System.out.println("getJsonrpc = " + send.getJsonrpc());

        BigInteger filterId = BigInteger.valueOf(send.getId());
        EthLog send1 = web3j.ethGetFilterChanges(filterId).send();
        System.out.println("send1.getRawResponse() = " + send1.getRawResponse());
        System.out.println("send1.getLogs() = " + send1.getLogs());
        return web3j.ethGetFilterChanges(filterId).send();
    }

    private EthFilter getEthFilter() throws Exception {
        EthBlockNumber blockNumber = getBlockNumber();
        EthFilter ethFilter = new EthFilter(DefaultBlockParameter.valueOf(blockNumber.getBlockNumber()), DefaultBlockParameterName.LATEST, CONTRACT_ADDRESS);

        Event event = new Event("Transfer",
                Arrays.asList(
                        new TypeReference<Address>(true) {
                            // from
                        },
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Uint256>(false) {
                            // amount
                        }
                ));
        String topicData = EventEncoder.encode(event);
        ethFilter.addSingleTopic(topicData);
        ethFilter.addNullTopic();// filter: event type (topic[0])
        //ethFilter.addOptionalTopics("0x"+ TypeEncoder.encode(new Address("")));

        return ethFilter;
    }

    public void transferEventFlowable() throws Exception {
        web3j.ethLogFlowable(getEthFilter())
                .subscribe(log -> {
                    System.out.println("log = " + log);
                    String data = log.getData();
                    System.out.println("data = " + data);
                    String address = log.getAddress();
                    System.out.println("address = " + address);
                });

        Thread.sleep(10000000);
    }
}
