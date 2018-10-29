package ch.heigvd.amt.wp1.services.business.errors;

/**
 * Error message.
 */
public class InfoAlert extends AbstractDismissableAlert {

    /**
     * Constructor.
     * @param message The message to display.
     */
    public InfoAlert(String message) {
        super(message, "alert-success");
    }
}
