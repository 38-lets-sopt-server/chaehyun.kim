package org.sopt.user.domain

import jakarta.persistence.*
import org.sopt.common.domain.BaseTimeEntity

@Entity
@Table(name = "users")
class User(

    @Column(nullable = false)
    var password: String,

    var name: String,

    @Column(nullable = false, unique = true)
    var email: String

)  : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    protected constructor(): this(
        password = "",
        name = "",
        email = "")
}
