package com.bluebox.security.authenticationserver.persistence.service.base.queryservice;

import com.bluebox.security.authenticationserver.common.viewModel.SortField;
import com.bluebox.security.authenticationserver.common.viewModel.SortablePageCto;
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
    private final TestQueryService.TestEntity firstEntity;
    private final TestQueryService.TestEntity secondEntity;

    public SearchTest() {
        firstEntity = new TestQueryService.TestEntity();
        firstEntity.setId(1L);
        entities.add(firstEntity);
        secondEntity = new TestQueryService.TestEntity();
        secondEntity.setId(2L);
        entities.add(secondEntity);
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

//    @Test
//    public void filterFirstEntity_shouldReturnFirstEntity() {
//        var filter = new SortablePageCto<TestQueryService.TestCto, SortField>();
//        var cto = new TestQueryService.TestCto();
//        filter.setCto();
//        service.search()
//    }

}
