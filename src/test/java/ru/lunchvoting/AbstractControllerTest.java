package ru.lunchvoting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoting.common.util.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

//https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-testing-spring-boot-applications
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
//https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-testing-spring-boot-applications-testing-with-mock-environment
public abstract class AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    protected <T> ResultActions getResultActionsPut(T t, String url) throws Exception {
        return perform(MockMvcRequestBuilders.put(url)
                               .contentType(MediaType.APPLICATION_JSON)
                               .content(JsonUtil.writeValue(t)))
                .andDo(print());
    }

    protected <T> ResultActions getResultActionsPost(T t, String url) throws Exception {
        return perform(MockMvcRequestBuilders.post(url)
                               .contentType(MediaType.APPLICATION_JSON)
                               .content(JsonUtil.writeValue(t)))
                .andDo(print());
    }

    protected ResultActions getResultActionsGet(String url) throws Exception {
        return perform(MockMvcRequestBuilders.get(url)
                               .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}
