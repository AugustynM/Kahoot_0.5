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

    static Map<Integer, List<Consumer<GameModel>>> listeners = new HashMap<>();

    static Executor executor = Executors.newSingleThreadExecutor();

    public static synchronized Registration register(Consumer<GameModel> listener, Integer gameId) {
        if (gameId == null) {
            return () -> {
            };
        }
        if (!listeners.containsKey(gameId)) {
            listeners.put(gameId, new LinkedList<>());
        }
        listeners.get(gameId).add(listener);
        return () -> {
            synchronized (GameListBroadcaster.class) {
                listeners.get(gameId).remove(listener);
            }
        };
    }

    public static synchronized void broadcast(GameModel game) {
        Integer gameId = game.getId();
        if (!listeners.containsKey(gameId)) {
            return;
        }
        for (Consumer<GameModel> listener : listeners.get(gameId)) {
            executor.execute(() -> listener.accept(game));
        }
    }

}
