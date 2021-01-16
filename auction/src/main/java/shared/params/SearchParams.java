package shared.params;

import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class SearchParams {

    @DefaultValue("")
    @QueryParam("n")
    private String name;

    @QueryParam("c")
    private List<String> categories;

    public SearchParams(String name, List<String> categories) {
        this.name = name;
        this.categories = categories;
    }

    public SearchParams() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

}
