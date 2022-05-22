package exceptions;


/**
 * O clasa de exceptie pentru exceptiile din repo ce extinde clasa Exception
 */
public class RepoException extends Exception {
    public RepoException(String errors) {
        super(errors);
    }
}
