package shared.params;

import java.util.List;
import javax.ws.rs.QueryParam;

public class PromoParams {

    @QueryParam("o")
    private List<Long> promotions;

    public PromoParams(List<Long> promotions) {
        this.promotions = promotions;
    }

    public PromoParams() {
    }

    public List<Long> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Long> promotions) {
        this.promotions = promotions;
    }

}
