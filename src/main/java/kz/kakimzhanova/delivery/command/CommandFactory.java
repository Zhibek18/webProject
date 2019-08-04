package kz.kakimzhanova.delivery.command;

import kz.kakimzhanova.delivery.command.impl.EmptyCommand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * CommandFactory class
 */
public class CommandFactory {
    private static Logger logger = LogManager.getLogger();

    /**
     * private constructor to make sure that object will not be created
     */
    private CommandFactory(){}

    /**
     * @param action name of the command from jsp page
     * defineCommand method finds given action command in CommandType enum
     * @return Command object that can be used in Controller
     * @see CommandType
     */
    public static Command defineCommand(String action){
        Command current;
        if ((action == null)||(action.isEmpty())){
            return new EmptyCommand();
        }
        try{
            String command = action.toUpperCase();
            CommandType currentType = CommandType.valueOf(command);
            current = currentType.getCommand();
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, "Command not found: " + e);
            current = new EmptyCommand();
        }
        return current;
    }
}
