package com.example.cinemaapp.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.cinemaapp.R;
import com.example.cinemaapp.model.Film;
import com.example.cinemaapp.model.Reservation;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Repository {
    static List<Film> filmList = new ArrayList<>();
    static List<Reservation> reservationList = new ArrayList<>();
    public static List<Film> favoriteList = new ArrayList<>();
    private static HashMap<String, HashMap<Time, List<Boolean>>> program;
    static List<Boolean> cinemaPlaces;

    public static List<Film> getHardcodedList() {
        List<Film> filmList = Arrays.asList(
                new Film("The Bad Guys", "Adventure", "This latest project from DreamWorks Animation is a heist comedy based on popular series of children’s books about a handful of reformed villains who decide to go straight and so some good in the world. In this case, the “bad guys” are Mr. Wolf, Mr. Piranha, Mr. Snake, Mr. Shark, and Ms. Tarantula. It was originally slated for release in 2021, but now remains undated in 2022.", R.drawable.badguys),
                new Film("Doctor Strange in the Multiverse of Madness", "Fantasy", "Anticipation will be high for this entry in the Marvel Cinematic Universe — the first of three in 2022 — that will directly tie in with the events in Marvel’s first Disney+ series, WandaVision. and possibly even the animated Disney+ series What If…? After some behind-the-scenes shake-ups, director Sam Raimi jumped on board, and judging from the first teaser, it’s going to be a wild ride.",  R.drawable.strange),
                new Film("Deep water", "Drama", "Ben Affleck and Ana de Armas star in director Adrian Lyne’s thriller based on the eponymous novel by Patricia Highsmith (Strangers on a Train, The Talented Mr. Ripley, Carol), about a married couple at odds with each other who begin to play dangerous mind games with each other, eventually leading to murder.", R.drawable.deepwater),
                new Film("Fantastic Beasts: The Secrets of Dumbledore", "Fantasy", "Eddie Redmayne returns as inquisitive wizard Newt Scamander in the third chapter of this magical spin-off/prequel series that takes place decades before Harry Potter enrolled at Hogwarts. The story picks up after the events of The Crimes of Grindelwald, as Newt and his compatriots continue to pursue the evil wizard (now played by Mads Mikkelsen), leading up to World War II.",  R.drawable.secrets),
                new Film("Capitan Marvel", "Fantasy", "After an encounter with warring alien races, Air Force pilot Carol Danvers gains super strength and becomes invulnerable. The heroine will have to cope with her new abilities in order to confront a powerful enemy.", R.drawable.capitan),
                new Film("The adam project", "Adventure", "Ryan Reynolds reunites with Free Guy director Shawn Levy for this Netflix sci-fi adventure about a man who travels back in time to team up with his younger self and confront his past in order to save the future. This is one of the higher-profile films for Netflix, which has once again committed to releasing at least one new film every week in 2022.", R.drawable.adam)
        );
        return filmList;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void addReservation(Reservation reservation) {

        if (!searchReservation(reservation)) {

            reservationList.add(reservation);
            markReservedPlaces(reservation.getPlaces());
        }
    }

    private static void markReservedPlaces(List<Integer> places) {
        for (int i : places) {
            cinemaPlaces.set(i, false);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static boolean searchReservation(Reservation reservation) {

        for (Reservation r : reservationList) {

            if (r.equals(reservation))
                return true;
        }
        return false;
    }

    public static void addToFavorites(Film film) {

        if (!searchInFavorites(film)) {

            favoriteList.add(film);
        }

    }

    public static boolean searchInFavorites(Film film) {

        for (Film f : favoriteList) {

            if (f.equals(film))
                return true;
        }
        return false;
    }

    public static void deleteFromFavorites(Film film) {

        for (Film f : favoriteList) {

            if (f.equals(film)) {
                favoriteList.remove(f);
                return;
            }
        }
    }

    public static HashMap<String, HashMap<Time, List<Boolean>>> getHardcodedProgram() {
        if (program == null) {
            program = new HashMap<>();
            List<Film> films = getHardcodedList();
            List<List<Time>> posibilities = Arrays.asList(
                    Arrays.asList(new Time(12, 0, 0), new Time(17, 0, 0), new Time(20, 30, 0)),
                    Arrays.asList(new Time(10, 0, 0), new Time(13, 30, 0), new Time(17, 0, 0)),
                    Arrays.asList(new Time(11, 0, 0), new Time(14, 30, 0), new Time(18, 0, 0)),
                    Arrays.asList(new Time(12, 30, 0), new Time(16, 0, 0), new Time(19, 30, 0)),
                    Arrays.asList(new Time(10, 0, 0), new Time(13, 30, 0), new Time(17, 0, 0))

            );
            int i = 0;
            for (Film f : films) {
                List<Time> thisFilmTimes = posibilities.get(i % 5);
                i++;
                HashMap<Time, List<Boolean>> thisFilmProgram = new HashMap<>();
                for (Time t : thisFilmTimes) {
                    thisFilmProgram.put(t, Arrays.asList(true, true, true, true, true, true, true, true, true));
                }
                program.put(f.getTitle(), thisFilmProgram);
            }
        }
        return program;
    }

    /**
     * Get cinemaPlaces for selected film at selected startTime
     * @param filmTitle
     * @param time
     * @return
     */
    public static List<Boolean> getCinemaPlaces(String filmTitle, String time) {
        //transform time from string to Time obj
        Time timeObj = null;
        DateFormat formatter = new SimpleDateFormat("kk:mm");
        try {
            timeObj = new Time(formatter.parse(time).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        cinemaPlaces = getHardcodedProgram().get(filmTitle).get(timeObj);
        return cinemaPlaces;
    }

    public static List<Reservation> getReservationList() {
        return reservationList;
    }
}

