package com.gfs.gordonnow.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.gfs.gordonnow.IntegrationTest;
import com.gfs.gordonnow.domain.CustomerUnitKey;
import com.gfs.gordonnow.repository.CustomerUnitKeyRepository;
import com.gfs.gordonnow.repository.EntityManager;
import com.gfs.gordonnow.service.dto.CustomerUnitKeyDTO;
import com.gfs.gordonnow.service.mapper.CustomerUnitKeyMapper;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link CustomerUnitKeyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class CustomerUnitKeyResourceIT {

    private static final String DEFAULT_CUSTOMER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SALES_ORGANIZATION = "AAAAAAAAAA";
    private static final String UPDATED_SALES_ORGANIZATION = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRIBUTION_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_DISTRIBUTION_CHANNEL = "BBBBBBBBBB";

    private static final String DEFAULT_DIVISION = "AAAAAAAAAA";
    private static final String UPDATED_DIVISION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/customer-unit-keys";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomerUnitKeyRepository customerUnitKeyRepository;

    @Autowired
    private CustomerUnitKeyMapper customerUnitKeyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private CustomerUnitKey customerUnitKey;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerUnitKey createEntity(EntityManager em) {
        CustomerUnitKey customerUnitKey = new CustomerUnitKey()
            .customerNumber(DEFAULT_CUSTOMER_NUMBER)
            .salesOrganization(DEFAULT_SALES_ORGANIZATION)
            .distributionChannel(DEFAULT_DISTRIBUTION_CHANNEL)
            .division(DEFAULT_DIVISION);
        return customerUnitKey;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerUnitKey createUpdatedEntity(EntityManager em) {
        CustomerUnitKey customerUnitKey = new CustomerUnitKey()
            .customerNumber(UPDATED_CUSTOMER_NUMBER)
            .salesOrganization(UPDATED_SALES_ORGANIZATION)
            .distributionChannel(UPDATED_DISTRIBUTION_CHANNEL)
            .division(UPDATED_DIVISION);
        return customerUnitKey;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(CustomerUnitKey.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        customerUnitKey = createEntity(em);
    }

    @Test
    void createCustomerUnitKey() throws Exception {
        int databaseSizeBeforeCreate = customerUnitKeyRepository.findAll().collectList().block().size();
        // Create the CustomerUnitKey
        CustomerUnitKeyDTO customerUnitKeyDTO = customerUnitKeyMapper.toDto(customerUnitKey);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerUnitKeyDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the CustomerUnitKey in the database
        List<CustomerUnitKey> customerUnitKeyList = customerUnitKeyRepository.findAll().collectList().block();
        assertThat(customerUnitKeyList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerUnitKey testCustomerUnitKey = customerUnitKeyList.get(customerUnitKeyList.size() - 1);
        assertThat(testCustomerUnitKey.getCustomerNumber()).isEqualTo(DEFAULT_CUSTOMER_NUMBER);
        assertThat(testCustomerUnitKey.getSalesOrganization()).isEqualTo(DEFAULT_SALES_ORGANIZATION);
        assertThat(testCustomerUnitKey.getDistributionChannel()).isEqualTo(DEFAULT_DISTRIBUTION_CHANNEL);
        assertThat(testCustomerUnitKey.getDivision()).isEqualTo(DEFAULT_DIVISION);
    }

    @Test
    void createCustomerUnitKeyWithExistingId() throws Exception {
        // Create the CustomerUnitKey with an existing ID
        customerUnitKey.setId(1L);
        CustomerUnitKeyDTO customerUnitKeyDTO = customerUnitKeyMapper.toDto(customerUnitKey);

        int databaseSizeBeforeCreate = customerUnitKeyRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerUnitKeyDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CustomerUnitKey in the database
        List<CustomerUnitKey> customerUnitKeyList = customerUnitKeyRepository.findAll().collectList().block();
        assertThat(customerUnitKeyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkCustomerNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerUnitKeyRepository.findAll().collectList().block().size();
        // set the field null
        customerUnitKey.setCustomerNumber(null);

        // Create the CustomerUnitKey, which fails.
        CustomerUnitKeyDTO customerUnitKeyDTO = customerUnitKeyMapper.toDto(customerUnitKey);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerUnitKeyDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<CustomerUnitKey> customerUnitKeyList = customerUnitKeyRepository.findAll().collectList().block();
        assertThat(customerUnitKeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkSalesOrganizationIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerUnitKeyRepository.findAll().collectList().block().size();
        // set the field null
        customerUnitKey.setSalesOrganization(null);

        // Create the CustomerUnitKey, which fails.
        CustomerUnitKeyDTO customerUnitKeyDTO = customerUnitKeyMapper.toDto(customerUnitKey);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerUnitKeyDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<CustomerUnitKey> customerUnitKeyList = customerUnitKeyRepository.findAll().collectList().block();
        assertThat(customerUnitKeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDistributionChannelIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerUnitKeyRepository.findAll().collectList().block().size();
        // set the field null
        customerUnitKey.setDistributionChannel(null);

        // Create the CustomerUnitKey, which fails.
        CustomerUnitKeyDTO customerUnitKeyDTO = customerUnitKeyMapper.toDto(customerUnitKey);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerUnitKeyDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<CustomerUnitKey> customerUnitKeyList = customerUnitKeyRepository.findAll().collectList().block();
        assertThat(customerUnitKeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDivisionIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerUnitKeyRepository.findAll().collectList().block().size();
        // set the field null
        customerUnitKey.setDivision(null);

        // Create the CustomerUnitKey, which fails.
        CustomerUnitKeyDTO customerUnitKeyDTO = customerUnitKeyMapper.toDto(customerUnitKey);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerUnitKeyDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<CustomerUnitKey> customerUnitKeyList = customerUnitKeyRepository.findAll().collectList().block();
        assertThat(customerUnitKeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllCustomerUnitKeys() {
        // Initialize the database
        customerUnitKeyRepository.save(customerUnitKey).block();

        // Get all the customerUnitKeyList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(customerUnitKey.getId().intValue()))
            .jsonPath("$.[*].customerNumber")
            .value(hasItem(DEFAULT_CUSTOMER_NUMBER))
            .jsonPath("$.[*].salesOrganization")
            .value(hasItem(DEFAULT_SALES_ORGANIZATION))
            .jsonPath("$.[*].distributionChannel")
            .value(hasItem(DEFAULT_DISTRIBUTION_CHANNEL))
            .jsonPath("$.[*].division")
            .value(hasItem(DEFAULT_DIVISION));
    }

    @Test
    void getCustomerUnitKey() {
        // Initialize the database
        customerUnitKeyRepository.save(customerUnitKey).block();

        // Get the customerUnitKey
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, customerUnitKey.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(customerUnitKey.getId().intValue()))
            .jsonPath("$.customerNumber")
            .value(is(DEFAULT_CUSTOMER_NUMBER))
            .jsonPath("$.salesOrganization")
            .value(is(DEFAULT_SALES_ORGANIZATION))
            .jsonPath("$.distributionChannel")
            .value(is(DEFAULT_DISTRIBUTION_CHANNEL))
            .jsonPath("$.division")
            .value(is(DEFAULT_DIVISION));
    }

    @Test
    void getNonExistingCustomerUnitKey() {
        // Get the customerUnitKey
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingCustomerUnitKey() throws Exception {
        // Initialize the database
        customerUnitKeyRepository.save(customerUnitKey).block();

        int databaseSizeBeforeUpdate = customerUnitKeyRepository.findAll().collectList().block().size();

        // Update the customerUnitKey
        CustomerUnitKey updatedCustomerUnitKey = customerUnitKeyRepository.findById(customerUnitKey.getId()).block();
        updatedCustomerUnitKey
            .customerNumber(UPDATED_CUSTOMER_NUMBER)
            .salesOrganization(UPDATED_SALES_ORGANIZATION)
            .distributionChannel(UPDATED_DISTRIBUTION_CHANNEL)
            .division(UPDATED_DIVISION);
        CustomerUnitKeyDTO customerUnitKeyDTO = customerUnitKeyMapper.toDto(updatedCustomerUnitKey);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, customerUnitKeyDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerUnitKeyDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the CustomerUnitKey in the database
        List<CustomerUnitKey> customerUnitKeyList = customerUnitKeyRepository.findAll().collectList().block();
        assertThat(customerUnitKeyList).hasSize(databaseSizeBeforeUpdate);
        CustomerUnitKey testCustomerUnitKey = customerUnitKeyList.get(customerUnitKeyList.size() - 1);
        assertThat(testCustomerUnitKey.getCustomerNumber()).isEqualTo(UPDATED_CUSTOMER_NUMBER);
        assertThat(testCustomerUnitKey.getSalesOrganization()).isEqualTo(UPDATED_SALES_ORGANIZATION);
        assertThat(testCustomerUnitKey.getDistributionChannel()).isEqualTo(UPDATED_DISTRIBUTION_CHANNEL);
        assertThat(testCustomerUnitKey.getDivision()).isEqualTo(UPDATED_DIVISION);
    }

    @Test
    void putNonExistingCustomerUnitKey() throws Exception {
        int databaseSizeBeforeUpdate = customerUnitKeyRepository.findAll().collectList().block().size();
        customerUnitKey.setId(count.incrementAndGet());

        // Create the CustomerUnitKey
        CustomerUnitKeyDTO customerUnitKeyDTO = customerUnitKeyMapper.toDto(customerUnitKey);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, customerUnitKeyDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerUnitKeyDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CustomerUnitKey in the database
        List<CustomerUnitKey> customerUnitKeyList = customerUnitKeyRepository.findAll().collectList().block();
        assertThat(customerUnitKeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCustomerUnitKey() throws Exception {
        int databaseSizeBeforeUpdate = customerUnitKeyRepository.findAll().collectList().block().size();
        customerUnitKey.setId(count.incrementAndGet());

        // Create the CustomerUnitKey
        CustomerUnitKeyDTO customerUnitKeyDTO = customerUnitKeyMapper.toDto(customerUnitKey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerUnitKeyDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CustomerUnitKey in the database
        List<CustomerUnitKey> customerUnitKeyList = customerUnitKeyRepository.findAll().collectList().block();
        assertThat(customerUnitKeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCustomerUnitKey() throws Exception {
        int databaseSizeBeforeUpdate = customerUnitKeyRepository.findAll().collectList().block().size();
        customerUnitKey.setId(count.incrementAndGet());

        // Create the CustomerUnitKey
        CustomerUnitKeyDTO customerUnitKeyDTO = customerUnitKeyMapper.toDto(customerUnitKey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerUnitKeyDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the CustomerUnitKey in the database
        List<CustomerUnitKey> customerUnitKeyList = customerUnitKeyRepository.findAll().collectList().block();
        assertThat(customerUnitKeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCustomerUnitKeyWithPatch() throws Exception {
        // Initialize the database
        customerUnitKeyRepository.save(customerUnitKey).block();

        int databaseSizeBeforeUpdate = customerUnitKeyRepository.findAll().collectList().block().size();

        // Update the customerUnitKey using partial update
        CustomerUnitKey partialUpdatedCustomerUnitKey = new CustomerUnitKey();
        partialUpdatedCustomerUnitKey.setId(customerUnitKey.getId());

        partialUpdatedCustomerUnitKey.division(UPDATED_DIVISION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCustomerUnitKey.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerUnitKey))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the CustomerUnitKey in the database
        List<CustomerUnitKey> customerUnitKeyList = customerUnitKeyRepository.findAll().collectList().block();
        assertThat(customerUnitKeyList).hasSize(databaseSizeBeforeUpdate);
        CustomerUnitKey testCustomerUnitKey = customerUnitKeyList.get(customerUnitKeyList.size() - 1);
        assertThat(testCustomerUnitKey.getCustomerNumber()).isEqualTo(DEFAULT_CUSTOMER_NUMBER);
        assertThat(testCustomerUnitKey.getSalesOrganization()).isEqualTo(DEFAULT_SALES_ORGANIZATION);
        assertThat(testCustomerUnitKey.getDistributionChannel()).isEqualTo(DEFAULT_DISTRIBUTION_CHANNEL);
        assertThat(testCustomerUnitKey.getDivision()).isEqualTo(UPDATED_DIVISION);
    }

    @Test
    void fullUpdateCustomerUnitKeyWithPatch() throws Exception {
        // Initialize the database
        customerUnitKeyRepository.save(customerUnitKey).block();

        int databaseSizeBeforeUpdate = customerUnitKeyRepository.findAll().collectList().block().size();

        // Update the customerUnitKey using partial update
        CustomerUnitKey partialUpdatedCustomerUnitKey = new CustomerUnitKey();
        partialUpdatedCustomerUnitKey.setId(customerUnitKey.getId());

        partialUpdatedCustomerUnitKey
            .customerNumber(UPDATED_CUSTOMER_NUMBER)
            .salesOrganization(UPDATED_SALES_ORGANIZATION)
            .distributionChannel(UPDATED_DISTRIBUTION_CHANNEL)
            .division(UPDATED_DIVISION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCustomerUnitKey.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerUnitKey))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the CustomerUnitKey in the database
        List<CustomerUnitKey> customerUnitKeyList = customerUnitKeyRepository.findAll().collectList().block();
        assertThat(customerUnitKeyList).hasSize(databaseSizeBeforeUpdate);
        CustomerUnitKey testCustomerUnitKey = customerUnitKeyList.get(customerUnitKeyList.size() - 1);
        assertThat(testCustomerUnitKey.getCustomerNumber()).isEqualTo(UPDATED_CUSTOMER_NUMBER);
        assertThat(testCustomerUnitKey.getSalesOrganization()).isEqualTo(UPDATED_SALES_ORGANIZATION);
        assertThat(testCustomerUnitKey.getDistributionChannel()).isEqualTo(UPDATED_DISTRIBUTION_CHANNEL);
        assertThat(testCustomerUnitKey.getDivision()).isEqualTo(UPDATED_DIVISION);
    }

    @Test
    void patchNonExistingCustomerUnitKey() throws Exception {
        int databaseSizeBeforeUpdate = customerUnitKeyRepository.findAll().collectList().block().size();
        customerUnitKey.setId(count.incrementAndGet());

        // Create the CustomerUnitKey
        CustomerUnitKeyDTO customerUnitKeyDTO = customerUnitKeyMapper.toDto(customerUnitKey);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, customerUnitKeyDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerUnitKeyDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CustomerUnitKey in the database
        List<CustomerUnitKey> customerUnitKeyList = customerUnitKeyRepository.findAll().collectList().block();
        assertThat(customerUnitKeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCustomerUnitKey() throws Exception {
        int databaseSizeBeforeUpdate = customerUnitKeyRepository.findAll().collectList().block().size();
        customerUnitKey.setId(count.incrementAndGet());

        // Create the CustomerUnitKey
        CustomerUnitKeyDTO customerUnitKeyDTO = customerUnitKeyMapper.toDto(customerUnitKey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerUnitKeyDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CustomerUnitKey in the database
        List<CustomerUnitKey> customerUnitKeyList = customerUnitKeyRepository.findAll().collectList().block();
        assertThat(customerUnitKeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCustomerUnitKey() throws Exception {
        int databaseSizeBeforeUpdate = customerUnitKeyRepository.findAll().collectList().block().size();
        customerUnitKey.setId(count.incrementAndGet());

        // Create the CustomerUnitKey
        CustomerUnitKeyDTO customerUnitKeyDTO = customerUnitKeyMapper.toDto(customerUnitKey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerUnitKeyDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the CustomerUnitKey in the database
        List<CustomerUnitKey> customerUnitKeyList = customerUnitKeyRepository.findAll().collectList().block();
        assertThat(customerUnitKeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCustomerUnitKey() {
        // Initialize the database
        customerUnitKeyRepository.save(customerUnitKey).block();

        int databaseSizeBeforeDelete = customerUnitKeyRepository.findAll().collectList().block().size();

        // Delete the customerUnitKey
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, customerUnitKey.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<CustomerUnitKey> customerUnitKeyList = customerUnitKeyRepository.findAll().collectList().block();
        assertThat(customerUnitKeyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
