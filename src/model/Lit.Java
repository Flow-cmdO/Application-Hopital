package model;
public class Lit {
    private String id;
    private boolean occupe;

    public Lit(String id) {
        this.id = id;
        this.occupe = false;
    }

    public String getId() {
        return id;
    }

    public boolean isOccupe() {
        return occupe;
    }

    public void setOccupe(boolean occupe) {
        this.occupe = occupe;
    }

    @Override
    public String toString() {
        return "Lit{" +
                "id='" + id + '\'' +
                ", occupe=" + occupe +
                '}';
    }
}