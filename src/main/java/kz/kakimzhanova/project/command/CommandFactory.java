package kz.kakimzhanova.project.command;

import kz.kakimzhanova.project.command.impl.EmptyCommand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandFactory {
    private static Logger logger = LogManager.getLogger();
    public static Command defineCommand(String action){
        Command current = null;
        if ((action == null)||(action.isEmpty())){
            return new EmptyCommand();
        }
        try{
            CommandType currentType = CommandType.valueOf(action.toUpperCase());
            current = currentType.getCommand();
        }catch (IllegalArgumentException e){
            logger.log(Level.WARN, e);
        }
        return current;
    }
}
