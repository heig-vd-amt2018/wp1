package ch.heigvd.amt.wp1.services;

import ch.heigvd.amt.wp1.model.Beer;
import java.util.List;
import javax.ejb.Local;

/**
 * @see BeersManager
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 */
@Local
public interface BeersManagerLocal {
  
  /**
   *
   * @return
   */
  List<Beer> getAllBeers();
  
}
