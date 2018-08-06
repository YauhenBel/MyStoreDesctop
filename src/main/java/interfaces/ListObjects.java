package interfaces;

import objects.Product;

public interface ListObjects {
    Product add(Product product);

    void update(Product product);

    void delete(Product product);

    void fillData();
}
