package cart.entity;

import cart.vo.Name;
import cart.vo.Price;
import cart.vo.Url;

public class Product {

    private final Integer id;
    private final Name name;
    private final Price price;
    private final Url imageUrl;

    public Product(int id, Name name, Price price, Url imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public Integer getPrice() {
        return price.getValue();
    }

    public String getImageUrl() {
        return imageUrl.getValue();
    }

    public static class Builder {

        private int id;
        private Name name;
        private Price price;
        private Url imageUrl;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder name(Name name) {
            this.name = name;
            return this;
        }

        public Builder price(Price price) {
            this.price = price;
            return this;
        }

        public Builder imageUrl(Url imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Product build() {
            return new Product(id, name, price, imageUrl);
        }

    }

}
