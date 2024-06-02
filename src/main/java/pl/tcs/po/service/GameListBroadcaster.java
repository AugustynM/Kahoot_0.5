package pl.tcs.po.service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.vaadin.flow.shared.Registration;

import pl.tcs.po.model.GameModel;

public class GameListBroadcaster {

    static List<Consumer<List<GameModel>>> listeners = new LinkedList<>();

    static Executor executor = Executors.newSingleThreadExecutor();

    public static synchronized Registration register(Consumer<List<GameModel>> listener) {
        listeners.add(listener);
        return () -> {
            synchronized (GameListBroadcaster.class) {
                listeners.remove(listener);
            }
        };
    }

    public static synchronized void broadcast(List<GameModel> games) {
        for (Consumer<List<GameModel>> listener : listeners) {
            executor.execute(() -> listener.accept(games));
        }
    }

}
