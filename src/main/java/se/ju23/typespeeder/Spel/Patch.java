package se.ju23.typespeeder.Spel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Patch {
    public String patchVersion;
    public LocalDateTime releaseDateTime;
    private List<PatchNotes> patches;

    public Patch() {
        this.patchVersion = "1.0";
        this.releaseDateTime = LocalDateTime.now();
        this.patches = new ArrayList<>();
        initializePatchNotes();
    }

    private void initializePatchNotes() {
        patches.add(new PatchNotes("2024-05-21", "Fixade buggar och förbättrade prestanda."));
        patches.add(new PatchNotes("2024-04-12", "Lade till flera spelnivåer."));
        patches.add(new PatchNotes("2024-04-9", "Engelska meningar lades till på nivå 1."));
    }

    public String getPatchVersion() {
        return patchVersion;
    }

    public void setPatchVersion(String patchVersion) {
        this.patchVersion = patchVersion;
    }

    public LocalDateTime getReleaseDateTime() {
        return releaseDateTime;
    }

    public void setReleaseDateTime(LocalDateTime releaseDateTime) {
        this.releaseDateTime = releaseDateTime;
    }

    public void addPatchNotes(String date, String description) {
        patches.add(new PatchNotes(date, description));
    }

    public void showPatchNotes() {
        System.out.println("Patch Version: " + patchVersion);
        System.out.println("Release Date: " + releaseDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("Senaste patchanteckningar:");
        for (PatchNotes patch : patches) {
            System.out.println(patch.getDate() + " - " + patch.getDescription());
        }
    }

    private class PatchNotes {
        private String date;
        private String description;

        public PatchNotes(String date, String description) {
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
}
