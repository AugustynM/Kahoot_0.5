package pl.tcs.po.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.vaadin.flow.shared.Registration;

import pl.tcs.po.model.GameModel;

public class GameBroadcaster {

    static Map<GameModel, List<Consumer<GameModel>>> listeners = new HashMap<>();

    static Executor executor = Executors.newSingleThreadExecutor();

    public static synchronized Registration register(Consumer<GameModel> listener, GameModel game) {
        if (!listeners.containsKey(game)) {
            listeners.put(game, new LinkedList<>());
        }
        listeners.get(game).add(listener);
        return () -> {
            synchronized (GameListBroadcaster.class) {
                listeners.get(game).remove(listener);
            }
        };
    }

    public static synchronized void broadcast(GameModel game) {
        for (Consumer<GameModel> listener : listeners.get(game)) {
            executor.execute(() -> listener.accept(game));
        }
    }

}
