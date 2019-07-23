package TATest;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class AppTest {
    @Test
    public void txt() {
        ArrayList<Action> actions = App.processingTests("task.txt");
        assertEquals(8, actions.size());
        assertEquals("true openurl https://google.com ", actions.get(0).toString());
    }

    @Test
    public void xml() {
        ArrayList<Action> actions = App.processingTests("task.xml");
        assertEquals(8, actions.size());
        assertEquals("true screenshot  ", actions.get(7).toString());
    }

    @Test
    public void json() {
        ArrayList<Action> actions = App.processingTests("task.json");
        assertEquals(8, actions.size());
        assertEquals("true openurl https://google.com ", actions.get(0).toString());
    }

    @Test
    public void xls() {
        ArrayList<Action> actions = App.processingTests("task.xls");
        assertEquals(8, actions.size());
        assertEquals("true screenshot  ", actions.get(7).toString());
    }
}
