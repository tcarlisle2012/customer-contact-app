package com.gfs.gordonnow.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import com.gfs.gordonnow.IntegrationTest;
import com.gfs.gordonnow.domain.CustomerContact;
import com.gfs.gordonnow.domain.CustomerUnitKey;
import com.gfs.gordonnow.repository.CustomerContactRepository;
import com.gfs.gordonnow.repository.EntityManager;
import com.gfs.gordonnow.service.CustomerContactService;
import com.gfs.gordonnow.service.dto.CustomerContactDTO;
import com.gfs.gordonnow.service.mapper.CustomerContactMapper;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Integration tests for the {@link CustomerContactResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class CustomerContactResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "5-f0l@6F0.FG";
    private static final String UPDATED_EMAIL = "u02@cVMn-l.DA.7Jycq.7k.cyP0";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/customer-contacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomerContactRepository customerContactRepository;

    @Mock
    private CustomerContactRepository customerContactRepositoryMock;

    @Autowired
    private CustomerContactMapper customerContactMapper;

    @Mock
    private CustomerContactService customerContactServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private CustomerContact customerContact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerContact createEntity(EntityManager em) {
        CustomerContact customerContact = new CustomerContact()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .displayName(DEFAULT_DISPLAY_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        // Add required entity
        CustomerUnitKey customerUnitKey;
        customerUnitKey = em.insert(CustomerUnitKeyResourceIT.createEntity(em)).block();
        customerContact.setCustomerUnitKey(customerUnitKey);
        return customerContact;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerContact createUpdatedEntity(EntityManager em) {
        CustomerContact customerContact = new CustomerContact()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        // Add required entity
        CustomerUnitKey customerUnitKey;
        customerUnitKey = em.insert(CustomerUnitKeyResourceIT.createUpdatedEntity(em)).block();
        customerContact.setCustomerUnitKey(customerUnitKey);
        return customerContact;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(CustomerContact.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        CustomerUnitKeyResourceIT.deleteEntities(em);
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        customerContact = createEntity(em);
    }

    @Test
    void createCustomerContact() throws Exception {
        int databaseSizeBeforeCreate = customerContactRepository.findAll().collectList().block().size();
        // Create the CustomerContact
        CustomerContactDTO customerContactDTO = customerContactMapper.toDto(customerContact);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerContactDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the CustomerContact in the database
        List<CustomerContact> customerContactList = customerContactRepository.findAll().collectList().block();
        assertThat(customerContactList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerContact testCustomerContact = customerContactList.get(customerContactList.size() - 1);
        assertThat(testCustomerContact.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCustomerContact.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCustomerContact.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testCustomerContact.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomerContact.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    void createCustomerContactWithExistingId() throws Exception {
        // Create the CustomerContact with an existing ID
        customerContact.setId(1L);
        CustomerContactDTO customerContactDTO = customerContactMapper.toDto(customerContact);

        int databaseSizeBeforeCreate = customerContactRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerContactDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CustomerContact in the database
        List<CustomerContact> customerContactList = customerContactRepository.findAll().collectList().block();
        assertThat(customerContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerContactRepository.findAll().collectList().block().size();
        // set the field null
        customerContact.setFirstName(null);

        // Create the CustomerContact, which fails.
        CustomerContactDTO customerContactDTO = customerContactMapper.toDto(customerContact);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerContactDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<CustomerContact> customerContactList = customerContactRepository.findAll().collectList().block();
        assertThat(customerContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerContactRepository.findAll().collectList().block().size();
        // set the field null
        customerContact.setLastName(null);

        // Create the CustomerContact, which fails.
        CustomerContactDTO customerContactDTO = customerContactMapper.toDto(customerContact);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerContactDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<CustomerContact> customerContactList = customerContactRepository.findAll().collectList().block();
        assertThat(customerContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDisplayNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerContactRepository.findAll().collectList().block().size();
        // set the field null
        customerContact.setDisplayName(null);

        // Create the CustomerContact, which fails.
        CustomerContactDTO customerContactDTO = customerContactMapper.toDto(customerContact);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerContactDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<CustomerContact> customerContactList = customerContactRepository.findAll().collectList().block();
        assertThat(customerContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerContactRepository.findAll().collectList().block().size();
        // set the field null
        customerContact.setEmail(null);

        // Create the CustomerContact, which fails.
        CustomerContactDTO customerContactDTO = customerContactMapper.toDto(customerContact);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerContactDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<CustomerContact> customerContactList = customerContactRepository.findAll().collectList().block();
        assertThat(customerContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerContactRepository.findAll().collectList().block().size();
        // set the field null
        customerContact.setPhoneNumber(null);

        // Create the CustomerContact, which fails.
        CustomerContactDTO customerContactDTO = customerContactMapper.toDto(customerContact);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerContactDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<CustomerContact> customerContactList = customerContactRepository.findAll().collectList().block();
        assertThat(customerContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllCustomerContacts() {
        // Initialize the database
        customerContactRepository.save(customerContact).block();

        // Get all the customerContactList
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
            .value(hasItem(customerContact.getId().intValue()))
            .jsonPath("$.[*].firstName")
            .value(hasItem(DEFAULT_FIRST_NAME))
            .jsonPath("$.[*].lastName")
            .value(hasItem(DEFAULT_LAST_NAME))
            .jsonPath("$.[*].displayName")
            .value(hasItem(DEFAULT_DISPLAY_NAME))
            .jsonPath("$.[*].email")
            .value(hasItem(DEFAULT_EMAIL))
            .jsonPath("$.[*].phoneNumber")
            .value(hasItem(DEFAULT_PHONE_NUMBER));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCustomerContactsWithEagerRelationshipsIsEnabled() {
        when(customerContactServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(customerContactServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCustomerContactsWithEagerRelationshipsIsNotEnabled() {
        when(customerContactServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(customerContactRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getCustomerContact() {
        // Initialize the database
        customerContactRepository.save(customerContact).block();

        // Get the customerContact
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, customerContact.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(customerContact.getId().intValue()))
            .jsonPath("$.firstName")
            .value(is(DEFAULT_FIRST_NAME))
            .jsonPath("$.lastName")
            .value(is(DEFAULT_LAST_NAME))
            .jsonPath("$.displayName")
            .value(is(DEFAULT_DISPLAY_NAME))
            .jsonPath("$.email")
            .value(is(DEFAULT_EMAIL))
            .jsonPath("$.phoneNumber")
            .value(is(DEFAULT_PHONE_NUMBER));
    }

    @Test
    void getNonExistingCustomerContact() {
        // Get the customerContact
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingCustomerContact() throws Exception {
        // Initialize the database
        customerContactRepository.save(customerContact).block();

        int databaseSizeBeforeUpdate = customerContactRepository.findAll().collectList().block().size();

        // Update the customerContact
        CustomerContact updatedCustomerContact = customerContactRepository.findById(customerContact.getId()).block();
        updatedCustomerContact
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        CustomerContactDTO customerContactDTO = customerContactMapper.toDto(updatedCustomerContact);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, customerContactDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerContactDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the CustomerContact in the database
        List<CustomerContact> customerContactList = customerContactRepository.findAll().collectList().block();
        assertThat(customerContactList).hasSize(databaseSizeBeforeUpdate);
        CustomerContact testCustomerContact = customerContactList.get(customerContactList.size() - 1);
        assertThat(testCustomerContact.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCustomerContact.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCustomerContact.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testCustomerContact.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomerContact.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    void putNonExistingCustomerContact() throws Exception {
        int databaseSizeBeforeUpdate = customerContactRepository.findAll().collectList().block().size();
        customerContact.setId(count.incrementAndGet());

        // Create the CustomerContact
        CustomerContactDTO customerContactDTO = customerContactMapper.toDto(customerContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, customerContactDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerContactDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CustomerContact in the database
        List<CustomerContact> customerContactList = customerContactRepository.findAll().collectList().block();
        assertThat(customerContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCustomerContact() throws Exception {
        int databaseSizeBeforeUpdate = customerContactRepository.findAll().collectList().block().size();
        customerContact.setId(count.incrementAndGet());

        // Create the CustomerContact
        CustomerContactDTO customerContactDTO = customerContactMapper.toDto(customerContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerContactDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CustomerContact in the database
        List<CustomerContact> customerContactList = customerContactRepository.findAll().collectList().block();
        assertThat(customerContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCustomerContact() throws Exception {
        int databaseSizeBeforeUpdate = customerContactRepository.findAll().collectList().block().size();
        customerContact.setId(count.incrementAndGet());

        // Create the CustomerContact
        CustomerContactDTO customerContactDTO = customerContactMapper.toDto(customerContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerContactDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the CustomerContact in the database
        List<CustomerContact> customerContactList = customerContactRepository.findAll().collectList().block();
        assertThat(customerContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCustomerContactWithPatch() throws Exception {
        // Initialize the database
        customerContactRepository.save(customerContact).block();

        int databaseSizeBeforeUpdate = customerContactRepository.findAll().collectList().block().size();

        // Update the customerContact using partial update
        CustomerContact partialUpdatedCustomerContact = new CustomerContact();
        partialUpdatedCustomerContact.setId(customerContact.getId());

        partialUpdatedCustomerContact.email(UPDATED_EMAIL);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCustomerContact.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerContact))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the CustomerContact in the database
        List<CustomerContact> customerContactList = customerContactRepository.findAll().collectList().block();
        assertThat(customerContactList).hasSize(databaseSizeBeforeUpdate);
        CustomerContact testCustomerContact = customerContactList.get(customerContactList.size() - 1);
        assertThat(testCustomerContact.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCustomerContact.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCustomerContact.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testCustomerContact.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomerContact.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    void fullUpdateCustomerContactWithPatch() throws Exception {
        // Initialize the database
        customerContactRepository.save(customerContact).block();

        int databaseSizeBeforeUpdate = customerContactRepository.findAll().collectList().block().size();

        // Update the customerContact using partial update
        CustomerContact partialUpdatedCustomerContact = new CustomerContact();
        partialUpdatedCustomerContact.setId(customerContact.getId());

        partialUpdatedCustomerContact
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCustomerContact.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerContact))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the CustomerContact in the database
        List<CustomerContact> customerContactList = customerContactRepository.findAll().collectList().block();
        assertThat(customerContactList).hasSize(databaseSizeBeforeUpdate);
        CustomerContact testCustomerContact = customerContactList.get(customerContactList.size() - 1);
        assertThat(testCustomerContact.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCustomerContact.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCustomerContact.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testCustomerContact.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomerContact.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    void patchNonExistingCustomerContact() throws Exception {
        int databaseSizeBeforeUpdate = customerContactRepository.findAll().collectList().block().size();
        customerContact.setId(count.incrementAndGet());

        // Create the CustomerContact
        CustomerContactDTO customerContactDTO = customerContactMapper.toDto(customerContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, customerContactDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerContactDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CustomerContact in the database
        List<CustomerContact> customerContactList = customerContactRepository.findAll().collectList().block();
        assertThat(customerContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCustomerContact() throws Exception {
        int databaseSizeBeforeUpdate = customerContactRepository.findAll().collectList().block().size();
        customerContact.setId(count.incrementAndGet());

        // Create the CustomerContact
        CustomerContactDTO customerContactDTO = customerContactMapper.toDto(customerContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerContactDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CustomerContact in the database
        List<CustomerContact> customerContactList = customerContactRepository.findAll().collectList().block();
        assertThat(customerContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCustomerContact() throws Exception {
        int databaseSizeBeforeUpdate = customerContactRepository.findAll().collectList().block().size();
        customerContact.setId(count.incrementAndGet());

        // Create the CustomerContact
        CustomerContactDTO customerContactDTO = customerContactMapper.toDto(customerContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(customerContactDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the CustomerContact in the database
        List<CustomerContact> customerContactList = customerContactRepository.findAll().collectList().block();
        assertThat(customerContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCustomerContact() {
        // Initialize the database
        customerContactRepository.save(customerContact).block();

        int databaseSizeBeforeDelete = customerContactRepository.findAll().collectList().block().size();

        // Delete the customerContact
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, customerContact.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<CustomerContact> customerContactList = customerContactRepository.findAll().collectList().block();
        assertThat(customerContactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
