package ch.heigvd.amt.wp1.services.business.errors;

/**
 * Error message.
 */
public class SuccessAlert extends AbstractDismissableAlert {

    /**
     * Constructor.
     * @param message The message to display.
     */
    public SuccessAlert(String message) {
        super(message, "alert-success");
    }
}
