package com.bluebox.security.authenticationserver.persistence.service.base;

import com.bluebox.security.authenticationserver.common.viewModel.BaseCto;
import com.bluebox.security.authenticationserver.common.viewModel.SortField;
import com.bluebox.security.authenticationserver.persistence.entity.BaseEntity;
import com.bluebox.security.authenticationserver.persistence.repository.BaseRepository;

/**
 * @author Kamran Ghiasvand
 */
public class TestQueryService extends AbstractQueryService<TestQueryService.TestEntity, TestQueryService.TestCto, SortField, Long> {

    private BaseRepository<TestEntity, Long> repository;

    public TestQueryService(BaseRepository<TestEntity, Long> repository) {
        this.repository = repository;
    }


    @Override
    protected BaseSpec<TestEntity> getSpec(TestCto criteria) {
        return null;
    }

    @Override
    public BaseRepository<TestEntity, Long> getRepository() {
        return repository;
    }

    @Override
    public String getEntityName() {
        return "testEntity";
    }

    public static class TestEntity extends BaseEntity<Long> {
    }

    public static class TestCto extends BaseCto {
    }
}
