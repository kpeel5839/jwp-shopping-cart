package cart.dao;

import cart.entity.Product;
import cart.vo.Name;
import cart.vo.Price;
import cart.vo.Url;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {

    private final RowMapper<Product> rowMapper = (resultSet, rowNum) -> new Product.Builder()
                    .id(resultSet.getLong("id"))
                    .price(Price.from(resultSet.getInt("price")))
                    .name(Name.from(resultSet.getString("name")))
                    .imageUrl(Url.from(resultSet.getString("image_url")))
                    .build();
    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> selectAll() {
        String sqlForSelectAll = "SELECT * FROM Product";
        return jdbcTemplate.query(
                sqlForSelectAll,
                rowMapper
        );
    }

    public void save(Product product) {
        String sqlForSave = "INSERT INTO Product (name, price, image_url) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlForSave, product.getName(), product.getPrice(), product.getImageUrl());
    }

    public int deleteById(Long id) {
        String sqlForDeleteById = "DELETE FROM Product WHERE id = ?";
        return jdbcTemplate.update(sqlForDeleteById, id);
    }

    public int updateById(Long id, Product product) {
        String sqlForUpdateById = "UPDATE Product SET name = ?, price = ?, image_url = ? WHERE id = ?";
        return jdbcTemplate.update(sqlForUpdateById, product.getName(), product.getPrice(), product.getImageUrl(), id);
    }

    public Product findById(Long id) {
        String sqlForFindById = "SELECT * FROM Product WHERE id = ?";
        return jdbcTemplate.queryForObject(sqlForFindById, rowMapper, id);
    }

}
