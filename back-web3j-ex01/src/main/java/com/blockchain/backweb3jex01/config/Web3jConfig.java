package com.blockchain.backweb3jex01.config;

import com.blockchain.backweb3jex01.contract.NFT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Configuration
public class Web3jConfig {

    @Value("${infura.API_URL}")
    private String INFURA_API_URL;

    @Value("${metamask.PRIVATE_KEY}")
    private String PRIVATE_KEY;

    @Value("${metamask.CONTRACT_ADDRESS}")
    private String CONTRACT_ADDRESS;

    private static final String PROVIDER_URL = "http://localhost:8545";

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(INFURA_API_URL));
    }

    @Bean
    public Credentials credentials() {
        BigInteger privateKeyInBT = new BigInteger(PRIVATE_KEY, 16);
        return Credentials.create(ECKeyPair.create(privateKeyInBT));
    }

    @Bean
    public NFT nft() {
        return NFT.load(CONTRACT_ADDRESS, web3j(), credentials(), new DefaultGasProvider());
    }
}
