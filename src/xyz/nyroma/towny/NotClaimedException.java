package xyz.nyroma.towny;

public class NotClaimedException extends Exception {
    public NotClaimedException(){
        super("Ce territoire n'est pas claim.");
    }
}
