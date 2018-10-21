package java_bootcamp;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;


@InitiatingFlow
@StartableByRPC
public class TwoPartyFlow extends FlowLogic<Integer> {

    private Party counterparty;

    public TwoPartyFlow(Party counterparty) {
        this.counterparty = counterparty;
    }

    @Override
    @Suspendable
    public Integer call() throws FlowException {
        //let's communicate with counterparty flow
        FlowSession session = initiateFlow(counterparty);
        session.send(1);

        int receiverIncremented = session.receive(Integer.class).unwrap(it -> it);
        return receiverIncremented;
    }
}
