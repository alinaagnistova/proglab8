package org.example.managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.MainServer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Класс хранящий коллекцию fixedThreadPool для их проверки на готовность и выполнения
 */
public class FutureManager {
    private static final Collection<Future<ConnectionManagerPool>> forkJoinPoolFutures = new ArrayList<>();
    private static final Logger futureManagerLogger = LogManager.getLogger(FutureManager.class);

    public static void addNewForkJoinPoolFuture(Future<ConnectionManagerPool> future){
        forkJoinPoolFutures.add(future);
    }

    public static void checkAllFutures(){
        if(!forkJoinPoolFutures.isEmpty()) {
            MainServer.rootLogger.debug("------------------------СПИСОК ВСЕХ ПОТОКОВ---------------------------");
            forkJoinPoolFutures.forEach(s -> futureManagerLogger.debug(s.toString()));
            MainServer.rootLogger.debug("-------------------------------КОНЕЦ----------------------------------");
        }
        forkJoinPoolFutures.stream()
                .filter(Future::isDone)
                .forEach(f -> {
                    try {
                        ConnectionManager.submitNewResponse(f.get());

                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                });
        forkJoinPoolFutures.removeIf(Future::isDone);
    }
}
