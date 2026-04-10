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

	public Post findById(Long id) {
		for (Post post : postList) {
			if (post.getId().equals(id)) {
				return post;
			}
		}
		return null;
	}

	public List<Post> findAll() {
		return new ArrayList<>(postList);
	}


	public void delete(Post post) {
		postList.remove(post);
	}
}
