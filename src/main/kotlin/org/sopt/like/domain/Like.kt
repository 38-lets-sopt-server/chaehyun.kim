package org.sopt.like.domain

import jakarta.persistence.*
import org.sopt.common.domain.BaseTimeEntity
import org.sopt.post.domain.Post
import org.sopt.user.domain.User

@Entity
@Table(name = "likes")
class Like : BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private var user: User? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private var post: Post? = null

    protected constructor()

    constructor(user: User, post: Post) {
        this.user = user
        this.post = post
    }
}
