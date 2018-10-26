package ch.heigvd.amt.wp1.services.dao;

/**
 *
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 */
public class BusinessDomainEntityNotFoundException extends Exception {

    public BusinessDomainEntityNotFoundException() {
        // Nothing more
    }

    public BusinessDomainEntityNotFoundException(String msg) {
        super(msg);
    }
}
