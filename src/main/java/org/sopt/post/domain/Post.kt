package org.sopt.post.domain

import jakarta.persistence.*
import org.hibernate.annotations.Formula
import org.sopt.common.domain.BaseTimeEntity
import org.sopt.common.enums.BoardType
import org.sopt.user.domain.User

@Entity
class Post(

    @Enumerated(EnumType.STRING)
    var boardType: BoardType,

    var title: String,

    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    var isAnonymous: Boolean

): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Formula("(select count(1) from likes l where l.post_id = id)")
    val likeCount: Int = 0

    var commentCount: Int = 0
        private set

    fun update(title: String, content: String) {
        this.title = title
        this.content = content
    }

    // TODO: 댓글 로직 구현
    fun increaseCommentCount() {
        commentCount++
    }
}
