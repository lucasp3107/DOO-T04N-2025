package com.seriestracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CatalogService {
    private static final List<Show> seriesCatalog = new ArrayList<>();

    
    static {
    
        seriesCatalog.add(new Show(-1, "Breaking Bad", "English", List.of("Crime", "Drama", "Thriller"), 9.5, "Ended", LocalDate.parse("2008-01-20"), LocalDate.parse("2013-09-29"), "AMC"));
        seriesCatalog.add(new Show(-2, "Game of Thrones", "English", List.of("Action", "Adventure", "Drama"), 9.2, "Ended", LocalDate.parse("2011-04-17"), LocalDate.parse("2019-05-19"), "HBO"));
        seriesCatalog.add(new Show(-3, "Stranger Things", "English", List.of("Drama", "Fantasy", "Horror"), 8.7, "Running", LocalDate.parse("2016-07-15"), null, "Netflix"));
        seriesCatalog.add(new Show(-4, "The Office", "English", List.of("Comedy"), 8.9, "Ended", LocalDate.parse("2005-03-24"), LocalDate.parse("2013-05-16"), "NBC"));
        seriesCatalog.add(new Show(-5, "Sherlock", "English", List.of("Crime", "Drama", "Mystery"), 9.1, "Ended", LocalDate.parse("2010-07-25"), LocalDate.parse("2017-01-15"), "BBC One"));
        seriesCatalog.add(new Show(-6, "The Mandalorian", "English", List.of("Action", "Adventure", "Fantasy"), 8.7, "Running", LocalDate.parse("2019-11-12"), null, "Disney+"));
        seriesCatalog.add(new Show(-7, "Friends", "English", List.of("Comedy", "Romance"), 8.9, "Ended", LocalDate.parse("1994-09-22"), LocalDate.parse("2004-05-06"), "NBC"));
        seriesCatalog.add(new Show(-8, "The Crown", "English", List.of("Biography", "Drama", "History"), 8.6, "Ended", LocalDate.parse("2016-11-04"), LocalDate.parse("2023-12-14"), "Netflix"));
        seriesCatalog.add(new Show(-9, "Black Mirror", "English", List.of("Drama", "Sci-Fi", "Thriller"), 8.7, "Running", LocalDate.parse("2011-12-04"), null, "Channel 4"));
        seriesCatalog.add(new Show(-10, "Fleabag", "English", List.of("Comedy", "Drama"), 8.7, "Ended", LocalDate.parse("2016-07-21"), LocalDate.parse("2019-04-08"), "BBC Three"));
        seriesCatalog.add(new Show(-11, "Peaky Blinders", "English", List.of("Crime", "Drama"), 8.8, "Ended", LocalDate.parse("2013-09-12"), LocalDate.parse("2022-04-03"), "BBC Two"));
        seriesCatalog.add(new Show(-12, "The Witcher", "English", List.of("Action", "Adventure", "Drama"), 8.1, "Running", LocalDate.parse("2019-12-20"), null, "Netflix"));
        seriesCatalog.add(new Show(-13, "Better Call Saul", "English", List.of("Crime", "Drama"), 8.9, "Ended", LocalDate.parse("2015-02-08"), LocalDate.parse("2022-08-15"), "AMC"));
        seriesCatalog.add(new Show(-14, "Ted Lasso", "English", List.of("Comedy", "Drama", "Sport"), 8.8, "Ended", LocalDate.parse("2020-08-14"), LocalDate.parse("2023-05-31"), "Apple TV+"));
        seriesCatalog.add(new Show(-15, "Arcane", "English", List.of("Animation", "Action", "Adventure"), 9.0, "Running", LocalDate.parse("2021-11-06"), null, "Netflix"));
        seriesCatalog.add(new Show(-16, "Severance", "English", List.of("Drama", "Mystery", "Sci-Fi"), 8.7, "Running", LocalDate.parse("2022-02-18"), null, "Apple TV+"));
        seriesCatalog.add(new Show(-17, "Dark", "German", List.of("Drama", "Mystery", "Sci-Fi"), 8.7, "Ended", LocalDate.parse("2017-12-01"), LocalDate.parse("2020-06-27"), "Netflix"));
        seriesCatalog.add(new Show(-18, "La Casa de Papel", "Spanish", List.of("Action", "Crime", "Drama"), 8.2, "Ended", LocalDate.parse("2017-05-02"), LocalDate.parse("2021-12-03"), "Antena 3"));
        seriesCatalog.add(new Show(-19, "Attack on Titan", "Japanese", List.of("Animation", "Action", "Adventure"), 9.1, "Ended", LocalDate.parse("2013-04-07"), LocalDate.parse("2023-11-05"), "MBS"));
        seriesCatalog.add(new Show(-20, "Succession", "English", List.of("Drama"), 8.9, "Ended", LocalDate.parse("2018-06-03"), LocalDate.parse("2023-05-28"), "HBO"));
    }

    /**
     * Retorna 
     * @return 
     */
    public static List<Show> getLocalCatalog() {
        return new ArrayList<>(seriesCatalog);
    }
}
