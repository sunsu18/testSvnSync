package com.sfr.engage.core;

import java.util.Comparator;

/**
 * This is a comparator class to sort the Cards by Blocked status i.e. Active(Block Action = 0), Temporary Blocked(Block Action = 1) and then Blocked cards(Block Action = 2).
 *
 */
public class CardStatusComparator implements Comparator<CardInfo> {
    public CardStatusComparator() {
        super();
    }

    public int compare(CardInfo o1, CardInfo o2) {
        int blockActionCard1 = Integer.parseInt(o1.getBlockAction());
        int blockActionCard2 = Integer.parseInt(o2.getBlockAction());

        if (blockActionCard1 > blockActionCard2) {
            return 1;
        } else if (blockActionCard2 == blockActionCard1) {
            // If block action is same, then sort by card number in sequence.
            return o1.getExternalCardID().compareTo(o2.getExternalCardID());
        } else {
            return -1;
        }
    }
}
