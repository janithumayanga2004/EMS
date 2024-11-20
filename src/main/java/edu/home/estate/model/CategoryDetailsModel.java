package edu.home.estate.model;

import edu.home.estate.dto.CategoryDetailsDto;
import edu.home.estate.util.CrudUtil;

import java.util.ArrayList;

public class CategoryDetailsModel {
    private final CategoryModel categoryModel = new CategoryModel();

    public boolean saveCategoryDetailsList(ArrayList<CategoryDetailsDto> categoryDetailsDtos) throws Exception {

        for (CategoryDetailsDto categoryDetailsDto : categoryDetailsDtos) {

            boolean isCategoryDetailsSaved = saveCategoryDetail(categoryDetailsDto);
            if (!isCategoryDetailsSaved) {
                return false;
            }



        }


        return true;
    }

    private boolean saveCategoryDetail(CategoryDetailsDto categoryDetailsDto) throws Exception {
        return CrudUtil.execute(
                "insert into CategoryDetails (quantity,category_id,division_id) values (?,?,?)",
                categoryDetailsDto.getQuantity(),
                categoryDetailsDto.getCategory_id(),
                categoryDetailsDto.getDivision_id()

        );
    }

}
