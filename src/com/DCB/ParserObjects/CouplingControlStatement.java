package com.DCB.ParserObjects;

/**
 * All control statements extend this class making them identifiable
 */
public abstract class CouplingControlStatement extends CouplingStatement {

    public CouplingControlStatement(CoupleObjectType coupleObjectType) {
        super(coupleObjectType);
    }
}
