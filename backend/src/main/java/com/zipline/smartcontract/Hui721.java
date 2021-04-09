package com.zipline.smartcontract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class Hui721 extends Contract {
    public static final String BINARY = "{\"linkReferences\": {}, \"object\": \"0x60806040523480156200001157600080fd5b506040518060400160405280600481526020017f48756973000000000000000000000000000000000000000000000000000000008152506040518060400160405280600381526020017f48554900000000000000000000000000000000000000000000000000000000008152508181620000986301ffc9a760e01b6200017660201b60201c565b620000b06380ac58cd60e01b6200017660201b60201c565b620000c863780e9d6360e01b6200017660201b60201c565b8151620000dd9060099060208501906200024a565b508051620000f390600a9060208401906200024a565b506200010c635b5e139f60e01b6200017660201b60201c565b505050506000620001226200024560201b60201c565b600d80546001600160a01b0319166001600160a01b038316908117909155604051919250906000907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a350620002ec565b7fffffffff0000000000000000000000000000000000000000000000000000000080821614156200020857604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601c60248201527f4552433136353a20696e76616c696420696e7465726661636520696400000000604482015290519081900360640190fd5b7fffffffff00000000000000000000000000000000000000000000000000000000166000908152602081905260409020805460ff19166001179055565b335b90565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200028d57805160ff1916838001178555620002bd565b82800160010185558215620002bd579182015b82811115620002bd578251825591602001919060010190620002a0565b50620002cb929150620002cf565b5090565b6200024791905b80821115620002cb5760008155600101620002d6565b611d6180620002fc6000396000f3fe608060405234801561001057600080fd5b50600436106101425760003560e01c806370a08231116100b8578063a22cb4651161007c578063a22cb465146103a0578063b88d4fde146103ce578063c87b56dd14610494578063d0def521146104b1578063e985e9c514610567578063f2fde38b1461059557610142565b806370a082311461035a578063715018a6146103805780638da5cb5b146103885780638f32d59b1461039057806395d89b411461039857610142565b806323b872dd1161010a57806323b872dd146102805780632f745c59146102b657806342842e0e146102e25780634f6ccce7146103185780636352211e146103355780636c0360eb1461035257610142565b806301ffc9a71461014757806306fdde0314610182578063081812fc146101ff578063095ea7b31461023857806318160ddd14610266575b600080fd5b61016e6004803603602081101561015d57600080fd5b50356001600160e01b0319166105bb565b604080519115158252519081900360200190f35b61018a6105de565b6040805160208082528351818301528351919283929083019185019080838360005b838110156101c45781810151838201526020016101ac565b50505050905090810190601f1680156101f15780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61021c6004803603602081101561021557600080fd5b5035610675565b604080516001600160a01b039092168252519081900360200190f35b6102646004803603604081101561024e57600080fd5b506001600160a01b0381351690602001356106da565b005b61026e610808565b60408051918252519081900360200190f35b6102646004803603606081101561029657600080fd5b506001600160a01b0381358116916020810135909116906040013561080e565b61026e600480360360408110156102cc57600080fd5b506001600160a01b03813516906020013561086d565b610264600480360360608110156102f857600080fd5b506001600160a01b038135811691602081013590911690604001356108ef565b61026e6004803603602081101561032e57600080fd5b503561090a565b61021c6004803603602081101561034b57600080fd5b5035610973565b61018a6109d0565b61026e6004803603602081101561037057600080fd5b50356001600160a01b0316610a31565b610264610a9c565b61021c610b42565b61016e610b51565b61018a610b77565b610264600480360360408110156103b657600080fd5b506001600160a01b0381351690602001351515610bd8565b610264600480360360808110156103e457600080fd5b6001600160a01b0382358116926020810135909116916040820135919081019060808101606082013564010000000081111561041f57600080fd5b82018360208201111561043157600080fd5b8035906020019184600183028401116401000000008311171561045357600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610ce0945050505050565b61018a600480360360208110156104aa57600080fd5b5035610d41565b61026e600480360360408110156104c757600080fd5b6001600160a01b0382351691908101906040810160208201356401000000008111156104f257600080fd5b82018360208201111561050457600080fd5b8035906020019184600183028401116401000000008311171561052657600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610f10945050505050565b61016e6004803603604081101561057d57600080fd5b506001600160a01b0381358116916020013516610f44565b610264600480360360208110156105ab57600080fd5b50356001600160a01b0316610f72565b6001600160e01b0319811660009081526020819052604090205460ff165b919050565b60098054604080516020601f600260001961010060018816150201909516949094049384018190048102820181019092528281526060939092909183018282801561066a5780601f1061063f5761010080835404028352916020019161066a565b820191906000526020600020905b81548152906001019060200180831161064d57829003601f168201915b505050505090505b90565b600061068082610fda565b6106be57604051600160e51b62461bcd02815260040180806020018281038252602c815260200180611c08602c913960400191505060405180910390fd5b506000908152600260205260409020546001600160a01b031690565b60006106e582610973565b9050806001600160a01b0316836001600160a01b0316141561073b57604051600160e51b62461bcd028152600401808060200182810382526021815260200180611cb86021913960400191505060405180910390fd5b806001600160a01b031661074d610ff7565b6001600160a01b0316148061076e575061076e81610769610ff7565b610f44565b6107ac57604051600160e51b62461bcd028152600401808060200182810382526038815260200180611b7d6038913960400191505060405180910390fd5b60008281526002602052604080822080546001600160a01b0319166001600160a01b0387811691821790925591518593918516917f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92591a4505050565b60075490565b61081f610819610ff7565b82610ffb565b61085d57604051600160e51b62461bcd028152600401808060200182810382526031815260200180611cd96031913960400191505060405180910390fd5b6108688383836110a2565b505050565b600061087883610a31565b82106108b857604051600160e51b62461bcd02815260040180806020018281038252602b815260200180611aaa602b913960400191505060405180910390fd5b6001600160a01b03831660009081526005602052604090208054839081106108dc57fe5b9060005260206000200154905092915050565b61086883838360405180602001604052806000815250610ce0565b6000610914610808565b821061095457604051600160e51b62461bcd02815260040180806020018281038252602c815260200180611d0a602c913960400191505060405180910390fd5b6007828154811061096157fe5b90600052602060002001549050919050565b6000818152600160205260408120546001600160a01b0316806109ca57604051600160e51b62461bcd028152600401808060200182810382526029815260200180611bdf6029913960400191505060405180910390fd5b92915050565b600b8054604080516020601f600260001961010060018816150201909516949094049384018190048102820181019092528281526060939092909183018282801561066a5780601f1061063f5761010080835404028352916020019161066a565b60006001600160a01b038216610a7b57604051600160e51b62461bcd02815260040180806020018281038252602a815260200180611bb5602a913960400191505060405180910390fd5b6001600160a01b03821660009081526003602052604090206109ca906110c1565b610aa4610b51565b610af85760408051600160e51b62461bcd02815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604482015290519081900360640190fd5b600d546040516000916001600160a01b0316907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a3600d80546001600160a01b0319169055565b600d546001600160a01b031690565b600d546000906001600160a01b0316610b68610ff7565b6001600160a01b031614905090565b600a8054604080516020601f600260001961010060018816150201909516949094049384018190048102820181019092528281526060939092909183018282801561066a5780601f1061063f5761010080835404028352916020019161066a565b610be0610ff7565b6001600160a01b0316826001600160a01b03161415610c495760408051600160e51b62461bcd02815260206004820152601960248201527f4552433732313a20617070726f766520746f2063616c6c657200000000000000604482015290519081900360640190fd5b8060046000610c56610ff7565b6001600160a01b03908116825260208083019390935260409182016000908120918716808252919093529120805460ff191692151592909217909155610c9a610ff7565b60408051841515815290516001600160a01b0392909216917f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c319181900360200190a35050565b610cf1610ceb610ff7565b83610ffb565b610d2f57604051600160e51b62461bcd028152600401808060200182810382526031815260200180611cd96031913960400191505060405180910390fd5b610d3b848484846110c5565b50505050565b6060610d4c82610fda565b610d8a57604051600160e51b62461bcd02815260040180806020018281038252602f815260200180611c89602f913960400191505060405180910390fd5b6000828152600c602090815260409182902080548351601f6002600019610100600186161502019093169290920491820184900484028101840190945280845260609392830182828015610e1f5780601f10610df457610100808354040283529160200191610e1f565b820191906000526020600020905b815481529060010190602001808311610e0257829003601f168201915b50505050509050805160001415610e465750506040805160208101909152600081526105d9565b600b816040516020018083805460018160011615610100020316600290048015610ea75780601f10610e85576101008083540402835291820191610ea7565b820191906000526020600020905b815481529060010190602001808311610e93575b5050825160208401908083835b60208310610ed35780518252601f199092019160209182019101610eb4565b6001836020036101000a038019825116818451168082178552505050505050905001925050506040516020818303038152906040529150506105d9565b600080610f1d600e6110c1565b9050610f29848261111a565b610f33818461113b565b610f3d600e6111a1565b9392505050565b6001600160a01b03918216600090815260046020908152604080832093909416825291909152205460ff1690565b610f7a610b51565b610fce5760408051600160e51b62461bcd02815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604482015290519081900360640190fd5b610fd7816111aa565b50565b6000908152600160205260409020546001600160a01b0316151590565b3390565b600061100682610fda565b61104457604051600160e51b62461bcd02815260040180806020018281038252602c815260200180611b51602c913960400191505060405180910390fd5b600061104f83610973565b9050806001600160a01b0316846001600160a01b0316148061108a5750836001600160a01b031661107f84610675565b6001600160a01b0316145b8061109a575061109a8185610f44565b949350505050565b6110ad83838361124e565b6110b78382611398565b610868828261148d565b5490565b6110d08484846110a2565b6110dc848484846114cb565b610d3b57604051600160e51b62461bcd028152600401808060200182810382526032815260200180611ad56032913960400191505060405180910390fd5b611124828261170f565b61112e828261148d565b61113781611846565b5050565b61114482610fda565b61118257604051600160e51b62461bcd02815260040180806020018281038252602c815260200180611c34602c913960400191505060405180910390fd5b6000828152600c602090815260409091208251610868928401906119f1565b80546001019055565b6001600160a01b0381166111f257604051600160e51b62461bcd028152600401808060200182810382526026815260200180611b076026913960400191505060405180910390fd5b600d546040516001600160a01b038084169216907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e090600090a3600d80546001600160a01b0319166001600160a01b0392909216919091179055565b826001600160a01b031661126182610973565b6001600160a01b0316146112a957604051600160e51b62461bcd028152600401808060200182810382526029815260200180611c606029913960400191505060405180910390fd5b6001600160a01b0382166112f157604051600160e51b62461bcd028152600401808060200182810382526024815260200180611b2d6024913960400191505060405180910390fd5b6112fa8161188a565b6001600160a01b038316600090815260036020526040902061131b906118c5565b6001600160a01b038216600090815260036020526040902061133c906111a1565b60008181526001602052604080822080546001600160a01b0319166001600160a01b0386811691821790925591518493918716917fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef91a4505050565b6001600160a01b0382166000908152600560205260408120546113c290600163ffffffff6118dc16565b60008381526006602052604090205490915080821461145d576001600160a01b03841660009081526005602052604081208054849081106113ff57fe5b906000526020600020015490508060056000876001600160a01b03166001600160a01b03168152602001908152602001600020838154811061143d57fe5b600091825260208083209091019290925591825260069052604090208190555b6001600160a01b0384166000908152600560205260409020805490611486906000198301611a6f565b5050505050565b6001600160a01b0390911660009081526005602081815260408084208054868652600684529185208290559282526001810183559183529091200155565b60006114df846001600160a01b031661191e565b6114eb5750600161109a565b600060606001600160a01b038616600160e11b630a85bd010261150c610ff7565b89888860405160240180856001600160a01b03166001600160a01b03168152602001846001600160a01b03166001600160a01b0316815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b8381101561158557818101518382015260200161156d565b50505050905090810190601f1680156115b25780820380516001836020036101000a031916815260200191505b5060408051601f198184030181529181526020820180516001600160e01b03166001600160e01b0319909a16999099178952518151919890975087965094509250829150849050835b6020831061161a5780518252601f1990920191602091820191016115fb565b6001836020036101000a0380198251168184511680821785525050505050509050019150506000604051808303816000865af19150503d806000811461167c576040519150601f19603f3d011682016040523d82523d6000602084013e611681565b606091505b5091509150816116d55780511561169b5780518082602001fd5b604051600160e51b62461bcd028152600401808060200182810382526032815260200180611ad56032913960400191505060405180910390fd5b60008180602001905160208110156116ec57600080fd5b50516001600160e01b031916600160e11b630a85bd010214935061109a92505050565b6001600160a01b03821661176d5760408051600160e51b62461bcd02815260206004820181905260248201527f4552433732313a206d696e7420746f20746865207a65726f2061646472657373604482015290519081900360640190fd5b61177681610fda565b156117cb5760408051600160e51b62461bcd02815260206004820152601c60248201527f4552433732313a20746f6b656e20616c7265616479206d696e74656400000000604482015290519081900360640190fd5b600081815260016020908152604080832080546001600160a01b0319166001600160a01b03871690811790915583526003909152902061180a906111a1565b60405181906001600160a01b038416906000907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef908290a45050565b600780546000838152600860205260408120829055600182018355919091527fa66cc928b5edb82af9bd49922954155ab7b0942694bea4ce44661d9a8736c6880155565b6000818152600260205260409020546001600160a01b031615610fd757600090815260026020526040902080546001600160a01b0319169055565b80546118d890600163ffffffff6118dc16565b9055565b6000610f3d83836040518060400160405280601e81526020017f536166654d6174683a207375627472616374696f6e206f766572666c6f770000815250611957565b6000813f7fc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a47081811480159061109a575050151592915050565b600081848411156119e957604051600160e51b62461bcd0281526004018080602001828103825283818151815260200191508051906020019080838360005b838110156119ae578181015183820152602001611996565b50505050905090810190601f1680156119db5780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b505050900390565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611a3257805160ff1916838001178555611a5f565b82800160010185558215611a5f579182015b82811115611a5f578251825591602001919060010190611a44565b50611a6b929150611a8f565b5090565b815481835581811115610868576000838152602090206108689181019083015b61067291905b80821115611a6b5760008155600101611a9556fe455243373231456e756d657261626c653a206f776e657220696e646578206f7574206f6620626f756e64734552433732313a207472616e7366657220746f206e6f6e20455243373231526563656976657220696d706c656d656e7465724f776e61626c653a206e6577206f776e657220697320746865207a65726f20616464726573734552433732313a207472616e7366657220746f20746865207a65726f20616464726573734552433732313a206f70657261746f7220717565727920666f72206e6f6e6578697374656e7420746f6b656e4552433732313a20617070726f76652063616c6c6572206973206e6f74206f776e6572206e6f7220617070726f76656420666f7220616c6c4552433732313a2062616c616e636520717565727920666f7220746865207a65726f20616464726573734552433732313a206f776e657220717565727920666f72206e6f6e6578697374656e7420746f6b656e4552433732313a20617070726f76656420717565727920666f72206e6f6e6578697374656e7420746f6b656e4552433732314d657461646174613a2055524920736574206f66206e6f6e6578697374656e7420746f6b656e4552433732313a207472616e73666572206f6620746f6b656e2074686174206973206e6f74206f776e4552433732314d657461646174613a2055524920717565727920666f72206e6f6e6578697374656e7420746f6b656e4552433732313a20617070726f76616c20746f2063757272656e74206f776e65724552433732313a207472616e736665722063616c6c6572206973206e6f74206f776e6572206e6f7220617070726f766564455243373231456e756d657261626c653a20676c6f62616c20696e646578206f7574206f6620626f756e6473a165627a7a7230582004c2b8d499a54217e54214674743f3086c3ead142b23379627456c6a33b568870029\", \"sourceMap\": \"198:485:0:-;;;367:54;8:9:-1;5:2;;;30:1;27;20:12;5:2;367:54:0;456:155:9;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;533:4;539:6;718:40:3;385:10;737:20;;718:18;;;:40;;:::i;:::-;2262::7;2136:10;2281:20;;2262:18;;;:40;;:::i;:::-;1357:51:8;1172:10;1376:31;;1357:18;;;:51;;:::i;:::-;912:12:10;;;;:5;;:12;;;;;:::i;:::-;-1:-1:-1;934:16:10;;;;:7;;:16;;;;;:::i;:::-;;1038:49;774:10;1057:29;;1038:18;;;:49;;:::i;:::-;840:254;;456:155:9;;698:17:6;718:12;:10;;;:12;;:::i;:::-;740:6;:18;;-1:-1:-1;;;;;;740:18:6;-1:-1:-1;;;;;740:18:6;;;;;;;;773:43;;740:18;;-1:-1:-1;740:18:6;-1:-1:-1;;773:43:6;;-1:-1:-1;;773:43:6;664:159;198:485:0;;1442:190:3;1517:25;;;;;;1509:66;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1585:33;;:20;:33;;;;;;;;;;:40;;-1:-1:-1;;1585:40:3;1621:4;1585:40;;;1442:190::o;788:96:1:-;867:10;788:96;;:::o;198:485:0:-;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;-1:-1:-1;198:485:0;;;-1:-1:-1;198:485:0;:::i;:::-;;;:::o;:::-;;;;;;;;;;;;;;;;;;;;;;;;;\"}";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_GETAPPROVED = "getApproved";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_TOKENOFOWNERBYINDEX = "tokenOfOwnerByIndex";

    public static final String FUNC_safeTransferFrom = "safeTransferFrom";

    public static final String FUNC_TOKENBYINDEX = "tokenByIndex";

    public static final String FUNC_OWNEROF = "ownerOf";

    public static final String FUNC_BASEURI = "baseURI";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_ISOWNER = "isOwner";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

    public static final String FUNC_TOKENURI = "tokenURI";

    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_MINT = "mint";

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event APPROVAL_EVENT = new Event("Approval",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
    ;

    @Deprecated
    protected Hui721(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Hui721(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Hui721(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Hui721(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final Function function = new Function(FUNC_SUPPORTSINTERFACE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> name() {
        final Function function = new Function(FUNC_NAME,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getApproved(BigInteger tokenId) {
        final Function function = new Function(FUNC_GETAPPROVED,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String to, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_APPROVE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to),
                new org.web3j.abi.datatypes.generated.Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(String from, String to, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_TRANSFERFROM,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from),
                new org.web3j.abi.datatypes.Address(160, to),
                new org.web3j.abi.datatypes.generated.Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> tokenOfOwnerByIndex(String owner, BigInteger index) {
        final Function function = new Function(FUNC_TOKENOFOWNERBYINDEX,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner),
                new org.web3j.abi.datatypes.generated.Uint256(index)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_safeTransferFrom,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from),
                new org.web3j.abi.datatypes.Address(160, to),
                new org.web3j.abi.datatypes.generated.Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> tokenByIndex(BigInteger index) {
        final Function function = new Function(FUNC_TOKENBYINDEX,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> ownerOf(BigInteger tokenId) {
        final Function function = new Function(FUNC_OWNEROF,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> baseURI() {
        final Function function = new Function(FUNC_BASEURI,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String owner) {
        final Function function = new Function(FUNC_BALANCEOF,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                FUNC_RENOUNCEOWNERSHIP,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isOwner() {
        final Function function = new Function(FUNC_ISOWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setApprovalForAll(String to, Boolean approved) {
        final Function function = new Function(
                FUNC_SETAPPROVALFORALL,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to),
                new org.web3j.abi.datatypes.Bool(approved)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId, byte[] _data) {
        final Function function = new Function(
                FUNC_safeTransferFrom,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from),
                new org.web3j.abi.datatypes.Address(160, to),
                new org.web3j.abi.datatypes.generated.Uint256(tokenId),
                new org.web3j.abi.datatypes.DynamicBytes(_data)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> tokenURI(BigInteger tokenId) {
        final Function function = new Function(FUNC_TOKENURI,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isApprovedForAll(String owner, String operator) {
        final Function function = new Function(FUNC_ISAPPROVEDFORALL,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner),
                new org.web3j.abi.datatypes.Address(160, operator)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public List<ApprovalForAllEventResponse> getApprovalForAllEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVALFORALL_EVENT, transactionReceipt);
        ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ApprovalForAllEventResponse>() {
            @Override
            public ApprovalForAllEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVALFORALL_EVENT, log);
                ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVALFORALL_EVENT));
        return approvalForAllEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> mint(String to, String tokenURI) {
        final Function function = new Function(
                FUNC_MINT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to),
                new org.web3j.abi.datatypes.Utf8String(tokenURI)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Hui721 load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Hui721(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Hui721 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Hui721(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Hui721 load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Hui721(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Hui721 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Hui721(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Hui721> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Hui721.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Hui721> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Hui721.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Hui721> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Hui721.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Hui721> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Hui721.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger tokenId;
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public String owner;

        public String approved;

        public BigInteger tokenId;
    }

    public static class ApprovalForAllEventResponse extends BaseEventResponse {
        public String owner;

        public String operator;

        public Boolean approved;
    }
}
