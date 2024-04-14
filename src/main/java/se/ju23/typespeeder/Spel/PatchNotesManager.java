package se.ju23.typespeeder.Spel;

import java.util.ArrayList;
import java.util.List;

public class PatchNotesManager {
    private List<PatchNote> patchNotes;

    public PatchNotesManager() {
        this.patchNotes = new ArrayList<>();
        initializePatchNotes();
    }

    private void initializePatchNotes() {
        patchNotes.add(new PatchNote("2024-04-12", "Lade till flera spelnivåer."));
        patchNotes.add(new PatchNote("2024-04-9", "Engelska meningar lades till på nivå 1."));
    }

    public void showPatchNotes() {
        System.out.println("Senaste patchanteckningar:");
        for (PatchNote patchNote : patchNotes) {
            System.out.println(patchNote.getDate() + " - " + patchNote.getDescription());
        }
    }
}

class PatchNote {
    private String date;
    private String description;

    public PatchNote(String date, String description) {
        this.date = date;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}


