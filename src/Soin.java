package src;
public abstract class Soin {
    private String id;
    private String date;
    private String description;

    public Soin(String id, String date, String description) {
        this.id = id;
        this.date = date;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
    
    public abstract String decrire();

    @Override
    public String toString() {
        return "Soin{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    
}
