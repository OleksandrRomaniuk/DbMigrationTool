package com.dbbest.consolexmlmanager;

import com.dbbest.xmlmanager.exceptions.ContainerException;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * An abstract class of the command of search in the container.
 */
public class CommandSearch implements Command {

    protected static final Logger logger = Logger.getLogger("Command logger");
    private String textToSearch;
    private String attributeKey;
    private String attributeValue;
    private String searchType;
    private Context context = Context.getInstance();
    private final int priority;

    private Predicate<SearchCommands> predicate = new Predicate<SearchCommands>() {
        @Override
        public boolean test(SearchCommands element) {
            return  (element.getSearchCommand().equals(searchType));
        }
    };

    /**
     * @param searchType the approach of search (vertical, horizontal, in names or values etc.).
     * @param textToSearch the parameter to search.
     * @param priority priority of the command.
     */
    public CommandSearch(String searchType, String textToSearch, int priority) {
        this.textToSearch = textToSearch;
        this.searchType = searchType;
        this.priority = priority;
    }

    /**
     * @param searchType the approach of search (vertical, horizontal, in names or values etc.).
     * @param attributeKey the key of the attribute to search.
     * @param attributeValue the value of the attribute to search.
     * @param priority priority of the command.
     */
    public CommandSearch(String searchType, String attributeKey, String attributeValue, int priority) {
        this.attributeKey = attributeKey;
        this.attributeValue = attributeValue;
        this.searchType = searchType;
        this.priority = priority;
    }

    /**
     * @throws ContainerException the container exception which is thrown during execution of serach.
     */
    public void execute() throws ContainerException {

        if (textToSearch != null) {
            Arrays.stream(SearchCommands.values()).filter(predicate)
                .forEach(p -> p.executeCommand(textToSearch));
        } else if (attributeKey != null && attributeValue != null) {
            Arrays.stream(SearchCommands.values()).filter(predicate)
                .forEach(p -> p.executeCommand(attributeKey, attributeValue));
        }
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
