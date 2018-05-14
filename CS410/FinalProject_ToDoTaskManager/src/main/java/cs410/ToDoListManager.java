package cs410;

import java.sql.*;
import java.io.IOException;
import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.budhash.cliche.*;

import static java.util.TimeZone.*;

/**
 * CS410 - Databases
 *
 * Final Project: To-Do List Task Manager
 *
 * @author marshalleddy
 */
public class ToDoListManager {

    private final Connection dataBase; // The Database connection.
    private static Session session = null;

    private static Session sshTunnel(String sshUser, String sshPass, String sshHost, int sshPort, String remoteHost, int localPort, int remotePort) throws JSchException {
        final JSch jsch = new JSch();
        session = jsch.getSession(sshUser, sshHost, sshPort);
        session.setPassword(sshPass);

        final Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();
        session.setPortForwardingL(localPort, remoteHost, remotePort);
        return session;
    }

    /**
     * Main Method
     * @param args - The command line arguments.
     * @throws IOException - If there is an error with arguments
     * @throws SQLException - If there is an error when connecting or executing the SQL statements.
     */
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        if (args.length != 5) {
            System.err.println("Error: Invalid Arguments.");
            System.err.println("Usage: \n\t <BroncoUserid> <BroncoPassword> <sandboxUSerID> <sandbox password> <yourportnumber>");
            System.exit(0);
        } else {

            Session session = null;
            String sshBSUUser = args[0];
            String sshBSUPass = args[1];
            String sshHost = "onyx.boisestate.edu";
            int sshPort = 22;

            /* I'm not sure how to get the right localhost port number. Normally it would be 3306, but that was not working.
             *  To find this port (52435) I had connected to the sandbox mysql database through intellij and looked at the info
             *  which had contained the database url, which had localhost:524435. Due to this, whomever is testing this code might
             *  need to do the same thing... */

            String dbUser = args[2];
            String dbPass = args[3];
            String str_dbPort = args[4];
            int remotePort = Integer.parseInt(str_dbPort);
            int localPort = 51848;
//        int localPort = 1433;
//        int localPort = 5742;
//        String dbHost = "localhost:5742/TodoListTaskManager?autoReconnect=true&useSSL=false";
            String dbHost = "localhost:" + localPort + "/TodoListTaskManager?autoReconnect=true&useSSL=false";

            try {
                // This is so we can connect to the database without logging into onyx in the console.
                session = ToDoListManager.sshTunnel(sshBSUUser, sshBSUPass, sshHost, sshPort, dbHost, localPort, remotePort);
            } catch (Exception e) {
//            e.printStackTrace();
                System.out.println("Error trying to connect.\nAttempting to reconnect...");
            }
            String dbUrl = "mysql://" + dbHost;
            setDefault(getTimeZone("MST"));
            System.out.println("Attempting to Connect...");
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection("jdbc:" + dbUrl, dbUser, dbPass)) {
                System.out.println("Successfully Connected.");
                System.out.println("Database URL: " + dbUrl);
                ToDoListManager toDoListManager = new ToDoListManager(conn);
                System.out.println("\nType '?help' for help with commands.");
                ShellFactory.createConsoleShell("MySQL ", "ToDoListManager:", toDoListManager).commandLoop();
            }
        }
    }

    /**
     * Constructor for the List Manager
     * @param conn - The connection to the database
     */
    public ToDoListManager(Connection conn) {
        dataBase = conn;
    }

    /* Here are the SQL Commands */

    @Command (name = "active", description = "View currently-active tasks", abbrev = "act", header = "Tasks:\n")
    public void active() throws SQLException {
        String query = "SELECT task_id, task_label, task_create_date, task_due_date" +
                    "   FROM tasks WHERE task_status = 'active'";
        try (Statement statement = dataBase.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                if (resultSet.getString("task_due_date") != null) {
                    System.out.printf("%d: %s - %s, due: %s\n",
                                    resultSet.getInt("task_id"),
                                    resultSet.getString("task_label"),
                                    resultSet.getString("task_create_date"),
                                    resultSet.getString("task_due_date"));
                } else {
                    System.out.printf("%d: %s - %s\n",
                                    resultSet.getInt("task_id"),
                                    resultSet.getString("task_label"),
                                    resultSet.getString("task_create_date"));
                }
            }
        }
    }

    @Command (name = "add", description = "Add a new task with a specififed label", abbrev = "nam", header = "Attempting to add task...")
    public void add(@Param(name = "labels", description = "String for a tasks label")
                                String... labels) throws SQLException {
        String label = String.join(" ", labels);
        if (validTask(label)) {
            System.out.println(label + " already exists.\n");
            return;
        }
        String query = "INSERT INTO tasks (task_label, task_create_date, task_status)   VALUES (?, ?, ?)";
        dataBase.setAutoCommit(false);
        try {
            try (PreparedStatement preparedStatement = dataBase.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, label);
                preparedStatement.setDate(2, new java.sql.Date(System.currentTimeMillis()));
                preparedStatement.setString(3, "active");
                preparedStatement.executeUpdate();
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (!resultSet.next()) {
                        throw new RuntimeException("Error: There are no generated keys.");
                    }
                    System.out.printf("Task %d %s added.\n", resultSet.getInt(1), label);
                }
            }
            dataBase.commit();
        } catch (SQLException | RuntimeException e) {
            dataBase.rollback();
            throw e;
        } finally {
            dataBase.setAutoCommit(true);
        }
    }

    @Command (name = "due", description = "Associate due dates with tasks", abbrev = "du", header = "Attempting to update due date...")
    public void due(@Param(name = "task_id", description = "a task's ID") int task_id,
                    @Param(name = "due_date", description = "a task's due date") String due_date)
                    throws SQLException {
        String query = "UPDATE tasks SET task_due_date = ? WHERE task_id = ?";
        Date date = java.sql.Date.valueOf(due_date);
        try (PreparedStatement preparedStatement = dataBase.prepareStatement(query)) {
            preparedStatement.setDate(1, date);
            preparedStatement.setInt(2, task_id);
            System.out.printf("Updating task %d's due date to %s\n", task_id, date);
            int numRows = preparedStatement.executeUpdate();
            System.out.printf("Updated %d tasks\n", numRows);
        }
    }

    @Command (name = "tag", description = "Associate tags with tasks", abbrev = "ta", header = "Attempting to update task...")
    public void tag(@Param(name = "task_id", description = "the task's ID") int task_id,
                    @Param(name = "tags", description = "string representaion of a task's tag or tags")String... tags)
                    throws SQLException {
        for (String tag : tags) {
            if (validTag(tag)) {
                setTaggedTask(task_id, getTag_IdFromTag_Name(tag));
            } else {
                addTaggedTask(task_id, tag);
            }
        }
        String s = String.join(", ", tags);
        System.out.printf("Updated task: %d with tags: %s\n", task_id, s);
    }

    @Command (name = "finish", description = "Mark tasks as completed", abbrev = "fin", header = "Attempting to update status...")
    public void finish(@Param(name = "task_id", description = "the task's ID") int task_id) throws SQLException {
        updateStatus(task_id, "completed");
    }

    @Command (name = "cancel", description = "Mark tasks as canceled", abbrev = "can", header = "Attempting to update status...")
    public void cancel(@Param(name = "task_id", description = "the task's ID") int task_id) throws SQLException {
        updateStatus(task_id, "canceled");
    }

    @Command (name = "show", description = "Show details for a task", abbrev = "sh", header = "Trying to get task information...")
    public void show(@Param(name = "task_id", description = "the task's ID") int task_id) throws SQLException {
        /* intellij shows this query as an error. The statement works in SQL and works for the purposes of this assignment. */
        String query = "SELECT t.task_id, task_label, task_due_date, task_create_date, task_status," +
                "       group_concat(DISTINCT tag_text SEPARATOR ', ') AS tags" +
                "       FROM tasks t" +
                "       JOIN taggedTasks t1 ON t.task_id = t1.task_id" +
                "       JOIN tags t2 ON t2.tag_id = t1.tag_id" +
                "       WHERE t.task_id = ?";
        try (PreparedStatement preparedStatement = dataBase.prepareStatement(query)) {
            preparedStatement.setInt(1, task_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String task_label = resultSet.getString("task_label");
                    String status = resultSet.getString("task_status");
                    System.out.printf("Task_Id: %d\nLabel: %s\nTags: %s\nCreate Date: %s\nDue Date: %s\nStatus: %s\n",
                                    task_id, task_label, resultSet.getString("tags"), resultSet.getString("task_create_date"),
                                    resultSet.getString("task_due_date"), status);
                }
            }
        }
    }

    @Command (name = "active", description = "Show active tasks for a tag", abbrev = "act", header = "Looking for active tasks...")
    public void active(@Param(name = "tag", description = "describes a task") String tag) throws SQLException {
        getTasks(tag, "active");
    }

    @Command (name = "completed", description = "Show completed tasks for a tag", abbrev = "com", header = "Looking for completed tasks...")
    public void completed(@Param(name = "tag", description = "describes a task") String tag) throws SQLException {
        getTasks(tag, "completed");
    }

    @Command (name = "overdue", description = "Show tasks overdue", abbrev = "od", header = "Looking for overdue tasks...")
    public void overdue() throws SQLException {
        String query = "SELECT * FROM tasks WHERE task_due_date < CURDATE() AND task_status = 'active'";
        try (PreparedStatement preparedStatement = dataBase.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println( resultSet.getString(1) + " " +
                                        resultSet.getString(2) + " " +
                                        resultSet.getString(3) + ", Due: " +
                                        resultSet.getString(4) + "\n");
                }
            }
        }
    }

    @Command (name = "due", description = "Show tasks due today, or due in the next 3 days", abbrev = "du", header = "Searching for tasks that are due...")
    public void due(@Param(name = "timeFrame", description = "today or soon (next 3 days)") String timeFrame) throws SQLException {
        if (!timeFrame.toUpperCase().equals("TODAY") && !timeFrame.toUpperCase().equals("SOON")) {
            System.out.println("Please enter a valid Time Frame: 'today' OR 'soon' (for next 3 days)");
            return;
        }
        String time;
        if (timeFrame.toUpperCase().equals("TODAY")) {
            time = "SELECT * FROM tasks WHERE task_due_date = CURDATE()";
        } else {
            time = "SELECT * FROM tasks WHERE task_due_date = CURDATE()" +
                "   OR task_due_date = DATE_ADD(CURDATE(), INTERVAL 1 DAY)" +
                "   OR task_due_date = DATE_ADD(CURDATE(), INTERVAL 2 DAY)";
        }
        String query = time;
        try (PreparedStatement preparedStatement = dataBase.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery(query)) {
                System.out.printf("Tasks that are due %s:%n", timeFrame);
                while (resultSet.next()) {
                    System.out.printf("%d: %s\n", resultSet.getInt("task_id")
                                                , resultSet.getString("task_label"));
                }
            }
        }
    }

    @Command (name = "rename", description = "Change the label of a task", abbrev = "rn", header = "Attempting to rename task...")
    public void rename(@Param(name = "task_id", description = "the task's ID") int task_id,
                        @Param(name = "labels", description = "strings for the task's label") String... labels) throws SQLException {
        String label = String.join(" ", labels);
        String query = "UPDATE tasks SET task_label = ? WHERE task_id = ?";
        try (PreparedStatement preparedStatement = dataBase.prepareStatement(query)) {
            preparedStatement.setString(1, label);
            preparedStatement.setInt(2, task_id);
            System.out.printf("Updating label of task \n to %s\n", task_id, label);
            int numRows = preparedStatement.executeUpdate();
            System.out.printf("Updated %d tasks\n", numRows);
        }
    }

    @Command (name = "search", description = "Search for tasks by keyword", abbrev = "sr", header = "Searching for tasks...")
    public void search(@Param(name = "phrase", description = "a phrase to search for in tasks' labels") String phrase) throws SQLException {
        String search = "%" + phrase + "%";
        String query = "SELECT task_id, task_label FROM tasks" +
                "       WHERE task_label LIKE ? GROUP BY task_id";
        try (PreparedStatement preparedStatement = dataBase.prepareStatement(query)) {
            preparedStatement.setString(1, search);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("Tasks that contain '" + phrase + "':\n");
                while (resultSet.next()) {
                    int task_id = resultSet.getInt("task_id");
                    String task_label = resultSet.getString("task_label");
                    System.out.printf("%d: %s \n", task_id, task_label);
                }
            }
        }
    }

    @Command (name = "exit", description = "Exit the shell", abbrev = "ex", header = "Closing...")
    public void exit() throws SQLException {
        dataBase.setAutoCommit(true);
        dataBase.close();
        session.disconnect();
        System.exit(0);
    }

    /* End SQL Commands */

    /* Start Helper Functions */

    /**
     * Helper function to check if the given task is a valid task (it exists).
     * Returns true if the task is valid, and false otherwise.
     * @param taskLabel - The label of the task
     * @return boolean
     * @throws SQLException - If there is an error when connecting or executing the SQL statements.
     */
    private boolean validTask(String taskLabel) throws SQLException{
        String query = "SELECT * FROM tasks WHERE task_label = ?";
        boolean valid = true;
        try (PreparedStatement preparedStatement = dataBase.prepareStatement(query)) {
            preparedStatement.setString(1, taskLabel);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    valid = false;
                }
            }
        }
        return valid;
    }

    /**
     * Helper function to check if the given tag is a valid tag (it exists).
     * Returns true if the tag is valid, and false otherwise.
     * @param tagName - Name of the tag
     * @return boolean
     * @throws SQLException - If there is an error when connecting or executing the SQL statements.
     */
    private boolean validTag(String tagName) throws SQLException {
        String query = "SELECT * FROM tags WHERE tag_text = ?";
        boolean valid = true;
        try (PreparedStatement preparedStatement = dataBase.prepareStatement(query)) {
            preparedStatement.setString(1, tagName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    valid = false;
                }
            }
        }
        return valid;
    }

    /**
     * Add a specified tag to the appropriate column in the taggedTask table.
     * @param task_id - The task being added
     * @param tag_id - The tag for the task
     * @throws SQLException - If there is an error when connecting or executing the SQL statements.
     */
    private void setTaggedTask(int task_id, int tag_id) throws SQLException {
        String query = "INSERT INTO taggedTasks (task_id, tag_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = dataBase.prepareStatement(query)) {
            preparedStatement.setInt(1, task_id);
            preparedStatement.setInt(2, tag_id);
            preparedStatement.executeUpdate();
            System.out.printf("Set tag '%d' for task '%d'.\n", tag_id, task_id);
        }
    }

    /**
     * Add a specified task to the appropriate table.
     * @param task_id - The task with the tag
     * @param tag_name - The name of the tag
     * @throws SQLException - If there is an error when connecting or executing the SQL statements.
     */
    private void addTaggedTask(int task_id, String tag_name) throws SQLException {
        String query = "INSERT INTO tags (tag_text) VALUES (?)";
        int tag_id;
        dataBase.setAutoCommit(false);
        try {
            try (PreparedStatement preparedStatement = dataBase.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, tag_name);
                preparedStatement.executeUpdate();
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (!resultSet.next()) {
                        throw new RuntimeException("Error: There are no generated keys.");
                    }
                    tag_id = resultSet.getInt(1);
                    System.out.printf("New tag added: %s id: %d%n", tag_name, tag_id);
                }
            }
            setTaggedTask(task_id, tag_id);
            dataBase.commit();
        } catch (SQLException | RuntimeException e) {
            dataBase.rollback();
            throw e;
        } finally {
            dataBase.setAutoCommit(true);
        }
    }

    /**
     * Helper function to update the status of a given task
     * @param task_id - The task to be updated
     * @param status - The status the task is to be updated to
     * @throws SQLException - If there is an error when connecting or executing the SQL statements.
     */
    private void updateStatus(int task_id, String status) throws SQLException {
        String query = "UPDATE tasks SET task_status = ? WHERE task_id = ?";
        try (PreparedStatement preparedStatement = dataBase.prepareStatement(query)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, task_id);
            System.out.printf("Updating task %d status to %s%n", task_id, status);
            int numRows = preparedStatement.executeUpdate();
            System.out.printf("Updated %d tasks%n", numRows);
        }
    }

    /**
     * Helper method to get the tasks of a specified status, for each tag.
     * @param tag_text - The specified tag for the tasks
     * @param status - The status of the tasks we need to get
     * @throws SQLException - If there is an error when connecting or executing the SQL statements.
     */
    private void getTasks(String tag_text, String status) throws SQLException {
        String query = "SELECT tasks.task_id, tasks.task_label FROM tasks" +
                    "   JOIN taggedTasks tt on tasks.task_id = tt.task_id" +
                    "   JOIN tags on tags.tag_id = tt.tag_id" +
                    "   WHERE task_status = ? AND tags.tag_text = ?" +
                    "   GROUP BY tasks.task_id";
        try (PreparedStatement preparedStatement = dataBase.prepareStatement(query)) {
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, tag_text);
            System.out.printf("Status: %s Tag: %s%n", status, tag_text);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.format("%d: %s%n", resultSet.getInt("task_id")
                                                , resultSet.getString("task_label"));
                }
            }
        }
    }

    /**
     * Helper function to get the tag_id given a tag name.
     *
     * @param tagName - The name of the tag
     * @return tag_id | -1
     * @throws SQLException - If there is an error when connecting or executing the SQL statements.
     */
    private int getTag_IdFromTag_Name(String tagName) throws SQLException {
        String query = "SELECT tag_id FROM tags WHERE tag_text = ?";
        try (PreparedStatement preparedStatement = dataBase.prepareStatement(query)) {
            preparedStatement.setString(1, tagName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("tag_id");
                } else {
                    return -1;
                }
            }
        }
    }

}
