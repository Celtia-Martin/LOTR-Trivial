package com.example.lotr_trivial.Tools.Auxiliares.Ranking;

import java.util.Comparator;

public class RankingComparator implements Comparator<ItemRanking> {
    @Override
    public int compare(ItemRanking o1, ItemRanking o2) {
        return o2.getPuntuacion()-o1.getPuntuacion();
    }
}
