package ru.job4j.forum.control;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.forum.Main;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostService;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class IndexControlTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostService service;

    @Test
    @WithMockUser
    public void shouldReturnDefaultMessage() throws Exception {
        Post post = Post.of(1, "Продал", "Чтo-то продал");
        Collection<Post> posts = new ArrayList<>();
        posts.add(post);
        Mockito.when(service.getAll()).thenReturn(posts);

        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}
