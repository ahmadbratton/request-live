package com.example.demo.model;

/**
 * Created by duhlig on 8/23/17.
 */
public class Booleans {
    private static Boolean renderPlaylistCreator = false;
    private static Boolean renderSongCreator = false;
    private static Boolean editLocation = false;
    private static Boolean editAddPlaylist = false;
    private static Boolean editPlaylist = false;
    private static Boolean editPlaylistName = false;

    public Booleans() {
    }

    public Booleans(Boolean renderPlaylistCreator, Boolean renderSongCreator, Boolean editLocation, Boolean editAddPlaylist, Boolean editPlaylist, Boolean editPlaylistName) {
        this.renderPlaylistCreator = renderPlaylistCreator;
        this.renderSongCreator = renderSongCreator;
        this.editLocation = editLocation;
        this.editAddPlaylist= editAddPlaylist;
        this.editPlaylist = editPlaylist;
        this.editPlaylistName = editPlaylistName;
    }

    public static Boolean getEditPlaylistName() {
        return editPlaylistName;
    }

    public static void setEditPlaylistName(Boolean editPlaylistName) {
        Booleans.editPlaylistName = editPlaylistName;
    }

    public static Boolean getEditPlaylist() {
        return editPlaylist;
    }

    public static void setEditPlaylist(Boolean editPlaylist) {
        Booleans.editPlaylist = editPlaylist;
    }

    public static Boolean getEditAddPlaylist() {
        return editAddPlaylist;
    }

    public static void setEditAddPlaylist(Boolean editAddPlaylist) {
        Booleans.editAddPlaylist = editAddPlaylist;
    }

    public static Boolean getEditLocation() {
        return editLocation;
    }

    public static void setEditLocation(Boolean editLocation) {
        Booleans.editLocation = editLocation;
    }

    public static Boolean getRenderPlaylistCreator() {
        return renderPlaylistCreator;
    }

    public static void setRenderPlaylistCreator(Boolean renderPlaylistCreator) {
        Booleans.renderPlaylistCreator = renderPlaylistCreator;
    }

    public static Boolean getRenderSongCreator() {
        return renderSongCreator;
    }

    public static void setRenderSongCreator(Boolean renderSongCreator) {
        Booleans.renderSongCreator = renderSongCreator;
    }

    @Override
    public String toString() {
        return "Booleans{" +
                "renderPlaylistCreator=" + renderPlaylistCreator +
                "renderSongCreator=" + renderSongCreator +
                "editLocation=" + editLocation +
                "editAddPlaylist=" + editAddPlaylist +
                "editPlaylist=" + editPlaylist +
                "editPlaylistName=" + editPlaylistName +
                '}';
    }
}
