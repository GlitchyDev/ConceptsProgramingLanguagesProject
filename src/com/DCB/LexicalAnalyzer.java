package com.DCB;

import java.util.ArrayList;

public class LexicalAnalyzer {
    private ArrayList<Object> analyzedScript = new ArrayList<>();


    public LexicalAnalyzer(ScriptReader scriptReader) {

        while(scriptReader.hasNextChar()) {
            char previewChar = scriptReader.previewNextChar();
            if (previewChar == '"') {
                createString(scriptReader);
            } else {
                if (previewChar == '\'') {
                    createString(scriptReader);
                } else {
                    if (previewChar == '~') {
                        createBoolean(scriptReader);
                    } else {
                        if(Character.isDigit(previewChar)) {
                            createNumber(scriptReader);
                        } else {
                            if(previewChar == '_') {
                                createIdentifier(scriptReader);
                            } else {
                                createValue(scriptReader);
                            }
                        }
                    }


                }
            }
        }


    }

    public void createString(ScriptReader scriptReader) {
        String createdString = "";

    }

    public void createChar(ScriptReader scriptReader) {

    }

    public void createBoolean(ScriptReader scriptReader) {

    }

    public void createNumber(ScriptReader scriptReader) {

    }

    public void createIdentifier(ScriptReader scriptReader) {

    }

    public void createValue(ScriptReader scriptReader) {

    }

    public ArrayList<Object> getAnalyzedScript() {
        return analyzedScript;
    }
}
