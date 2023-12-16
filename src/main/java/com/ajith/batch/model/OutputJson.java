package com.ajith.batch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class OutputJson {

    @JsonProperty("Recordings")
    private List<RecordingOutput> recordings;

    public OutputJson() {
        this.recordings = new ArrayList<>();
    }

    public OutputJson(List<RecordingOutput> singletonList) {
    }

    public List<RecordingOutput> getRecordings() {
        return recordings;
    }

    public void setRecordings(List<RecordingOutput> recordings) {
        this.recordings = recordings;
    }

    public static class RecordingOutput {
        @JsonProperty("Title")
        private String title;

        @JsonProperty("MainArtists")
        private List<MainArtistOutput> mainArtists = new ArrayList<>();

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<MainArtistOutput> getMainArtists() {
            return mainArtists;
        }

        public void setMainArtists(List<MainArtistOutput> mainArtists) {
            this.mainArtists = mainArtists;
        }
    }

    public static class MainArtistOutput {
        @JsonProperty("Artist")
        private String artist;

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }
    }
}