package com.bluebox.security.authenticationserver.persistence.service.base.queryservice;

import com.bluebox.security.authenticationserver.persistence.repository.BaseRepository;
import com.bluebox.security.authenticationserver.persistence.service.base.TestQueryService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * @author Kamran Ghiasvand
 */
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SearchTest {


    private TestQueryService service;
    private BaseRepository<TestQueryService.TestEntity, Long> repository;
    private Collection<TestQueryService.TestEntity> entities = new ArrayList<>();

    public SearchTest() {
        var entity = new TestQueryService.TestEntity();
        entity.setId(1L);
        entities.add(entity);
        entity = new TestQueryService.TestEntity();
        entity.setId(2L);
        entities.add(entity);
    }


    @BeforeEach
    void beforeEach() {
        //noinspection unchecked
        repository = (BaseRepository<TestQueryService.TestEntity, Long>) Mockito.mock(BaseRepository.class);
        mockFetch();
        mockFindAll();
        service = new TestQueryService(repository);
    }


    private void mockFetch() {
        when(repository.findById(anyLong())).then(invocationOnMock -> {
            Long id = invocationOnMock.getArgument(0);
            if (id <= 0)
                return Optional.empty();
            final TestQueryService.TestEntity entity = new TestQueryService.TestEntity();
            entity.setId(id);
            return Optional.of(entity);
        });
    }

    private void mockFindAll() {
        when(repository.findAll(any(Sort.class))).thenAnswer(invocationOnMock -> entities);
    }

    @Test
    public void withoutInput_shouldReturnEntireList() {
        final var result = service.search(null);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getResults());
        Assertions.assertEquals(entities.size(), result.getResults().size());
        Assertions.assertEquals(entities, result.getResults());
    }

}
