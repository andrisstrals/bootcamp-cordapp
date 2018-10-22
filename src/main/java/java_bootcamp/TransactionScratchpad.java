package java_bootcamp;

import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.ContractState;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.identity.Party;
import net.corda.core.transactions.TransactionBuilder;

import java.security.PublicKey;
import java.util.List;

public class TransactionScratchpad {
    public static void main(String[] args) {
        TransactionBuilder builder = new TransactionBuilder();

        StateAndRef<ContractState> inputState = null;
        ContractState outputState = new HouseState("123 Moorfield", null);
        PublicKey requiredSigner = ((HouseState) outputState).getOwner().getOwningKey();
        List<PublicKey> requiredSigners = ImmutableList.of(requiredSigner);
        Party notary = null;


        builder.setNotary(notary);
        builder
                .addInputState(inputState)
                .addOutputState(outputState, "java_bootcamp.HouseContract")
                .addCommand(new HouseContract.Register(), requiredSigners);
//        builder.verify();

    }
}
