package aconcorrencia.obdsync;

/**
 * Created by Sillas on 21/09/2016.
 */
public class ListViewObjPids {
    private String title;
    private String description;
    private String available;

    public ListViewObjPids() {
    }

    @Override
    public String toString() {
        return "ListViewObjPids{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", available=" + available +
                '}';
    }

    public ListViewObjPids(String title, String description, String available) {
        this.title = title;
        this.description = description;
        this.available = available;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
