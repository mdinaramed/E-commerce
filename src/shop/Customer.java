package shop;

public class Customer {
    private final String email;
    private int points;
    public Customer(String email) {
        this.email = email;
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
