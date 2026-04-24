package org.sopt.repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	public Optional<Post> findById(Long id) {
		return postList.stream()
			.filter(post -> post.getId().equals(id))
			.findFirst();
	}

	public List<Post> findAll() {
		return List.copyOf(postList);
	}


	public void delete(Post post) {
		postList.remove(post);
	}
}
