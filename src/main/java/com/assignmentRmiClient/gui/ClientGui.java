package com.assignmentRmiClient.gui;

import com.assignmentRmiClient.enums.Commands;
import com.assignmentRmiServer.RemoteInterface;
import com.assignmentRmiServer.enums.Role;
import com.sun.org.apache.bcel.internal.generic.ALOAD;
import domain.Album;
import domain.Artist;
import domain.Song;
import domain.User;

import javax.jws.soap.SOAPBinding;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * shows client interface in command windows
 */
public class ClientGui {
    private boolean isLoggedIn = false;
    private User currentlyLoggedUser;
    Map<User, String> notifications = new HashMap<>();

    public ClientGui() {
    }

    public void startClient(RemoteInterface remoteInterface) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type '/help' for help anytime");
        while (scanner.hasNext()) {
            String input = scanner.next();
            if (input.equalsIgnoreCase(Commands.EXIT)) {
                break;
            }
            if (input.equals(Commands.HELP)) {
                System.out.println("available commands:");
                System.out.println("register, exit, login");
            }

            if (!isLoggedIn) {
                scanner.nextLine();
                switch (input) {
                    case Commands.REGISTER:
                        register(remoteInterface, scanner);
                        break;
                    case Commands.LOGIN:
                        login(remoteInterface, scanner);
                        break;
                }
            }
            //zalogowany
            else {
                scanner.nextLine();
                System.out.println(notifications.get(currentlyLoggedUser));
                //user
                if (currentlyLoggedUser.getType().equalsIgnoreCase(String.valueOf(Role.USER))) {
                    userInterface(remoteInterface, scanner, input);
                }
                //admin
                else {
                    adminInterface(remoteInterface, scanner, input);
                }
            }
        }
    }

    private void adminInterface(RemoteInterface remoteInterface, Scanner scanner, String input) throws Exception {
        if (input.equals(Commands.HELP)) {
            System.out.println(Commands.getAllCommands());
        }
        switch (input) {
            case Commands.LOGOUT:
                logout(remoteInterface);
                break;
            case Commands.BROWSE_ALL_SONGS:
                remoteInterface.getAllSongs().forEach(System.out::println);
                break;
            case Commands.BROWSE_ALL_ALBUMS:
                remoteInterface.getAllAlbums().forEach(System.out::println);
                break;
            case Commands.BROWSE_ALL_ARTISTS:
                remoteInterface.getAllArtists().forEach(System.out::println);
                break;
            case Commands.BROWSE_ONE_ALBUMS:
                browseOneAlbum(remoteInterface, scanner);
                break;
            case Commands.BROWSE_ONE_ARTIST:
                browseOneArtist(remoteInterface, scanner);

                break;
            case Commands.BROWSE_ONE_SONG:
                browseOneSong(remoteInterface, scanner);
                break;
            case Commands.INSERT_SONGS:
                insertSong(remoteInterface, scanner);
                break;
            case Commands.CHANGE_SONG:
                changeSong(remoteInterface, scanner);
                break;
            case Commands.CHANGE_ALBUM:
                changeAlbum(remoteInterface, scanner);
                break;
            case Commands.CHANGE_ARTIST:
                changeArtist(remoteInterface, scanner);
                break;
            case Commands.DELETE_SONG:
                deleteSong(remoteInterface, scanner);
                break;
            case Commands.DELETE_ALBUM:
                deleteAlbum(remoteInterface, scanner);
                break;
            case Commands.DELETE_ARTIST:
                deleteArtist(remoteInterface, scanner);
                break;
            case Commands.SEARCH_SONGS_BY_GENRE:
                searchSongByGenre(remoteInterface, scanner);
                break;
            case Commands.SEARCH_SONGS_BY_ARTIST:
                searchSongByArtist(remoteInterface, scanner);
                break;
            case Commands.SEARCH_SONGS_BY_ALBUM:
                searchSongByAlbum(remoteInterface, scanner);
                break;
            case Commands.WRITE_DESC:
                writeDescription(remoteInterface, scanner);
                break;
            case Commands.GIVE_PRIVILAGE:
                promoteUser(remoteInterface, scanner);
                break;
            case Commands.CHECK_NOTIFICATIONS:
                System.out.println(notifications.get(currentlyLoggedUser));
                break;
            case Commands.INSERT_ARTIST:
                insertArtist(remoteInterface, scanner);
                break;
            case Commands.INSERT_ALBUM:
                insertAlbum(remoteInterface, scanner);
            case Commands.GET_FAVOURITE_SONGS:
                favouriteSongs(scanner, remoteInterface);
                break;
            case Commands.GET_SHARED_SONGS:
                sharedSongs(scanner, remoteInterface);
                break;
            case Commands.SHARE_SONG:
                shareSong(scanner, remoteInterface);
                break;
            case Commands.INSERT_FAVOURITE_SONG:
                insertFavouriteSong(scanner, remoteInterface);
                break;
            case Commands.CHANGE_ARTIST_BIO:
                updateArtistBio(scanner, remoteInterface);
                break;
            case Commands.DELETE_USER:
                deleteUser(scanner, remoteInterface);
                break;
            default:
                System.err.println("Wrong command!");
                break;

        }
    }

    private void deleteArtist(RemoteInterface remoteInterface, Scanner scanner) throws RemoteException {
        System.out.println("insert name of artist you would like to delete:");
        String deleteArtistName = scanner.nextLine();
        remoteInterface.deleteArtist(deleteArtistName);
        System.out.println("Song succesfully deleted");
    }

    private void insertAlbum(RemoteInterface remoteInterface, Scanner scanner) throws RemoteException {
        System.out.println("name:");
        String al = scanner.nextLine();
        System.out.println("genre:");
        String gen = scanner.nextLine();
        System.out.println("description:");
        String des = scanner.nextLine();
        System.out.println("artist id:");
        String arId = scanner.nextLine();
        System.out.println(remoteInterface.insertAlbum(al, gen, des, getArtistById(Integer.parseInt(arId), remoteInterface).getName()));
    }

    private void insertArtist(RemoteInterface remoteInterface, Scanner scanner) throws RemoteException {
        System.out.println("name:");
        String art = scanner.nextLine();
        System.out.println("bio:");
        String bio = scanner.nextLine();
        System.out.println(remoteInterface.insertArtist(art, bio));
    }

    private void searchSongByAlbum(RemoteInterface remoteInterface, Scanner scanner) throws RemoteException {
        System.out.println("type name of album");
        String nameOfAlbumSearch = scanner.nextLine();
        System.out.println(remoteInterface.searchSongsByAlbum(nameOfAlbumSearch));
    }

    private void searchSongByGenre(RemoteInterface remoteInterface, Scanner scanner) throws RemoteException {
        System.out.println("type genre: ");
        String songGenre = scanner.nextLine();
        remoteInterface.searchSongsByGenre(songGenre).forEach(System.out::println);
    }

    private void deleteAlbum(RemoteInterface remoteInterface, Scanner scanner) throws RemoteException {
        System.out.println("insert name of album you would like to delete:");
        String deleteAlbumName = scanner.nextLine();
        remoteInterface.deleteAlbum(deleteAlbumName);
        System.out.println("Album succesfully deleted");
    }

    private void deleteSong(RemoteInterface remoteInterface, Scanner scanner) throws RemoteException {
        System.out.println("insert name of song you would like to delete:");
        String deleteSongName = scanner.nextLine();
        remoteInterface.deleteSong(deleteSongName);
        System.out.println("Song succesfully deleted");
    }

    private void changeArtist(RemoteInterface remoteInterface, Scanner scanner) throws RemoteException {
        System.out.println("insert name of artist you would like to change:");
        String changeArtistsName = scanner.nextLine();
        System.out.println("new name:");
        String artistNewName = scanner.nextLine();
        System.out.println(remoteInterface.updateArtist(artistNewName, changeArtistsName));
        System.out.println("Succesfully upadted!");
    }

    private void changeAlbum(RemoteInterface remoteInterface, Scanner scanner) throws RemoteException {
        System.out.println("insert name of album you would like to change:");
        String changeAlbumName = scanner.nextLine();
        System.out.println("new name:");
        String albumNewName = scanner.nextLine();
        System.out.println(remoteInterface.updateAlbum(albumNewName, changeAlbumName));
    }

    private void changeSong(RemoteInterface remoteInterface, Scanner scanner) throws RemoteException {
        System.out.println("insert name of song you would like to change:");
        String changeSongName = scanner.nextLine();
        System.out.println("new name:");
        String songNewName = scanner.nextLine();
        System.out.println(remoteInterface.updateSong(songNewName, changeSongName));
    }

    private void insertSong(RemoteInterface remoteInterface, Scanner scanner) throws RemoteException {
        System.out.println("name:");
        String title = scanner.nextLine();
        System.out.println("name of album:");
        String nameOfAlbum = scanner.nextLine();
        System.out.println(remoteInterface.insertSong(title, nameOfAlbum));
    }

    private void browseOneArtist(RemoteInterface remoteInterface, Scanner scanner) {
        System.out.println("type name of artist:");
        String artistName = scanner.nextLine();
        if (getArtist(artistName, remoteInterface) == null) {
            System.out.println("there is no such artist");
        } else {
            System.out.println(getArtist(artistName, remoteInterface));
        }
    }

    private void logout(RemoteInterface remoteInterface) throws RemoteException {
        isLoggedIn = false;
        remoteInterface.logout();
        currentlyLoggedUser = null;
        System.out.println("logged out.");
    }

    private Artist getArtistById(int arId, RemoteInterface remoteInterface) {
        Artist artist = null;
        try {
            for (Artist ar : remoteInterface.getAllArtists()) {
                if (ar.getId() == arId) {
                    artist = ar;
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return artist;
    }

    private Artist getArtist(String artistName, RemoteInterface remoteInterface) {
        Artist artist = null;
        try {
            for (Artist artist1 : remoteInterface.getAllArtists()) {
                if (artist1.getName().equals(artistName)) {
                    artist = artist1;
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return artist;
    }

    private void writeDescription(RemoteInterface remoteInterface, Scanner scanner) throws RemoteException {
        System.out.println("type name of album");
        String descAlbumName = scanner.nextLine();
        List<Album> albums = remoteInterface.getAllAlbums();
        for (Album album : albums) {
            if (album.getName().equals(descAlbumName)) {
                System.out.println("type description:");
                String descr = scanner.nextLine();
                remoteInterface.getAllUsers().forEach(user -> {
                    if (notifications.isEmpty() || notifications.get(user) == null || notifications.get(user).equals("")) {
                        notifications.put(user, "notifications: description of album: " + descAlbumName + " was changed to " + descr);
                    } else {
                        notifications.put(user, notifications.get(user) + " | description of album " + descAlbumName + " was changed to " + descr);
                    }
                });
                System.out.println(remoteInterface.writeDescriptonToAlbum(descAlbumName, descr));
                break;
            }
        }

    }

    private Album getAlbum(String albumName, RemoteInterface remoteInterface) {
        Album searchedAlbum = null;
        try {
            for (Album album : remoteInterface.getAllAlbums()) {
                if (album.getName().equals(albumName)) {
                    searchedAlbum = album;
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return searchedAlbum;
    }

    private void promoteUser(RemoteInterface remoteInterface, Scanner scanner) throws Exception {
        System.out.println("type username you would like to promote:");
        String promotedUser = scanner.nextLine();
        System.out.println("type admin or user");
        String newRole = scanner.nextLine();
        System.out.println(remoteInterface.changeRoleOfAUser(promotedUser, newRole));
        User user = getOneUser(promotedUser, remoteInterface);
        if (notifications.isEmpty() || notifications.get(user).equals("")) {
            notifications.put(user, "notifications: you have been promoted to " + newRole);
        } else {
            notifications.put(user, notifications.get(user) + " | you have been promoted to " + newRole);
        }
        //System.out.println("**************");
        //System.out.println(notifications.get(user));
        System.out.println(remoteInterface.promoteUser(user.getUsername(), newRole));
    }

    private void userInterface(RemoteInterface remoteInterface, Scanner scanner, String input) throws RemoteException {
        if (input.equals(Commands.HELP)) {
            System.out.println(Commands.getUserCommands());
        }
        //user
        userCases(remoteInterface, scanner, input);
    }

    private void userCases(RemoteInterface remoteInterface, Scanner scanner, String input) throws RemoteException {
        switch (input) {
            case Commands.LOGOUT:
                logout(remoteInterface);
                break;
            case Commands.BROWSE_ALL_SONGS:
                remoteInterface.getAllSongs().forEach(System.out::println);
                break;
            case Commands.BROWSE_ALL_ALBUMS:
                remoteInterface.getAllAlbums().forEach(System.out::println);
                break;
            case Commands.BROWSE_ALL_ARTISTS:
                remoteInterface.getAllArtists().forEach(System.out::println);
                break;
            case Commands.BROWSE_ONE_ALBUMS:
                browseOneAlbum(remoteInterface, scanner);
                break;
            case Commands.BROWSE_ONE_ARTIST:
                browseOneArtist(remoteInterface, scanner);
                break;
            case Commands.BROWSE_ONE_SONG:
                browseOneSong(remoteInterface, scanner);
                break;
            case Commands.SEARCH_SONGS_BY_GENRE:
                searchSongByGenre(remoteInterface, scanner);
                break;
            case Commands.SEARCH_SONGS_BY_ARTIST:
                searchSongByArtist(remoteInterface, scanner);
                break;
            case Commands.SEARCH_SONGS_BY_ALBUM:
                System.out.println("type name of album");
                String nameOfAlbum = scanner.nextLine();
                remoteInterface.searchSongsByAlbum(nameOfAlbum).forEach(System.out::println);
                break;
            case Commands.WRITE_DESC:
                writeDescription(remoteInterface, scanner);
                break;
            case Commands.CHECK_NOTIFICATIONS:
                System.out.println(notifications.get(currentlyLoggedUser));
                break;
            case Commands.GET_FAVOURITE_SONGS:
                favouriteSongs(scanner, remoteInterface);
                break;
            case Commands.GET_SHARED_SONGS:
                sharedSongs(scanner, remoteInterface);
                break;
            case Commands.SHARE_SONG:
                shareSong(scanner, remoteInterface);
                break;
            case Commands.INSERT_FAVOURITE_SONG:
                insertFavouriteSong(scanner, remoteInterface);
                break;
            default:
                System.err.println("Wrong command!");
                break;
        }
    }

    private void searchSongByArtist(RemoteInterface remoteInterface, Scanner scanner) throws RemoteException {
        System.out.println("type name of artist");
        String nameOfArtist = scanner.nextLine();
        remoteInterface.searchSongsByArtist(nameOfArtist).forEach(System.out::println);
    }

    private void browseOneSong(RemoteInterface remoteInterface, Scanner scanner) throws RemoteException {
        System.out.println("type name of song:");
        String songName = scanner.nextLine();
        System.out.println(remoteInterface.searchSongByName(songName));
    }

    private void browseOneAlbum(RemoteInterface remoteInterface, Scanner scanner) {
        System.out.println("type name of album:");
        String albumName = scanner.nextLine();
        if (getAlbum(albumName, remoteInterface) == null) {
            System.out.println("there is no such album");
        } else {
            System.out.println(getAlbum(albumName, remoteInterface));
        }
    }

    private void login(RemoteInterface remoteInterface, Scanner scanner) throws Exception {
        System.out.println("username: ");
        String username = scanner.nextLine();
        System.out.println("password: ");
        String password = scanner.nextLine();
        System.out.print("logging");
        Thread.sleep(400);
        System.out.print(".");
        Thread.sleep(400);
        System.out.print(".");
        Thread.sleep(400);
        System.out.println(".");
        if (remoteInterface.login(username, password).equals("Authentication successful")) {
            System.out.println(remoteInterface.login(username, password));
            currentlyLoggedUser = getOneUser(username, remoteInterface);
            isLoggedIn = true;
            /*System.out.println("notifications: ");
            System.out.println(notifications.get(remoteInterface.findByUsername(username)));*/
        } else {
            System.out.println(remoteInterface.login(username, password));
        }

    }

    private boolean isLoginOk(List<User> users, String username, String pass) {
        AtomicBoolean result = new AtomicBoolean(false);
        users.forEach(user -> {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(pass)) {
                    result.set(true);
                }
            }
        });

        return result.get();
    }

    private void register(RemoteInterface remoteInterface, Scanner scanner) throws RemoteException, InterruptedException {
        System.out.println("username: ");
        String username = scanner.nextLine();
        System.out.println("password: ");
        String password = scanner.nextLine();
        System.out.println("email");
        String email = scanner.nextLine();
        System.out.println("select role - write admin or user: ");
        String role = scanner.nextLine();
        //remoteInterface.register(username, password, email, Role.valueOf(role.toUpperCase()));
        System.out.println("registering...");
        Thread.sleep(500);
        System.out.println(remoteInterface.register(username, password, role.toUpperCase()));
    }

    private User getOneUser(String username, RemoteInterface remoteInterface) throws Exception {
        if (remoteInterface.findByUsername(username).size() == 1) {
            return remoteInterface.findByUsername(username).get(0);
        } else if (remoteInterface.findByUsername(username).size() > 1) {
            throw new Exception("There is more than one user with this username");
        } else throw new Exception("there is no user with this username");
    }

    private void favouriteSongs(Scanner scanner, RemoteInterface remoteInterface) {
        try {
            System.out.println("insert your username: ");
            String username = scanner.nextLine();
            System.out.println(remoteInterface.getFavouriteSongs(username));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void sharedSongs(Scanner scanner, RemoteInterface remoteInterface) {
        try {
            System.out.println("insert your username: ");
            String username = scanner.nextLine();
            System.out.println(remoteInterface.getSharedSongs(username));
            notifications.put(currentlyLoggedUser, "a song have been shared");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void shareSong(Scanner scanner, RemoteInterface remoteInterface) {
        System.out.println("which user you would like to share a song with?");
        String username = scanner.nextLine();
        System.out.println("insert name of file with extension:");
        String filename = scanner.nextLine();
        try {
            if (remoteInterface.checkIfUserCanDownload(username, filename).equals("ok")) {
                if (remoteInterface.checkIfFileCorresponds(filename).equals("ok")) {
                    System.out.println(remoteInterface.shareSong(username, filename));
                } else {
                    System.out.println("file corresponds with other one!");
                }
            } else {
                System.out.println("this username cannot download this song!");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void insertFavouriteSong(Scanner scanner, RemoteInterface remoteInterface){
        try {
            System.out.println("insert your username: ");
            String username = scanner.nextLine();
            System.out.println("insert name of favourite song with extension:");
            String song = scanner.nextLine();
            System.out.println(remoteInterface.insertFavoriteSong(username, song));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void updateArtistBio(Scanner scanner, RemoteInterface remoteInterface){
        try {
            System.out.println("insert artist name:");
            String artist = scanner.nextLine();
            System.out.println("insert new bio: ");
            String bio = scanner.nextLine();
            System.out.println(remoteInterface.updateArtistBio(artist, bio));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void deleteUser(Scanner scanner, RemoteInterface remoteInterface){
        try {
            System.out.println("which user you would like to delete?");
            String username = scanner.nextLine();
            System.out.println(remoteInterface.deleteUser(String.valueOf(currentlyLoggedUser.getId())));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}