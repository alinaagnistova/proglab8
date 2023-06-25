package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.collection.CollectionManager;
import org.example.command.*;
import org.example.managers.CommandManager;
import org.example.utils.*;

import java.util.List;

public class MainServer extends Thread{
    public static final String HASHING_ALGORITHM = "SHA-1";
    public static final int CONNECTION_TIMEOUT = 60 * 1000;
    public static final String DATABASE_URL = "jdbc:postgresql://localhost:5433/studs";
    public static final String DATABASE_URL_HELIOS = "jdbc:postgresql://pg:5432/studs";
    public static final String DATABASE_CONFIG_PATH = "C:\\Users\\agnis\\IdeaProjects\\proglab7\\server\\dbconfig.cfg";

        public static int PORT;
        public static final Logger rootLogger = LogManager.getRootLogger();

        public static void main(String[] args) {
            rootLogger.info("--------------------------------------------------------------------");
            rootLogger.info("----------------------ЗАПУСК СЕРВЕРА--------------------------------");
            rootLogger.info("--------------------------------------------------------------------");
            if(args.length != 0){
                try{
                    PORT = Integer.parseInt(args[0]);
                } catch (NumberFormatException ignored) {}
            }
            CollectionManager collectionManager = new CollectionManager();


            CommandManager commandManager = new CommandManager(DatabaseHandler.getDatabaseManager());
            commandManager.addCommand(List.of(
                    new HelpCommand(commandManager),
                    new InfoCommand(collectionManager),
                    new ShowCommand(collectionManager),
                    new AddCommand(collectionManager),
                    new Ping(),
                    new UpdateIdCommand(collectionManager),
                    new RemoveByIdCommand(collectionManager),
                    new ClearCommand(collectionManager),
                    new ExecuteScriptCommand(),
                    new Register(DatabaseHandler.getDatabaseManager()),
                    new ExitCommand(),
                    new SortCommand(collectionManager),
                    new RemoveGreaterCommand(collectionManager),
                    new ShuffleCommand(collectionManager),
                    new FilterByWeaponCommand(collectionManager),
                    new PrintFieldDescendingWeapon(collectionManager),
                    new PrintUniqueMeleeWeaponCommand(collectionManager)
            ));
            Server server = new Server(commandManager, DatabaseHandler.getDatabaseManager());
            server.run();
        }
    }
