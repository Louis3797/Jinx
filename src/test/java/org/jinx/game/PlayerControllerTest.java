package org.jinx.game;

import org.jinx.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class PlayerControllerTest {

    private PlayerController playerController = PlayerController.getPlayerControllerInstance();

    @AfterEach
    void afterEach() {
        playerController.getPlayers().clear(); // clear players in Queue after each test
    }

    /**
     * Test if PlayerController returns same instance everytime
     */
    @Test
    void testPlayerControllerInstance() {
        assertEquals(PlayerController.getPlayerControllerInstance(), PlayerController.getPlayerControllerInstance());
    }

    /**
     * Test if currentPlayer is not null after next
     */
    @Test
    void testGetCurrentPlayerAfterNext() {
        playerController.getPlayers().add(new Player("Bob"));
        playerController.getPlayers().add(new Player("Bob2"));

        playerController.next();
        assertNotNull(playerController.getCurrentPlayer());
    }

    /**
     * Test if currentPlayer changes after next
     */
    @Test
    void testNext() {
        Player bob = new Player("Bob");
        Player bob2 = new Player("Bob2");
        playerController.getPlayers().add(bob);
        playerController.getPlayers().add(bob2);

        playerController.next(); // set currentPlayer from null to Bob
        playerController.next(); // set currentPlayer from Bob to Bob2

        assertEquals(bob2, playerController.getCurrentPlayer());
    }

    /**
     * Test if oder of queue changes after shuffle
     */
    @Test
    void testShufflePlayerOrder() {
        Player bob = new Player("Bob");
        Player bob2 = new Player("Bob2");
        Player bob3 = new Player("Bob3");

        playerController.getPlayers().add(bob);
        playerController.getPlayers().add(bob2);
        playerController.getPlayers().add(bob3);


        List<Player> oldPlayerOrder = playerController.getPlayers().stream().toList();

        playerController.shufflePlayerOrder();

        List<Player> newPlayerOrder = playerController.getPlayers().stream().toList();


        assertNotEquals(oldPlayerOrder, newPlayerOrder);
    }

    /**
     * Test doesPlayerExists with empty queue
     *
     * @throws NoSuchMethodException     Exception for Reflection
     * @throws InvocationTargetException Exception for Reflection
     * @throws IllegalAccessException    Exception for Reflection
     */
    @Test
    void testDoesPlayerExistsWithEmptyQueue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        // Reflection to get private method doesPlayer exists
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = playerController.getClass().getDeclaredMethod("doesPlayerExist", parameters);
        method.setAccessible(true);

        Object[] medthodArgruments = new Object[1];
        medthodArgruments[0] = "Bob";

        boolean result = (boolean) method.invoke(playerController, medthodArgruments);
        assertFalse(result);
    }

    /**
     * Test doesPlayerExists with existing player
     *
     * @throws NoSuchMethodException     Exception for Reflection
     * @throws InvocationTargetException Exception for Reflection
     * @throws IllegalAccessException    Exception for Reflection
     */
    @Test
    void testDoesPlayerExistsWithExistingPlayer() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        playerController.getPlayers().add(new Player("Bob"));

        // Reflection to get private method doesPlayer exists
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = playerController.getClass().getDeclaredMethod("doesPlayerExist", parameters);
        method.setAccessible(true);

        Object[] methodArguments = new Object[1];
        methodArguments[0] = "Bob";

        boolean result = (boolean) method.invoke(playerController, methodArguments);
        assertTrue(result);
    }

    /**
     * Test doesPlayerExists with no existing player
     *
     * @throws NoSuchMethodException     Exception for Reflection
     * @throws InvocationTargetException Exception for Reflection
     * @throws IllegalAccessException    Exception for Reflection
     */
    @Test
    void testDoesPlayerExistsWithNoExistingPlayer() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        playerController.getPlayers().add(new Player("Bob"));

        // Reflection to get private method doesPlayer exists
        Class[] parameters = new Class[1];
        parameters[0] = String.class;
        Method method = playerController.getClass().getDeclaredMethod("doesPlayerExist", parameters);
        method.setAccessible(true);

        Object[] methodArguments = new Object[1];
        methodArguments[0] = "Bob2";

        boolean result = (boolean) method.invoke(playerController, methodArguments);
        assertFalse(result);
    }

}

