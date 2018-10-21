package java_bootcamp;

import co.paralleluniverse.fibers.Suspendable;
import net.corda.core.flows.FlowException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.flows.InitiatingFlow;


@InitiatingFlow //flow we can start on node directly
//@InitiatedBy()  //started as response to other flow
public class VerySimpleFlow extends FlowLogic<Integer> {

    @Override
    @Suspendable
    public Integer call() throws FlowException {
        int a = 1;
        int b = 2;

        return a + b;
    }
}
