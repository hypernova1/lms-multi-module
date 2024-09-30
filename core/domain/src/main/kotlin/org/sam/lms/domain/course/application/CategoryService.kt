package org.sam.lms.domain.course.application

import org.sam.lms.domain.course.application.payload.CreateCategoryDto
import org.sam.lms.domain.course.domain.Category
import org.sam.lms.domain.course.domain.CategoryRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    /**
     * 카테고리가 존재하는지 확인한다.
     *
     * @param id 카테고리 아이디
     * @return 카테고리 존재 여부
     * */
    fun existsById(id: Long): Boolean {
        return this.categoryRepository.existsById(id)
    }

    /**
     * 카테고리 목록을 조회한다.
     *  - 자주 바뀌지 않는 데이터이기 때문에 Redis Caching 적용
     *
     *  @return 카테고리 목록
     * */
    @Cacheable(value = ["categoryCache"], key = "'categoryList'")
    fun findAll(): List<Category> {
        return this.categoryRepository.findAll()
    }

    /**
     * 카테고리를 등록한다.
     * - 카테고리 캐시를 비운다.
     * */
    @CacheEvict(value = ["categoryCache"], key = "'categoryList'")
    fun create(createCategoryDto: CreateCategoryDto) {
        val category = Category.from(name = createCategoryDto.name)
        this.categoryRepository.save(category)
    }
}