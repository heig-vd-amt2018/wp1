package ch.heigvd.amt.wp1.services.business.errors;

/**
 * Error message.
 */
public class WarningAlert extends AbstractDismissableAlert {

    /**
     * Constructor.
     * @param message The message to display.
     */
    public WarningAlert(String message) {
        super(message, "alert-warning");
    }
}
