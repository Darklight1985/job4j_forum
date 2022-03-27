package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.Post;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostMem {
    private HashMap<Integer, Post> posts = new HashMap<>();
    private AtomicInteger number = new AtomicInteger();

    public Post add(Post post) {
        if (post.getId() == 0) {
            post.setId(number.incrementAndGet());
        }
        post.setCreated(new GregorianCalendar());
        return posts.put(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}
