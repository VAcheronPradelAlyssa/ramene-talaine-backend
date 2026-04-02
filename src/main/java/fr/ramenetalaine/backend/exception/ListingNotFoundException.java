package fr.ramenetalaine.backend.exception;

public class ListingNotFoundException extends RuntimeException {
    public ListingNotFoundException(Long id) {
        super("Annonce introuvable: " + id);
    }
}
