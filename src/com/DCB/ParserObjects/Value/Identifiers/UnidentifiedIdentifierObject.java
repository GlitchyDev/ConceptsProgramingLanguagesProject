package com.DCB.ParserObjects.Value.Identifiers;

import com.DCB.LexicalObjects.Identifier;
import com.DCB.LexicalObjects.KeyWord;
import com.DCB.LexicalObjects.Value;
import com.DCB.ParserObjects.CoupledObject;
import com.DCB.ParserObjects.CouplingStatement;
import com.DCB.ParserObjects.Value.BooleanValueObject;

public class UnidentifiedIdentifierObject extends CoupledObject {
    private final Identifier identifier;

    public UnidentifiedIdentifierObject(Identifier identifier) {
        super(CoupleObjectType.UNDECLARED_IDENTIFIER);
        this.identifier = identifier;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public boolean hasReturnType() {
        return true;
    }

    @Override
    public KeyWord.VariableType getReturnType() {
        return KeyWord.VariableType.TYPELESS;
    }

    @Override
    public String getStringIdentifier() {
        return "[" + coupleObjectType + " | " + identifier + " ]";
    }

    @Override
    public String getParsedGrammar() {
        return "<arithmetic_expression> -> literal_undeclared \n";
    }
}
