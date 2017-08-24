package com.example.demo.model;

/**
 * Created by duhlig on 8/23/17.
 */
public class Booleans {
    private static Boolean renderPlaylistCreator = false;
    private static Boolean renderSongCreator = false;

    public Booleans() {
    }

    public Booleans(Boolean renderPlaylistCreator, Boolean renderSongCreator) {
        this.renderPlaylistCreator = renderPlaylistCreator;
        this.renderSongCreator = renderSongCreator;
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
                '}';
    }
}
