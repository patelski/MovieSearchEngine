/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moviesearchengine.clustering;

import java.util.*;

/**
 *
 * @author flavius
 */
public class Movie {

    private String id;
    private String title;
    private String description;
    private ArrayList<String> genres;
    private ArrayList<String> directors;
    private ArrayList<String> actors;

    public Movie(String id) {
        this.id = id;
        this.title = "";
        this.description = "";
        this.genres = new ArrayList<String>();
        this.directors = new ArrayList<String>();
        this.actors = new ArrayList<String>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addGenre(String genre) {
        this.genres.add(genre);
    }

    public void addDirector(String director) {
        this.directors.add(director);
    }

    public void addActor(String actor) {
        this.actors.add(actor);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getGenres() {
        StringBuilder builder = new StringBuilder();
        for (String genre : genres) {
            builder.append(genre).append(" ");
        }
        return builder.toString();
    }

    public String getDirectors() {
        StringBuilder builder = new StringBuilder();
        for (String director : directors) {
            builder.append(director).append(" ");
        }
        return builder.toString();
    }

    public String getActors() {
        StringBuilder builder = new StringBuilder();
        for (String actor : actors) {
            builder.append(actor).append(" ");
        }
        return builder.toString();
    }

    public String getId() {
        return id;
    }
}
