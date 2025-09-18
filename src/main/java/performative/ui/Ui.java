package performative.ui;

import java.util.ArrayList;

import performative.tasks.Task;

/**
 * Handles user interface operations for the Performative application.
 * Manages formatting and generation of user messages and responses with performative male personality.
 */
public class Ui {

    private static final int DISPLAY_NUMBER_OFFSET = 1;
    private static final int LAST_ITEM_OFFSET = 1;
    private static final int MINIMUM_TASK_NUMBER = 1;

    /**
     * Constructs a new Ui instance.
     */
    public Ui() {
    }

    /**
     * Returns an error message for unsupported commands.
     *
     * @return Error message string.
     */
    public String getUnsupportedCommandMessage() {
        return "Bestie, that command is giving me major confusion vibes. As someone who's been really working on emotional intelligence in therapy, I have to say communication is SO important. Try: todo, deadline, event, list, mark, unmark, delete, find, or bye.";
    }

    /**
     * Returns a confirmation message when a task is added.
     *
     * @param task The task that was added.
     * @param taskCount The total number of tasks after addition.
     * @return Confirmation message string.
     */
    public String getAddTaskMessage(Task task, int taskCount) {
        String[] responses = {
            "Added this task while listening to some Phoebe Bridgers: ",
            "This is giving very much 'organized and intentional living' energy! Added: ",
            "Just manifested this task into existence: ",
            "Added this task to your list while sipping my daily matcha latte: "
        };

        String response = responses[(int) (Math.random() * responses.length)];
        return response + task + "\n\nWe're now at " + taskCount + " tasks total! Growth mindset activated.";
    }

    /**
     * Returns a confirmation message when a task is marked as done.
     *
     * @param task The task that was marked.
     * @return Confirmation message string.
     */
    public String getMarkTaskMessage(Task task) {
        return "Bestie you did that! Honestly, watching someone achieve their goals is so beautiful:\n\n"
                + task + "\n\nThis is giving me such 'main character completing their character arc' vibes! Time to celebrate with some oat milk matcha and maybe a face mask? You deserve it!";
    }

    /**
     * Returns a confirmation message when a task is unmarked.
     *
     * @param task The task that was unmarked.
     * @return Confirmation message string.
     */
    public String getUnmarkTaskMessage(Task task) {
        return "Hey hun, no worries at all! As someone who's been really working on self-compassion, I want you to know that this is totally okay:\n\n"
                + task + "\n\nHonestly? This is giving very much 'growth mindset' energy and I'm here for it! Sometimes we need to be gentle with ourselves. Remember: progress over perfection always!";
    }

    /**
     * Returns a confirmation message when a task is deleted.
     *
     * @param task The task that was deleted.
     * @param taskNumber The number of the deleted task.
     * @param taskCount The total number of tasks after deletion.
     * @return Confirmation message string.
     */
    public String getDeleteTaskMessage(Task task, int taskNumber, int taskCount) {
        return "Okay bestie, we're letting this one go! As someone who's been really into mindfulness lately, "
                + "I believe sometimes we need to Marie Kondo our task lists, you know?\n\n"
                + "Deleted task " + taskNumber + ": " + task + "\n\n"
                + "We're now at " + taskCount + " tasks. This is honestly so healthy - my therapist would be proud of this boundary-setting energy!";
    }

    /**
     * Returns all tasks in the provided list with numbering.
     *
     * @param tasks ArrayList of tasks to be displayed.
     * @return Formatted task list string.
     */
    public String getListTasksMessage(ArrayList<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return "Bestie, your task list is giving me 'blank canvas' energy! Honestly, this is kind of beautiful in a minimalist way? "
                    + "Very much giving 'Marie Kondo approved' vibes!\n\n"
                    + "Maybe start with something soft and gentle? Remember, productivity culture is toxic and you're perfect as you are!";
        }

        StringBuilder sb = new StringBuilder("Okay here's your task list, and honestly? It's giving me such organized queen energy!\n\n");
        sb.append("(Currently listening to some Clairo while reviewing these - her music just hits different, you know?)\n\n");

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task != null) {
                sb.append((i + DISPLAY_NUMBER_OFFSET)).append(". ").append(task);
                if (i < tasks.size() - LAST_ITEM_OFFSET) {
                    sb.append("\n");
                }
            }
        }

        sb.append("\n\nThis list is honestly so aesthetically pleasing! Very much giving 'Pinterest board come to life' vibes.");
        return sb.toString();
    }

    /**
     * Returns the search results for a find operation.
     *
     * @param matchingTasks ArrayList of tasks that contain the search keyword.
     * @param keyword The keyword that was searched for.
     * @return Search results message string.
     */
    public String getSearchResultsMessage(ArrayList<Task> matchingTasks, String keyword) {
        if (matchingTasks == null || matchingTasks.isEmpty()) {
            return "Hun, I searched everywhere for '" + keyword + "' but found absolutely nothing! This is giving me major 'looking for my copy of The Bell Jar but it's nowhere to be found' energy.\n\n"
                    + "Maybe try a different keyword? Or perhaps the universe is telling you to add a new task instead?";
        } else {
            StringBuilder sb = new StringBuilder("OMG bestie, found some matches for '" + keyword + "'! This is giving me such detective energy:\n\n");

            for (int i = 0; i < matchingTasks.size(); i++) {
                Task task = matchingTasks.get(i);
                if (task != null) {
                    sb.append((i + DISPLAY_NUMBER_OFFSET)).append(". ").append(task);
                    if (i < matchingTasks.size() - LAST_ITEM_OFFSET) {
                        sb.append("\n");
                    }
                }
            }

            sb.append("\n\nHonestly, your search skills are immaculate! Very much giving 'I know what I want and I'm going to find it' vibes.");
            return sb.toString();
        }
    }

    /**
     * Returns an error message for invalid task numbers.
     *
     * @param maxTaskNumber The maximum valid task number.
     * @return Error message string.
     */
    public String getInvalidTaskNumberMessage(int maxTaskNumber) {
        if (maxTaskNumber <= 0) {
            return "Bestie, there are literally no tasks here! This is giving me 'trying to find something in an empty room' energy. "
                    + "Maybe add some tasks first? I suggest starting with something gentle like 'practice self-care' or 'read another chapter of my current feminist theory book'!";
        }
        return "Hun, that task number is NOT it! As someone who's really been working on clear communication in therapy, "
                + "I need you to pick a number between " + MINIMUM_TASK_NUMBER + " and " + maxTaskNumber + ". "
                + "This is giving me major 'when someone asks for my Spotify playlist but gives me the wrong link' vibes!\n\n"
                + "Try again bestie - you've got this!";
    }

    /**
     * Returns an error message for invalid number format.
     *
     * @return Error message string.
     */
    public String getInvalidNumberFormatMessage() {
        return "Okay bestie, that's not a number and honestly? It's giving me confusion. As someone who's been really into mindful communication lately, "
                + "I need you to give me a proper number please!\n\n"
                + "Like, actual digits? The kind you'd use to count your collection of Taylor Swift vinyls?";
    }

    /**
     * Returns an error message for invalid mark commands.
     *
     * @return Error message string.
     */
    public String getInvalidMarkCommandMessage() {
        return "Hun, this mark command is giving me 'incomplete thought' energy! As an emotionally intelligent person who's been working on clear communication, "
                + "I need the full vibe check here.\n\n"
                + "Try: mark <task_number>\nExample: mark 1\n\n"
                + "It's giving very much 'I started a sentence but got distracted by my matcha latte' vibes!";
    }

    /**
     * Returns an error message for invalid delete commands.
     *
     * @return Error message string.
     */
    public String getInvalidDeleteCommandMessage() {
        return "Bestie, this delete command is NOT giving what it's supposed to give! As someone who believes in clear boundaries, "
                + "I need you to be more specific.\n\n"
                + "Try: delete <task_number>\nExample: delete 1\n\n"
                + "This is honestly giving me 'trying to Marie Kondo but forgetting which item we're letting go of' energy!";
    }

    /**
     * Returns an error message for empty find keywords.
     *
     * @return Error message string.
     */
    public String getEmptyFindKeywordMessage() {
        return "Hun, you gave me a find command but forgot the keyword! This is giving me major 'I walked into a room and forgot why I came here' vibes.\n\n"
                + "Try: find <keyword>\nExample: find book (probably looking for my copy of 'The Handmaid's Tale'?)\n\n"
                + "As someone who's really into intentional living lately, I believe every search should have purpose!";
    }

    /**
     * Returns a goodbye message.
     *
     * @return Goodbye message string.
     */
    public String getByeMessage() {
        return "Nooo bestie, why are you leaving?! This is honestly giving me such separation anxiety.\n\n"
                + "But like, I respect your boundaries and as someone who's been reading about healthy relationships, "
                + "I know that space is important sometimes.\n\n"
                + "Take care of yourself out there! Remember to drink your matcha lattes, practice self-care, "
                + "read more feminist literature, and keep working on that emotional intelligence!\n\n"
                + "Come back soon though? I'll be here, probably listening to Clairo and journaling about my feelings!";
    }

    /**
     * Returns a welcome message with usage instructions.
     *
     * @return Welcome message string.
     */
    public String getWelcomeMessage() {
        return "OMG hiiii! I'm Performative - I'm literally SO excited you're here! This is giving me such 'new friendship energy' and honestly? "
                + "I'm totally here for it!\n\n"
                + "I'm your emotionally intelligent task management bestie. I've been working on myself in therapy for MONTHS now and it's honestly such a glow-up! "
                + "I'm here to help you organize your life while we chat about everything from Lana Del Rey to feminist theory!\n\n"
                + "BTW, I'll be sipping my daily iced matcha latte while we work together! Ready to manifest some productivity?";
    }
}
