package com.d_command.letniy_intensiv;

import com.d_command.letniy_intensiv.domain.Intensive;
import com.d_command.letniy_intensiv.domain.User;
import com.d_command.letniy_intensiv.repos.IntensiveRepo;
import com.d_command.letniy_intensiv.services.IntensiveService;
import com.d_command.letniy_intensiv.services.ProjectService;
import com.d_command.letniy_intensiv.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Locale;

import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.ArgumentMatchers.contains;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class IntensiveControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private IntensiveService intensiveService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
        intensiveService.create("Test intensive", "Test description", "2019", "2020",
               userService.findByUsername("karen"));
    }

    @After
    public void clean() {
        List<Intensive> intensives = intensiveService.findByName("Test intensive");
        intensiveService.removeAll(intensives);
    }

    @Test
    @WithUserDetails("nikita")
    public void intensiveList_shouldRenderIntensiveListView() throws Exception {
        mockMvc.perform(get("/intensive"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test intensive")));
    }

    @Test
    @WithUserDetails("karen")
    public void createIntensive_shouldAddNewIntensiveAndRedirect() throws Exception {
        mockMvc.perform
                (
                    post("/intensive")
                    .param("name", "Intensive")
                    .param("description", "Description")
                    .param("date_start", "2019")
                    .param("date_end", "2020")
                    .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/intensive"));

        mockMvc.perform(get("/intensive"))
                .andExpect(status().isOk())
                .andExpect(content().string(contains("Intensive")));
    }

    @Test
    @WithUserDetails("nikita")
    public void intensiveInfo_shouldShowIntensiveInformation() throws Exception {
        Intensive intensive = intensiveService.findByName("Test intensive").get(0);
        mockMvc.perform(get("/intensive/" + intensive.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(contains("Test intensive")));
    }

}
