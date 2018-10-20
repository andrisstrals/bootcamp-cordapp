package java_bootcamp;

import net.corda.core.contracts.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.LedgerTransaction;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.List;

import static net.corda.core.contracts.ContractsDSL.requireThat;

/* Our contract, governing how our state will evolve over time.
 * See src/main/java/examples/ExampleContract.java for an example. */
public class TokenContract implements Contract {
    public static String ID = "java_bootcamp.TokenContract";

    @Override
    public void verify(@NotNull LedgerTransaction tx) throws IllegalArgumentException {
        if(tx.getCommands().size() != 1) {
            throw new IllegalArgumentException("Transaction must have exactly one command");
        }
        Command command = tx.getCommand(0);
        List<PublicKey> requiredSigners = command.getSigners();
        CommandData commandType = command.getValue();

        if(commandType instanceof Issue) {
            //"Shape" constraints
            if(tx.getInputStates().size() != 0) {//this is the brand new house to be registered
                throw new IllegalArgumentException("Issue transaction must have no inputs");
            }
            if(tx.getOutputStates().size() != 1) {//this is the brand new house to be registered
                throw new IllegalArgumentException("Issue transaction must have one output");
            }
            if(tx.getCommands().size() != 1) {
                throw new IllegalArgumentException("Must have exactly one command");
            }

            //Content constraints

            ContractState output = tx.getOutput(0);
            if(!(output instanceof TokenState)) {
                throw new IllegalArgumentException("Output must be token state");
            }

            TokenState outTokenState = (TokenState) output;
            if(outTokenState.getAmount() <= 0) {
                throw new IllegalArgumentException("Amount can't be zero");
            }

            // Required signer constraints
            Party owner = outTokenState.getOwner();
            Party issuer = outTokenState.getIssuer();
            PublicKey ownersKey = owner.getOwningKey();
            PublicKey issuersKey = issuer.getOwningKey();

            if(!requiredSigners.contains(issuersKey)) {
                throw new IllegalArgumentException("Issuer is required signer");
            }
//            if(!(requiredSigners.contains(ownersKey))) {
//                throw new IllegalArgumentException("Owner must sign the transaction");
//            }
        }
        else {
            throw new IllegalArgumentException("Command type not recognised");
        }

    }

    public static class Issue implements CommandData {
    }
}