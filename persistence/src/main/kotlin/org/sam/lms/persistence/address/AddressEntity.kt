package org.sam.lms.persistence.address

import jakarta.persistence.*
import org.sam.lms.domain.address.domain.Address
import org.sam.lms.persistence.AuditEntity

@Table(name = "address")
@Entity
class AddressEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "zipcode", nullable = false, columnDefinition = "varchar")
    val zipcode: String,

    @Column(name = "si", nullable = false, columnDefinition = "varchar")
    val si: String,

    @Column(name = "gugun", nullable = false, columnDefinition = "varchar")
    val gugun: String,

    @Column(name = "doro", nullable = false, columnDefinition = "varchar")
    val doro: String,

    @Column(name = "detail", nullable = false, columnDefinition = "varchar")
    val detail: String,

    ) : AuditEntity() {
    fun toDomain(): Address {
        return Address(id = this.id, zipcode = zipcode, si = si, gugun = gugun, doro = doro, detail = detail)
    }

    companion object {
        fun from(address: Address): AddressEntity {
            return AddressEntity(
                id = address.id,
                zipcode = address.zipcode,
                si = address.si,
                gugun = address.gugun,
                doro = address.doro,
                detail = address.detail
            )
        }
    }
}