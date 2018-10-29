package ch.heigvd.amt.wp1.services.business.errors;

/**
 * Error message.
 */
public class ErrorAlert extends AbstractDismissableAlert {

    /**
     * Constructor.
     * @param message The message to display.
     */
    public ErrorAlert(String message) {
        super(message, "alert-danger");
    }
}
