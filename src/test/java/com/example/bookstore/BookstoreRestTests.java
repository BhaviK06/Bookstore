package com.example.bookstore;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class BookstoreRestTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void exposesBooksAndCategoriesButNotUserAccounts() throws Exception {
        mockMvc.perform(get("/api/books").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/vnd.hal+json"))
                .andExpect(jsonPath("$._embedded.books").isArray());

        mockMvc.perform(get("/api/categories").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.categories").isArray());

        mockMvc.perform(get("/api/appUsers").with(user("admin").roles("ADMIN")))
                .andExpect(status().isNotFound());
    }

    @Test
    void createsSearchesAndDeletesBooksThroughRestApi() throws Exception {
        String bookJson = """
                {
                  "title": "REST Integration Test Book",
                  "author": "Test Author",
                  "publicationYear": 2025,
                  "isbn": "isbn-rest-test",
                  "price": 19.95
                }
                """;

        MvcResult createResult = mockMvc.perform(post("/api/books")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("REST Integration Test Book"))
                .andReturn();

        URI bookUri = URI.create(createResult.getResponse().getHeader("Location"));
        String bookPath = bookUri.getPath();

        mockMvc.perform(get(bookPath).with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("isbn-rest-test"));

        mockMvc.perform(get("/api/books/search/findByTitleContainingIgnoreCase")
                        .with(user("admin").roles("ADMIN"))
                        .param("title", "integration test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.books[0].title").value("REST Integration Test Book"));

        mockMvc.perform(delete(bookPath)
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(bookPath).with(user("admin").roles("ADMIN")))
                .andExpect(status().isNotFound());
    }
}
