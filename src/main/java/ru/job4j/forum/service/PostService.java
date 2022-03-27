package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.repository.PostMem;
import ru.job4j.forum.repository.PostRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class PostService {
    private PostRepository posts;

    public PostService(PostRepository posts) {
        this.posts = posts;
    }

    public Post add(Post post) {
        post.setCreated(new GregorianCalendar());
        posts.save(post);
        return post;
    }

    public Post findById(int id) {
        return posts.findById(id).get();
    }

    public Collection<Post> getAll() {
        List<Post> rsl = new ArrayList<>();
        posts.findAll().forEach(rsl::add);
        return rsl;
    }
}
