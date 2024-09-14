package org.sam.lms

import org.sam.lms.account.domain.RoleName
import org.sam.lms.account.infrastructure.persistence.entity.RoleEntity
import org.sam.lms.account.infrastructure.persistence.repository.RoleJpaRepository
import org.sam.lms.course.infrastructure.persistence.entity.CategoryEntity
import org.sam.lms.course.infrastructure.persistence.repository.CategoryJpaRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {
    @Bean
    fun commandLineRunner(
        roleRepository: RoleJpaRepository,
        categoryJpaRepository: CategoryJpaRepository
    ): CommandLineRunner {
        return CommandLineRunner {
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
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
