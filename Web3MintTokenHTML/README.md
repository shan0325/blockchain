# Web3MintTokenHTML

Example of how to mint ERC-721 and ERC-1155 tokens through HTML web pages using Web3 JS and Metamask

1. Enter the address and ABI of the Smart Contract you want to interact with  
   You can get this from etherscan.io  
   `const ABI = [{"inputs":[],"stateMutability":"nonpayable","type":"constructor"} ... ];`  
   `const ADDRESS = "0x00000000000000000000000000000000";`

2. Set the amount of ETH to be paid as the 'value' (in Wei) - this is only for index721.html. index1155.html retrieves the price from the contract dynamically.

```
document.getElementById('mint').onclick = () => {
    contract.methods.mint(account, 1).send({ from: account, value: "10000000000000000" });
}
```

index721.html  
![](screenshots/721.jpg?raw=true)

index1155.html  
![](screenshots/1155.jpg?raw=true)
