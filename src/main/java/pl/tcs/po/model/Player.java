package pl.tcs.po.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Player {
    final int id;
    final String name;
    int score;
    boolean answered = false;
}
