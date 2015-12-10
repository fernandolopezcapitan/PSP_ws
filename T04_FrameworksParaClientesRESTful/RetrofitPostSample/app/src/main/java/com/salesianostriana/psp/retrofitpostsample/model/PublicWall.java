package com.salesianostriana.psp.retrofitpostsample.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luismi on 09/12/2015.
 */
public class PublicWall {

    @SerializedName("results")
    @Expose
    private List<Note> notes = new ArrayList<Note>();

    /**
     *
     * @return
     * The notes
     */
    public List<Note> getNotes() {
        return notes;
    }

    /**
     *
     * @param notes
     * The notes
     */
    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }


}
