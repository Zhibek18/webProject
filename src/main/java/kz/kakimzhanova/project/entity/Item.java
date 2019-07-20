package kz.kakimzhanova.project.entity;

public class Item {
    private int price;

    public Item(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
