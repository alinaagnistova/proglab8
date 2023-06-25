package org.example.utils;

import org.example.console.ReaderWriter;
import org.example.gui.GuiManager;
import org.example.console.BlankConsole;
import org.example.console.Console;
import org.example.console.UserInput;
import org.example.data.SpaceMarine;
import org.example.dtp.Request;
import org.example.dtp.Response;
import org.example.dtp.ResponseStatus;
import org.example.dtp.User;
import org.example.error.ExitObligedException;
import org.example.error.FileModeException;
import org.example.error.LoginFail;
import org.example.forms.SpaceMarineForm;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Класс для хранения файл менеджера для команды execute
 */
public class ExecuteFileManager implements UserInput {
    private static final ArrayDeque<String> pathQueue = new ArrayDeque<>();
    private static final ArrayDeque<BufferedReader> fileReaders = new ArrayDeque<>();
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("GuiLabels", GuiManager.getLocale());
    private final ReaderWriter console;
    private final Client client;
    private User user;
    public ExecuteFileManager() {
        this.console = new BlankConsole();
        this.client = null;
        this.user = null;
    }
    public ExecuteFileManager(Console console, Client client, User user) {
        this.console = console;
        this.client = client;
        this.user = user;
    }
    public void fileExecution(String args) throws ExitObligedException, LoginFail {
        if (args == null || args.isEmpty()) {
            console.printError(resourceBundle.getString("ErrorFile"));
            return;
        }
        else console.write(ConsoleColors.toColor(resourceBundle.getString("FileGot"), ConsoleColors.PURPLE));
        args = args.trim();
        try {
            ExecuteFileManager.pushFile(args);
            for (String line = ExecuteFileManager.readLine(); line != null; line = ExecuteFileManager.readLine()) {
                String[] userCommand = (line + " ").split(" ", 2);
                if (userCommand[0].isBlank()) return;
                userCommand[0] = userCommand[0].trim();
                userCommand[1] = userCommand[1].trim();
                if (userCommand[0].equals("execute_script")){
                    if(ExecuteFileManager.fileRepeat(userCommand[1])){
                        console.printError(MessageFormat.format(resourceBundle.getString("FoundRecursion"), new File(userCommand[1]).getAbsolutePath()));
                        continue;
                    }
                }
                console.write(ConsoleColors.toColor(resourceBundle.getString("DoingCommand") + userCommand[0], ConsoleColors.YELLOW));
                Response response = client.sendAndAskResponse(new Request(userCommand[0], userCommand[1], user, GuiManager.getLocale()));
                this.printResponse(response);
                switch (response.getStatus()){
                    case ASK_OBJECT -> {
                        SpaceMarine spaceMarine;
                        try{
                            spaceMarine = new SpaceMarineForm(console).build();
                            if (!spaceMarine.validate()) throw new FileModeException();
                        } catch (FileModeException err){
                            console.printError(resourceBundle.getString("VariablesNotValid"));
                            continue;
                        }
                        Response newResponse = client.sendAndAskResponse(
                                new Request(
                                        userCommand[0].trim(),
                                        userCommand[1].trim(),
                                        user,
                                        spaceMarine,
                                        GuiManager.getLocale()));
                        if (newResponse.getStatus() != ResponseStatus.OK){
                            console.printError(newResponse.getResponse());
                        }
                        else {
                            this.printResponse(newResponse);
                        }
                    }
                    case EXIT -> throw new ExitObligedException();
                    case EXECUTE_SCRIPT -> {
                        this.fileExecution(response.getResponse());
                    }
                    case LOGIN_FAILED -> {
                        console.printError("Ошибка с вашим аккаунтом. Зайдите в него снова");
                        this.user = null;
                        throw new LoginFail();
                    }
                    default -> {}
                }
            }
            ExecuteFileManager.popFile();
        } catch (FileNotFoundException fileNotFoundException){
            console.printError(resourceBundle.getString("FileNotExists"));
        } catch (IOException e) {
            console.printError("Ошибка ввода вывода");
        }
    }

    private void printResponse(Response response){
        switch (response.getStatus()){
            case OK -> {
                if ((Objects.isNull(response.getCollection()))) {
                    console.write(response.getResponse());
                } else {
                    console.write(response.getResponse() + "\n" + response.getCollection().toString());
                }
            }
            case ERROR -> console.printError(response.getResponse());
            case WRONG_ARGUMENTS -> console.printError("Неверное использование команды!");
            default -> {}
        }
    }

    public static void pushFile(String path) throws FileNotFoundException {
        pathQueue.push(new File(path).getAbsolutePath());
        fileReaders.push(new BufferedReader(new InputStreamReader(new FileInputStream(path))));
    }

    public static File getFile() {
        return new File(pathQueue.getFirst());
    }

        public static String readLine() throws IOException {
            String line;
            while ((line = fileReaders.getFirst().readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    return line;
                }
            }
            return null;
        }

    public static void popFile() throws IOException {
        fileReaders.getFirst().close();
        fileReaders.pop();
        if(pathQueue.size() >= 1) {
            pathQueue.pop();
        }
    }

    public static void popRecursion(){
        if(pathQueue.size() >= 1) {
            pathQueue.pop();
        }
    }

    public static boolean fileRepeat(String path){
        return pathQueue.contains(new File(path).getAbsolutePath());
    }

    @Override
    public String nextLine() {
        try{
            return readLine();
        } catch (IOException e){
            return "";
        }
    }
}