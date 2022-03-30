package ru.job4j.forum.control;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.forum.Main;
import ru.job4j.forum.model.Authority;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.AuthorityRepository;
import ru.job4j.forum.repository.UserRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import ru.job4j.forum.service.PostService;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class RegControlTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository users;
    @MockBean
    private AuthorityRepository author;

    @Test
    @WithMockUser
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("reg"));
    }

    @Test
    @WithMockUser
    public void whenPostNewUser() throws Exception {
        this.mockMvc.perform(post("/reg")
                .param("username", "root").param("password", "987654"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(users).save(argument.capture());
        assertThat(argument.getValue().getUsername(), is("root"));
    }

    @Test
    @WithMockUser
    public void whenPostSameUser() throws Exception {
        User user = new User();
        user.setUsername("Vasya");
        user.setPassword("987654");
        user.setEnabled(true);
        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        user.setAuthority(authority);
        Mockito.when(author.findByAuthority(Mockito.anyString())).thenReturn(authority);
        Mockito.when(users.findByUsername(Mockito.anyString())).thenReturn(user);
        this.mockMvc.perform(post("/reg")
                        .param("username", "Vasya").param("password", "987654"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("errorMessage", "A user with this name is already registered !!"))
                .andExpect(view().name("reg"));
    }
}