package ch.heigvd.amt.wp1.services.business.errors;

/**
 * Abstract error message.
 */
public abstract class AbstractDismissableAlert {
    //! Message to display.
    private String message;

    //! CSS class to use for the message.
    private String cssClass;

    /**
     * Constructor.
     * @param message The message to display.
     * @param cssClass The CSS class to use for the message.
     */
    AbstractDismissableAlert(String message, String cssClass) {
        this.message = message;
        this.cssClass = cssClass;
    }

    public String getMessage() {
        return message;
    }

    public String getCssClass() {
        return cssClass;
    }
}
