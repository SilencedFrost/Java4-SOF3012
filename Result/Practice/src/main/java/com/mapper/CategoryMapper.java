package com.mapper;

import com.dto.CategoryDTO;
import com.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryDTO(
                category.getCategoryId(),
                category.getCategoryName()
        );
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }

        return new Category(
                categoryDTO.getCategoryId(),
                categoryDTO.getCategoryName()
        );
    }

    public static List<CategoryDTO> toDTOList(List<Category> entityList) {
        List<CategoryDTO> dtoList = new ArrayList<>();
        for (Category category : entityList) {
            dtoList.add(CategoryMapper.toDTO(category));
        }
        return dtoList;
    }
}
