package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;


import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImplementation implements DeathNote{

    private final long MAX_MILLISECONDS_TO_WRITE_DEATH_CAUSE = 40;
    private final long MAX_MILLISECONDS_TO_WRITE_DETAILS = 6040;

    private long timeLastNameAdded;
    private long timeLastDeathCauseAdded;
    private Map<String, Pair<String, String>> note;  
    private String lastNameAdded;

    public DeathNoteImplementation() {
        this.timeLastNameAdded = 0;
        this.timeLastDeathCauseAdded = 0;
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
        if (!isNameWritten(name)) {
            note.put(name, new Pair<>());   
            this.lastNameAdded = name;  
            startTimerDeathCause();
        }
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if (isNoteEmpty() || cause == null) {
            throw new IllegalStateException("The note is empty or the cause is null");
        }
        if (checkIfDeathCauseInTime()) {
            note.get(lastNameAdded).setFirst(cause);
            startTimerDetails();
            return true;
        }
        return false;
    }

    @Override
    public boolean writeDetails(String details) {
        if (isNoteEmpty() || details == null) {
            throw new IllegalStateException("The note is empty or the details are null");
        }
        if (checkIfDetailsInTime()) {
            note.get(lastNameAdded).setSecond(details);
            return true;
        }
        return false;
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

    private void startTimerDeathCause() {
        this.timeLastNameAdded = System.currentTimeMillis();
    }

    private void startTimerDetails() {
        this.timeLastDeathCauseAdded = System.currentTimeMillis();
    }

    private Boolean checkIfDeathCauseInTime() {
        return (this.timeLastNameAdded >= System.currentTimeMillis() - MAX_MILLISECONDS_TO_WRITE_DEATH_CAUSE);
    }  
    
    private Boolean checkIfDetailsInTime() {
        return (this.timeLastDeathCauseAdded >= System.currentTimeMillis() - MAX_MILLISECONDS_TO_WRITE_DETAILS);
    }  

    private Boolean isNoteEmpty() {
        return this.note.isEmpty();
    }
}