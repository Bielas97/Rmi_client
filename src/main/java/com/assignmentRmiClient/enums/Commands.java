package com.assignmentRmiClient.enums;

import java.util.Arrays;
import java.util.List;

/**
 * commands in command prompt
 */
public final class Commands {
    public static final String REGISTER = "register";
    public static final String LOGIN = "login";
    public static final String HELP = "/help";
    public static final String EXIT = "exit";
    public static final String LOGOUT = "logout";
    public static final String BROWSE_ALL_SONGS = "getAllSongs";
    public static final String BROWSE_ALL_ARTISTS = "getAllArtists";
    public static final String BROWSE_ALL_ALBUMS = "getAllAlbums";
    public static final String BROWSE_ONE_ALBUMS = "getOneAlbum";
    public static final String BROWSE_ONE_SONG = "getOneSong";
    public static final String BROWSE_ONE_ARTIST = "getOneArtist";
    public static final String INSERT_SONGS = "insertSong";
    public static final String INSERT_ARTIST = "insertArtist";
    public static final String INSERT_ALBUM = "insertAlbum";
    public static final String CHANGE_SONG = "updateSong";
    public static final String CHANGE_ARTIST = "updateArtist";
    public static final String CHANGE_ALBUM = "updateAlbum";
    public static final String DELETE_SONG = "deleteSong";
    public static final String DELETE_ALBUM = "deleteAlbum";
    public static final String DELETE_ARTIST = "deleteArtist";
    public static final String SEARCH_SONGS_BY_GENRE = "searchSongByGenre";
    public static final String SEARCH_SONGS_BY_ALBUM = "searchSongByAlbum";
    public static final String SEARCH_SONGS_BY_ARTIST = "searchSongByArtist";
    public static final String WRITE_DESC = "writeDescription";
    public static final String GIVE_PRIVILAGE = "promote";
    public static final String CHECK_NOTIFICATIONS = "checkNotifications";
    public static final String GET_FAVOURITE_SONGS = "getFavouriteSongs";
    public static final String GET_SHARED_SONGS = "getSharedSongs";
    public static final String SHARE_SONG = "shareSong";
    public static final String INSERT_FAVOURITE_SONG = "insertFavouriteSong";
    public static final String CHANGE_ARTIST_BIO = "changeArtistBio";
    public static final String DELETE_USER = "deleteUser";
    public static final String GET_ALL_USERS= "getAllUsers";
    public static final String GET_SONG = "getSong";


    public static List<String> getAllCommands() {
        return Arrays.asList(REGISTER, LOGIN, HELP, EXIT, LOGOUT, BROWSE_ALL_ALBUMS, BROWSE_ALL_ARTISTS, BROWSE_ALL_SONGS,
                BROWSE_ONE_ALBUMS, BROWSE_ONE_ARTIST, BROWSE_ONE_SONG, INSERT_SONGS, INSERT_ALBUM, INSERT_ARTIST,
                INSERT_FAVOURITE_SONG, CHANGE_ALBUM, CHANGE_ARTIST, CHANGE_SONG, CHANGE_ARTIST_BIO, DELETE_ALBUM,
                DELETE_ARTIST, DELETE_SONG, DELETE_USER, SEARCH_SONGS_BY_ALBUM, SEARCH_SONGS_BY_ARTIST,
                SEARCH_SONGS_BY_GENRE, WRITE_DESC, GIVE_PRIVILAGE, CHECK_NOTIFICATIONS, GET_FAVOURITE_SONGS,
                GET_SHARED_SONGS, SHARE_SONG, GET_ALL_USERS, GET_SONG);
    }

    public static List<String> getUserCommands(){
        return Arrays.asList(REGISTER, LOGIN, HELP, EXIT, LOGOUT, BROWSE_ALL_ALBUMS, BROWSE_ALL_ARTISTS, BROWSE_ALL_SONGS,
                BROWSE_ONE_ALBUMS, BROWSE_ONE_ARTIST, BROWSE_ONE_SONG,INSERT_FAVOURITE_SONG, SEARCH_SONGS_BY_ALBUM, SEARCH_SONGS_BY_ARTIST,
                SEARCH_SONGS_BY_GENRE, WRITE_DESC, CHECK_NOTIFICATIONS, GET_FAVOURITE_SONGS,GET_SHARED_SONGS, SHARE_SONG, GET_SONG);
    }
}
