package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;


import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImplementation implements DeathNote{

    private long currentMillis;
    private Map<String, Pair<String, String>> note;  
    private String lastNameAdded;

    public DeathNoteImplementation() {
        this.currentMillis = 0;
        this.note = new HashMap<>();
        this.lastNameAdded = null;
    }

    @Override
    public String getRule(int ruleNumber) {
        final int nRules = DeathNote.RULES.size();
        if (ruleNumber < 1 || ruleNumber > nRules) {
            throw new IllegalArgumentException("You shall give a number within 1 and " + nRules);
        }
        return DeathNote.RULES.get(nRules - 1);
    }

    @Override
    public void writeName(String name) {
        if (name == null) {
            throw new NullPointerException("The name must not be null!");
        }
        if (isNameWritten(name)) {
            note.put(name, new Pair<>());   
            this.lastNameAdded = name;  
            startTimer();
        }
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if (checkIfInTime(40)) {
            if (isNoteEmpty() || cause == null) {
                throw new IllegalStateException("The note is empty or the cause is null");
            }
            note.get(lastNameAdded).setFirst(cause);
            return true;
        }
        return false;
    }

    @Override
    public boolean writeDetails(String details) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeDetails'");
    }

    @Override
    public String getDeathCause(String name) {
        if (name == null) {
            throw new IllegalArgumentException("The name must not be null!");
        }
       return note.get(name).getFirst();
    }

    @Override
    public String getDeathDetails(String name) {
        if (name == null) {
            throw new IllegalArgumentException("The name must not be null!");
        }
        return note.get(name).getSecond();
    }

    @Override
    public boolean isNameWritten(String name) {
        return this.note.containsKey(name);
    }

    private void startTimer() {
        this.currentMillis = System.currentTimeMillis();
    }

    private Boolean checkIfInTime(long time) {
        return (this.currentMillis < System.currentTimeMillis() - time);
    }   

    private Boolean isNoteEmpty() {
        return this.note.isEmpty();
    }
}