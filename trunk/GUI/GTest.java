package GUI;

import WorldStateTracker.Player;

public class GTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        PrimaryGUI newGUI = new PrimaryGUI(true, 0, new Player("Tester", null));
        newGUI.setVisible(true);
    }

}
