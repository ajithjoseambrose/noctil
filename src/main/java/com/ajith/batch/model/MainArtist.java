package com.ajith.batch.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MAIN-ARTISTS")
public class MainArtist {

    @JacksonXmlProperty(localName = "MAIN-ARTIST-NAME-COLLECTING-SOCIETY")
    private String artist;
    public MainArtist(){

    }
    @XmlElement(name = "MAIN-ARTIST-NAME-COLLECTING-SOCIETY")
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
    @Override
    public String toString() {
        return "MainArtist{" +
                "artist='" + artist + '\'' +
                '}';
    }
}
