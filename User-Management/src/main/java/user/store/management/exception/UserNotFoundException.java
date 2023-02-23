package user.store.management.exception;

/**
 * Classe che lancia un'eccezzione se l'utente richiesto non è presente nel database
 **/
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("User with ID " + id + " not found.");
    }

    public UserNotFoundException(String id) {
        super("User with ID " + id + " not found.");
    }
}

