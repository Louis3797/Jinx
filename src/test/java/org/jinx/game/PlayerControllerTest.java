package org.jinx.game;

import org.jinx.player.Player;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;


class PlayerControllerTest {

    /**
     * Test if the method doesPlayerExist() returns false if the player list is empty
     *
     */
    @Test
    void testdoesPlayerExist() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var playerController = PlayerController.getPlayerControllerInstance();
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = playerController.getClass().getDeclaredMethod("doesPlayerExist", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        medthodArgruments[0] = "test";
        boolean temp = (boolean) method.invoke(playerController, medthodArgruments);
        assertFalse(temp);
    }

}

