package com.ajith.batch.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.*;
import java.util.List;
@XmlRootElement(name = "RECORDING")
@XmlType(propOrder = {"recordingLocalId", "title", "mainArtists"})
public class Recording {

    @JacksonXmlProperty(localName = "RECORDING-LOCAL-ID-COLLECTING-SOCIETY")
    private String recordingLocalId;

    @JacksonXmlProperty(localName = "RECORDING-TITLE-COLLECTING-SOCIETY")
    private String title;
    private List<MainArtist> mainArtists;

    @XmlElement(name = "RECORDING-LOCAL-ID-COLLECTING-SOCIETY")
    public String getRecordingLocalId() {
        return recordingLocalId;
    }

    public void setRecordingLocalId(String recordingLocalId) {
        this.recordingLocalId = recordingLocalId;
    }

    @XmlElement(name = "RECORDING-TITLE-COLLECTING-SOCIETY")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElementWrapper(name = "MAIN-ARTISTS")
    @XmlElement(name = "MAIN-ARTIST")
    public List<MainArtist> getMainArtists() {
        return mainArtists;
    }

    public void setMainArtists(List<MainArtist> mainArtists) {
        this.mainArtists = mainArtists;
    }

}
