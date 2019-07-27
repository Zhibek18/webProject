package kz.kakimzhanova.delivery.command;

import kz.kakimzhanova.delivery.command.impl.EmptyCommand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandFactory {
    private static Logger logger = LogManager.getLogger();
    private CommandFactory(){}
    public static Command defineCommand(String action){
        Command current = null;
        if ((action == null)||(action.isEmpty())){
            return new EmptyCommand();
        }
        try{
            String command = action.toUpperCase();
            CommandType currentType = CommandType.valueOf(command);
            current = currentType.getCommand();
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, "Command not found" + e);
        }
        return current;
    }
}
