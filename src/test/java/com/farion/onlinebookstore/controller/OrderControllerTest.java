package com.farion.onlinebookstore.controller;

import static com.farion.onlinebookstore.util.TestObjectMother.ITEM_ENDPOINT;
import static com.farion.onlinebookstore.util.TestObjectMother.ORDER_ENDPOINT;
import static com.farion.onlinebookstore.util.TestObjectMother.SLASH;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.farion.onlinebookstore.dto.item.orderitem.OrderItemDto;
import com.farion.onlinebookstore.dto.order.OrderDto;
import com.farion.onlinebookstore.entity.Order;
import com.farion.onlinebookstore.entity.User;
import com.farion.onlinebookstore.util.EqualityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {
    private static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext context) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Sql(scripts = {
            "classpath:db/users/add-test-user.sql",
            "classpath:db/orders/add-test-order.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:db/users/remove-test-user.sql",
            "classpath:db/orders/remove-test-order.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("Get order history of authorized user")
    void getOrderHistory_authorizedUser_Success() throws Exception {
        Long expectedId = 1L;
        BigDecimal expectedTotal = BigDecimal.valueOf(100);
        Order.Status expectedStatus = Order.Status.PENDING;
        LocalDateTime expectedOrderDate =
                LocalDateTime.of(2012, 12, 12, 0, 0, 0);

        OrderDto order = new OrderDto();
        order.setId(expectedId);
        order.setTotal(expectedTotal);
        order.setStatus(expectedStatus);
        order.setOrderDate(expectedOrderDate);
        order.setOrderItems(Collections.emptySet());
        order.setUserId(expectedId);
        List<OrderDto> expected = List.of(order);

        addUserToSecurityContextHolder(expectedId);

        MvcResult result = mockMvc.perform(get(ORDER_ENDPOINT))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        List<OrderDto> actual = objectMapper.readValue(
                result
                        .getResponse()
                        .getContentAsString(),
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, OrderDto.class));

        EqualityUtil.testCollectionEquality(expected, actual, actual.size());
    }

    @Sql(scripts = {
            "classpath:db/users/add-test-user.sql",
            "classpath:db/orders/add-test-order.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:db/users/remove-test-user.sql",
            "classpath:db/orders/remove-test-order.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("Get order items from order of authorized user")
    void getOrderItems_authorizedUser_Success() throws Exception {
        Long expectedId = 1L;
        int expectedQuantity = 10;

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(expectedId);
        orderItemDto.setQuantity(expectedQuantity);
        orderItemDto.setBookId(expectedId);
        addUserToSecurityContextHolder(expectedId);

        BigDecimal expectedTotal = BigDecimal.valueOf(100);
        Order.Status expectedStatus = Order.Status.PENDING;
        LocalDateTime expectedOrderDate =
                LocalDateTime.of(2012, 12, 12, 0, 0, 0);

        OrderDto order = new OrderDto();
        order.setId(expectedId);
        order.setUserId(expectedId);
        order.setStatus(expectedStatus);
        order.setTotal(expectedTotal);
        order.setOrderDate(expectedOrderDate);
        List<OrderItemDto> expectedItems = List.of(orderItemDto);

        MvcResult result = mockMvc.perform(get(
                ORDER_ENDPOINT + SLASH + expectedId + ITEM_ENDPOINT
                ))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        List<OrderItemDto> actual = objectMapper.readValue(
                result
                        .getResponse()
                        .getContentAsString(),
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, OrderItemDto.class));

        EqualityUtil.testCollectionEquality(expectedItems, actual, actual.size());
    }

    @Sql(scripts = {
            "classpath:db/users/add-test-user.sql",
            "classpath:db/orders/add-test-order.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:db/users/remove-test-user.sql",
            "classpath:db/orders/remove-test-order.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("Get specific order item from order of authorized user")
    void getSpecificOrderItem_authorizedUser_Success() throws Exception {
        Long expectedId = 1L;
        int expectedQuantity = 10;

        OrderItemDto expectedItem = new OrderItemDto();
        expectedItem.setId(expectedId);
        expectedItem.setQuantity(expectedQuantity);
        expectedItem.setBookId(expectedId);
        addUserToSecurityContextHolder(expectedId);

        BigDecimal expectedTotal = BigDecimal.valueOf(100);
        Order.Status expectedStatus = Order.Status.PENDING;
        LocalDateTime expectedOrderDate =
                LocalDateTime.of(2012, 12, 12, 0, 0, 0);

        OrderDto order = new OrderDto();
        order.setId(expectedId);
        order.setUserId(expectedId);
        order.setStatus(expectedStatus);
        order.setTotal(expectedTotal);
        order.setOrderDate(expectedOrderDate);

        MvcResult result = mockMvc.perform(get(
                ORDER_ENDPOINT + SLASH + expectedId + ITEM_ENDPOINT + SLASH + expectedId
                ))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        OrderItemDto actual = objectMapper.readValue(
                result
                        .getResponse()
                        .getContentAsString(),
                OrderItemDto.class);

        EqualsBuilder.reflectionEquals(expectedItem, actual);
    }

    private void addUserToSecurityContextHolder(Long id) {
        String email = "test@example.com";
        String role = "ROLE_USER";
        User user = new User();
        user.setId(id);
        user.setEmail(email);

        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(user, null,
                        Collections.singleton(authority));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
