package it.unibo.deathnote;

import java.lang.Thread;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImplementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout.ThreadMode;
import org.junit.jupiter.api.function.Executable;

class TestDeathNote {

    private DeathNote deathNote;

    @BeforeEach
    void setUp() {
        deathNote = new DeathNoteImplementation();
    }

    @Test
    void testGetZeroAndNegativeRules() {
        testRulesIndex(0);
        testRulesIndex(-1);
    }

    private void testRulesIndex(final int index) {
        try {
            deathNote.getRule(index);
        } catch (Exception e) {
            assertInstanceOf(IllegalArgumentException.class, e);
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
            assertFalse(e.getMessage().isEmpty());
        }
    }

    @Test
    void testRulesNotNullAndNotEmpty() {
        for (String string : DeathNote.RULES) {
            assertNotEquals(string, null);
            assertNotEquals(string, "");
        }
    }

    @Test
    void testHumanDeadIfWritten() {
        final String name1 = "Ngolo Kante";
        final String name2 = "Giovanni Rana";
        assertFalse(deathNote.isNameWritten(name1));
        deathNote.writeName(name1);
        assertTrue(deathNote.isNameWritten(name1));
        assertFalse(deathNote.isNameWritten(name2));
        assertFalse(deathNote.isNameWritten(""));
    }

    @Test
    void testCauseOfDeath() throws InterruptedException {
        final String deathCause1 = "heart attack";
        assertThrows(IllegalStateException.class, new Executable() {
            public void execute() throws Throwable {
                deathNote.writeDeathCause(deathCause1);
            }
        });
        final String name1 = "Ezio Greggio";
        deathNote.writeName(name1);
        assertTrue(deathNote.isNameWritten(name1));
        deathNote.writeDeathCause(deathCause1);
        assertEquals(deathNote.getDeathCause(name1), deathCause1);
        final String name2 = "Gianluigi Rossi";
        deathNote.writeName(name2);
        assertTrue(deathNote.isNameWritten(name2));
        final String deathCause2 = "karting accident";
        deathNote.writeDeathCause(deathCause2);
        assertEquals(deathNote.getDeathCause(name2), deathCause2);
        Thread.sleep(100);
        deathNote.writeDeathCause(deathCause1);
        assertEquals(deathNote.getDeathCause(name2), deathCause2);
    }
}