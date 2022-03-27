package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.repository.PostMem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PostService {
    private PostMem posts;

    public PostService(PostMem posts) {
        this.posts = posts;
    }

    public Post add(Post post) {
        posts.add(post);
        return post;
    }

    public Post findById(int id) {
        return posts.findById(id);
    }

    public Collection<Post> getAll() {
        return posts.findAll();
    }
}
