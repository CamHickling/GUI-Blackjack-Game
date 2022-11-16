import model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ui.UserInterface.playersTurn;

public class UserInterfaceTest {

    @Test
    public void testPlayersTurn() {
        Player play = new Player("", 1);
        int handsize = play.getHand().getMyCards().size();
        assertEquals(handsize, 2);
        playersTurn(play, true);
        assertEquals(play.getHand().getMyCards().size(), 3);
    }
}
