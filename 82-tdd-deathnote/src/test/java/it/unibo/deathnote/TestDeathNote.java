package it.unibo.deathnote;

import java.lang.Thread;
import java.util.List;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImplementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        for (int i = 1; i <= DeathNote.RULES.size(); i++) {
            assertNotNull(deathNote.getRule(i));
            assertNotEquals(deathNote.getRule(i), "");
        }
    }

    @Test
    void testHumanDeadIfWritten() {
        final String name1 = "Ngolo Kante";
        final String name2 = "Giovanni Rana";
        assertFalse(deathNote.isNameWritten(name1));
        addNameAndVerify(name1);
        assertFalse(deathNote.isNameWritten(name2));
        assertFalse(deathNote.isNameWritten(""));
    }

    private void addNameAndVerify(String name) {
        deathNote.writeName(name);
        assertTrue(deathNote.isNameWritten(name));
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
        addNameAndVerify(name1);
        addCauseOfDeathAndVerify(deathCause1, name1);
        final String name2 = "Gianluigi Rossi";
        addNameAndVerify(name2);
        final String deathCause2 = "karting accident";
        addCauseOfDeathAndVerify(deathCause2, name2);
        Thread.sleep(100);
        deathNote.writeDeathCause(deathCause1);
        assertEquals(deathNote.getDeathCause(name2), deathCause2);
    }

    private void addCauseOfDeathAndVerify(String cause, String name) {
        deathNote.writeDeathCause(cause);
        assertEquals(deathNote.getDeathCause(name), cause);
    }

    void test() throws InterruptedException {
        final String deathDeatails1 = "ran for too long";
        assertThrows(IllegalStateException.class, new Executable() {
            public void execute() throws Throwable {
                deathNote.writeDetails(deathDeatails1);
            }
        });
        final String name1 = "Giancarlo Carli";
        addNameAndVerify(name1);
        assertNull(deathNote.getDeathDetails(name1));
        assertTrue(deathNote.writeDetails(deathDeatails1));
        assertEquals(deathNote.getDeathDetails(name1), deathDeatails1);
        final String name2 = "Anubis";
        addNameAndVerify(name2);
        Thread.sleep(6100);
        assertFalse(deathNote.writeDetails(deathDeatails1));
        assertNull(deathNote.getDeathDetails(name2));
    }
}