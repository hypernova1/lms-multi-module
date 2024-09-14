package org.sam.lms

import org.sam.lms.account.domain.RoleName
import org.sam.lms.account.domain.RoleRepository
import org.sam.lms.account.infrastructure.persistence.entity.RoleEntity
import org.sam.lms.account.infrastructure.persistence.repository.RoleJpaRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {
    @Bean
    fun commandLineRunner(roleRepository: RoleJpaRepository): CommandLineRunner {
        return CommandLineRunner {
            roleRepository.saveAll(
                listOf(
                    RoleEntity(name = RoleName.ADMIN.toEntity()),
                    RoleEntity(name = RoleName.TEACHER.toEntity()),
                    RoleEntity(name = RoleName.STUDENT.toEntity())
                )
            )
        }
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
