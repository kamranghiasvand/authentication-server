package com.bluebox.security.authenticationserver.persistence.service.base.queryservice;

import com.bluebox.security.authenticationserver.common.exception.GlobalException;
import com.bluebox.security.authenticationserver.common.exception.ResourceNotFoundException;
import com.bluebox.security.authenticationserver.persistence.repository.BaseRepository;
import com.bluebox.security.authenticationserver.persistence.service.base.TestQueryService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * @author Kamran Ghiasvand
 */
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FetchTest {


    private TestQueryService service;
    BaseRepository<TestQueryService.TestEntity, Long> repository;

    @BeforeEach
    void beforeEach() {
        //noinspection unchecked
        repository = (BaseRepository<TestQueryService.TestEntity, Long>) Mockito.mock(BaseRepository.class);
        when(repository.findById(anyLong())).then((Answer<Object>) invocationOnMock -> {
            Long id = invocationOnMock.getArgument(0);
            if (id <= 0)
                return Optional.empty();
            final TestQueryService.TestEntity entity = new TestQueryService.TestEntity();
            entity.setId(id);
            return Optional.of(entity);
        });
        service = new TestQueryService(repository);
    }

    @Test
    @Order(1)
    public void passNullId_ShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, () -> service.fetch(null));
    }

    @Test
    @Order(2)
    public void passNegativeId_ShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, () -> service.fetch(-1L));
    }

    @Test
    @Order(3)
    public void passZeroId_ShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, () -> service.fetch(0L));
    }

    @Test
    @Order(4)
    public void passIdOne_ShouldFindTheEntity() throws GlobalException {
        final var entity = service.fetch(1L);
        assertNotNull(entity);
        assertEquals(1L, entity.getId());

    }
}
