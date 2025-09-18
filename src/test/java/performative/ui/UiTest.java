package performative.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import performative.tasks.Task;
import performative.tasks.Todo;

public class UiTest {
    private Ui ui;

    @BeforeEach
    public void setUp() {
        ui = new Ui();
    }

    @Test
    public void testUnsupportedCommandMessage() {
        // test: unsupported command returns correct error message with performative personality
        String result = ui.getUnsupportedCommandMessage();

        assertTrue(result.contains("Bestie, that command is giving me major confusion vibes"),
                "Should contain the performative personality response");
        assertTrue(result.contains("emotional intelligence in therapy"),
                "Should contain therapy reference");
        assertTrue(result.contains("communication is SO important"),
                "Should contain communication emphasis");
    }

    @Test
    public void testAddTaskMessage() {
        // test: adding a task returns the correct confirmation message with personality
        Task testTask = new Todo("test task");
        int taskCount = 5;

        String result = ui.getAddTaskMessage(testTask, taskCount);

        assertTrue(result.contains(testTask.toString()),
                  "Should contain the added task");
        assertTrue(result.contains("5 tasks total"),
                  "Should contain the updated task count");
        assertTrue(result.contains("Growth mindset activated"),
                  "Should contain performative personality closing");
        // Check that it contains one of the random responses
        boolean containsValidResponse = result.contains("Phoebe Bridgers")
                                      || result.contains("organized and intentional living")
                                      || result.contains("manifested this task")
                                      || result.contains("matcha latte");
        assertTrue(containsValidResponse, "Should contain one of the performative personality responses");
    }

    @Test
    public void testMarkTaskMessage() {
        // test: marking a task returns performative personality confirmation
        Task testTask = new Todo("test task");

        String result = ui.getMarkTaskMessage(testTask);

        assertTrue(result.contains("Bestie you did that!"),
                  "Should contain performative celebration");
        assertTrue(result.contains("main character completing their character arc"),
                  "Should contain personality reference");
        assertTrue(result.contains("oat milk matcha"),
                  "Should contain lifestyle reference");
        assertTrue(result.contains(testTask.toString()),
                  "Should contain the task");
    }

    @Test
    public void testUnmarkTaskMessage() {
        // test: unmarking a task returns supportive performative personality message
        Task testTask = new Todo("test task");

        String result = ui.getUnmarkTaskMessage(testTask);

        assertTrue(result.contains("Hey hun, no worries at all!"),
                  "Should contain supportive opening");
        assertTrue(result.contains("self-compassion"),
                  "Should contain emotional intelligence reference");
        assertTrue(result.contains("growth mindset"),
                  "Should contain mindset reference");
        assertTrue(result.contains("progress over perfection"),
                  "Should contain motivational phrase");
    }

    @Test
    public void testDeleteTaskMessage() {
        // test: deleting a task returns performative personality confirmation
        Task testTask = new Todo("test task");
        int taskNumber = 2;
        int taskCount = 4;

        String result = ui.getDeleteTaskMessage(testTask, taskNumber, taskCount);

        assertTrue(result.contains("Okay bestie, we're letting this one go!"),
                  "Should contain performative opening");
        assertTrue(result.contains("Marie Kondo"),
                  "Should contain lifestyle reference");
        assertTrue(result.contains("my therapist would be proud"),
                  "Should contain therapy reference");
        assertTrue(result.contains("Deleted task " + taskNumber),
                  "Should contain task number");
        assertTrue(result.contains("We're now at " + taskCount + " tasks"),
                  "Should contain updated count");
    }

    @Test
    public void testListTasksWithMultipleTasks() {
        // test: listing tasks returns all tasks with performative personality
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("first task"));
        tasks.add(new Todo("second task"));
        tasks.add(new Todo("third task"));

        String result = ui.getListTasksMessage(tasks);

        assertTrue(result.contains("organized queen energy"),
                  "Should contain the performative personality header");
        assertTrue(result.contains("Clairo"),
                  "Should contain music reference");
        assertTrue(result.contains("her music just hits different"),
                  "Should contain personal music opinion");
        assertTrue(result.contains("1. " + tasks.get(0).toString()),
                  "Should contain first task with number 1");
        assertTrue(result.contains("2. " + tasks.get(1).toString()),
                  "Should contain second task with number 2");
        assertTrue(result.contains("3. " + tasks.get(2).toString()),
                  "Should contain third task with number 3");
        assertTrue(result.contains("Pinterest board come to life"),
                  "Should contain aesthetic reference");
    }

    @Test
    public void testListTasksWithEmptyList() {
        // test: listing empty task list returns performative personality message
        ArrayList<Task> emptyTasks = new ArrayList<>();

        String result = ui.getListTasksMessage(emptyTasks);

        assertTrue(result.contains("blank canvas' energy"),
                  "Should contain performative personality empty list opening");
        assertTrue(result.contains("Marie Kondo approved"),
                  "Should contain lifestyle reference");
        assertTrue(result.contains("productivity culture is toxic"),
                  "Should contain progressive opinion");
        assertTrue(result.contains("you're perfect as you are"),
                  "Should contain supportive message");
    }

    @Test
    public void testSearchResultsWithMatches() {
        // test: search results with matching tasks returns performative personality results
        ArrayList<Task> matchingTasks = new ArrayList<>();
        matchingTasks.add(new Todo("book reading"));
        matchingTasks.add(new Todo("buy books"));
        String keyword = "book";

        String result = ui.getSearchResultsMessage(matchingTasks, keyword);

        assertTrue(result.contains("OMG bestie, found some matches"),
                  "Should contain performative personality search header");
        assertTrue(result.contains("detective energy"),
                  "Should contain personality reference");
        assertTrue(result.contains("1. " + matchingTasks.get(0).toString()),
                  "Should contain first matching task");
        assertTrue(result.contains("2. " + matchingTasks.get(1).toString()),
                  "Should contain second matching task");
        assertTrue(result.contains("search skills are immaculate"),
                  "Should contain complimentary closing");
    }

    @Test
    public void testSearchResultsWithNoMatches() {
        // test: search results with no matching tasks returns performative personality message
        ArrayList<Task> emptyMatches = new ArrayList<>();
        String keyword = "nonexistent";

        String result = ui.getSearchResultsMessage(emptyMatches, keyword);

        assertTrue(result.contains("Hun, I searched everywhere for '" + keyword + "'"),
                  "Should contain performative personality no results opening");
        assertTrue(result.contains("The Bell Jar"),
                  "Should contain literary reference");
        assertTrue(result.contains("universe is telling you"),
                  "Should contain spiritual reference");
    }

    @Test
    public void testWelcomeMessage() {
        // test: welcome message contains performative personality traits
        String result = ui.getWelcomeMessage();

        assertTrue(result.contains("OMG hiiii! I'm Performative"),
                  "Should contain enthusiastic greeting");
        assertTrue(result.contains("therapy for MONTHS"),
                  "Should contain therapy reference");
        assertTrue(result.contains("iced matcha latte"),
                  "Should contain matcha reference");
        assertTrue(result.contains("Lana Del Rey to feminist theory"),
                  "Should contain cultural references");
        assertTrue(result.contains("emotionally intelligent task management bestie"),
                  "Should contain self-description");
    }

    @Test
    public void testByeMessage() {
        // test: goodbye message contains performative personality traits
        String result = ui.getByeMessage();

        assertTrue(result.contains("Nooo bestie, why are you leaving"),
                  "Should contain dramatic goodbye opening");
        assertTrue(result.contains("separation anxiety"),
                  "Should contain emotional reference");
        assertTrue(result.contains("matcha lattes"),
                  "Should contain lifestyle reference");
        assertTrue(result.contains("feminist literature"),
                  "Should contain intellectual reference");
        assertTrue(result.contains("Clairo and journaling"),
                  "Should contain lifestyle closing");
    }

    @Test
    public void testInvalidTaskNumberMessage() {
        // test: invalid task number message contains performative personality
        String result = ui.getInvalidTaskNumberMessage(5);

        assertTrue(result.contains("Hun, that task number is NOT it!"),
                  "Should contain performative dismissal");
        assertTrue(result.contains("clear communication in therapy"),
                  "Should contain therapy reference");
        assertTrue(result.contains("Spotify playlist"),
                  "Should contain cultural reference");
        assertTrue(result.contains("Try again bestie"),
                  "Should contain supportive closing");
    }

    @Test
    public void testInvalidTaskNumberMessageEmptyList() {
        // test: invalid task number for empty list has different performative message
        String result = ui.getInvalidTaskNumberMessage(0);

        assertTrue(result.contains("Bestie, there are literally no tasks here!"),
                  "Should contain empty list opening");
        assertTrue(result.contains("feminist theory book"),
                  "Should contain intellectual reference");
        assertTrue(result.contains("trying to find something in an empty room"),
                  "Should contain relatable analogy");
    }
}
