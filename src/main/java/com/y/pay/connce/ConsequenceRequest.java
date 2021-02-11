package com.y.pay.connce;

/**
 * @author XiFYuW
 * @since  2020/10/20 17:31
 */
public class ConsequenceRequest implements Runnable {

    private final Consequence consequence;

    public ConsequenceRequest(Consequence consequence) {
        this.consequence = consequence;
    }

    public Consequence getConsequence() {
        return consequence;
    }

    @Override
    public void run() {
        consequence.ConsequenceQuery();
    }
}
