package org.sam.lms.persistence

import org.sam.lms.domain.account.domain.RoleName
import org.sam.lms.persistence.account.entity.RoleEntity
import org.sam.lms.persistence.account.repository.RoleJpaRepository
import org.sam.lms.persistence.course.entity.CategoryEntity
import org.sam.lms.persistence.course.repository.CategoryJpaRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataRunner(
    private val roleRepository: RoleJpaRepository,
    private val categoryJpaRepository: CategoryJpaRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val numberOfRoleEntities = roleRepository.count()
        if (numberOfRoleEntities == 0L) {
            roleRepository.saveAll(
                listOf(
                    RoleEntity(name = RoleName.ADMIN.toEntityName()),
                    RoleEntity(name = RoleName.TEACHER.toEntityName()),
                    RoleEntity(name = RoleName.STUDENT.toEntityName())
                )
            )
        }

        val numberOfCategoryEntity = categoryJpaRepository.count()
        if (numberOfCategoryEntity == 0L) {
            categoryJpaRepository.saveAll(
                listOf(
                    CategoryEntity(name = "백엔드"),
                    CategoryEntity(name = "프론트엔드"),
                    CategoryEntity(name = "네트워크"),
                    CategoryEntity(name = "운영체제")
                )
            )
        }
    }
}