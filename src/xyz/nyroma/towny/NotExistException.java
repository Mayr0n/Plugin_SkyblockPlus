package xyz.nyroma.towny;

public class NotExistException extends Exception {
    public NotExistException(){
        super("Cette ville n'existe pas !");
    }
}
