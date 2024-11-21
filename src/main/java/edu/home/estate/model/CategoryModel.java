package edu.home.estate.model;

import edu.home.estate.dto.CategoryDetailsDto;
import edu.home.estate.dto.CategoryDto;
import edu.home.estate.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryModel {
    public ArrayList<CategoryDto> gettAllCategories() throws Exception {
        ResultSet rst = CrudUtil.execute("select * from Category");

        ArrayList<CategoryDto> categories = new ArrayList<>();

        while (rst.next()) {
            CategoryDto category = new CategoryDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
            categories.add(category);
        }

        return categories;

    }

    public boolean saveCategory(CategoryDto category) throws Exception {
        return CrudUtil.execute(
                "insert into Category values (?,?,?)",
                category.getId(),
                category.getName(),
                category.getDescription()

        );
    }

    public boolean updateCategory(CategoryDto category) throws Exception {
        return CrudUtil.execute(
                "update Category set name=?, description=? where id=?",
                category.getName(),
                category.getDescription(),
                category.getId()
        );
    }

    public boolean deleteCategory(String id) throws Exception {
        return CrudUtil.execute(
                "delete from Category where id=?",id
        );
    }

    public CategoryDto findById(String selectedCategoryId  ) throws Exception {
        ResultSet rst = CrudUtil.execute("select * from Category where id=?", selectedCategoryId);

        if (rst.next()) {
            return new CategoryDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
        }
        return null;
    }

//    public boolean reduceQty(CategoryDetailsDto categoryDetailsDto) throws Exception {
//        return CrudUtil.execute(
//                "update Category set quantity = quantity - ? where id = ?",
//                categoryDetailsDto.getQuantity(),
//                categoryDetailsDto.getId()
//        );
//    }

    public ArrayList<String> getAllCategoryIds() throws Exception {
        ResultSet rst = CrudUtil.execute("select id from Category");
        ArrayList<String> categoryIds = new ArrayList<>();

        while (rst.next()) {
            categoryIds.add(rst.getString(1));
        }
       return categoryIds;
    }

    public String getNextCategoryId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select id from Category order by id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("C%03d", newIdIndex);
        }
        return "C001";
    }

    public int getCategoryCount() throws SQLException {
        int count = 0;
        ResultSet rst = CrudUtil.execute("SELECT COUNT(id) AS count FROM Category");
        if (rst.next()) {
            count = rst.getInt("count"); // Get the count from the ResultSet
        }
        return count;
    }

}
