package me.moonss.to_dolist.service.model;

public class Note {
    private String note;
    private boolean finished;
    public Note() {}

    public Note(String note) {
        this.note = note;
    }

    public Note(Boolean finished) {
        this.finished = finished;
    }

    public String getNote() {
        return note;
    }

    public boolean isFinished() {
        return finished;
    }
}
