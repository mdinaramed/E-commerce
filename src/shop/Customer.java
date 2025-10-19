package shop;

public class Customer {
    private final String email;
    private final String name;
    private int points;

    public Customer(String email, String name) {
        this.email = email;
        this.name = name;
        this.points = 0;
    }

    public Customer(String email, String name, int points) {
        this.email = email;
        this.name = name;
        this.points = points;
    }

    public String name() {
        return name;
    }
    public String email() {
        return email;
    }
    public int points() {
        return points;
    }
    public void addPoints(int points) {
        this.points += points;
    }
}
