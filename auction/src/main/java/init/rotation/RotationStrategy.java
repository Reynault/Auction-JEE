package init.rotation;

import java.util.List;
import model.Promotion;

public interface RotationStrategy {

    List<Promotion> rotate(List<Promotion> promotions);

}
