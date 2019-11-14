package com.DCB.ParserObjects;

import com.DCB.ParserObjects.CoupledObject;


/**
 * All statements extend this class making them identifiable
 */
public abstract class CouplingStatement extends CoupledObject {
    private boolean isLateStatement = false;
    public CouplingStatement(CoupleObjectType coupleObjectType) {
        super(coupleObjectType);
    }

    public void setLateStatement() {
        isLateStatement = true;
    }

    public boolean isLateStatement() {
        return isLateStatement;
    }
}
