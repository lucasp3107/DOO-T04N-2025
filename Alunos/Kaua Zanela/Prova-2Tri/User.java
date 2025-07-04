package com.seriestracker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class User {
    private String nickname;
    private List<Show> favorites;
    private List<Show> watched;
    private List<Show> toWatch;

    public User(String nickname) {
        this.nickname = nickname;
        this.favorites = new ArrayList<>();
        this.watched = new ArrayList<>();
        this.toWatch = new ArrayList<>();
    }

    public String getNickname() {
        return nickname;
    }

    public List<Show> getFavorites() {
        return favorites;
    }

    public List<Show> getWatched() {
        return watched;
    }

    public List<Show> getToWatch() {
        return toWatch;
    }

    public void addFavorite(Show show) {
        if (!favorites.contains(show)) favorites.add(show);
    }

    public void removeFavorite(Show show) {
        favorites.remove(show);
    }

    public void addWatched(Show show) {
        if (!watched.contains(show)) watched.add(show);
    }

    public void removeWatched(Show show) {
        watched.remove(show);
    }

    public void addToWatch(Show show) {
        if (!toWatch.contains(show)) toWatch.add(show);
    }

    public void removeToWatch(Show show) {
        toWatch.remove(show);
    }

    public void sortList(List<Show> list, int criterion) {
        Comparator<Show> comp;
        switch (criterion) {
            case 1: comp = Comparator.comparing(Show::getName); break;
            case 2: comp = Comparator.comparing(Show::getRating).reversed(); break;
            case 3: comp = Comparator.comparing(Show::getStatus); break;
            case 4: comp = Comparator.comparing(Show::getPremiered); break;
            default: return;
        }
        list.sort(comp);
    }
}
