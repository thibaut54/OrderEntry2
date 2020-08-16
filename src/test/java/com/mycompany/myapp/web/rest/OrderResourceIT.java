package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OrderEntry2App;
import com.mycompany.myapp.domain.Order;
import com.mycompany.myapp.repository.OrderRepository;
import com.mycompany.myapp.service.OrderService;
import com.mycompany.myapp.service.dto.OrderDTO;
import com.mycompany.myapp.service.mapper.OrderMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OrderResource} REST controller.
 */
@SpringBootTest(classes = OrderEntry2App.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrderResourceIT {

    private static final String DEFAULT_ORDER_INITIATOR = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_INITIATOR = "BBBBBBBBBB";

    private static final String DEFAULT_SIDE_COMBO = "AAAAAAAAAA";
    private static final String UPDATED_SIDE_COMBO = "BBBBBBBBBB";

    private static final String DEFAULT_QUANTITY = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITY = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_GOOD_TILL = "AAAAAAAAAA";
    private static final String UPDATED_GOOD_TILL = "BBBBBBBBBB";

    private static final String DEFAULT_TRADING_INSTRUCTION = "AAAAAAAAAA";
    private static final String UPDATED_TRADING_INSTRUCTION = "BBBBBBBBBB";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderMockMvc;

    private Order order;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createEntity(EntityManager em) {
        Order order = new Order()
            .orderInitiator(DEFAULT_ORDER_INITIATOR)
            .sideCombo(DEFAULT_SIDE_COMBO)
            .quantity(DEFAULT_QUANTITY)
            .orderType(DEFAULT_ORDER_TYPE)
            .goodTill(DEFAULT_GOOD_TILL)
            .tradingInstruction(DEFAULT_TRADING_INSTRUCTION);
        return order;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createUpdatedEntity(EntityManager em) {
        Order order = new Order()
            .orderInitiator(UPDATED_ORDER_INITIATOR)
            .sideCombo(UPDATED_SIDE_COMBO)
            .quantity(UPDATED_QUANTITY)
            .orderType(UPDATED_ORDER_TYPE)
            .goodTill(UPDATED_GOOD_TILL)
            .tradingInstruction(UPDATED_TRADING_INSTRUCTION);
        return order;
    }

    @BeforeEach
    public void initTest() {
        order = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrder() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();
        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);
        restOrderMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isCreated());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate + 1);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getOrderInitiator()).isEqualTo(DEFAULT_ORDER_INITIATOR);
        assertThat(testOrder.getSideCombo()).isEqualTo(DEFAULT_SIDE_COMBO);
        assertThat(testOrder.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrder.getOrderType()).isEqualTo(DEFAULT_ORDER_TYPE);
        assertThat(testOrder.getGoodTill()).isEqualTo(DEFAULT_GOOD_TILL);
        assertThat(testOrder.getTradingInstruction()).isEqualTo(DEFAULT_TRADING_INSTRUCTION);
    }

    @Test
    @Transactional
    public void createOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // Create the Order with an existing ID
        order.setId(1L);
        OrderDTO orderDTO = orderMapper.toDto(order);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrders() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList
        restOrderMockMvc.perform(get("/api/orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(order.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderInitiator").value(hasItem(DEFAULT_ORDER_INITIATOR)))
            .andExpect(jsonPath("$.[*].sideCombo").value(hasItem(DEFAULT_SIDE_COMBO)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].orderType").value(hasItem(DEFAULT_ORDER_TYPE)))
            .andExpect(jsonPath("$.[*].goodTill").value(hasItem(DEFAULT_GOOD_TILL)))
            .andExpect(jsonPath("$.[*].tradingInstruction").value(hasItem(DEFAULT_TRADING_INSTRUCTION)));
    }
    
    @Test
    @Transactional
    public void getOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", order.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(order.getId().intValue()))
            .andExpect(jsonPath("$.orderInitiator").value(DEFAULT_ORDER_INITIATOR))
            .andExpect(jsonPath("$.sideCombo").value(DEFAULT_SIDE_COMBO))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.orderType").value(DEFAULT_ORDER_TYPE))
            .andExpect(jsonPath("$.goodTill").value(DEFAULT_GOOD_TILL))
            .andExpect(jsonPath("$.tradingInstruction").value(DEFAULT_TRADING_INSTRUCTION));
    }
    @Test
    @Transactional
    public void getNonExistingOrder() throws Exception {
        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Update the order
        Order updatedOrder = orderRepository.findById(order.getId()).get();
        // Disconnect from session so that the updates on updatedOrder are not directly saved in db
        em.detach(updatedOrder);
        updatedOrder
            .orderInitiator(UPDATED_ORDER_INITIATOR)
            .sideCombo(UPDATED_SIDE_COMBO)
            .quantity(UPDATED_QUANTITY)
            .orderType(UPDATED_ORDER_TYPE)
            .goodTill(UPDATED_GOOD_TILL)
            .tradingInstruction(UPDATED_TRADING_INSTRUCTION);
        OrderDTO orderDTO = orderMapper.toDto(updatedOrder);

        restOrderMockMvc.perform(put("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isOk());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getOrderInitiator()).isEqualTo(UPDATED_ORDER_INITIATOR);
        assertThat(testOrder.getSideCombo()).isEqualTo(UPDATED_SIDE_COMBO);
        assertThat(testOrder.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrder.getOrderType()).isEqualTo(UPDATED_ORDER_TYPE);
        assertThat(testOrder.getGoodTill()).isEqualTo(UPDATED_GOOD_TILL);
        assertThat(testOrder.getTradingInstruction()).isEqualTo(UPDATED_TRADING_INSTRUCTION);
    }

    @Test
    @Transactional
    public void updateNonExistingOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderMockMvc.perform(put("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        int databaseSizeBeforeDelete = orderRepository.findAll().size();

        // Delete the order
        restOrderMockMvc.perform(delete("/api/orders/{id}", order.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
