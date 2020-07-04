package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.Category;
import com.cskaoyan.mall.bean.CategoryExample;
import com.cskaoyan.mall.bean.vo.CategoryOneVO;
import com.cskaoyan.mall.config.MyException;
import com.cskaoyan.mall.constants.MallConstant;
import com.cskaoyan.mall.mapper.CategoryMapper;
import com.cskaoyan.mall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryOneVO> queryLevelOne() {
        List<CategoryOneVO> categoryOneVOList = new ArrayList<>();
        CategoryExample levelOneCategoryExample = new CategoryExample();
        levelOneCategoryExample.createCriteria().andDeletedEqualTo(false).andLevelEqualTo("L1");
        List<Category> levelOnes = categoryMapper.selectByExample(levelOneCategoryExample);
        for (int i = 0; i < levelOnes.size(); i++) {
            Category category = levelOnes.get(i);
            CategoryOneVO categoryOneVO = new CategoryOneVO();
            categoryOneVO.setValue(category.getId());
            categoryOneVO.setLabel(category.getName());
            categoryOneVOList.add(categoryOneVO);
        }
        return categoryOneVOList;
    }

    @Override
    public void addCategory(Category category) {
        String level = category.getLevel();
        if (Objects.equals(level, "L1")) {
            List<String> names = categoryMapper.selectLevelName();
            if (names.contains(category.getName())) {
                throw new MyException(605, MallConstant.CATEGORY_NAME_ERROR);
            }
        }
        if (Objects.equals(level, "L2")) {
            List<String> names = categoryMapper.selectLevelTwoName(category.getPid());
            if (names.contains(category.getName())) {
                throw new MyException(605, MallConstant.CATEGORY_NAME_ERROR);
            }
            if (category.getPid()==0){
                throw new MyException(605, MallConstant.CATEGORY_LEVEL_ERROR);
            }
        }
        category.setAddTime(new Date());
        category.setUpdateTime(new Date());
        category.setDeleted(false);
        try {
            categoryMapper.insert(category);
        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                throw new MyException(605, MallConstant.BRAND_ADD_OTHER_ERROR);
            }
            throw new MyException(605, MallConstant.UNKNOWN_ERROR);
        }
    }

    @Override
    public List<Category> list() {
        CategoryExample levelOneCategoryExample = new CategoryExample();
        levelOneCategoryExample.createCriteria().andDeletedEqualTo(false).andLevelEqualTo("L1");
        List<Category> levelOnes = categoryMapper.selectByExample(levelOneCategoryExample);

        CategoryExample levelTwoCategoryExample = new CategoryExample();
        levelTwoCategoryExample.createCriteria().andDeletedEqualTo(false).andLevelEqualTo("L2");
        List<Category> levelTwos = categoryMapper.selectByExample(levelTwoCategoryExample);

        for (int i = 0; i < levelOnes.size(); i++) {
            for (int j = 0; j < levelTwos.size(); j++) {
                Category categoryOne = levelOnes.get(i);
                Category categoryTwo = levelTwos.get(j);
                if (categoryTwo.getPid().equals(categoryOne.getId())) {
                    categoryOne.getChildren().add(categoryTwo);
                }
            }
        }
        return levelOnes;
    }

    @Override
    public void deleteCategory(Category category) {
        if (!Objects.isNull(category.getChildren())&&category.getChildren().size()!=0){
            for (int i=0;i<category.getChildren().size();i++){
                Category categoryChild=category.getChildren().get(i);
                categoryChild.setUpdateTime(new Date());
                categoryChild.setDeleted(true);
                categoryMapper.updateByPrimaryKeySelective(categoryChild);
            }
        }
        category.setUpdateTime(new Date());
        category.setDeleted(true);
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public void updateCategory(Category category) {
        Category originalCategory = categoryMapper.selectByPrimaryKey(category.getId());
        if (Objects.equals(originalCategory.getLevel(),"L1")&&Objects.equals(category.getLevel(),"L2")){
            for (int i=0;i<category.getChildren().size();i++){
                Category categoryChild=category.getChildren().get(i);
                categoryChild.setUpdateTime(new Date());
                categoryChild.setPid(category.getPid());
                categoryMapper.updateByPrimaryKeySelective(categoryChild);
            }
        }
        categoryMapper.updateByPrimaryKeySelective(category);
    }
}
