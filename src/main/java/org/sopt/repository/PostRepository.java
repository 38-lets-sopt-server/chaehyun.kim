package org.sopt.repository;
import java.util.ArrayList;
import java.util.List;

import org.sopt.domain.Post;

public class PostRepository {
	private final List<Post> postList = new ArrayList<>();
	private Long nextId = 1L;

	public Post save(Post post) {
		postList.add(post);
		return post;
	}

	public Long generateId() {
		return nextId++;
	}
}
